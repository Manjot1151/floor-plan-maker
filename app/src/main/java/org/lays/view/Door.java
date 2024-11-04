package org.lays.view;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class Door extends RoomEdgeDrawable {
    private static BasicStroke wallStroke = new BasicStroke(wallThickness);

    public Door(Point start, Point end) {
        super(start, end);
    }

    public Door(int startX, int startY, int endX, int endY) {
        super(startX, startY, endX, endY);
    }

    @Override
    public void paintShape(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(wallStroke);
        g2d.setComposite(AlphaComposite.Clear);

        g2d.draw(getVisibleShape());
        g2d.dispose();
    }
}
