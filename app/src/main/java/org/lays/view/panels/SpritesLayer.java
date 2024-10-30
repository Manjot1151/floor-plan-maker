package org.lays.view.panels;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.lays.view.Drawable;

public class SpritesLayer {
    private ArrayList<Drawable> sprites;
    private JPanel view;
    
    public SpritesLayer(JPanel view) {
        super();
        sprites = new ArrayList<Drawable>();
        this.view = view;
    }

    public JPanel getView() {
        return view;
    }

    public void add(Drawable sprite) {
        sprites.add(sprite);
        sprite.setVisible(false);

        view.repaint();
    }

    public void remove(Drawable sprite) {
        sprites.remove(sprite);
        sprite.setVisible(false);

        view.repaint();
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

    public ArrayList<Drawable> getSprites() {
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

    public void paintLayer(Graphics g) {
        for (Drawable s: sprites) {
            s.paintShape(g);
        }
    }
}