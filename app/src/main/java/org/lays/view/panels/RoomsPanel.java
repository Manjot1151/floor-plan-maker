package org.lays.view.panels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.lays.view.Drawable;
import org.lays.view.Room;

public class RoomsPanel extends JPanel {
    private static ArrayList<Room> rooms;

    public RoomsPanel() {
        super();
        setLayout(null);
        rooms = new ArrayList<Room>();
    }

    public void addRoom(Room room) {
        rooms.add(room);
        room.setVisible(true);

        repaint();
    }

    public void removeRoom(Room room) {
        rooms.remove(room);
        room.setVisible(false);

        repaint();
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

    @Override
    public void paintComponent(Graphics g) {
        Rectangle bounds = g.getClipBounds();
        BufferedImage bufferedImage = new BufferedImage((int)bounds.getWidth(), (int)bounds.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics gbuf = bufferedImage.createGraphics();
        for (Room room : rooms) {
            Graphics2D g2d = (Graphics2D) gbuf.create();
            room.paintShape(g2d);
            g2d.dispose();
        }

        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(bufferedImage,0, 0,this);
    }
}