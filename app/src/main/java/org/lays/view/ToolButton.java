package org.lays.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import org.lays.view.panels.ButtonsPanel;

public abstract class ToolButton extends JButton {
    private static Color unselectedColor = new Color(0x4e5090);
    private static Color selectedColor = new Color(0x3a285a);

    public ToolButton(String name) {
        super(name);
        setPreferredSize(new Dimension(100, 50));
        setBackground(unselectedColor);
        setForeground(new Color(0xf8e4e9));
        setFocusable(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (ButtonsPanel.selectedTool == ToolButton.this) {
                    ButtonsPanel.selectedTool.setBackground(unselectedColor);
                    ButtonsPanel.selectedTool = null;
                    return;
                }
                if (ButtonsPanel.selectedTool != null) {
                    ButtonsPanel.selectedTool.setBackground(unselectedColor);
                }
                ButtonsPanel.selectedTool = ToolButton.this;
                setBackground(selectedColor);
            }
        });
    }

    public abstract void onMouseClicked(MouseEvent e);
    public abstract void onMousePressed(MouseEvent e);
    public abstract void onMouseDragged(MouseEvent e);
    public abstract void onMouseReleased(MouseEvent e);
}
