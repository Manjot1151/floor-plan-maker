package org.lays.view;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Utils {
    public static double EPSILON = 0.0001;

    public static boolean equals(double x1, double x2) {
        return Math.abs(x1 - x2) < EPSILON;
    }

    public static boolean gt_equals(double x1, double x2) {
        return Math.abs(x1 - x2) < EPSILON || x1 > x2;
    }

    public static boolean lt_equals(double x1, double x2) {
        return Math.abs(x1 - x2) < EPSILON || x1 < x2;
    }

    public static Rectangle2D rotateRectangle(Rectangle2D rect, int numQuadrants) {
        if (numQuadrants % 2 == 0) {
            return rect;
        }

        if (numQuadrants < 0) {
            numQuadrants += 2;
        }

        if (numQuadrants == 3) {
            numQuadrants = 1;
        }
        
        Point2D newTopLeft = rotatePoint(new Point2D.Double(rect.getMaxX(), rect.getMinY()), new Point2D.Double(rect.getCenterX(), rect.getCenterY()), numQuadrants);
        return new Rectangle2D.Double(newTopLeft.getX(), newTopLeft.getY(), rect.getHeight(), rect.getWidth());
    }

    public static Point2D rotatePoint(Point2D point, Point2D center, int numQuadrants) {
        double centerX = -center.getX();
        double centerY = center.getY();

        double dx = (-point.getX()) - centerX;
        double dy = point.getY() - centerY;

        double theta = numQuadrants * Math.PI/2;
        double rdx = (Math.cos(theta) * dx) - (Math.sin(theta) * dy);
        double rdy = (Math.sin(theta)) * dx + Math.cos(theta) * dy;
        double x = rdx + centerX;
        double y = rdy + centerY;


        return new Point2D.Double(-x, y);
    }
}
