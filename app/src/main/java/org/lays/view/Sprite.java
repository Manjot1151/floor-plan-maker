package org.lays.view;

import org.lays.view.panels.RoomsLayer;

public abstract class Sprite extends Drawable {
    public static RoomsLayer roomsLayer = Canvas.getInstance().getRoomsLayer();

    public abstract boolean isValidOnRoom(Room room);

    // checks that there are no interesctions with sprites which are invalid, 
    // return false if check fails.
    public boolean validatePossibleSpriteInterersection(Sprite sprite) {
        return !this.intersects(sprite);
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
