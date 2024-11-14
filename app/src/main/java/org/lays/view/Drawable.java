package org.lays.view;

import java.awt.Graphics;
import java.awt.Shape;
import javax.swing.JComponent;
import java.awt.geom.Rectangle2D;


public abstract class Drawable extends JComponent {
    public abstract Shape getHitBox();
    protected boolean selected = false;

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean intersects(Drawable drawable){
        return getHitBox().intersects((Rectangle2D)drawable.getHitBox());
    }

    public abstract Shape getVisibleShape();

    public abstract boolean isValidDrawable();

    public abstract void paintShape(Graphics g);
} 
