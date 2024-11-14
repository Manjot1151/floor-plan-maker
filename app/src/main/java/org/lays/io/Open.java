package org.lays.io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class Open extends JMenuItem {
    private static final RoomsLayer roomsLayer = Canvas.getInstance().getRoomsLayer();
    private static final SpritesLayer spritesLayer = Canvas.getInstance().getSpritesLayer();
    
    public Open() {
        super("Open");
        addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (JsonReader reader = new JsonReader(new FileReader(file))) {
                    JsonElement parsedJson = JsonParser.parseReader(reader);
                    JsonArray rooms = parsedJson.getAsJsonObject().getAsJsonArray("rooms");
                    JsonObject sprites = parsedJson.getAsJsonObject().getAsJsonObject("sprites");
                    JsonArray furnitures = sprites.getAsJsonArray("furnitures");
                    JsonArray edgeDrawables = sprites.getAsJsonArray("edgeDrawables");

                    roomsLayer.getRooms().clear();
                    spritesLayer.getSprites().clear();

                    rooms.forEach(room -> {
                        JsonObject roomJson = room.getAsJsonObject();
                        int x = roomJson.get("x").getAsInt();
                        int y = roomJson.get("y").getAsInt();
                        int width = roomJson.get("width").getAsInt();
                        int height = roomJson.get("height").getAsInt();
                        String type = roomJson.get("type").getAsString();
                        roomsLayer.add(new Room(x, y, width, height, RoomType.valueOf(type)));
                    });

                    furnitures.forEach(furniture -> {
                        JsonObject furnitureJson = furniture.getAsJsonObject();
                        int x = furnitureJson.get("x").getAsInt();
                        int y = furnitureJson.get("y").getAsInt();
                        String type = furnitureJson.get("type").getAsString();
                        spritesLayer.add(new Furniture(x, y, FurnitureType.valueOf(type)));
                    });

                    edgeDrawables.forEach(edgeDrawable -> {
                        JsonObject edgeDrawableJson = edgeDrawable.getAsJsonObject();
                        int startX = edgeDrawableJson.get("startX").getAsInt();
                        int startY = edgeDrawableJson.get("startY").getAsInt();
                        int endX = edgeDrawableJson.get("endX").getAsInt();
                        int endY = edgeDrawableJson.get("endY").getAsInt();
                        String type = edgeDrawableJson.get("type").getAsString();
                        if (type.equals("Door")) {
                            spritesLayer.add(new Door(startX, startY, endX, endY));
                        } else if (type.equals("Window")) {
                            spritesLayer.add(new Window(startX, startY, endX, endY));
                        }   
                    });

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
