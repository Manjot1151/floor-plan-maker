package org.lays.view.buttons;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JSlider;

import org.lays.view.Canvas;
import org.lays.view.Config;
import org.lays.view.Drawable;
import org.lays.view.Room;
import org.lays.view.RoomType;
import org.lays.view.Sprite;
import org.lays.view.ToolButton;
import org.lays.view.panels.GraphicsPanel;
import org.lays.view.panels.RoomsLayer;
import org.lays.view.panels.SpritesLayer;

public class AlignButton extends ToolButton {
    private final GraphicsPanel graphicsPanel = Canvas.getInstance().getGraphicsPanel();
    private final RoomsLayer roomsLayer = graphicsPanel.getRoomsLayer();
    private final SpritesLayer spritesLayer = graphicsPanel.getSpritesLayer();
    
    private HashMap<Drawable, Point> moveItemStarts = new HashMap<>();

    private Alignment currAlignment;
    private Point prevLocation;

    public AlignButton() {
        super("Align");
    }

    @Override
    public void onMouseClicked(MouseEvent e) {
        if (currAlignment == null) {
            Room currentRoom = roomsLayer.getClickedRoom(e.getPoint());
            if (currentRoom == null) {
                return;
            }
            try {
                currAlignment = new Alignment(currentRoom);
                if (currAlignment.roomOption == AlignRoomOption.NEW_ROOM) {
                    alignNewRoom();
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Click on the room you want to align to the " + currAlignment + " of this room.",
                            "Align Existing Room",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception err) {
                cancelAlignment(null);
            }
            return;
        }

        if (currAlignment.roomOption == AlignRoomOption.EXISTING_ROOM) {
            alignExistingRoom(roomsLayer.getClickedRoom(e.getPoint()));
        }
    }

    @Override
    public void onMousePressed(MouseEvent e) {

    }

    @Override
    public void onMouseDragged(MouseEvent e) {

    }

    @Override
    public void onMouseReleased(MouseEvent e) {

    }

    private void cancelAlignment(String reason) {
        if (reason != null) {
            JOptionPane.showMessageDialog(
                    null,
                    reason,
                    "Alignment Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        currAlignment = null;
    }

    private void alignExistingRoom(Room clickedRoom) {
        if (clickedRoom == null) {
            cancelAlignment("No room clicked!");
            return;
        }
        if (clickedRoom == currAlignment.room) {
            cancelAlignment("Cannot align a room with itself!");
            return;
        }

        try {
            alignRooms(clickedRoom, currAlignment);
            cancelAlignment(null);
        } catch (Exception e) {
            cancelAlignment("Moving room back to previous location...");
            clickedRoom.setLocation(prevLocation);
            moveItemStarts.forEach((sprite, point) -> sprite.setLocation(point));
            graphicsPanel.repaint();
        }
    }

    private void alignNewRoom() {
        int gridSize = Config.getInstance().getGridSize();
        int min = 0;
        int max = 1000;

        JSlider widthSlider = new JSlider(JSlider.HORIZONTAL, min, max, min);
        JSlider heightSlider = new JSlider(JSlider.HORIZONTAL, min, max, min);

        widthSlider.setPreferredSize(new Dimension(max, 75));
        widthSlider.setMinorTickSpacing(gridSize);
        widthSlider.setMajorTickSpacing(gridSize * 5);
        widthSlider.setSnapToTicks(true);
        widthSlider.setPaintTicks(true);
        widthSlider.setPaintLabels(true);

        heightSlider.setPreferredSize(new Dimension(max, 75));
        heightSlider.setMinorTickSpacing(gridSize);
        heightSlider.setMajorTickSpacing(gridSize * 5);
        heightSlider.setSnapToTicks(true);
        heightSlider.setPaintTicks(true);
        heightSlider.setPaintLabels(true);

        Object[] sliders = {
                "Width:",
                widthSlider,
                "Height:",
                heightSlider
        };
        int option = JOptionPane.showConfirmDialog(
                null,
                sliders,
                "Set Width and Height",
                JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            int width = widthSlider.getValue();
            int height = heightSlider.getValue();
            if (width == 0 || height == 0) {
                cancelAlignment("Width and height must be greater than 0.");
                return;
            }
            Room newRoom = new Room(-width, -height, width, height, (RoomType) RoomButton.getRoomTypeComboBox().getSelectedItem());
            roomsLayer.add(newRoom);
            try {
                alignRooms(newRoom, currAlignment);
            } catch (Exception e) {
                cancelAlignment("Removing room...");
                roomsLayer.remove(newRoom);
            }
        }
        cancelAlignment(null);
    }

    private void alignRooms(Room room, Alignment alignment) throws Exception {
        prevLocation = room.getLocation();
        int x = alignment.room.getX();
        int y = alignment.room.getY();
        int width = alignment.room.getWidth();
        int height = alignment.room.getHeight();

        switch (alignment.direction) {
            case NORTH -> {
                y -= room.getHeight();
            }
            case WEST -> {
                x -= room.getWidth();
            }
            case SOUTH -> {
                y += height;
            }
            case EAST -> {
                x += width;
            }
        }

        if (alignment.direction == AlignDirection.NORTH || alignment.direction == AlignDirection.SOUTH) {
            switch (alignment.positionH) {
                case LEFT -> {
                }
                case CENTER -> {
                    x += (width - room.getWidth()) / 2;
                }
                case RIGHT -> {
                    x += width - room.getWidth();
                }
            }
        } else {
            switch (alignment.positionV) {
                case TOP -> {
                }
                case CENTER -> {
                    y += (height - room.getHeight()) / 2;
                }
                case BOTTOM -> {
                    y += height - room.getHeight();
                }
            }
        }
        
        List<Sprite> moveableSprites = spritesLayer.getSprites().stream()
                .filter(sprite -> sprite.intersects(room))
                .filter(sprite -> roomsLayer.getRooms().stream().noneMatch(r -> r != room && sprite.intersects(r)))
                .toList();
        int dx = x - room.getX();
        int dy = y - room.getY();
        room.setLocation(x, y);
        System.out.println(moveableSprites);
        for (Sprite sprite : moveableSprites) {
            moveItemStarts.put(sprite, sprite.getLocation());
            Point translatedPoint = new Point(sprite.getLocation());
            translatedPoint.translate(dx, dy);
            sprite.setLocation(translatedPoint);
        }
        graphicsPanel.repaint();
        if (!validateAlignment()) {
            throw new Exception("Misalignment detected.");
        }
    }

    public boolean validateAlignment(){
        return roomsLayer.checkForOverlap() && spritesLayer.checkForOverlap() && spritesLayer.validateSpritePlacement();
    }
}

class Alignment {
    public Room room;
    public AlignRoomOption roomOption;
    public AlignDirection direction;
    public AlignPositionH positionH;
    public AlignPositionV positionV;

    public Alignment(Room currentRoom) throws Exception {
        this.room = currentRoom;

        String[] prompts = {
                "Choose the type of room you want to align to this room.",
                "Choose the direction you want to align the room to.",
                "Choose the horizontal position of the room.",
                "Choose the vertical position of the room."
        };

        Object[] options = {
                AlignRoomOption.values(),
                AlignDirection.values(),
                AlignPositionH.values(),
                AlignPositionV.values()
        };

        for (int i = 0; i < prompts.length; i++) {
            if (direction == AlignDirection.NORTH || direction == AlignDirection.SOUTH) {
                if (i == 3)
                    continue;
            } else {
                if (i == 2)
                    continue;
            }
            int index = JOptionPane.showOptionDialog(
                    null,
                    prompts[i],
                    "Room alignment",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    (Object[]) options[i],
                    1);
            if (index == -1) {
                throw new Exception("Alignment cancelled");
            }
            switch (i) {
                case 0:
                    roomOption = AlignRoomOption.values()[index];
                    break;
                case 1:
                    direction = AlignDirection.values()[index];
                    break;
                case 2:
                    positionH = AlignPositionH.values()[index];
                    break;
                case 3:
                    positionV = AlignPositionV.values()[index];
                    break;
            }
        }
    }

    public String toString() {
        return direction + " " + (positionH != null ? positionH : positionV);
    }
}

enum AlignRoomOption {
    NEW_ROOM, EXISTING_ROOM;

    public String toString() {
        return switch (this) {
            case NEW_ROOM -> "New Room";
            case EXISTING_ROOM -> "Existing Room";
        };
    }
}

enum AlignDirection {
    NORTH, WEST, SOUTH, EAST;

    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    };
}

enum AlignPositionH {
    LEFT, CENTER, RIGHT;

    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    };
}

enum AlignPositionV {
    TOP, CENTER, BOTTOM;

    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    };
}