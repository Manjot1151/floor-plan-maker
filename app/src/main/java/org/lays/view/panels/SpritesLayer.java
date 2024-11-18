package org.lays.view.panels;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.lays.view.Drawable;
import org.lays.view.Sprite;

public class SpritesLayer {
    private ArrayList<Sprite> sprites;
    private JPanel view;
    
    public SpritesLayer(JPanel view) {
        super();
        sprites = new ArrayList<Sprite>();
        this.view = view;
    }

    public JPanel getView() {
        return view;
    }

    public void add(Sprite sprite) {
        sprites.add(sprite);

        view.repaint();
    }

    public void remove(Drawable sprite) {
        sprites.remove(sprite);

        view.repaint();
    }

    public boolean validateSpriteIntersections(Sprite sprite) {
        for (Sprite sprite2 : sprites) {
            if (sprite2.equals(sprite)) {
                continue;
            }

            if (!sprite.validatePossibleSpriteInterersection(sprite2)) {
                return false;
            }
        }

        return true;
    }

    public Sprite getClickedSprite(Point point) {
        for (Sprite sprite: getSprites()) {
            if (sprite.getBounds().contains(point)) {
                return sprite;
            }
        }
        return null;
    }

    public boolean checkForOverlap() {
        for (Sprite sprite: sprites) {
            if(!validateSpriteIntersections(sprite)) return false;
        }

        return true;
    }

    public boolean validateSpritePlacement() {
        for (Sprite sprite : sprites) {
            if (!sprite.hasValidPlacement()) {
                return false;
            }
        }

        return true;
    }

    public ArrayList<Sprite> getSprites() {
        return sprites;
    }
    
    public void paintLayer(Graphics g) {
        for (Drawable s: sprites) {
            s.paintShape(g);
        }
    }
}