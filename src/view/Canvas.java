package src.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Container;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import java.util.ArrayList;
import src.view.Room;
import java.awt.*;
import javax.swing.*;


class ShapesPanel extends JPanel {
    private ArrayList<Drawable> shapesList;

    public ShapesPanel() {
        super();
        setLayout(null);
        shapesList = new ArrayList<Drawable>();
    }

    public void addShape(Drawable shape) {
        shapesList.add(shape);
        shape.setVisible(true);
        revalidate();
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        for (Drawable shape: shapesList) {
            shape.paintShape(g2d);
        }
    }
}

public class Canvas extends JLayeredPane {
    private ShapesPanel shapes_panel;
    private Grid grid;


    public Canvas() {
        super();

        shapes_panel = new ShapesPanel();
        this.shapes_panel.setLayout(null);
        this.shapes_panel.setOpaque(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("mouse pressed");
                if (Toolbar.selectedTool != null) {
                    Toolbar.selectedTool.onMousePressed(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("mouse released");
                if (Toolbar.selectedTool != null) {
                    Drawable shape = Toolbar.selectedTool.onMouseReleased(e);
                    Canvas.this.shapes_panel.addShape(shape);
                }
            }
        });

        this.grid = new Grid(20);

        add(this.grid);
        setLayer(this.grid, 0);
        add(this.shapes_panel);
        setLayer(this.shapes_panel, 1);
        this.shapes_panel.addShape(new Room(0, 0, 1000, 1000));
        setVisible(true);
    }

    @Override
    public void doLayout() {
        synchronized(getTreeLock()) {
            int w = getWidth();
            int h = getHeight();

            for (Component c: getComponents()) {
                c.setBounds(0,0,w,h);
            }
        }
    }
}
