package org.lays.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.lays.view.ToolButton;
import org.lays.view.buttons.*;

public class ButtonsPanel extends JPanel {
    public static ToolButton selectedTool;

    public ButtonsPanel() {
        super();
        setLayout(new BorderLayout());
        setBackground(new Color(0xa9aad5));
        setPreferredSize(new Dimension(150, 500));
        
        JPanel tools = new JPanel();
        tools.setLayout(new FlowLayout());
        tools.setPreferredSize(new Dimension(150, 200));
        tools.setOpaque(false);

        JPanel options = new JPanel();
        options.setLayout(new FlowLayout());
        options.setPreferredSize(new Dimension(150, 200));
        options.setOpaque(false);

        JPanel togglables = new JPanel();
        togglables.setLayout(new FlowLayout());
        togglables.setPreferredSize(new Dimension(150, 200));
        togglables.setOpaque(false);

        add(tools, BorderLayout.NORTH);
        add(togglables, BorderLayout.CENTER);
        add(options, BorderLayout.SOUTH);

        // tools
        tools.add(new SelectButton());
        tools.add(new MoveButton());
        tools.add(new RoomButton());
        tools.add(new DoorButton());

        // togglables
        togglables.add(new SnapButton());
        
        // options
        options.add(new JLabel("Room type:"));
        options.add(RoomButton.getRoomTypeComboBox());
    }
}