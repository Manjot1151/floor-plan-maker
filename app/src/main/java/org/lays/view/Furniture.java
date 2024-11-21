package org.lays.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import org.lays.view.panels.SpritesLayer;

public class Furniture extends Sprite {
    private static final SpritesLayer spritesLayer = Canvas.getInstance().getSpritesLayer();

    private Rectangle2D bounds;
    private FurnitureType type;
    private BufferedImage image;
    private BufferedImage selectedImage;
    private int orientation;

    @Override
    public void setBounds(Rectangle2D rect) {
        this.bounds = rect;
    }

    @Override
    public Rectangle2D getBounds() {
        return bounds;
    }

    public Furniture(double x, double y, FurnitureType type) {
        this.type = type;
        this.image = type.getImage();

        int width = image.getWidth();
        int height = image.getHeight();
        this.orientation = 0;

        setBounds(new Rectangle2D.Double(x, y, width, height));

        this.selectedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g = (Graphics2D) selectedImage.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawImage(image, 0, 0, null);
        g.setColor(new Color(0, 0, 100, 50));
        g.fillRect(0, 0, width, height);
    }

    public void rotate(int numQuadrants) {
        rotateIntermediate(numQuadrants);
        orientation = (orientation + numQuadrants) % 4;
        if (orientation < 0) {
            orientation += 4;
        }
    }

    private void rotateIntermediate(int numQuadrants) {
        numQuadrants %= 4;

        if (numQuadrants < 0) {
            numQuadrants += 4;
        }

        int w0 = image.getWidth();
        int h0 = image.getHeight();
        int centerX = w0 / 2;
        int centerY = h0 / 2;

        if (numQuadrants % 4 == 3) {
            centerX = h0 / 2;
            centerY = h0 / 2;
        } else if (numQuadrants % 4 == 1) {
            centerX = w0 / 2;
            centerY = w0 / 2;
        }

        AffineTransform tx = AffineTransform.getQuadrantRotateInstance(-numQuadrants, centerX, centerY);
        AffineTransformOp txop = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        this.image = txop.filter(this.image, null);
        this.selectedImage = txop.filter(this.selectedImage, null);

        Rectangle2D newBounds = Utils.rotateRectangle(bounds, numQuadrants);
        setBounds(newBounds);
    }

    public int getOrientation() {
        return orientation;
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
