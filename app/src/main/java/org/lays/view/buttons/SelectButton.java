package org.lays.view.buttons;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import org.lays.view.Canvas;
import org.lays.view.Drawable;
import org.lays.view.ToolButton;
import org.lays.view.panels.RoomsLayer;
import org.lays.view.panels.SpritesLayer;

public class SelectButton extends ToolButton {
    private final SpritesLayer spritesLayer = Canvas.getInstance().getSpritesLayer();
    private final RoomsLayer roomsLayer  = Canvas.getInstance().getRoomsLayer();
    private final JPanel view = roomsLayer.getView();
    
    public SelectButton() {
        super("Select");
    }

    @Override
    public void onMouseClicked(MouseEvent e) {
        Point point = e.getPoint();
        Drawable clickedDrawable = spritesLayer.getClickedSprite(point);

        if (clickedDrawable == null) {
            clickedDrawable = roomsLayer.getClickedRoom(point);
        }

        if (clickedDrawable == null) {
            roomsLayer.getRooms().forEach(room -> room.setSelected(false));
            spritesLayer.getSprites().forEach(sprite -> sprite.setSelected(false));
            view.repaint();
            return;
        }

        clickedDrawable.setSelected(!clickedDrawable.isSelected());
        view.repaint();
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        // RoomsLayer rooms = graphicsPanel.getRoomsLayer();
        // rooms.getRooms().forEach(shape -> shape.setSelected(false));
        
        // // TODO: Implement selection box
        
        // graphicsPanel.repaint();
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        
    }

    
}
