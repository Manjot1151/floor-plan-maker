package org.lays.view;
import java.awt.geom.Point2D;

public interface EdgeDrawableFactory<T extends RoomEdgeDrawable> {
    T fromPoints(Point2D start, Point2D end);
    T fromCoordinates(double startX, double startY, double endX, double endY);
}
