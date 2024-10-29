package org.lays.view.buttons;

import java.awt.event.MouseEvent;

import org.lays.snap.SnapCalculator;
import org.lays.view.Canvas;
import org.lays.view.Room;
import org.lays.view.ToolButton;
import org.lays.view.panels.GraphicsPanel;
import org.lays.view.panels.RoomsLayer;

public class SelectButton extends ToolButton {
    private final GraphicsPanel graphicsPanel = Canvas.getInstance().getGraphicsPanel();
    
    public SelectButton() {
        super("Select");
    }

    @Override
    public void onMouseClicked(MouseEvent e) {
        RoomsLayer roomsLayer = graphicsPanel.getRoomsLayer();
        Room clickedRoom = roomsLayer.getClickedRoom(SnapCalculator.calcSnap(e.getPoint()));
        if (clickedRoom == null) {
            roomsLayer.getRooms().forEach(shape -> shape.setSelected(false));
            graphicsPanel.repaint();
            return;
        }

        clickedRoom.setSelected(!clickedRoom.isSelected());
        graphicsPanel.repaint();
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        RoomsLayer rooms = graphicsPanel.getRoomsLayer();
        rooms.getRooms().forEach(shape -> shape.setSelected(false));
        
        // TODO: Implement selection box
        
        graphicsPanel.repaint();
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        
    }

    
}
