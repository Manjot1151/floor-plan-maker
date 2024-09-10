package src.view.buttons;

import java.awt.event.MouseEvent;

import src.view.Canvas;
import src.view.Room;
import src.view.ToolButton;

public class RoomButton extends ToolButton {
    private int startX;
    private int startY;
    private Room currentRoom;

    public RoomButton() {
        super("Room");
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        startX = Canvas.snapIndicator.x;
        startY = Canvas.snapIndicator.y;
        currentRoom = new Room(startX, startY, 0, 0);
        Canvas.shapesPanel.addShape(currentRoom);
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        updateRoom(Canvas.snapIndicator.x, Canvas.snapIndicator.y);

        boolean isRoomInvalid = currentRoom.getWidth() == 0 || currentRoom.getHeight() == 0
                || Canvas.shapesPanel.isIntersecting(currentRoom);

        if (isRoomInvalid) {
            Canvas.shapesPanel.removeShape(currentRoom);
            Canvas.shapesPanel.repaint();
            currentRoom = null;
        }
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        updateRoom(Canvas.snapIndicator.x, Canvas.snapIndicator.y);
    }

    private void updateRoom(int curX, int curY) {
        int x = Math.min(startX, curX);
        int y = Math.min(startY, curY);
        int width = Math.abs(curX - startX);
        int height = Math.abs(curY - startY);
        currentRoom.setBounds(x, y, width, height);
        Canvas.shapesPanel.repaint();
    }
}
