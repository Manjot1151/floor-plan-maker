package org.lays.view;

public class Config {
    private static Config INSTANCE;
    private int gridSize = 20;

    public int getGridSize() {
        return this.gridSize;
    }

    public void setGridSize(int gridSize)  {
        this.gridSize = gridSize; 
    }

    public static Config getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Config();
        }
        return INSTANCE;
    }
}
