package org.lays.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JMenuBar;
import javax.swing.JPanel;

import org.lays.io.Open;
import org.lays.io.Save;
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
        
        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new FlowLayout());
        menuBar.add(new Save());
        menuBar.add(new Open());
        mainPanel.add(menuBar, BorderLayout.NORTH);

        add(mainPanel);
    }
}
