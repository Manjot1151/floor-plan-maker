package src.main;

import src.view.PlanFrame;

public class Main {
    public static void main(String[] args) {
        PlanFrame planFrame = PlanFrame.getInstance();
        planFrame.setVisible(true);
    }
}