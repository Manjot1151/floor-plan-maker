package org.lays.view.panels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GraphicsPanel extends JPanel {
    private RoomsLayer rooms;
    private SpritesLayer sprites;

    public GraphicsPanel() {
        this.rooms = new RoomsLayer(this);
        this.sprites = new SpritesLayer(this);
    }

    public RoomsLayer getRoomsLayer() {
        return this.rooms;
    }

    public SpritesLayer getSpritesLayer() {
        return this.sprites;
    }

    @Override
    public void paintComponent(Graphics g) {
        Rectangle bounds = g.getClipBounds();
        BufferedImage bufferedImage = new BufferedImage((int)bounds.getWidth(), (int)bounds.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics gbuf = bufferedImage.createGraphics();

        rooms.paintLayer(gbuf);
        sprites.paintLayer(gbuf);

        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(bufferedImage,0, 0,null);
    }
}
