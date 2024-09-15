package src.view.buttons;

import java.awt.event.MouseEvent;

import src.snap.SnapCalculator;
import src.view.Canvas;
import src.view.Drawable;
import src.view.ShapesPanel;
import src.view.ToolButton;

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
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        
    }

    
}
