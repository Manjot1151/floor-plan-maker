package src.view.buttons;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.List;

import src.snap.SnapCalculator;
import src.view.Canvas;
import src.view.Drawable;
import src.view.ShapesPanel;
import src.view.ToolButton;

public class MoveButton extends ToolButton {
    private final ShapesPanel shapesPanel = Canvas.getInstance().getShapesPanel();
    private Point start;
    
    public MoveButton() {
        super("Move");
    }

    @Override
    public void onMouseClicked(MouseEvent e) {

    }

    @Override
    public void onMousePressed(MouseEvent e) {
        start = SnapCalculator.calcSnap(e.getPoint());
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        moveShapes(e.getPoint());
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        moveShapes(e.getPoint());
    }

    public void moveShapes(Point p) {
        List<Drawable> selectedShapes = shapesPanel.getSelectedShapes();
        Point end = SnapCalculator.calcSnap(p);
        int dx = end.x - start.x;
        int dy = end.y - start.y;
        shapesPanel.getSelectedShapes().forEach(shape -> shape.setLocation(shape.getX() + dx, shape.getY() + dy));
        for (Drawable shape : selectedShapes) {
            if (shapesPanel.isIntersecting(shape)) {
                shapesPanel.getSelectedShapes().forEach(s -> s.setLocation(s.getX() - dx, s.getY() - dy));
                return;
            }
        }
        shapesPanel.repaint();
        start = end;
    }
}
