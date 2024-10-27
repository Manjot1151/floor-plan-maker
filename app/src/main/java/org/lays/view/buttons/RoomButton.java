package org.lays.view.buttons;

import java.awt.event.MouseEvent;

import javax.swing.JComboBox;

import org.lays.snap.SnapCalculator;
import org.lays.view.Canvas;
import org.lays.view.Room;
import org.lays.view.RoomType;
import org.lays.view.ToolButton;
import org.lays.view.panels.RoomsPanel;

import java.awt.Point;

public class RoomButton extends ToolButton {
    private final RoomsPanel roomsPanel = Canvas.getInstance().getRoomsPanel();

    private Point rectStart;
    private Room currentRoom;
    private RoomType currentRoomType;
    private static JComboBox<RoomType> roomTypeComboBox;

    public RoomButton() {
        super("Room");

        roomTypeComboBox = new JComboBox<>(RoomType.values());
        this.currentRoomType = (RoomType) roomTypeComboBox.getSelectedItem();
        roomTypeComboBox.addActionListener(e -> this.currentRoomType = (RoomType) roomTypeComboBox.getSelectedItem());
    }

    public static JComboBox<RoomType> getRoomTypeComboBox() {
        return roomTypeComboBox;
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        rectStart = SnapCalculator.calcSnap(e.getPoint());
        currentRoom = new Room(rectStart.x, rectStart.y, 0, 0, currentRoomType);
        roomsPanel.addRoom(currentRoom);
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        updateRoom(SnapCalculator.calcSnap(e.getPoint()));

        boolean isRoomInvalid = currentRoom.getWidth() == 0 || currentRoom.getHeight() == 0 || roomsPanel.isIntersecting(currentRoom);
        if (isRoomInvalid) {
            roomsPanel.removeRoom(currentRoom);
            roomsPanel.repaint();
            currentRoom = null;
        }
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        updateRoom(SnapCalculator.calcSnap(e.getPoint()));
    }

    private void updateRoom(Point rectEnd) {
        int x = Math.min(rectStart.x, rectEnd.x);
        int y = Math.min(rectStart.y, rectEnd.y);
        int width = Math.abs(rectEnd.x - rectStart.x);
        int height = Math.abs(rectEnd.y - rectStart.y);
        currentRoom.setBounds(x, y, width, height);
        roomsPanel.repaint();
    }

    @Override
    public void onMouseClicked(MouseEvent e) {
        
    }
}