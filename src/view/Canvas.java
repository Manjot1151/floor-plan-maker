package src.view;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Canvas extends JLayeredPane {
    private ShapesPanel shapesPanel;
    private Grid grid;

    public Canvas() {
        super();

        shapesPanel = new ShapesPanel();
        shapesPanel.setLayout(null);
        shapesPanel.setOpaque(false);

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
                    Drawable shape = Toolbar.selectedTool.onMouseReleased(e);
                    Canvas.this.shapesPanel.addShape(shape);
                }
            }
        });

        grid = new Grid(20);

        add(grid);
        setLayer(grid, 0);
        add(shapesPanel);
        setLayer(shapesPanel, 1);
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

class ShapesPanel extends JPanel {
    private ArrayList<Drawable> shapesList;

    public ShapesPanel() {
        super();
        setLayout(null);
        shapesList = new ArrayList<Drawable>();
    }

    public void addShape(Drawable shape) {
        shapesList.add(shape);
        shape.setVisible(true);
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (Drawable shape : shapesList) {
            shape.paintShape(g2d);
        }
    }
}