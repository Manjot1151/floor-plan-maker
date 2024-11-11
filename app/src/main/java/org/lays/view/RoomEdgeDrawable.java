package org.lays.view;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.Graphics;

import org.lays.view.panels.SpritesLayer;

public abstract class RoomEdgeDrawable extends Sprite {
    SpritesLayer spritesLayer = Canvas.getInstance().getSpritesLayer();
    protected Point start;
    protected Point end;
    protected static int wallThickness  = 3;
    protected static int margin = 10;

    public RoomEdgeDrawable(Point start, Point end) {
        this.start = start;
        setEnd(end);
    }

    public RoomEdgeDrawable(int startX,int startY, int endX, int endY) {
        this.start = new Point(startX, startY);
        setEnd(new Point(endX, endY));
    }

    @Override
    public Point getLocation() {
        return start;
    }

    @Override
    public void setLocation(Point point) {
        int dx = point.x - start.x;
        int dy = point.y - start.y;

        this.start = point;

        this.end.x += dx;
        this.end.y += dy;
    }

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
    
    protected Point calcOrthoEnd(Point start, Point end) {
        int dispY = end.x - start.x;
        int dispX = end.y - start.y;
        if (Math.abs(dispX) <= Math.abs(dispY)) {
            return new Point(end.x, start.y);
        } else {
            return new Point(start.x, end.y);
        }
    }


    @Override
    public boolean intersects(Drawable drawable)   {
        if (drawable instanceof RoomEdgeDrawable edgeDrawable) {
            boolean areInSameOrientation = 
                (this.isHorizontal() && edgeDrawable.isHorizontal()) || 
                (this.isVertical() && edgeDrawable.isVertical());

            return areInSameOrientation && this.getLine().intersectsLine(edgeDrawable.getLine());
        } else {
            return super.intersects(drawable);
        }
    }


    public void setEnd(Point end) {
        this.end = calcOrthoEnd(start, end);
    }

    public Line2D getLine() {
        return new Line2D.Float(start, end);
    }

    @Override
    public Shape getHitBox() {
        if (isVertical()) {
            return new Rectangle(start.x - margin, getMinY() + wallThickness, 2 * margin, Math.abs(start.y - end.y) - (2*wallThickness));
        } else {
            return new Rectangle(getMinX() + wallThickness, start.y - margin, Math.abs(start.x - end.x) - (2*wallThickness), 2*margin);
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

    public boolean hasValidDimensions() {
        return !this.isPoint();
    }

    @Override
    public boolean validatePossibleSpriteInterersection(Sprite sprite) {
        return !this.intersects(sprite) || this.getClass() == sprite.getClass();
    }

    public boolean isValidDrawable() {
        return hasValidDimensions() && hasValidPlacement() && spritesLayer.validateSpriteInteresctions(this);
    }

    @Override
    public abstract void paintShape(Graphics g);

    public boolean isValidOnRoom(Room room) {
        boolean checkVertical = 
            this.isVertical() && 
            room.isOnVerticalEdge(this.getStart()) && 
            room.isOnVerticalEdge(this.getEnd());

        boolean checkHorizontal = 
            this.isHorizontal() && 
            room.isOnHorizontalEdge(this.getStart()) && 
            room.isOnHorizontalEdge(this.getEnd());
        
        return checkHorizontal ^ checkVertical;
    }
}
