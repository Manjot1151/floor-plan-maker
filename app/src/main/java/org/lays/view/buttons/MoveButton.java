package org.lays.view.buttons;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.JOptionPane;
import org.lays.snap.SnapCalculator;
import org.lays.view.Canvas;
import org.lays.view.Drawable;
import org.lays.view.ToolButton;
import org.lays.view.panels.RoomsLayer;
import org.lays.view.panels.SpritesLayer;

public class MoveButton extends ToolButton {
    private final RoomsLayer roomsLayer = Canvas.getInstance().getRoomsLayer();
    private final SpritesLayer spritesLayer = Canvas.getInstance().getSpritesLayer();
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
        roomsLayer.getSelectedRooms().forEach(shape -> shapeStarts.put(shape, new Point(shape.getX(), shape.getY())));
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        moveShapes(e.getPoint());
    }

    public boolean validateMove(){
        return roomsLayer.checkForOverlap() && spritesLayer.checkForOverlap() && spritesLayer.validateSpritePlacement();

    }


    @Override
    public void onMouseReleased(MouseEvent e) {
        if (!validateMove()) {
            JOptionPane.showMessageDialog(null, "Moving Shapes back to their previous location", "Misalignment Detected", JOptionPane.ERROR_MESSAGE);
            abortMove();
        }

        // roomsLayer.getView().repaint();
        spritesLayer.getView().repaint();
        shapeStarts.clear();
    }


    public void abortMove() {
        roomsLayer.getSelectedRooms().forEach(s -> s.setLocation(shapeStarts.get(s)));
    }

    public void moveShapes(Point p) {
        Point end = SnapCalculator.calcSnap(p);
        int dx = end.x - start.x;
        int dy = end.y - start.y;

        roomsLayer.getSelectedRooms()
                .forEach(shape -> shape.setLocation((int) shapeStarts.get(shape).getX() + dx,
                        (int) shapeStarts.get(shape).getY() + dy));

        roomsLayer.getView().repaint();
    }
}
