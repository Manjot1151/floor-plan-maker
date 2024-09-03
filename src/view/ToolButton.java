package src.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComponent;

import src.view.Drawable;

public abstract class ToolButton extends JButton {
    private static Color unselectedColor = new Color(0x4e5090);
    private static Color selectedColor = new Color(0x3a285a);
    public ToolButton(String name) {
        super(name);
        setPreferredSize(new Dimension(80, 50));
        setBackground(unselectedColor);
        setForeground(new Color(0xf8e4e9));
        setFocusable(false);
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (Toolbar.selectedTool == ToolButton.this) {
                    return;
                }
                if (Toolbar.selectedTool != null) {
                    Toolbar.selectedTool.setBackground(unselectedColor);
                }
                Toolbar.selectedTool = ToolButton.this;
                setBackground(selectedColor);
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {}
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {}
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {}
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {}
        });
    }

    public abstract void onMousePressed(MouseEvent e);
    public abstract Drawable onMouseReleased(MouseEvent e);
}
