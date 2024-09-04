package src.view;

import java.awt.*;
import java.awt.Shape;
import javax.swing.*;

public abstract class Drawable extends JComponent {
    protected abstract Shape getShape();

    public final boolean intersects(Drawable drawable){
        return getBounds().intersects(drawable.getBounds());
    }

    public void paintShape(Graphics2D g2d) {
        g2d.setColor(new Color(100, 100, 100, 70));
        Shape shape = getShape();
        g2d.fill(shape);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(shape);
    }
} 
