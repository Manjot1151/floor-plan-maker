package org.lays.view;

import java.awt.Rectangle;
import java.awt.Shape;

public class Room extends Drawable {
    public Room(int x, int y, int width, int height) {
        super();
        setBounds(new Rectangle(x, y, width, height));
    }
 
    @Override 
    protected Shape getShape() {
        return getBounds();
    }
}
