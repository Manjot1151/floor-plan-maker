package org.lays.view.panels;

import java.awt.Graphics;
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
        sprite.setVisible(false);

        view.repaint();
    }

    public void remove(Drawable sprite) {
        sprites.remove(sprite);
        sprite.setVisible(false);

        view.repaint();
    }

    public boolean validateSpriteInteresctions(Sprite sprite) {
        for (Sprite sprite2 : sprites) {
            if (sprite2.equals(sprite)) {
                continue;
            }

            if (!sprite.validatePossibleSpriteInterersection(sprite2)) {
                System.out.print(sprite);
                System.out.print(" ");
                System.out.println(sprite2);
                return false;
            }
        }

        return true;
    }

    public boolean checkForOverlap() {
        for (Sprite sprite: sprites) {
            if(!validateSpriteInteresctions(sprite)) return false;
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