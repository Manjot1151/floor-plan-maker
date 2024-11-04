package org.lays.view;

import java.awt.Point;
import java.awt.BasicStroke;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;

public class Window extends RoomEdgeDrawable {
    public static BasicStroke windowStroke = new BasicStroke(wallThickness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {9}, 0);

    public Window(Point start, Point end) {
        super(start, end);
    }

    public Window(int startX, int startY, int endX, int endY) {
        super(startX, startY, endX, endY);
    }

    @Override
    public void paintShape(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.Clear);
        g2d.setStroke(windowStroke);
        g2d.draw(getLine());
        g2d.dispose();
    }
}
