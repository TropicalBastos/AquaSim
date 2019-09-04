package com.tropicalbastos.boids.objects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FloorTile implements Drawable {

    private int width;
    private int height;
    private int posX;
    private int posY;
    private TexturePaint texturePainter;
    private BufferedImage texture;
    public static final String FLOOR_TILE_TAG = "FLOOR_TILE";

    public FloorTile(int width, int height) {
        this.width = width;
        this.height = height;
        this.posX = 0;
        this.posY = 0;
        initTexture();
    }

    public FloorTile(int width, int height, int posX, int posY) {
        this.width = width;
        this.height = height;
        this.posX = posX;
        this.posY = posY;
        initTexture();
    }

    private void initTexture() {
        try {
            texture = ImageIO.read(new File(getClass().getClassLoader().getResource("floortexture.png").getFile()));
            texturePainter = new TexturePaint(texture, new Rectangle(0, 0, texture.getWidth(), texture.getHeight()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            texture = null;
            texturePainter = null;
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (texturePainter !=  null) {
            g2d.setPaint(texturePainter);
            g2d.fillRect(posX, posY, width, height);
        }
    }

    public String getTag() {
        return FLOOR_TILE_TAG;
    }

}