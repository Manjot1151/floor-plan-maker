package org.lays.view.panels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.lays.view.Drawable;
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
        room.setVisible(true);

        view.repaint();
    }

    public void remove(Room room) {
        rooms.remove(room);
        room.setVisible(false);

        view.repaint();
    }


    public boolean isIntersecting(Drawable targetRoom) {
        for (Drawable room : rooms) {
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
            Graphics2D g2d = (Graphics2D)g.create();
            room.paintShape(g2d);
            g2d.dispose();
        }
    }
}