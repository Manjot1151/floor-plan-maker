package src.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class MainPane extends JPanel {
    private Toolbar toolbar;
    private Canvas canvas;

    public Canvas getCanvas() {
        return canvas;
    }

    public MainPane() {
        setLayout(new BorderLayout());

        canvas = Canvas.getInstance();
        toolbar = new Toolbar(canvas.getShapesPanel());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(toolbar, BorderLayout.WEST);
        mainPanel.add(canvas, BorderLayout.CENTER);
        
        add(mainPanel);
    }
}
