package org.lays.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.lays.view.panels.SpritesLayer;

public class Furniture extends Sprite {
    private static final SpritesLayer spritesLayer = Canvas.getInstance().getSpritesLayer();

    private Rectangle2D bounds;
    private FurnitureType type;
    private BufferedImage image;
    private BufferedImage selectedImage;

    @Override
    public void setBounds(Rectangle2D rect) {
        this.bounds = rect;
    }

    @Override
    public Rectangle2D getBounds() {
        return bounds;
    }

    public Furniture(int x, int y, FurnitureType type) {
        this.type = type;
        this.image = type.getImage();

        int width = image.getWidth();
        int height = image.getHeight();

        setBounds(new Rectangle2D.Double(x, y, width, height));

        this.selectedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g = (Graphics2D) selectedImage.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawImage(image, 0, 0, null);
        g.setColor(new Color(0, 0, 100, 50));
        g.fillRect(0, 0, width, height);
    }

    public FurnitureType getType() {
        return type;
    }

    @Override
    public boolean isValidDrawable() {
        for (Sprite sprite : spritesLayer.getSprites()) {
            if (sprite == this) {
                continue;
            }
            if (sprite.intersects(this)) {
                return false;
            }
        }
        return hasValidPlacement();
    }

    @Override
    public void paintShape(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Rectangle2D bounds = getBounds();
        if (isSelected()) {
            g2d.drawImage(selectedImage, (int)bounds.getX(), (int)bounds.getY(), null);
        } else {
            g2d.drawImage(image, (int)bounds.getX(), (int)bounds.getY(), null);
        }
    }

    @Override
    public boolean isValidOnRoom(Room room) {
        return room.getBounds().contains(getBounds());
    }
    
}
