package org.lays.view;

import java.awt.Color;

public enum RoomType {
    BEDROOM("Bedroom", new Color(0, 255, 0, 25)),
    BATHROOM("Bathroom", new Color(0, 0, 255, 25)),
    KITCHEN("Kitchen", new Color(255, 0, 0, 25)),
    DRAWING_ROOM("Drawing Room", new Color(255, 255, 0, 25));

    private String name;    
    private Color color;

    private RoomType(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String toString() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
