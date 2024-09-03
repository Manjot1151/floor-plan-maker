package src.view;

import javax.swing.JFrame;
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
    }

    public static PlanFrame getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlanFrame();
        }
        return INSTANCE;
    }
}
