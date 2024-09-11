package src.snap;

import java.awt.Point;
import src.view.Config;

public class SnapCalculator {
    private static boolean shouldSnap = true;

    // public static void setSnapEnabled(boolean shouldSnap) {
    //     SnapCalculator.shouldSnap = shouldSnap;
    // }

    public static void toggleSnapEnabled() {
        SnapCalculator.shouldSnap = !SnapCalculator.shouldSnap;
    }

    public static boolean isSnapEnabled() {
        return SnapCalculator.shouldSnap;
    }

    public static Point calcSnap(Point mousePosition) {
        if (!SnapCalculator.isSnapEnabled()) {
            return mousePosition;
        }

        int gridSize = Config.getInstance().getGridSize();
        int x = mousePosition.x;
        int y = mousePosition.y;

        x -= calcSnapCorrection(x, gridSize);
        y -= calcSnapCorrection(y, gridSize) ;

        return new Point(x, y);
    }

    private static int calcSnapCorrection(int p, int gridSize) {
        int rem = p % gridSize; 
        return (rem < gridSize - rem)  ? rem : rem - gridSize; 
    }
}
