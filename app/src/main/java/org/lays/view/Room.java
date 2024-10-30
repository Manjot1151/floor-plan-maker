package org.lays.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

public class Room extends Drawable {
    private RoomType roomType;
    private static int wallThickness = 3;

    private static final Color selectOverlay = new Color(0, 0, 255, 100);
    private static final float overlayOpacity = 0.6f;

    public Room(int x, int y, int width, int height, RoomType type) {
        super();
        setBounds(new Rectangle(x, y, width, height));
        this.roomType = type;
    }

    public boolean isOnHorizontalEdge(Point point) {
        Rectangle roomBounds = getBounds();
        return (
            point.x >= roomBounds.getMinX() && point.x <= roomBounds.getMaxX() && 
            (point.y == roomBounds.getMinY() || point.y == roomBounds.getMaxY())
        );
    }

    public boolean isOnVerticalEdge(Point point) {
        Rectangle roomBounds = getBounds();
        return (
            point.y >= roomBounds.getMinY() && point.y <= roomBounds.getMaxY() && 
            (point.x == roomBounds.getMinX() || point.x == roomBounds.getMaxX())
        );
    }

    @Override 
    public Shape getHitBox() {
        return getBounds();
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public Color getColor() {
        return roomType.getColor();
    }

    public Shape getVisibleShape() {
        return getHitBox();
    }

    public void paintShape(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

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
        Shape shape = getHitBox();
        g2d.fill(shape);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(wallThickness));
        g2d.draw(shape);

        g2d.dispose();
    }

}
