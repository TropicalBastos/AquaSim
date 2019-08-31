package com.tropicalbastos.boids.animation;

public class Spritesheet {

    private Sheet[] sheets;

    public void loadSpritesheet(int[][] spritesheet) {
        sheets = new Sheet[spritesheet.length];
        for (int i = 0; i < spritesheet.length; i++) {
            Sheet s = new Sheet();
            s.setX(spritesheet[i][0]);
            s.setY(spritesheet[i][1]);
            s.setWidth(spritesheet[i][2]);
            s.setHeight(spritesheet[i][3]);
            sheets[i] = s;
        }
    }

    public Sheet[] getSheets() {
        return sheets;
    }

}