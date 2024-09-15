package src.view.buttons;

import src.snap.SnapCalculator;
import src.view.ToggleButton;

public class SnapButton extends ToggleButton{

    public SnapButton() {
        super("Snap", true);
    }

    @Override
    public void toggle() {
        SnapCalculator.toggleSnapEnabled();
    }
}
