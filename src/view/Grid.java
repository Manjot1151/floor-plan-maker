package src.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

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
        int width = getWidth();
        int height = getHeight();
       
        for (int x = 0; x <= width; x += gridSize)
            g.drawLine(x, 0, x, height);
        for (int y = 0; y <= height; y += gridSize)
            g.drawLine(0, y, width, y);
    }
}
