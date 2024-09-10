package src.view;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import src.snap.SnapIndicator;

public class Canvas extends JLayeredPane {
    public ShapesPanel shapesPanel;
    public SnapIndicator snapIndicator;
    private static Grid grid;


    public SnapIndicator getSnapIndicator() {
        return snapIndicator;
    }

    public ShapesPanel getShapesPanel() {
        return shapesPanel;
    }

    public Canvas() {
        super();

        int gridSize = Config.getInstance().getGridSize();
        grid = new Grid(gridSize);

        this.shapesPanel = new ShapesPanel(); 
        this.snapIndicator = new SnapIndicator();

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

                snapIndicator.update(e.getPoint());
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                snapIndicator.update(e.getPoint());
            }
        });


        shapesPanel = new ShapesPanel();
        shapesPanel.setLayout(null);
        shapesPanel.setOpaque(false);

        JPanel popupsPanel = new JPanel();
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