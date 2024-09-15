package src.view.panels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import src.view.Drawable;

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

    public Drawable getClickedShape(Point p) {
        for (Drawable s : shapesList) {
            if (s.getBounds().contains(p)) {
                return s;
            }
        }
        return null;
    }

    public List<Drawable> getShapes() {
        return shapesList;
    }

    public List<Drawable> getSelectedShapes() {
        List<Drawable> selectedShapes = new ArrayList<Drawable>();
        for (Drawable s : shapesList) {
            if (s.isSelected()) {
                selectedShapes.add(s);
            }
        }
        return selectedShapes;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (Drawable s : shapesList) {
            s.paintShape(g2d);
        }
    }
}