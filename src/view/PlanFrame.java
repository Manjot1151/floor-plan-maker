package src.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class PlanFrame extends JFrame {
    private static PlanFrame INSTANCE;
    private PlanFrame() {
        super("2D Floor Plan Maker");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        add(new Toolbar(), BorderLayout.WEST);
        add(new Canvas(), BorderLayout.CENTER);
    }
    public static PlanFrame getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlanFrame();
        }
        return INSTANCE;
    }
}
