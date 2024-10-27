package org.lays.view;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.lays.snap.SnapIndicator;
import org.lays.view.panels.ButtonsPanel;
import org.lays.view.panels.RoomsPanel;

public class Canvas extends JLayeredPane {
    private RoomsPanel roomsPanel;
    private SnapIndicator snapIndicator;
    private static Grid grid;
    private static Canvas INSTANCE;

    public static Canvas getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Canvas();
        }
        return INSTANCE;
    }

    public SnapIndicator getSnapIndicator() {
        return snapIndicator;
    }

    public RoomsPanel getRoomsPanel() {
        return roomsPanel;
    }

    private Canvas() {
        super();

        int gridSize = Config.getInstance().getGridSize();
        grid = new Grid(gridSize);

        this.roomsPanel = new RoomsPanel();
        roomsPanel.setOpaque(false);
        roomsPanel.setVisible(true);
    
        this.snapIndicator = new SnapIndicator();

        JPanel glassPane = new RoomsPanel();
        glassPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Room room = roomsPanel.getClickedRoom(e.getPoint());
                
                if (room != null && SwingUtilities.isRightMouseButton(e)) {
                    RoomPopup roomPopup = new RoomPopup(room);
                    roomPopup.show(Canvas.this, e.getX(), e.getY());
                }

                if (ButtonsPanel.selectedTool != null && SwingUtilities.isLeftMouseButton(e)) {
                    ButtonsPanel.selectedTool.onMouseClicked(e);
                }
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                if (ButtonsPanel.selectedTool != null && SwingUtilities.isLeftMouseButton(e)) {
                    ButtonsPanel.selectedTool.onMousePressed(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (ButtonsPanel.selectedTool != null && SwingUtilities.isLeftMouseButton(e)) {
                    ButtonsPanel.selectedTool.onMouseReleased(e);
                }
            }
        });

        glassPane.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (ButtonsPanel.selectedTool != null && SwingUtilities.isLeftMouseButton(e)) {
                    ButtonsPanel.selectedTool.onMouseDragged(e);
                }
                
                snapIndicator.update(e.getPoint());
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                snapIndicator.update(e.getPoint());
            }
        });

        glassPane.setOpaque(false);
        glassPane.setVisible(true);

        add(snapIndicator);

        add(grid);
        setLayer(grid, 0);
        add(roomsPanel);
        setLayer(roomsPanel, 1);
        add(glassPane);
        setLayer(glassPane, 2);
        setVisible(true);
    }


    @Override
    public void doLayout() {
        synchronized (getTreeLock()) {
            int w = getWidth();
            int h = getHeight();

            for (Component c : getComponents()) {
                c.setBounds(0, 0, w, h);
            }
        }
    }
}