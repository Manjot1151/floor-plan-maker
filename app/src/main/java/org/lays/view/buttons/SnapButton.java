package org.lays.view.buttons;

import org.lays.snap.SnapCalculator;
import org.lays.view.ToggleButton;

public class SnapButton extends ToggleButton{

    public SnapButton() {
        super("Snap", true);
    }

    @Override
    public void toggle() {
        SnapCalculator.toggleSnapEnabled();
    }
}
