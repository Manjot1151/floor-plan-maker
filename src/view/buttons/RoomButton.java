package src.view.buttons;

import java.awt.event.MouseEvent;

import src.view.Drawable;
import src.view.Room;
import src.view.ToolButton;

public class RoomButton extends ToolButton {
    private int x1;
    private int y1;

    public RoomButton() {
        super("Room");
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        x1 = e.getX();
        y1 = e.getY();
    }

    @Override
    public Drawable onMouseReleased(MouseEvent e) {
        int width = Math.abs(x1 - e.getX());
        int height = Math.abs(y1 - e.getY());

        return new Room(Math.min(x1, e.getX()), Math.min(y1, e.getY()), width, height);
    }
}
