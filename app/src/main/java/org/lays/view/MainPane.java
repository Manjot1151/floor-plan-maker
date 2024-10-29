package org.lays.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import org.lays.view.panels.ButtonsPanel;

public class MainPane extends JPanel {
    private ButtonsPanel buttonsPanel;
    private Canvas canvas;

    public Canvas getCanvas() {
        return canvas;
    }

    public MainPane() {
        setLayout(new BorderLayout());

        canvas = Canvas.getInstance();
        buttonsPanel = new ButtonsPanel();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(buttonsPanel, BorderLayout.WEST);
        mainPanel.add(canvas, BorderLayout.CENTER);
        
        add(mainPanel);
    }
}
