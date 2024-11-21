package org.lays.view.buttons;

import org.lays.view.Canvas;
import org.lays.view.Grid;
import org.lays.view.ToggleButton;

public class GridButton extends ToggleButton {
    public GridButton() {
        super("Grid", true);
    }

    @Override
    public void toggle() {
        Grid grid = Canvas.getInstance().getGrid();
        grid.setVisible(!grid.isVisible());
    }
}
