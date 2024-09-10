package src.view.buttons;

import java.awt.event.MouseEvent;

import src.snap.SnapIndicator;
import src.view.Canvas;
import src.view.Room;
import src.view.ShapesPanel;
import src.view.ToolButton;

public class RoomButton extends ToolButton {
    private int startX;
    private int startY;
    private Room currentRoom;
    private Canvas canvas;

    public RoomButton(Canvas canvas) {
        super("Room");
        this.canvas = canvas;
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        SnapIndicator snapIndicator = canvas.getSnapIndicator();
        startX = snapIndicator.x;
        startY = snapIndicator.y;
        currentRoom = new Room(startX, startY, 0, 0);
        canvas.getShapesPanel().addShape(currentRoom);
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        SnapIndicator snapIndicator = canvas.getSnapIndicator();
        ShapesPanel shapesPanel = canvas.getShapesPanel();

        updateRoom(snapIndicator.x, snapIndicator.y);

        boolean isRoomInvalid = currentRoom.getWidth() == 0 || currentRoom.getHeight() == 0
                || shapesPanel.isIntersecting(currentRoom);

        if (isRoomInvalid) {
            shapesPanel.removeShape(currentRoom);
            shapesPanel.repaint();
            currentRoom = null;
        }
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        SnapIndicator snapIndicator = canvas.getSnapIndicator();
        updateRoom(snapIndicator.x, snapIndicator.y);
    }

    private void updateRoom(int curX, int curY) {
        int x = Math.min(startX, curX);
        int y = Math.min(startY, curY);
        int width = Math.abs(curX - startX);
        int height = Math.abs(curY - startY);
        currentRoom.setBounds(x, y, width, height);
        canvas.getShapesPanel().repaint();
    }
}
