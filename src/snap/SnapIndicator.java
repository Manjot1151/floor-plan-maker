package src.snap;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

public class SnapIndicator extends JComponent {
    public int x;
    public int y;
    private int gridSize;
    private boolean snap = true;

    public SnapIndicator(int gridSize) {
        super();
        this.gridSize = gridSize;

        setOpaque(false);
        setVisible(true);
    }

    public boolean getSnap() {
        return snap;
    }

    public void toggleSnap() {
        snap = !snap;
        repaint();
    }

    public void update(MouseEvent e) {
        if (e.getX() % gridSize < gridSize - e.getX() % gridSize) {
            this.x = e.getX() - e.getX() % gridSize;
        } else {
            this.x = e.getX() + gridSize - e.getX() % gridSize;
        }

        if (e.getY() % gridSize < gridSize - e.getY() % gridSize) {
            this.y = e.getY() - e.getY() % gridSize;
        } else {
            this.y = e.getY() + gridSize - e.getY() % gridSize;
        }

        // this.x -= gridSize / 2;
        // this.y -= gridSize / 2;

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        if (!snap) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(200, 100, 50, 70));
        g2d.fillArc(x - gridSize / 2, y - gridSize / 2, gridSize, gridSize, 0, 360);
    }
}
