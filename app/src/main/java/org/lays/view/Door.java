package org.lays.view;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;

public class Door extends Drawable {
    private Point start;
    private Point end;
    private static BasicStroke wallStroke = new BasicStroke(3);

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
        return getShape().intersects((Rectangle2D)room.getShape());
    }

    public void setEnd(Point end) {
        this.end = calcOrthoEnd(start, end);
    }

    @Override
    public Shape getShape() {
        return new Line2D.Float(start, end);
    }


    @Override
    public void paintShape(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(wallStroke);
        g2d.setComposite(AlphaComposite.Clear);

        g2d.draw(getShape());
        g2d.dispose();
    }
}
