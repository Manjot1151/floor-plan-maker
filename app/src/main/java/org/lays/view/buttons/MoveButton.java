package org.lays.view.buttons;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.JOptionPane;
import org.lays.snap.SnapCalculator;
import org.lays.view.Canvas;
import org.lays.view.Drawable;
import org.lays.view.ToolButton;
import org.lays.view.panels.ShapesPanel;

public class MoveButton extends ToolButton {
    private final ShapesPanel shapesPanel = Canvas.getInstance().getShapesPanel();
    private Point start;
    private HashMap<Drawable, Point> shapeStarts = new HashMap<>();

    public MoveButton() {
        super("Move");
    }

    @Override
    public void onMouseClicked(MouseEvent e) {

    }

    @Override
    public void onMousePressed(MouseEvent e) {
        start = SnapCalculator.calcSnap(e.getPoint());
        shapesPanel.getSelectedShapes().forEach(shape -> shapeStarts.put(shape, new Point(shape.getX(), shape.getY())));
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        moveShapes(e.getPoint());
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        for (Drawable shape : shapesPanel.getSelectedShapes()) {
            if (shapesPanel.isIntersecting(shape)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Moving shapes back to their previous location...",
                        "Overlap Detected",
                        JOptionPane.ERROR_MESSAGE);
                shapesPanel.getSelectedShapes().forEach(s -> s.setLocation(shapeStarts.get(s)));
                break;
            }
        }
        shapesPanel.repaint();
        shapeStarts.clear();
    }

    public void moveShapes(Point p) {
        Point end = SnapCalculator.calcSnap(p);
        int dx = end.x - start.x;
        int dy = end.y - start.y;

        shapesPanel.getSelectedShapes()
                .forEach(shape -> shape.setLocation((int) shapeStarts.get(shape).getX() + dx,
                        (int) shapeStarts.get(shape).getY() + dy));

        shapesPanel.repaint();
    }
}
