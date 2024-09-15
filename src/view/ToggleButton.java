package src.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public abstract class ToggleButton extends JButton {
    private static Color unselectedColor = new Color(0x4e5090);
    private static Color selectedColor = new Color(0x3a285a);

    private boolean enabled = false;

    public ToggleButton(String name, boolean enabled) {
        super(name);
        setPreferredSize(new Dimension(80, 50));
        setBackground(unselectedColor);
        setForeground(new Color(0xf8e4e9));
        setFocusable(false);

        if (enabled) toggleButton();
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleButton();
            }
        });
    }

    private void toggleButton() {
        toggle();

        enabled = !enabled;
        if (enabled) {
            setBackground(selectedColor);
        } else {
            setBackground(unselectedColor);
        }
    }

    public abstract void toggle();
}
