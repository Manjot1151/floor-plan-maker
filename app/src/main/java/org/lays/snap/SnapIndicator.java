package org.lays.snap;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JComponent;

import org.lays.view.Config;

public class SnapIndicator extends JComponent {
    private Point snapPosition = new Point(0,0);

    public SnapIndicator() {
        setOpaque(false);
        setVisible(true);
    }

    public void update(Point position) {
        snapPosition = SnapCalculator.calcSnap(position);
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        int gridSize = Config.getInstance().getGridSize();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(200, 100, 50, 70));
        g2d.fillArc(snapPosition.x - gridSize / 2, snapPosition.y - gridSize / 2, gridSize, gridSize, 0, 360);
    }
}
