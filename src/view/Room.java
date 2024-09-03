package src.view;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.*;
import javax.swing.*;
import src.view.Drawable;

public class Room extends Drawable {
    public Room(int x, int y, int width, int height) {
        super();
        Rectangle rect = new Rectangle( x, y, width - 1, height - 1);
        setBounds(rect);
    }
 
    @Override 
    protected Shape getShape() {
        return getBounds();
    }
}
