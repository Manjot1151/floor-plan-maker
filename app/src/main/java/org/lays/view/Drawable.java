package org.lays.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import javax.swing.JComponent;


public abstract class Drawable extends JComponent {
    protected abstract Shape getShape();
    private boolean selected = false;

    private static final Color selectOverlay = new Color(0, 0, 255, 100);
    private static final float overlayOpacity = 0.6f;

    public final boolean intersects(Drawable drawable){
        return getBounds().intersects(drawable.getBounds());
    }

    public final void setSelected(boolean selected) {
        this.selected = selected;
    }

    public final boolean isSelected() {
        return selected;
    }

    public void paintShape(Graphics2D g2d) {
        g2d.setColor(new Color(100, 100, 100, 70));
        if (this instanceof Room) {
            g2d.setColor(((Room) this).getColor());
        }
        if (selected) {
            Color selectedColor = new Color(
                    (int) (selectOverlay.getRed() * (overlayOpacity) + g2d.getColor().getRed() * (1-overlayOpacity)),
                    (int) (selectOverlay.getGreen() * (overlayOpacity) + g2d.getColor().getGreen() * (1-overlayOpacity)),
                    (int) (selectOverlay.getBlue() * (overlayOpacity) + g2d.getColor().getBlue() * (1-overlayOpacity)),
                    (int) (selectOverlay.getAlpha() * (overlayOpacity) + g2d.getColor().getAlpha() * (1-overlayOpacity))
            );
            g2d.setColor(selectedColor);
        }
        Shape shape = getShape();
        g2d.fill(shape);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(shape);
    }
} 
