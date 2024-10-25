package org.lays.view;

import javax.swing.JPanel;

public class DrawView extends JPanel {
    public static final int gridSize = 20;

    public DrawView() {
        Grid grid = new Grid(gridSize);
        grid.setBounds(0, 0, getWidth(), getHeight());

        add(grid);
    }
}
