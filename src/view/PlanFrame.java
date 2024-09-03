package src.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;


import src.view.Grid;
public class PlanFrame extends JFrame {
    static PlanFrame INSTANCE;

    private PlanFrame() {
        super("2D Floor Plan Maker");
        setSize(800,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        setContentPane(new MainPane());

        setVisible(true);
        pack();
    }

    public static PlanFrame getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlanFrame();
        }
        return INSTANCE;
    }
}
