package org.lays.view;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public enum FurnitureType {
    BED("bed.png", 80),
    CHAIR("chair.png",40),
    COMMODE("commode.png", 40),
    SOFA("sofa.png", 40),;

    private String path;
    private int scale;
    private BufferedImage image;

    FurnitureType(String filename, int size) {
        String spritesPath = "app/src/main/resources/sprites/";
        this.path = spritesPath + filename;
        
        try {
            image = ImageIO.read(new File(this.path));
            int imageSize = Math.max(image.getWidth(null), image.getHeight(null));
            int width = (image.getWidth(null) * size) / imageSize;
            int height = (image.getHeight(null) * size) / imageSize;
            BufferedImage scaledImage = new BufferedImage(width, height, image.getType());
            Graphics2D g2d = scaledImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.drawImage(image, 0, 0, width, height, null);
            g2d.dispose();
            image = scaledImage;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getScale() {
        return scale;
    }

    @Override
    public String toString() {
        String[] words = name().split("_");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(Character.toString(word.charAt(0)).toUpperCase());
            sb.append(word.substring(1).toLowerCase());
            sb.append(" ");
        }
        return sb.toString().trim();
    }
}
