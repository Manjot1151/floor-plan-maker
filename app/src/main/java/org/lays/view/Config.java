package org.lays.view;

public class Config {
    private static Config INSTANCE;
    private int gridSize = 20;
    private String spritesPath = "src/main/resources/sprites/";

    public int getGridSize() {
        return this.gridSize;
    }

    public void setGridSize(int gridSize)  {
        this.gridSize = gridSize; 
    }

    public String getSpritesPath() {
        return this.spritesPath;
    }

    public static Config getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Config();
        }
        return INSTANCE;
    }
}
