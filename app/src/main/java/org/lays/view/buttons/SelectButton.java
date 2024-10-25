package org.lays.view.buttons;

import java.awt.event.MouseEvent;

import org.lays.snap.SnapCalculator;
import org.lays.view.Canvas;
import org.lays.view.Drawable;
import org.lays.view.ToolButton;
import org.lays.view.panels.ShapesPanel;

public class SelectButton extends ToolButton {
    private final ShapesPanel shapesPanel = Canvas.getInstance().getShapesPanel();
    
    public SelectButton() {
        super("Select");
    }

    @Override
    public void onMouseClicked(MouseEvent e) {
        Drawable clickedShape = shapesPanel.getClickedShape(SnapCalculator.calcSnap(e.getPoint()));
        if (clickedShape == null) {
            shapesPanel.getShapes().forEach(shape -> shape.setSelected(false));
            shapesPanel.repaint();
            return;
        }

        clickedShape.setSelected(!clickedShape.isSelected());
        shapesPanel.repaint();
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        shapesPanel.getShapes().forEach(shape -> shape.setSelected(false));
        
        // TODO: Implement selection box
        
        shapesPanel.repaint();
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        
    }

    
}
