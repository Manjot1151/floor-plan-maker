package org.lays.view.buttons;

import org.lays.view.Window;
import org.lays.view.Drawable;
import org.lays.view.panels.RoomsLayer;
import org.lays.view.Canvas;
import org.lays.view.Room;


public class WindowButton extends EdgeDrawableButton<Window, Window.Factory> {
    private static RoomsLayer roomsPanel  = Canvas.getInstance().getRoomsLayer();

    public WindowButton() {
        super("Window", new Window.Factory(), Window.class);
    }

    @Override
    public boolean isValidDrawable(Window window) {
        if (window.isPoint()) {
            return false;
        }

        int n_intersects = 0;
        for (Room room : roomsPanel.getRooms()) {
            if (window.intersects(room)) {
                n_intersects += 1;

                if (n_intersects > 1 || !window.isValidOnRoom(room)) {
                    return false;
                }
            }
        }

        for (Drawable drawable: spritesPanel.getSprites()) {
            if (!drawable.equals(window) && drawable.intersects(window) && !(drawable instanceof Window)) {
                return false;
            }
        }

        return n_intersects != 0;
    }
}
