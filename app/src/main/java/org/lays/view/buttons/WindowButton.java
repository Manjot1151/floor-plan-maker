package org.lays.view.buttons;

import org.lays.view.Window;


public class WindowButton extends EdgeDrawableButton<Window, Window.Factory> {

    public WindowButton() {
        super("Window", new Window.Factory(), Window.class);
    }

}
