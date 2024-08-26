package src.view.buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import src.view.ToolButton;

public class WallButton extends ToolButton {

    private static ArrayList<Integer> pointsX = new ArrayList<Integer>();
    private static ArrayList<Integer> pointsY = new ArrayList<Integer>();

    public WallButton() {
        super("Wall");
    }

    @Override
    public void onMousePressed(MouseEvent e, Graphics g) {
        addPoint(e.getX(), e.getY());
    }

    @Override
    public void onMouseReleased(MouseEvent e, Graphics g) {
        addPoint(e.getX(), e.getY());

        g.setColor(Color.BLACK);

        for (int i = 0; i < pointsX.size(); i += 2) {
            g.drawLine(pointsX.get(i), pointsY.get(i), pointsX.get(i + 1), pointsY.get(i + 1));
        }
    }

    private static void addPoint(int x, int y) {
        pointsX.add(x);
        pointsY.add(y);
    }
}
