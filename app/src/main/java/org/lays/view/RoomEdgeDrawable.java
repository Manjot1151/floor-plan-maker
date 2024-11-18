package org.lays.view;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics;

import org.lays.view.panels.SpritesLayer;

public abstract class RoomEdgeDrawable extends Sprite {
    SpritesLayer spritesLayer = Canvas.getInstance().getSpritesLayer();
    protected Point2D start;
    protected Point2D end;
    protected static int wallThickness = 3;
    protected static int margin = 10;

    public RoomEdgeDrawable(Point2D start, Point2D end) {
        this.start = start;
        setEnd(end);
    }

    public RoomEdgeDrawable(double startX, double startY, double endX, double endY) {
        this.start = new Point2D.Double(startX, startY);
        setEnd(new Point2D.Double(endX, endY));
    }

    @Override
    public Point2D getLocation() {
        return getStart();
    }

    @Override
    public void setLocation(Point2D point) {
        setStart(point);
    }

    public void setStart(Point2D point) {
        double dx = point.getX() - start.getX();
        double dy = point.getY() - start.getY();
        this.start = point;
        this.end = new Point2D.Double(end.getX() + dx , end.getY() + dy);
    }

    public void setEnd(Point2D end) {
        this.end = calcOrthoEnd(start, end);
    }

    public Point2D getStart() {
        return start;
    }

    public Point2D getEnd() {
        return end;
    }

        start.setLocation(point);
        end.setLocation(new Point2D.Double(end.getX() + dx , end.getY() + dy));
    }

    public boolean isVertical() {
        return Double.compare(start.getX(), end.getX()) == 0;
    }

    public boolean isHorizontal() {
        return Double.compare(start.getY(), end.getY()) == 0;
    }

    public boolean isPoint() {
        return isVertical() && isHorizontal();
    }

    public double getMinX() {
        return Math.min(start.getX(), end.getY());
    }

    public double getMaxX() {
        return Math.max(start.getX(), end.getX());
    }

    public double getMinY() {
        return Math.min(start.getY(), end.getY());
    }

    public double getMaxY() {
        return Math.max(start.getY(), end.getY());
    }
    
    protected Point2D calcOrthoEnd(Point2D start, Point2D end) {
        double dispY = end.getX() - start.getX();
        double dispX = end.getY() - start.getY();
        if (Math.abs(dispX) <= Math.abs(dispY)) {
            return new Point2D.Double(end.getX(), start.getY());
        } else {
            return new Point2D.Double(start.getX(), end.getY());
        }
    }

    @Override
    public Point2D getCenter() {
        return new Point2D.Double((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
    }

    @Override
    public void setCenterPoint(Point2D point) {
        Point2D center = getCenter();
        double dx = point.getX() - center.getX();
        double dy = point.getY() - center.getY();


        setStart(new Point2D.Double(start.getX() + dx, start.getY() + dy));
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


    public Line2D getLine() {
        return new Line2D.Float(start, end);
    }

    @Override
    public Rectangle2D getBounds() {
        if (isVertical()) {
            return new Rectangle2D.Double(start.getX() - margin, getMinY() + wallThickness, 2 * margin, Math.abs(start.getY() - end.getY()) - (2*wallThickness));
        } else {
            return new Rectangle2D.Double(getMinX() + wallThickness, start.getY() - margin, Math.abs(start.getX() - end.getX()) - (2*wallThickness), 2*margin);
        }
    }

    public Line2D getVisibleLine() {
        if (isPoint()) {
            return new Line2D.Double(start,start);
        }

        if (isVertical()) {
            return new Line2D.Double(start.getX(), getMinY() + wallThickness, start.getX(), getMaxY() - wallThickness);
        } else {
            return new Line2D.Double(getMinX() + wallThickness, start.getY(), getMaxX() - wallThickness, start.getY());
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
        return hasValidDimensions() && hasValidPlacement() && spritesLayer.validateSpriteIntersections(this);
    }

    @Override
    public void setBounds(Rectangle2D rect){
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
