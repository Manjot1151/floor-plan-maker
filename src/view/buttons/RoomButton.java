package src.view.buttons;

import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.Rectangle;
import javax.swing.JComponent;



import src.view.Room;
import src.view.ToolButton;
import src.view.Drawable;

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
        int width = x1 - e.getX();
        int height = e.getY() - y1;

        return new Room(x1, y1, width, height);
    }
}
