package org.lays.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import org.lays.view.ToolButton;
import org.lays.view.buttons.*;

public class ButtonsPanel extends JPanel {
    public static ToolButton selectedTool;

    public ButtonsPanel(ShapesPanel shapesPanel) {
        super();
        setLayout(new BorderLayout());
        setBackground(new Color(0xa9aad5));
        setPreferredSize(new Dimension(100, 500));
        
        JPanel tools = new JPanel();
        tools.setLayout(new FlowLayout());
        tools.setPreferredSize(new Dimension(100, 200));
        tools.setOpaque(false);

        JPanel togglables = new JPanel();
        togglables.setLayout(new FlowLayout());
        togglables.setPreferredSize(new Dimension(100, 200));
        togglables.setOpaque(false);

        add(tools, BorderLayout.NORTH);
        add(togglables, BorderLayout.SOUTH);

        // tools
        tools.add(new SelectButton());
        tools.add(new MoveButton());
        tools.add(new RoomButton());

        // togglables
        togglables.add(new SnapButton());        
    }
}