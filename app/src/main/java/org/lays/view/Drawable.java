package org.lays.view;

import java.awt.Graphics2D;
import java.awt.Shape;
import javax.swing.JComponent;


public abstract class Drawable extends JComponent {
    public abstract Shape getShape();
    protected boolean selected = false;

    public final void setSelected(boolean selected) {
        this.selected = selected;
    }

    public final boolean isSelected() {
        return selected;
    }

    public final boolean intersects(Drawable drawable){
        return getBounds().intersects(drawable.getBounds());
    }

    public abstract void paintShape(Graphics2D g2d);
} 