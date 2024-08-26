package src.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class Canvas extends JPanel {
    private static final int gridSize = 20;

    public Canvas() {
        super();
        setLayout(new BorderLayout());
        setBackground(new Color(0xfff7ef));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (Toolbar.selectedTool != null) {
                    Toolbar.selectedTool.onMousePressed(e, getGraphics());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (Toolbar.selectedTool != null) {
                    Toolbar.selectedTool.onMouseReleased(e, getGraphics());
                }
            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.LIGHT_GRAY);
        for (int x = 0; x <= getWidth(); x += gridSize)
            g.drawLine(x, 0, x, getHeight());
        for (int y = 0; y <= getHeight(); y += gridSize)
            g.drawLine(0, y, getWidth(), y);
    }
}
