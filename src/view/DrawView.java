package src.view;

import javax.swing.JLayeredPane;
import java.awt.BorderLayout;
import src.view.Grid;
import src.view.Canvas;
import javax.swing.JPanel;
import java.awt.Container;

public class DrawView extends JPanel {
    public static int grid_size = 20;

    public DrawView() {
        Grid grid = new Grid(grid_size);
        grid.setBounds(0, 0, getWidth(), getHeight());

        add(grid);
    }
}
