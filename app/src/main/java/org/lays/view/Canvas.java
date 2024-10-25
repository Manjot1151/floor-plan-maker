package org.lays.view;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.lays.snap.SnapIndicator;
import org.lays.view.panels.ButtonsPanel;
import org.lays.view.panels.ShapesPanel;

public class Canvas extends JLayeredPane {
    public ShapesPanel shapesPanel;
    public SnapIndicator snapIndicator;
    private static Grid grid;
    private static Canvas INSTANCE;

    public static Canvas getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Canvas();
        }
        return INSTANCE;
    }

    public SnapIndicator getSnapIndicator() {
        return snapIndicator;
    }

    public ShapesPanel getShapesPanel() {
        return shapesPanel;
    }

    private Canvas() {
        super();

        int gridSize = Config.getInstance().getGridSize();
        grid = new Grid(gridSize);

        this.shapesPanel = new ShapesPanel();
        this.snapIndicator = new SnapIndicator();

        JPanel glassPane = new ShapesPanel();
        glassPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Drawable shape = shapesPanel.getClickedShape(e.getPoint());
                
                if (shape instanceof Room && SwingUtilities.isRightMouseButton(e)) {
                    RoomPopup roomPopup = new RoomPopup((Room)shape);
                    roomPopup.show(Canvas.this, e.getX(), e.getY());
                }

                if (ButtonsPanel.selectedTool != null) {
                    ButtonsPanel.selectedTool.onMouseClicked(e);
                }
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                if (ButtonsPanel.selectedTool != null) {
                    ButtonsPanel.selectedTool.onMousePressed(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (ButtonsPanel.selectedTool != null) {
                    ButtonsPanel.selectedTool.onMouseReleased(e);
                }
            }
        });

        glassPane.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (ButtonsPanel.selectedTool != null) {
                    ButtonsPanel.selectedTool.onMouseDragged(e);
                }
                
                snapIndicator.update(e.getPoint());
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                snapIndicator.update(e.getPoint());
            }
        });

        glassPane.setOpaque(false);
        glassPane.setVisible(true);

        shapesPanel = new ShapesPanel();
        shapesPanel.setLayout(null);
        shapesPanel.setOpaque(false);

        JPanel spritesPanel = new JPanel();
        spritesPanel.add(snapIndicator);
        spritesPanel.setOpaque(false);

        JPanel popupsPanel = new JPanel();
        popupsPanel.setOpaque(false);

        add(grid);
        setLayer(grid, 0);
        add(shapesPanel);
        setLayer(shapesPanel, 1);
        add(spritesPanel);
        setLayer(spritesPanel, 2);
        add(glassPane);
        setLayer(glassPane, 3);
        add(popupsPanel);
        setLayer(popupsPanel, 4);
        setVisible(true);
    }


    @Override
    public void doLayout() {
        synchronized (getTreeLock()) {
            int w = getWidth();
            int h = getHeight();

            for (Component c : getComponents()) {
                c.setBounds(0, 0, w, h);
            }
        }
    }
}