package src.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class ShapesPanel extends JPanel {
    private static ArrayList<Drawable> shapesList;

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

    public void removeShape(Drawable shape) {
        shapesList.remove(shape);
        shape.setVisible(false);
        repaint();
    }

    public boolean isIntersecting(Drawable shape) {
        for (Drawable s : shapesList) {
            if (s == shape) {
                continue;
            }

            if (s.intersects(shape)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (Drawable shape : shapesList) {
            shape.paintShape(g2d);
        }
    }
}