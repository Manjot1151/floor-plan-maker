package src.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class SnapIndicator extends JPanel {
    public int x;
    public int y;
    private Grid grid;
    private boolean toggle = true;

    public SnapIndicator(Grid grid) {
        super();

        this.grid = grid;
        
        setBounds(grid.getBounds());
        setSize(grid.getSize());
        setOpaque(false);
        setVisible(true);
    }

    public void toggleSnap() {
        toggle = !toggle;
        repaint();
    }

    public void update(MouseEvent e) {
        int gridSize = grid.getGridSize();

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
        if (!toggle) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(200, 100, 50, 70));
        int gridSize = grid.getGridSize();
        g2d.fillArc(x - gridSize / 2, y - gridSize / 2, gridSize, gridSize, 0, 360);
    }
}
