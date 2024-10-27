package org.lays.view.panels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.lays.view.Drawable;

public class SpritesPanel extends JPanel {
    private static ArrayList<Drawable> sprites;

    public SpritesPanel() {
        super();
        setLayout(null);
        sprites = new ArrayList<Drawable>();
    }

    public void addSprite(Drawable sprite) {
        sprites.add(sprite);
        sprite.setVisible(true);

        repaint();
    }

    public void removeSprite(Drawable sprite) {
        sprites.remove(sprite);
        sprite.setVisible(false);

        repaint();
    }

    // public boolean isIntersecting(Drawable shape) {
    //     for (Drawable s : sprites) {
    //         if (s == shape) {
    //             continue;
    //         }

    //         if (s.intersects(shape)) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    // public Drawable getClickedShape(Point p) {
    //     for (Drawable s : sprites) {
    //         if (s.getBounds().contains(p)) {
    //             return s;
    //         }
    //     }
    //     return null;
    // }

    public List<Drawable> getSprites() {
        return sprites;
    }

    // public List<Drawable> getSelectedShapes() {
    //     List<Drawable> selectedShapes = new ArrayList<Drawable>();
    //     for (Drawable s : sprites) {
    //         if (s.isSelected()) {
    //             selectedShapes.add(s);
    //         }
    //     }
    //     return selectedShapes;
    // }

    @Override
    public void paintComponent(Graphics g) {
        for (Drawable s : sprites) {
            Graphics2D g2d = (Graphics2D)g.create();
            s.paintShape(g2d);
            g2d.dispose();
        }
    }
}