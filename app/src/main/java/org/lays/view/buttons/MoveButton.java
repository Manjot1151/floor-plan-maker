package org.lays.view.buttons;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.HashMap;

import javax.swing.JOptionPane;
import org.lays.snap.SnapCalculator;
import org.lays.view.Canvas;
import org.lays.view.Drawable;
import org.lays.view.Sprite;
import org.lays.view.ToolButton;
import org.lays.view.panels.GraphicsPanel;
import org.lays.view.panels.SpritesLayer;

public class MoveButton extends ToolButton {
    private GraphicsPanel graphicsPanel = Canvas.getInstance().getGraphicsPanel();
    private SpritesLayer spritesLayer = graphicsPanel.getSpritesLayer();
    private Point start;
    private HashMap<Drawable, Point2D> moveItemStarts = new HashMap<>();

    public MoveButton() {
        super("Move");
    }

    @Override
    public void onMouseClicked(MouseEvent e) {

    }

    @Override
    public void onMousePressed(MouseEvent e) {
        start = SnapCalculator.calcSnap(e.getPoint());

        graphicsPanel.getSelectedItems().forEach(drawable -> moveItemStarts.put(drawable, drawable.getLocation()));

        for (Sprite sprite: spritesLayer.getSprites()) {
            if (sprite.shouldSoftSelect()) {
                moveItemStarts.put(sprite, sprite.getLocation());
            }
        }
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        moveShapes(e.getPoint());
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        if (!graphicsPanel.validateCanvas()) {
            JOptionPane.showMessageDialog(null, "Moving Shapes back to their previous location", "Misalignment Detected", JOptionPane.ERROR_MESSAGE);
            abortMove();
        }

        // roomsLayer.getView().repaint();
        graphicsPanel.repaint();
        moveItemStarts.clear();
    }

    public void abortMove() {
        moveItemStarts.entrySet().forEach(entry -> entry.getKey().setLocation(entry.getValue()));
    }

    public void setSoftSelects() {
    }

    public void moveShapes(Point p) {
        Point end = SnapCalculator.calcSnap(p);
        int dx = end.x - start.x;
        int dy = end.y - start.y;


        moveItemStarts.entrySet().forEach(entry -> {
            Point2D startPoint = (Point2D)entry.getValue().clone();
            entry.getKey().setLocation(new Point2D.Double(startPoint.getX() + dx, startPoint.getY() + dy));
        });

        graphicsPanel.repaint();
    }
}
