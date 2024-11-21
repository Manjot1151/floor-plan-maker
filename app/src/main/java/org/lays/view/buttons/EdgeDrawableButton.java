package org.lays.view.buttons;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import org.lays.snap.SnapCalculator;
import org.lays.view.ToolButton;
import org.lays.view.Drawable;
import org.lays.view.panels.RoomsLayer;
import org.lays.view.panels.SpritesLayer;
import org.lays.view.Canvas;
import org.lays.view.Config;
import org.lays.view.RoomEdgeDrawable;
import org.lays.view.EdgeDrawableFactory;
import org.lays.view.Room;

import java.util.ArrayList;

public abstract class EdgeDrawableButton<T extends RoomEdgeDrawable, F extends EdgeDrawableFactory<T>> extends ToolButton {
    protected static RoomsLayer roomsLayer = Canvas.getInstance().getRoomsLayer();
    protected static SpritesLayer spritesLayer = Canvas.getInstance().getSpritesLayer();
    private T currentEdgeDrawable;
    private F factory;
    Class<T> clasz;

    public EdgeDrawableButton(String name, F factory, Class<T> clasz) {
        super(name);
        this.factory = factory;
        this.clasz = clasz;
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        Point2D start = calcSnapToWall(e.getPoint());
        this.currentEdgeDrawable = factory.fromPoints(start, start);
        spritesLayer.add(currentEdgeDrawable);
    }

    private Point2D calcSnapToWall(Point2D point) {
        double minDistance = Config.getInstance().getGridSize();
        double minPointX = point.getX();
        double minPointY = point.getY();
        for (Room room : roomsLayer.getRooms()) {
            if (room.getBounds().contains(point)) {
                if (point.getX() - room.getMinX() < minDistance) {
                    minDistance = point.getX() - room.getMinX();
                    minPointX = room.getMinX();
                    minPointY = point.getY();
                }

                if (room.getMaxX() - point.getX() < minDistance) {
                    minDistance = room.getMaxX() - point.getX();
                    minPointX = room.getMaxX();
                    minPointY = point.getY();
                }

                if (point.getY() - room.getMinY() < minDistance) {
                    minDistance = point.getY() - room.getMinY();
                    minPointX = point.getX();
                    minPointY = room.getMinY();
                }

                if (room.getMaxY() - point.getY() < minDistance) {
                    minDistance = room.getMaxY() - point.getY();
                    minPointX = point.getX();
                    minPointY = room.getMaxY();
                }

                return new Point2D.Double(minPointX, minPointY);
            }

            double distance;
            double distanceX;
            double distanceY;

            double snapX, snapY;

            if (point.getX() < room.getMinX()) {
                snapX = room.getMinX();
                distanceX = snapX - point.getX();
            } else if (point.getX() > room.getMaxX()) {
                snapX = room.getMaxX();
                distanceX = point.getX() - snapX;
            } else {
                snapX = point.getX();
                distanceX = 0;
            }

            if (point.getY() < room.getMinY()) {
                snapY = room.getMinY();
                distanceY = snapY - point.getY();
            } else if (point.getY() > room.getMaxY()) {
                snapY = room.getMaxY();
                distanceY = point.getY() - snapY;
            } else {
                snapY = point.getY();
                distanceY = 0;
            }

            distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

            if (distance < minDistance) {
                minDistance = distance;
                minPointX = snapX;
                minPointY = snapY;
            }
        }

        return new Point2D.Double(minPointX, minPointY);
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        currentEdgeDrawable.setEnd(SnapCalculator.calcSnap(e.getPoint()));
        if (!currentEdgeDrawable.isValidDrawable()) {
            spritesLayer.remove(currentEdgeDrawable);
        } else {
            mergeDrawableIfPossible(currentEdgeDrawable);
        }

        spritesLayer.getView().repaint();
    }

    protected void update(Point end) {
        currentEdgeDrawable.setEnd(end);
        spritesLayer.getView().repaint();
    }

    public boolean mergeDrawableIfPossible(T edgeDrawable) {
        ArrayList<T> mergeEdgeDrawables = new ArrayList<T>();

        for (Drawable sprite: spritesLayer.getSprites()) {
            if (clasz.isInstance(sprite)) {
                T testDrawable = clasz.cast(sprite);

                if (!testDrawable.equals(edgeDrawable) && testDrawable.intersects(edgeDrawable)) {
                    mergeEdgeDrawables.add(testDrawable);
                }
            }
        }

        if (mergeEdgeDrawables.isEmpty()) {
            return false;
        }

        T mergedEdgeDrawable;
        if (edgeDrawable.isVertical()) {
            double minY = edgeDrawable.getMinY();
            double maxY = edgeDrawable.getMaxY();
            double x = edgeDrawable.getStart().getX();

            for (T edgeDrawableToMerge: mergeEdgeDrawables) {
                minY = Math.min(edgeDrawableToMerge.getMinY(), minY);
                maxY = Math.max(edgeDrawableToMerge.getMaxY(), maxY);
            }

            mergedEdgeDrawable = factory.fromCoordinates(x, minY, x, maxY);

        } else {
            double minX = edgeDrawable.getMinX();
            double maxX = edgeDrawable.getMaxX();
            double y = edgeDrawable.getStart().getY();

            for (T edgeDrawableToMerge: mergeEdgeDrawables) {
                minX = Math.min(edgeDrawableToMerge.getMinX(), minX);
                maxX = Math.max(edgeDrawableToMerge.getMaxX(), maxX);
            }

            mergedEdgeDrawable = factory.fromCoordinates(minX, y, maxX, y);
        }

        if (!mergedEdgeDrawable.isValidDrawable()) {
            return false;
        }

        mergeEdgeDrawables.add(edgeDrawable);
        spritesLayer.getSprites().removeAll(mergeEdgeDrawables);
        spritesLayer.add(mergedEdgeDrawable);

        return true;
    }


    @Override
    public void onMouseDragged(MouseEvent e) {
        update(SnapCalculator.calcSnap(e.getPoint()));
    }

    @Override
    public void onMouseClicked(MouseEvent e) {
        
    }
}

