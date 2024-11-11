package org.lays.view;
import java.awt.Point;

public interface EdgeDrawableFactory<T extends RoomEdgeDrawable> {
    T fromPoints(Point start, Point end);
    T fromCoordinates(int startX, int StartY, int endX, int endY);
}
