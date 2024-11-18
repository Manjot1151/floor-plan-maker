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

public class DeleteButton extends ToolButton {
    private GraphicsPanel graphicsPanel = Canvas.getInstance().getGraphicsPanel();
    private RoomsLayer roomsLayer = graphicsPanel.getRoomsLayer();
    private SpritesLayer spritesLayer = graphicsPanel.getSpritesLayer();

    public DeleteButton() {
        super("Delete");
    }

    @Override
    public void onMouseClicked(MouseEvent e) {
        Point point = e.getPoint();
        Sprite clickedSprite = spritesLayer.getClickedSprite(point);
        if (clickedSprite != null) {
            spritesLayer.remove(clickedSprite);
            graphicsPanel.repaint();

            if (!graphicsPanel.validateCanvas()) {
                JOptionPane.showMessageDialog(null, "Drawing the deleted shapes back on the canvas", "Misalignment Detected", JOptionPane.ERROR_MESSAGE);
                spritesLayer.add(clickedSprite);
                graphicsPanel.repaint();
            }
            return;
        }

        Room clickedRoom = roomsLayer.getClickedRoom(point);
        
        if (clickedRoom != null) {
            ArrayList<Sprite> ownedSprites = clickedRoom.getOwnedSprites();
            spritesLayer.getSprites().removeAll(ownedSprites);
            roomsLayer.remove(clickedRoom);
            graphicsPanel.repaint();

            if (!graphicsPanel.validateCanvas()) {
                JOptionPane.showMessageDialog(null, "Drawing the deleted shapes back on the canvas", "Misalignment Detected", JOptionPane.ERROR_MESSAGE);
                roomsLayer.add(clickedRoom);
                spritesLayer.getSprites().addAll(ownedSprites);
                graphicsPanel.repaint();
            }
            return;
        }
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
