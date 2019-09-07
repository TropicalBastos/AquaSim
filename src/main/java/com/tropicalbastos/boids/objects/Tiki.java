package com.tropicalbastos.boids.objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Tiki implements Drawable {

    private int posX;
    private int posY;
    private int width;
    private int height;
    private BufferedImage image;
    public static final String TIKI_TAG = "TIKI";

    public Tiki(int width, int height, int posX, int posY) {
        this.width = width;
        this.height = height;
        this.posX = posX;
        this.posY = posY;

        try {
            this.image = ImageIO.read(new File(getClass().getClassLoader().getResource("tiki.png").getFile()));
        } catch(IOException e) {
            System.out.println(e.getMessage());
            this.image = null;
        }
    }

    public void draw(Graphics g) {
        if (image !=  null) {
            g.drawImage(image, posX, posY, width, height, null);
        }
    }

    public String getTag() {
        return TIKI_TAG;
    }

}