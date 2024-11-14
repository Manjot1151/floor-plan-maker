package org.lays.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

import org.lays.view.panels.RoomsLayer;
import org.lays.view.panels.SpritesLayer;

public class Room extends Drawable {
    private static SpritesLayer spritesLayer = Canvas.getInstance().getSpritesLayer();
    private static RoomsLayer roomsLayer = Canvas.getInstance().getRoomsLayer();

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
            (Double.compare(point.y, roomBounds.getMinY()) == 0 || Double.compare(point.y, roomBounds.getMaxY()) == 0)
        );
    }

    public boolean isOnVerticalEdge(Point point) {
        Rectangle roomBounds = getBounds();
        return (
            point.y >= roomBounds.getMinY() && point.y <= roomBounds.getMaxY() && 
            (Double.compare(point.x, roomBounds.getMinX()) == 0 || Double.compare(point.x, roomBounds.getMaxX()) == 0)
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
        g2d.setColor(this.getColor());
        if (isSelected()) {
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

    public boolean hasValidDimensinons() {
        return this.getWidth() != 0 && this.getHeight() != 0;
    }

    public boolean validateSprites() {
        for (Sprite sprite: spritesLayer.getSprites()) {
            if(sprite.intersects(this) && (!sprite.isValidOnRoom(this) || (sprite instanceof Window)))  {
                return false;
            }
        }

        return true;
    }

    public boolean isValidDrawable() {
        return this.hasValidDimensinons() && !roomsLayer.isIntersecting(this) && this.validateSprites();
    }

}
