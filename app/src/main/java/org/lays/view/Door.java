package org.lays.view;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import org.lays.view.panels.RoomsLayer;
import org.lays.view.panels.SpritesLayer;

public class Door extends RoomEdgeDrawable {
    public static RoomsLayer roomsLayer = Canvas.getInstance().getRoomsLayer();
    public static SpritesLayer spritesLayer = Canvas.getInstance().getSpritesLayer();
    private static BasicStroke wallStroke = new BasicStroke(wallThickness);

    public static class Factory implements EdgeDrawableFactory<Door> {
        public Door fromPoints(Point start, Point end) {
            return new Door(start, end);
        }

        public Door fromCoordinates(int startX, int startY, int endX,int endY) {
            return new Door(startX, startY, endX, endY);
        }
    }

    public Door(Point start, Point end) {
        super(start, end);
    }

    public Door(int startX, int startY, int endX, int endY) {
        super(startX, startY, endX, endY);
    }

    @Override
    public void paintShape(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(wallStroke);

        if (isSelected()) {
            g2d.setColor(Color.RED);
        } else {
            g2d.setComposite(AlphaComposite.Clear);
        }

        g2d.draw(getVisibleShape());
        g2d.dispose();
    }

    @Override
    public boolean hasValidPlacement() {
        int n_intersects = 0;
        RoomType lastIntersectingRoomType = null;
        for (Room room : roomsLayer.getRooms()) {
            if (this.intersects(room)) {
                lastIntersectingRoomType = room.getRoomType();
                n_intersects += 1;

                if (!this.isValidOnRoom(room)) {
                    return false;
                }
            }
        }

        if (n_intersects == 0) {
            return false;
        } else if (n_intersects == 1 && (lastIntersectingRoomType.equals(RoomType.BATHROOM) || lastIntersectingRoomType.equals(RoomType.BEDROOM))) {
            return false;
        }

        return true;
    }
}
