package org.lays.view.buttons;

import java.awt.event.MouseEvent;

import org.lays.snap.SnapCalculator;
import org.lays.view.Canvas;
import org.lays.view.Room;
import org.lays.view.ToolButton;
import org.lays.view.panels.RoomsPanel;

public class SelectButton extends ToolButton {
    private final RoomsPanel roomsPanel = Canvas.getInstance().getRoomsPanel();
    
    public SelectButton() {
        super("Select");
    }

    @Override
    public void onMouseClicked(MouseEvent e) {
        Room clickedRoom = roomsPanel.getClickedRoom(SnapCalculator.calcSnap(e.getPoint()));
        if (clickedRoom == null) {
            roomsPanel.getRooms().forEach(shape -> shape.setSelected(false));
            roomsPanel.repaint();
            return;
        }

        clickedRoom.setSelected(!clickedRoom.isSelected());
        roomsPanel.repaint();
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        roomsPanel.getRooms().forEach(shape -> shape.setSelected(false));
        
        // TODO: Implement selection box
        
        roomsPanel.repaint();
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        
    }

    
}
