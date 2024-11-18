package org.lays.view;

import java.awt.geom.Point2D;

import org.lays.view.panels.RoomsLayer;

public abstract class Sprite extends Drawable {
    public static RoomsLayer roomsLayer = Canvas.getInstance().getRoomsLayer();

    public abstract boolean isValidOnRoom(Room room);

    // checks that there are no interesctions with sprites which are invalid, 
    // return false if check fails.
    public boolean validatePossibleSpriteInterersection(Sprite sprite) {
        return !this.intersects(sprite);
    }

    public void rotateOnRoom(Room room, int numQuadrants) {
        Point2D center = room.getCenter();
        Point2D movedCenter = Utils.rotatePoint(getCenter(), center, numQuadrants);
        setCenterPoint(movedCenter);
        rotate(numQuadrants);
    }

    public boolean shouldSoftSelect() {
        for (Room room : roomsLayer.getRooms()) {
            if (this.intersects(room) && !room.isSelected()) {
                return false;
            }
        }
        return true;
    }

    // only checks wrt to rooms which the sprite interesects.
    public boolean hasValidPlacement() {
        int n_intersects = 0;
        for (Room room : roomsLayer.getRooms()) {
            if (intersects(room)) {
                n_intersects += 1;

                if (n_intersects > 1 || !isValidOnRoom(room)) {
                    return false;
                }
            }
        }
        return n_intersects != 0;
    }
}
