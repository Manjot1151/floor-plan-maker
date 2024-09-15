package src.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public abstract class ToolButton extends JButton {
    private static Color unselectedColor = new Color(0x4e5090);
    private static Color selectedColor = new Color(0x3a285a);

    public ToolButton(String name) {
        super(name);
        setPreferredSize(new Dimension(80, 50));
        setBackground(unselectedColor);
        setForeground(new Color(0xf8e4e9));
        setFocusable(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Toolbar.selectedTool == ToolButton.this) {
                    Toolbar.selectedTool.setBackground(unselectedColor);
                    Toolbar.selectedTool = null;
                    return;
                }
                if (Toolbar.selectedTool != null) {
                    Toolbar.selectedTool.setBackground(unselectedColor);
                }
                Toolbar.selectedTool = ToolButton.this;
                setBackground(selectedColor);
            }
        });
    }

    public abstract void onMouseClicked(MouseEvent e);
    public abstract void onMousePressed(MouseEvent e);
    public abstract void onMouseDragged(MouseEvent e);
    public abstract void onMouseReleased(MouseEvent e);
}
