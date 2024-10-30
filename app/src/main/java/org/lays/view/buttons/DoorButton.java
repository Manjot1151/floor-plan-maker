package org.lays.view.buttons;

import java.awt.Point;
import java.awt.event.MouseEvent;

import org.lays.snap.SnapCalculator;
import org.lays.view.ToolButton;
import org.lays.view.Door;
import org.lays.view.Drawable;
import org.lays.view.panels.RoomsLayer;
import org.lays.view.panels.SpritesLayer;
import org.lays.view.Canvas;
import org.lays.view.Room;
import java.util.ArrayList;

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
        currentDoor.setEnd(SnapCalculator.calcSnap(e.getPoint()));
        if (!isDoorValid(currentDoor)) {
            spritesPanel.remove(currentDoor);
        } else {
            mergeDoorIfPossible(currentDoor);
        }
        System.out.println(spritesPanel.getSprites().size());

        spritesPanel.getView().repaint();
    }

    private void updateLine(Point end) {
        currentDoor.setEnd(end);
        spritesPanel.getView().repaint();
    }

    public static boolean isValidRoomPlacement(Room room) {
        for (Drawable sprite: spritesPanel.getSprites()) {
            if (!(sprite instanceof Door)) {
                continue;
            }

            Door door = (Door) sprite;
            if (!door.intersects(room)) {
                continue;
            }

            if (!isValidDoorOnRoom(door, room)) {
                return false;
            }
        }

        return true;
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

    public static boolean mergeDoorIfPossible(Door door) {
        ArrayList<Door> mergeDoors = new ArrayList<Door>();

        for (Drawable sprite: spritesPanel.getSprites()) {
            if (sprite.equals(door)) {
                continue;
            }

            Door testDoor = (Door)sprite;
            if (sprite instanceof Door && testDoor.intersects(door)) {
                mergeDoors.add(testDoor);
            }
        }

        if (mergeDoors.isEmpty()) {
            return false;
        }

        Door mergedDoor;
        if (door.isVertical()) {
            int minY = door.getMinY();
            int maxY = door.getMaxY();
            int x = door.getStart().x;

            for (Door doorToMerge: mergeDoors) {
                minY = Math.min(doorToMerge.getMinY(), minY);
                maxY = Math.max(doorToMerge.getMaxY(), maxY);
            }

            mergedDoor = new Door(x, minY, x, maxY);

        } else {
            int minX = door.getMinX();
            int maxX = door.getMaxX();
            int y = door.getStart().y;

            for (Door doorToMerge: mergeDoors) {
                minX = Math.min(doorToMerge.getMinX(), minX);
                maxX = Math.max(doorToMerge.getMaxX(), maxX);
            }

            mergedDoor = new Door(minX, y, maxX, y);
        }

        if (!isDoorValid(mergedDoor)) {
            return false;
        }

        mergeDoors.add(door);
        spritesPanel.getSprites().removeAll(mergeDoors);
        spritesPanel.add(mergedDoor);

        return true;
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        updateLine(SnapCalculator.calcSnap(e.getPoint()));
    }

    @Override
    public void onMouseClicked(MouseEvent e) {
        
    }
}
