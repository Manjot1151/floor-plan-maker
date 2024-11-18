package org.lays.view;

import java.awt.Graphics;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


public abstract class Drawable{
    protected boolean selected = false;
    public abstract Rectangle2D getBounds();
    public abstract void setBounds(Rectangle2D rect);

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public Point2D getLocation() {
        Rectangle2D bounds = getBounds();
        return new Point2D.Double(bounds.getX(), bounds.getY());
    }

    public void setLocation(Point2D newLocation) {
        Rectangle2D bounds = (Rectangle2D)getBounds().clone();
        setBounds(new Rectangle2D.Double(newLocation.getX(), newLocation.getY(), bounds.getWidth(), bounds.getHeight()));
    }


    public double getX() {
        return getBounds().getX();
    }

    public double getY() {
        return getBounds().getY();
    }

    public double getHeight() {
        return getBounds().getHeight();
    }

    public double getWidth() {
        return getBounds().getWidth();
    }

    public void setCenterPoint(Point2D point) {
        Rectangle2D rect = (Rectangle2D)getBounds().clone();
        Point2D center = getCenter();
        double dx = point.getX() - center.getX();
        double dy = point.getY() - center.getY();
        setBounds(new Rectangle2D.Double(rect.getX() + dx , rect.getY() + dy, rect.getWidth(), rect.getHeight()));
    }

    public double getMinX() {
        return getBounds().getMinX();
    };

    public double getMaxX() {
        return getBounds().getMaxX();
    };

    public double getMinY() {
        return getBounds().getMinY();
    };

    public double getMaxY() {
        return getBounds().getMaxY();
    };

    public Point2D getCenter() {
        Rectangle2D bounds = getBounds();
        return new Point2D.Double(bounds.getCenterX(), bounds.getCenterY());
    }

    public abstract void rotate(int numQuadrants);

    public boolean intersects(Drawable drawable){
        return getBounds().intersects(drawable.getBounds());
    }

    public abstract boolean isValidDrawable();

    public abstract void paintShape(Graphics g);
} 
