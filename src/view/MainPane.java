package src.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class MainPane extends JPanel {
    public MainPane() {
        setLayout(new BorderLayout());

        Canvas canvas = new Canvas();
        Toolbar toolbar = new Toolbar(canvas);

        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(toolbar, BorderLayout.WEST);
        mainPanel.add(canvas, BorderLayout.CENTER);
        
        add(mainPanel);
    }
}
