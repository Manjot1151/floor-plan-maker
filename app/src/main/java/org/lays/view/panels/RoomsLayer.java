package org.lays.view.panels;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.lays.view.Room;

public class RoomsLayer {
    private ArrayList<Room> rooms;
    private JPanel view;

    public RoomsLayer(JPanel view) {
        rooms = new ArrayList<Room>();
        this.view = view;
    }

    public JPanel getView() {
        return view;
    }

    public void add(Room room) {
        rooms.add(room);

        view.repaint();
    }

    public void remove(Room room) {
        rooms.remove(room);

        view.repaint();
    }

    public boolean checkForOverlap() {
        int n = rooms.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (rooms.get(i).intersects(rooms.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isIntersecting(Room targetRoom) {
        for (Room room : rooms) {
            if (room == targetRoom) {
                continue;
            }

            if (room.intersects(targetRoom)) {
                return true;
            }
        }
        return false;
    }

    public Room getClickedRoom(Point p) {
        for (Room room : rooms) {
            if (room.getBounds().contains(p)) {
                return room;
            }
        }
        return null;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public ArrayList<Room> getSelectedRooms() {
        ArrayList<Room> selectedShapes = new ArrayList<Room>();
        for ( Room r : getRooms()) {
            if (r.isSelected()) {
                selectedShapes.add(r);
            }
        }
        return selectedShapes;
    }

    public void paintLayer(Graphics g) {
        for (Room room : rooms) {
            room.paintShape(g);
        }
    }
}