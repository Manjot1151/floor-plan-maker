package src.view;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import src.snap.SnapIndicator;

public class Canvas extends JLayeredPane {
    public static ShapesPanel shapesPanel;
    public static SnapIndicator snapIndicator;
    
    private static Grid grid;

    public Canvas() {
        super();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (Toolbar.selectedTool != null) {
                    Toolbar.selectedTool.onMousePressed(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (Toolbar.selectedTool != null) {
                    Toolbar.selectedTool.onMouseReleased(e);
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (Toolbar.selectedTool != null) {
                    Toolbar.selectedTool.onMouseDragged(e);
                }

                snapIndicator.update(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                snapIndicator.update(e);
            }
        });


        shapesPanel = new ShapesPanel();
        shapesPanel.setLayout(null);
        shapesPanel.setOpaque(false);

        int gridSize = 20;

        grid = new Grid(gridSize);

        JPanel popupsPanel = new JPanel();
        snapIndicator = new SnapIndicator(gridSize);
        popupsPanel.add(snapIndicator);

        add(grid);
        setLayer(grid, 0);
        add(shapesPanel);
        setLayer(shapesPanel, 1);
        add(snapIndicator);
        setLayer(popupsPanel, 2);
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