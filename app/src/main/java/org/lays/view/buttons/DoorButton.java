package org.lays.view.buttons;

import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseEvent;

import org.lays.snap.SnapCalculator;
import org.lays.view.ToolButton;
import org.lays.view.Door;
import org.lays.view.panels.RoomsPanel;
import org.lays.view.panels.SpritesPanel;
import org.lays.view.Canvas;
import org.lays.view.Room;

public class DoorButton extends ToolButton {
    private SpritesPanel spritesPanel = Canvas.getInstance().getSpritesPanel();
    private RoomsPanel roomsPanel = Canvas.getInstance().getRoomsPanel();
    private Door currentDoor;

    public DoorButton() {
        super("Wall");
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        Point start = SnapCalculator.calcSnap(e.getPoint());
        this.currentDoor = new Door(start, start);
        spritesPanel.addSprite(currentDoor);
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        updateLine(SnapCalculator.calcSnap(e.getPoint()));
        validateWall();
    }

    private void updateLine(Point end) {
        currentDoor.setEnd(end);
        spritesPanel.repaint();
    }

    private void validateWall() {
        Shape line = currentDoor.getShape();

        int n_intersects = 0;
        boolean invalidate = false;
        for (Room room : roomsPanel.getRooms()) {
            if (line.intersects(room.getBounds())) {
                n_intersects += 1;
                boolean checkVertical = 
                    currentDoor.isVertical() && 
                    room.isOnVerticalEdge(currentDoor.getStart()) && 
                    room.isOnVerticalEdge(currentDoor.getEnd());

                boolean checkHorizontal = 
                    currentDoor.isHorizontal() && 
                    room.isOnHorizontalEdge(currentDoor.getStart()) && 
                    room.isOnHorizontalEdge(currentDoor.getEnd());

                if (!checkHorizontal && !checkVertical) {
                    invalidate = true;
                    break;
                }
            }
        }

        invalidate = invalidate || n_intersects == 0;

        if (invalidate) {
            spritesPanel.removeSprite(currentDoor);
            spritesPanel.repaint();
        }
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        updateLine(SnapCalculator.calcSnap(e.getPoint()));
    }

    @Override
    public void onMouseClicked(MouseEvent e) {
        
    }
}
