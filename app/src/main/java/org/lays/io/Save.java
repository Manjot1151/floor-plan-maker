package org.lays.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.lays.view.Canvas;
import org.lays.view.Furniture;
import org.lays.view.RoomEdgeDrawable;
import org.lays.view.panels.RoomsLayer;
import org.lays.view.panels.SpritesLayer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Save extends JMenuItem {
    private static final RoomsLayer roomsLayer = Canvas.getInstance().getRoomsLayer();
    private static final SpritesLayer spritesLayer = Canvas.getInstance().getSpritesLayer();

    public Save() {
        super("Save");
        addActionListener(e -> { 
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File("plan.json"));
            int result = fileChooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                JsonArray rooms = new JsonArray();
                JsonObject sprites = new JsonObject();
                JsonArray furnitures = new JsonArray();
                JsonArray edgeDrawables = new JsonArray();

                roomsLayer.getRooms().forEach(room -> {
                    JsonObject roomJson = new JsonObject();
                    roomJson.addProperty("x", room.getX());
                    roomJson.addProperty("y", room.getY());
                    roomJson.addProperty("width", room.getWidth());
                    roomJson.addProperty("height", room.getHeight());
                    roomJson.addProperty("type", room.getType().name());
                    rooms.add(roomJson);
                });
                
                spritesLayer.getSprites().forEach(sprite -> {
                    if (sprite instanceof Furniture) {
                        Furniture furniture = (Furniture) sprite;
                        JsonObject furnitureJson = new JsonObject();
                        furnitureJson.addProperty("x", furniture.getX());
                        furnitureJson.addProperty("y", furniture.getY());
                        furnitureJson.addProperty("type", furniture.getType().name());
                        furnitures.add(furnitureJson);
                    } else if (sprite instanceof RoomEdgeDrawable) {
                        RoomEdgeDrawable edgeDrawable = (RoomEdgeDrawable) sprite;
                        JsonObject edgeDrawableJson = new JsonObject();
                        edgeDrawableJson.addProperty("startX", edgeDrawable.getStart().getX());
                        edgeDrawableJson.addProperty("startY", edgeDrawable.getStart().getY());
                        edgeDrawableJson.addProperty("endX", edgeDrawable.getEnd().getX());
                        edgeDrawableJson.addProperty("endY", edgeDrawable.getEnd().getY());
                        edgeDrawableJson.addProperty("type", edgeDrawable.getClass().getSimpleName());
                        edgeDrawables.add(edgeDrawableJson);
                    }
                });
                sprites.add("furnitures", furnitures);
                sprites.add("edgeDrawables", edgeDrawables);
                JsonObject jsonObject = new JsonObject();
                jsonObject.add("rooms", rooms);
                jsonObject.add("sprites", sprites);
                
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(gson.toJson(jsonObject));
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(fileChooser, "Could not save file.", "Error Saving File", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                }
            }
        });
    }
}
