package org.lays.view.buttons;

import java.awt.event.MouseEvent;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.lays.snap.SnapCalculator;
import org.lays.view.Canvas;
import org.lays.view.Furniture;
import org.lays.view.FurnitureType;
import org.lays.view.ToolButton;
import org.lays.view.panels.SpritesLayer;

import java.awt.Dimension;
import java.awt.Point;

public class FurnitureButton extends ToolButton {
    private final SpritesLayer spritesLayer = Canvas.getInstance().getSpritesLayer();

    private Furniture currentFurniture;
    private FurnitureType currentFurnitureType;
    private static JComboBox<FurnitureType> furnitureTypeComboBox;

    public FurnitureButton() {
        super("Furniture");

        furnitureTypeComboBox = new JComboBox<>(FurnitureType.values());
        furnitureTypeComboBox.setFocusable(false);
        furnitureTypeComboBox.setPreferredSize(new Dimension(125, 25));
        this.currentFurnitureType = (FurnitureType) furnitureTypeComboBox.getSelectedItem();
        furnitureTypeComboBox.addActionListener(e -> this.currentFurnitureType = (FurnitureType) furnitureTypeComboBox.getSelectedItem());
    }

    public static JComboBox<FurnitureType> getFurnitureTypeComboBox() {
        return furnitureTypeComboBox;
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        Point point = SnapCalculator.calcSnap(e.getPoint());
        currentFurniture = new Furniture(point.x, point.y, currentFurnitureType);
        spritesLayer.add(currentFurniture);
        spritesLayer.getView().repaint();
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        Point point = SnapCalculator.calcSnap(e.getPoint());
        currentFurniture.setLocation(point);
        spritesLayer.getView().repaint();
        
        if (!currentFurniture.isValidDrawable()) {
            JOptionPane.showMessageDialog(null, "Removing the furniture...", "Invalid Furniture Placement", JOptionPane.ERROR_MESSAGE);
            spritesLayer.remove(currentFurniture);
            spritesLayer.getView().repaint();
        }
    }


    @Override
    public void onMouseDragged(MouseEvent e) {
        Point point = SnapCalculator.calcSnap(e.getPoint());
        currentFurniture.setLocation(point);
        spritesLayer.getView().repaint();
    }

    @Override
    public void onMouseClicked(MouseEvent e) {
    }
}
