package org.lays.view;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;

public class Room extends Drawable {
    private RoomType roomType;

    public Room(int x, int y, int width, int height, RoomType type) {
        super();
        setBounds(new Rectangle(x, y, width, height));
        this.roomType = type;
    }
 
    @Override 
    protected Shape getShape() {
        return getBounds();
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public Color getColor() {
        return roomType.getColor();
    }
}
