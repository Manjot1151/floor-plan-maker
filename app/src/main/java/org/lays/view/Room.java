package org.lays.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.lays.view.panels.RoomsLayer;
import org.lays.view.panels.SpritesLayer;
import java.util.ArrayList;

public class Room extends Drawable {
    private static SpritesLayer spritesLayer = Canvas.getInstance().getSpritesLayer();
    private static RoomsLayer roomsLayer = Canvas.getInstance().getRoomsLayer();

    private RoomType roomType;
    private Rectangle2D bounds;
    private static int wallThickness = 3;

    private static final Color selectOverlay = new Color(0, 0, 255, 100);
    private static final float overlayOpacity = 0.6f;


    @Override
    public void setBounds(Rectangle2D bounds)  {
        this.bounds = bounds;
    }

    @Override
    public Rectangle2D getBounds() {
        return bounds;
    }

    public Room(double x, double y, double width, double height, RoomType type) {
        super();
        setBounds(new Rectangle2D.Double(x, y, width, height));
        this.roomType = type;
    }


    public boolean isOnHorizontalEdge(Point2D point) {
        Rectangle2D roomBounds = getBounds();
        return (
            Utils.gt_equals(point.getX(), roomBounds.getMinX()) && Utils.lt_equals(point.getX(), roomBounds.getMaxX()) && 
            Utils.equals(point.getY(), roomBounds.getMinY()) || Utils.equals(point.getY(), roomBounds.getMaxY()) 
        );
    }

    public boolean isOnVerticalEdge(Point2D point) {
        Rectangle2D roomBounds = getBounds();
        return (
            Utils.gt_equals(point.getY() , roomBounds.getMinY()) && Utils.lt_equals(point.getY() , roomBounds.getMaxY()) && 
            Utils.equals(point.getX(), roomBounds.getMinX()) || Utils.equals(point.getX() ,  roomBounds.getMaxX())
        );
    }

    public RoomType getType() {
        return roomType;
    }

    public Color getColor() {
        return roomType.getColor();
    }
    
    
    public ArrayList<Sprite> getOwnedSprites() {
        ArrayList<Sprite> ownedSprites = new ArrayList<>();
        for (Sprite s: spritesLayer.getSprites()) {
            if (!s.intersects(this)) {
                continue;
            }

            boolean isOwned = true;
            for (Room r: roomsLayer.getRooms()) {
                if (r.equals(this)) {
                    continue;
                }            

                if (r.intersects(s)) {
                    isOwned = false;
                    break;
                }
            }

            if (isOwned) {
                ownedSprites.add(s);
            }
        }
        return ownedSprites;
    }

    public void rotate(int numQuadrants) {
        setBounds(Utils.rotateRectangle(getBounds(), numQuadrants));
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
        Shape shape = getBounds();
        g2d.fill(shape);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(wallThickness));
        g2d.draw(shape);

        g2d.dispose();
    }

    public boolean hasValidDimensinons() {
        Rectangle2D bounds = getBounds();
        return !Utils.equals(bounds.getWidth(), 0) && !Utils.equals(bounds.getHeight(), 0);
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
