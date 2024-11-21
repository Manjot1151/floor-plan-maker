package org.lays.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import org.lays.view.Grid;

public class Grid extends JComponent {
    public Grid(int gridSize) {
        super();
        setVisible(false);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.LIGHT_GRAY);

        int gridSize = Config.getInstance().getGridSize();
        int width = getWidth();
        int height = getHeight();
       
        for (int x = 0; x <= width; x += gridSize)
            g.drawLine(x, 0, x, height);
        for (int y = 0; y <= height; y += gridSize)
            g.drawLine(0, y, width, y);
    }
}
