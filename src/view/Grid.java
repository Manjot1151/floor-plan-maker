package src.view;

import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;

public class Grid extends JComponent {
    private int gridSize;

    public Grid(int gridSize) {
        super();
        this.gridSize = gridSize;
        setVisible(true);
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;    
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.LIGHT_GRAY);
        int w = getWidth();
        int h = getHeight();
       
        for (int x = 0; x <= w; x += gridSize)
            g.drawLine(x, 0, x, h);
        for (int y = 0; y <= h; y += gridSize)
            g.drawLine(0, y, w, y);
    }
}
