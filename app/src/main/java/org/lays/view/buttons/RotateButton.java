package org.lays.view.buttons;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.lays.view.Canvas;
import org.lays.view.Room;
import org.lays.view.Sprite;
import org.lays.view.ToolButton;
import org.lays.view.panels.GraphicsPanel;
import org.lays.view.panels.RoomsLayer;
import org.lays.view.panels.SpritesLayer;

public class RotateButton extends ToolButton {
    private final GraphicsPanel graphicsPanel = Canvas.getInstance().getGraphicsPanel() ;
    private final SpritesLayer spritesLayer = graphicsPanel.getSpritesLayer();
    private final RoomsLayer roomsLayer  = graphicsPanel.getRoomsLayer();
    
    public RotateButton() {
        super("Rotate");
    }

    private void rotateSpriteAndValidate(Sprite sprite, int numQuadrants) {
        sprite.rotate(numQuadrants);

        graphicsPanel.repaint();

        if (!sprite.isValidDrawable()) {
            JOptionPane.showMessageDialog(null, "Moving Shape Back to Previous Position", "Misalignment Detected", JOptionPane.ERROR_MESSAGE);
            sprite.rotate(-numQuadrants);
            graphicsPanel.repaint();
        }
    }

    private void rotateRoomAndValidate(Room room, int numQuadrants) {
        ArrayList<Sprite> ownedSprites = room.getOwnedSprites();
        room.rotate(numQuadrants);
        for (Sprite sprite : ownedSprites) {
            sprite.rotateOnRoom(room, numQuadrants);
        }
        graphicsPanel.repaint();

        if (!graphicsPanel.validateCanvas()) {
            JOptionPane.showMessageDialog(null, "Moving Shape Back to Previous Position", "Misalignment Detected", JOptionPane.ERROR_MESSAGE);
            room.rotate(-numQuadrants);
            for (Sprite sprite : ownedSprites) {
                sprite.rotateOnRoom(room, -numQuadrants);
            }
            graphicsPanel.repaint();
        }
    }

    @Override
    public void onMouseClicked(MouseEvent e) {
        Point point = e.getPoint();
        Sprite clickedSprite = spritesLayer.getClickedSprite(point);

        if (clickedSprite != null) {
            rotateSpriteAndValidate(clickedSprite, 1);
            return;
        }


        Room clickedRoom = roomsLayer.getClickedRoom(point);
        if (clickedRoom == null) {
            return;
        }

        rotateRoomAndValidate(clickedRoom, 1);
    }


    @Override
    public void onMousePressed(MouseEvent e) {
        
    }

    @Override
    public void onMouseDragged(MouseEvent e) {

    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        
    }

    
}
