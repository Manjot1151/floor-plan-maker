package src.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import src.view.buttons.*;


class Toolbar extends JPanel {
    public static ToolButton selectedTool;

    public Toolbar() {
        super();
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(100, 100));

        JPanel tools = new JPanel();
        tools.setLayout(new FlowLayout());
        tools.add(new SelectButton());
        tools.add(new WallButton());
        tools.setBackground(new Color(0xa9aad5));

        add(tools, BorderLayout.CENTER);
    }
}
