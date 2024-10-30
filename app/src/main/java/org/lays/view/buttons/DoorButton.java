package org.lays.view.buttons;

import java.awt.Point;
import java.awt.event.MouseEvent;

import org.lays.snap.SnapCalculator;
import org.lays.view.ToolButton;
import org.lays.view.Door;
import org.lays.view.panels.RoomsLayer;
import org.lays.view.panels.SpritesLayer;
import org.lays.view.Canvas;
import org.lays.view.Room;

public class DoorButton extends ToolButton {
    private static SpritesLayer spritesPanel = Canvas.getInstance().getSpritesLayer();
    private static RoomsLayer roomsPanel = Canvas.getInstance().getRoomsLayer();
    private Door currentDoor;

    public DoorButton() {
        super("Door");
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        Point start = SnapCalculator.calcSnap(e.getPoint());
        this.currentDoor = new Door(start, start);
        spritesPanel.add(currentDoor);
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        updateLine(SnapCalculator.calcSnap(e.getPoint()));
        if (!isDoorValid(currentDoor)) {
            spritesPanel.remove(currentDoor);
        };
    }

    private void updateLine(Point end) {
        currentDoor.setEnd(end);
        spritesPanel.getView().repaint();
    }

    public static boolean isValidDoorOnRoom(Door door, Room room) {
        boolean checkVertical = 
            door.isVertical() && 
            room.isOnVerticalEdge(door.getStart()) && 
            room.isOnVerticalEdge(door.getEnd());

        boolean checkHorizontal = 
            door.isHorizontal() && 
            room.isOnHorizontalEdge(door.getStart()) && 
            room.isOnHorizontalEdge(door.getEnd());
        
        return checkHorizontal ^ checkVertical;
    }
    
    public static boolean isDoorValid(Door door) {
        if (door.isPoint()) {
            return false;
        }

        int n_intersects = 0;
        boolean isValid = true;
        for (Room room : roomsPanel.getRooms()) {
            if (door.intersects(room)) {
                n_intersects += 1;

                if (!isValidDoorOnRoom(door, room)) {
                    isValid = false;
                    break;
                }
            }
        }

        isValid = isValid && n_intersects != 0;

        return isValid;
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        updateLine(SnapCalculator.calcSnap(e.getPoint()));
    }

    @Override
    public void onMouseClicked(MouseEvent e) {
        
    }
}
