package org.lays.view.buttons;

import java.awt.Point;
import java.awt.event.MouseEvent;

import org.lays.snap.SnapCalculator;
import org.lays.view.ToolButton;
import org.lays.view.Drawable;
import org.lays.view.panels.SpritesLayer;
import org.lays.view.Canvas;
import org.lays.view.RoomEdgeDrawable;
import org.lays.view.EdgeDrawableFactory;

import java.util.ArrayList;

public abstract class EdgeDrawableButton<T extends RoomEdgeDrawable, F extends EdgeDrawableFactory<T>> extends ToolButton {
    protected static SpritesLayer spritesPanel = Canvas.getInstance().getSpritesLayer();
    private T currentEdgeDrawable;
    private F factory;
    Class<T> clasz;

    public EdgeDrawableButton(String name, F factory, Class<T> clasz) {
        super(name);
        this.factory = factory;
        this.clasz = clasz;
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        Point start = SnapCalculator.calcSnap(e.getPoint());
        this.currentEdgeDrawable = factory.fromPoints(start, start);
        spritesPanel.add(currentEdgeDrawable);
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        currentEdgeDrawable.setEnd(SnapCalculator.calcSnap(e.getPoint()));
        if (!currentEdgeDrawable.isValidDrawable()) {
            spritesPanel.remove(currentEdgeDrawable);
        } else {
            mergeDrawableIfPossible(currentEdgeDrawable);
        }
        System.out.println(spritesPanel.getSprites().size());

        spritesPanel.getView().repaint();
    }

    protected void update(Point end) {
        currentEdgeDrawable.setEnd(end);
        spritesPanel.getView().repaint();
    }

    public boolean mergeDrawableIfPossible(T edgeDrawable) {
        ArrayList<T> mergeEdgeDrawables = new ArrayList<T>();

        for (Drawable sprite: spritesPanel.getSprites()) {
            if (clasz.isInstance(sprite)) {
                T testDrawable = clasz.cast(sprite);

                if (!testDrawable.equals(edgeDrawable) && testDrawable.intersects(edgeDrawable)) {
                    mergeEdgeDrawables.add(testDrawable);
                }
            }
        }

        if (mergeEdgeDrawables.isEmpty()) {
            return false;
        }

        T mergedEdgeDrawable;
        if (edgeDrawable.isVertical()) {
            int minY = edgeDrawable.getMinY();
            int maxY = edgeDrawable.getMaxY();
            int x = edgeDrawable.getStart().x;

            for (T windowToMerge: mergeEdgeDrawables) {
                minY = Math.min(windowToMerge.getMinY(), minY);
                maxY = Math.max(windowToMerge.getMaxY(), maxY);
            }

            mergedEdgeDrawable = factory.fromCoordinates(x, minY, x, maxY);

        } else {
            int minX = edgeDrawable.getMinX();
            int maxX = edgeDrawable.getMaxX();
            int y = edgeDrawable.getStart().y;

            for (T edgeDrawableToMerge: mergeEdgeDrawables) {
                minX = Math.min(edgeDrawableToMerge.getMinX(), minX);
                maxX = Math.max(edgeDrawableToMerge.getMaxX(), maxX);
            }

            mergedEdgeDrawable = factory.fromCoordinates(minX, y, maxX, y);
        }

        if (!currentEdgeDrawable.isValidDrawable()) {
            return false;
        }

        mergeEdgeDrawables.add(edgeDrawable);
        spritesPanel.getSprites().removeAll(mergeEdgeDrawables);
        spritesPanel.add(mergedEdgeDrawable);

        return true;
    }


    @Override
    public void onMouseDragged(MouseEvent e) {
        update(SnapCalculator.calcSnap(e.getPoint()));
    }

    @Override
    public void onMouseClicked(MouseEvent e) {
        
    }
}

