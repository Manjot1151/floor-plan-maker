package org.lays.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

public class Room extends Drawable {
    private RoomType roomType;

    private static final Color selectOverlay = new Color(0, 0, 255, 100);
    private static final float overlayOpacity = 0.6f;

    public Room(int x, int y, int width, int height, RoomType type) {
        super();
        setBounds(new Rectangle(x, y, width, height));
        this.roomType = type;
    }
 
    @Override 
    public Shape getShape() {
        return getBounds();
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public Color getColor() {
        return roomType.getColor();
    }

    public void paintShape(Graphics2D g2d) {
        g2d.setColor(new Color(100, 100, 100, 70));
        if (this instanceof Room) {
            g2d.setColor(((Room) this).getColor());
        }
        if (selected) {
            Color selectedColor = new Color(
                    (int) (selectOverlay.getRed() * (overlayOpacity) + g2d.getColor().getRed() * (1-overlayOpacity)),
                    (int) (selectOverlay.getGreen() * (overlayOpacity) + g2d.getColor().getGreen() * (1-overlayOpacity)),
                    (int) (selectOverlay.getBlue() * (overlayOpacity) + g2d.getColor().getBlue() * (1-overlayOpacity)),
                    (int) (selectOverlay.getAlpha() * (overlayOpacity) + g2d.getColor().getAlpha() * (1-overlayOpacity))
            );
            g2d.setColor(selectedColor);
        }
        Shape shape = getShape();
        g2d.fill(shape);
        g2d.setColor(Color.PINK);
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(shape);
    }

}
