package org.lays.view.buttons;

import org.lays.view.Door;


public class DoorButton extends EdgeDrawableButton<Door, Door.Factory> {
    public DoorButton() {
        super("Door", new Door.Factory(), Door.class);
    }
}
