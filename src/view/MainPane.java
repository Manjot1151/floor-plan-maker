package src.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class MainPane extends JPanel {
    public MainPane() {
        setLayout(new BorderLayout());

        Toolbar toolbar = new Toolbar();

        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(toolbar, BorderLayout.WEST);
        mainPanel.add(new Canvas(), BorderLayout.CENTER);
        
        add(mainPanel);
    }
}
