package src.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import javax.swing.JComponent;


public abstract class Drawable extends JComponent {
    protected abstract Shape getShape();
    private boolean selected = false;
    private static final Color selectedColor = new Color(0, 0, 255, 100);

    public final boolean intersects(Drawable drawable){
        return getBounds().intersects(drawable.getBounds());
    }

    public final void setSelected(boolean selected) {
        this.selected = selected;
    }

    public final boolean isSelected() {
        return selected;
    }

    public void paintShape(Graphics2D g2d) {
        g2d.setColor(new Color(100, 100, 100, 70));
        if (selected) {
            g2d.setColor(selectedColor);
        }
        Shape shape = getShape();
        g2d.fill(shape);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(shape);
    }
} 
