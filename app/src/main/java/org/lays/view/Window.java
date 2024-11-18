package org.lays.view;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;

public class Window extends RoomEdgeDrawable {
    public static class Factory implements EdgeDrawableFactory<Window> {
        public Window fromPoints(Point2D start, Point2D end) {
            return new Window(start, end);
        }

        public Window fromCoordinates(double startX, double startY, double endX, double endY) {
            return new Window(startX, startY, endX, endY);
        }
    }

    public static BasicStroke eraseStroke = new BasicStroke(wallThickness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {9}, 0);
    public static BasicStroke fillStroke = new BasicStroke(wallThickness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {9}, 9f);

    public Window(Point2D start, Point2D end) {
        super(start, end);
    }

    public Window(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
    }

    @Override
    public void paintShape(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        Line2D line = getVisibleLine();

        if (isSelected()) {
            g2d.setPaint(Color.RED);
            g2d.setStroke(fillStroke);
            g2d.draw(line);
        }


        g2d.setComposite(AlphaComposite.Clear);
        g2d.setStroke(eraseStroke);
        g2d.draw(line);
        g2d.dispose();
    }
}
