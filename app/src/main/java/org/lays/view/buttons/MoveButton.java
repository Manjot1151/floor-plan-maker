package org.lays.view.buttons;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.List;

import org.lays.snap.SnapCalculator;
import org.lays.view.Canvas;
import org.lays.view.Drawable;
import org.lays.view.ToolButton;
import org.lays.view.panels.ShapesPanel;

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
