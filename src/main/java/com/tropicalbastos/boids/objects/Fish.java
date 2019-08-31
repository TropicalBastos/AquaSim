package com.tropicalbastos.boids.objects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.tropicalbastos.boids.animation.Spritesheet;
import com.tropicalbastos.boids.animation.Sheet;

public class Fish {

    private final int WIDTH = 109;
    private final int HEIGHT = 89;

    private int[][] spritesheetData = {{0, 0, WIDTH, HEIGHT}, {109, 0, WIDTH, HEIGHT}, {218, 0, WIDTH, HEIGHT},
                                   {327, 0, WIDTH, HEIGHT}, {436, 0, WIDTH, HEIGHT}, {545, 0, WIDTH, HEIGHT}};
                        
    private Spritesheet spritesheet;
    private int spritesheetIndex;
    private long animationDelta;
    private BufferedImage sprite;
    private int posX;
    private int posY;
    private int width;
    private int height;

    public Fish(int startPosX, int startPosY) {
        spritesheetIndex = 0;
        animationDelta = System.currentTimeMillis();
        String imgPath = getClass().getClassLoader().getResource("fishsprite.png").getFile();

        try {
            sprite = ImageIO.read(new File(imgPath));
        } catch(IOException e) {
            sprite = null;
            System.out.println(e.getMessage());
        }

        spritesheet = new Spritesheet();
        spritesheet.loadSpritesheet(spritesheetData);

        posX = startPosX;
        posY = startPosY;

        // downscale width/height
        width = (int) (WIDTH * 0.40);
        height = (int) (HEIGHT * 0.40);
    }

    public void draw(Graphics g) {
        Sheet sheet = spritesheet.getSheets()[spritesheetIndex];
        Image clippedDrawable = sprite.getSubimage(sheet.getX(), sheet.getY(), sheet.getWidth(), sheet.getHeight());
        clippedDrawable = clippedDrawable.getScaledInstance(width, height, 0);
        g.drawImage(clippedDrawable, posX, posY, null);
    }

}