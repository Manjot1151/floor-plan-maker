package org.lays.io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.lays.view.Canvas;
import org.lays.view.Door;
import org.lays.view.Furniture;
import org.lays.view.FurnitureType;
import org.lays.view.Room;
import org.lays.view.RoomType;
import org.lays.view.Window;
import org.lays.view.panels.RoomsLayer;
import org.lays.view.panels.SpritesLayer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class Open extends JMenuItem {
    private static final RoomsLayer roomsLayer = Canvas.getInstance().getRoomsLayer();
    private static final SpritesLayer spritesLayer = Canvas.getInstance().getSpritesLayer();
    
    public Open() {
        super("Open");
        setSize(100, 50);
        addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (JsonReader reader = new JsonReader(new FileReader(file))) {
                    JsonObject parsedJson = JsonParser.parseReader(reader).getAsJsonObject();
                    JsonArray rooms = parsedJson.getAsJsonArray("rooms");
                    JsonObject sprites = parsedJson.getAsJsonObject("sprites");
                    JsonArray furnitures = sprites.getAsJsonArray("furnitures");
                    JsonArray edgeDrawables = sprites.getAsJsonArray("edgeDrawables");

                    roomsLayer.getRooms().clear();
                    spritesLayer.getSprites().clear();

                    rooms.forEach(room -> {
                        JsonObject roomJson = room.getAsJsonObject();
                        double x = roomJson.get("x").getAsDouble();
                        double y = roomJson.get("y").getAsDouble();
                        double width = roomJson.get("width").getAsDouble();
                        double height = roomJson.get("height").getAsDouble();
                        String type = roomJson.get("type").getAsString();
                        roomsLayer.add(new Room(x, y, width, height, RoomType.valueOf(type)));
                    });

                    furnitures.forEach(furniture -> {
                        JsonObject furnitureJson = furniture.getAsJsonObject();
                        double x = furnitureJson.get("x").getAsDouble();
                        double y = furnitureJson.get("y").getAsDouble();
                        int orientation = furnitureJson.get("orientation").getAsInt();
                        String type = furnitureJson.get("type").getAsString();
                        
                        Furniture newFurniture = new Furniture(x, y, FurnitureType.valueOf(type));
                        newFurniture.rotate(orientation);
                        spritesLayer.add(newFurniture);
                    });

                    edgeDrawables.forEach(edgeDrawable -> {
                        JsonObject edgeDrawableJson = edgeDrawable.getAsJsonObject();
                        double startX = edgeDrawableJson.get("startX").getAsDouble();
                        double startY = edgeDrawableJson.get("startY").getAsDouble();
                        double endX = edgeDrawableJson.get("endX").getAsDouble();
                        double endY = edgeDrawableJson.get("endY").getAsDouble();
                        String type = edgeDrawableJson.get("type").getAsString();
                        if (type.equals("Door")) {
                            spritesLayer.add(new Door(startX, startY, endX, endY));
                        } else if (type.equals("Window")) {
                            spritesLayer.add(new Window(startX, startY, endX, endY));
                        }   
                    });

                } catch (IOException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(fileChooser, "Could not open file.", "Error Opening File", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
