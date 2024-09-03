package src.view;

import java.awt.*;
import javax.swing.*;
import src.view.*;

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
