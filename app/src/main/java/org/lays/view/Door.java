package org.lays.view;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;

public class Door extends Drawable {
    private Point start;
    private Point end;
    private static int wallThickness = 3;
    private static BasicStroke wallStroke = new BasicStroke(wallThickness);
    private static final int padding = 10;

    public boolean isVertical() {
        return start.x == end.x;
    }

    public boolean isHorizontal() {
        return start.y == end.y;
    }

    public Point getStart() {
        return start;
    }
    public Point getEnd() {
        return end;
    }

    public boolean isPoint() {
        return isVertical() && isHorizontal();
    }

    public int getMinX() {
        return Math.min(start.x, end.x);
    }

    public int getMaxX() {
        return Math.max(start.x, end.x);
    }

    public int getMinY() {
        return Math.min(start.y, end.y);
    }

    public int getMaxY() {
        return Math.max(start.y, end.y);
    }
    
    public Door(Point start, Point end) {
        this.start = start;
        setEnd(end);
    }

    private Point calcOrthoEnd(Point start, Point end) {
        int dispY = end.x - start.x;
        int dispX = end.y - start.y;
        if (Math.abs(dispX) <= Math.abs(dispY)) {
            return new Point(end.x, start.y);
        } else {
            return new Point(start.x, end.y);
        }
    }

    public boolean intersects(Room room)   {
        return getHitBox().intersects((Rectangle2D)room.getHitBox());
    }

    public void setEnd(Point end) {
        this.end = calcOrthoEnd(start, end);
    }

    @Override
    public Shape getHitBox() {
        if (isVertical()) {
            return new Rectangle(start.x - padding, getMinY() + wallThickness, 2 * padding, Math.abs(start.y - end.y) - (2*wallThickness));
        } else {
            return new Rectangle(getMinX() + wallThickness, start.y - padding, Math.abs(start.x - end.x) - (2*wallThickness), 2*padding);
        }
    }

    @Override
    public Shape getVisibleShape() {
        if (isVertical()) {
            return new Line2D.Float(start.x, getMinY() + wallThickness, start.x, getMaxY() - wallThickness);
        } else {
            return new Line2D.Float(getMinX() + wallThickness, start.y, getMaxX() - wallThickness, start.y);
        }
    }

    @Override
    public void paintShape(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(wallStroke);
        g2d.setComposite(AlphaComposite.Clear);

        g2d.draw(getVisibleShape());
        g2d.dispose();
    }
}
