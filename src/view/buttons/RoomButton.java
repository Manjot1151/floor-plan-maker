package src.view.buttons;

import java.awt.event.MouseEvent;

import src.snap.SnapCalculator;
import src.view.Room;
import src.view.ShapesPanel;
import src.view.ToolButton;
import java.awt.Point;

public class RoomButton extends ToolButton {
    private Point rectStart;
    private ShapesPanel shapesPanel;
    private Room currentRoom;

    public RoomButton(ShapesPanel shapesPanel) {
        super("Room");
        this.shapesPanel = shapesPanel;
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        rectStart = SnapCalculator.calcSnap(e.getPoint());
        currentRoom = new Room(rectStart.x, rectStart.y, 0, 0);
        shapesPanel.addShape(currentRoom);
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        Point rectEnd = SnapCalculator.calcSnap(e.getPoint());
        updateRoom(rectEnd);

        boolean isRoomInvalid = currentRoom.getWidth() == 0 || currentRoom.getHeight() == 0 || shapesPanel.isIntersecting(currentRoom);
        if (isRoomInvalid) {
            shapesPanel.removeShape(currentRoom);
            shapesPanel.repaint();
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
        shapesPanel.repaint();
    }
}
