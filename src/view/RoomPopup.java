package src.view;

import java.awt.Component;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import src.view.Room;

enum AlignEdgeDirection {
    North (
        new JMenuItem("North")
    ),
    South(
        new JMenuItem("South")
    ),
    West(
        new JMenuItem("West")
    ),
    East(
        new JMenuItem("East")
    );

    public JMenuItem menuItem;
    private AlignEdgeDirection(JMenuItem item) {
        this.menuItem = item;
    }
}



public class RoomPopup extends JPopupMenu {
    private Room room;
    public RoomPopup(Room room) {
        this.room = room;
        for (AlignEdgeDirection d: AlignEdgeDirection.values()) {
            add(d.menuItem);
        }
    }
}
