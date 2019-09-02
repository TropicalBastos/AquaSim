package com.tropicalbastos.boids.objects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.tropicalbastos.boids.animation.Spritesheet;
import com.tropicalbastos.boids.core.Renderer;
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
    public int posX;
    public int posY;
    public long heading;
    private int width;
    private int height;
    private int speed;
    private Renderer context;
    private long lastTimeHeadingChanged;

    // width of a single sprite not of the entire spritesheet
    private final int singleWidth;

    public Fish(int startPosX, int startPosY, Renderer context) {
        this.context = context;
        spritesheetIndex = 0;
        speed = 5;
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
        heading = 0;
        lastTimeHeadingChanged = 0;

        // downscale width/height
        width = (int) (WIDTH * 0.40);
        height = (int) (HEIGHT * 0.40);

        singleWidth = WIDTH / spritesheetData.length;
    }

    // check boundaries and change heading if at the edge
    // returns true if the heading changed
    public boolean checkBoundaries() {
        int noseX;
        boolean headingChanged = false;

        if (heading <= 270 && heading >= 90) {
            noseX = posX;
        }
        else {
            noseX = posX + sprite.getWidth() / spritesheetData.length;
        }

        if (noseX >= context.getWidth()) {
            heading = 180;
            headingChanged = true;
        }

        if (noseX < 0) {
            heading = 0;
            headingChanged = true;
        }

        if ((posY + sprite.getHeight()) > context.getHeight()) {
            heading = 270;
            headingChanged = true;
        }

        if (posY < 0) {
            heading = 90;
            headingChanged = true;
        }

        if (headingChanged)
            lastTimeHeadingChanged = System.currentTimeMillis();

        return headingChanged;
    }

    public void changeHeading() {
        int add = 1;
        long result = Math.round(Math.random());
        if (result == add)
            heading = heading + Math.round(Math.random() * 40);
        else
            heading = heading - Math.round(Math.random() * 40);

        lastTimeHeadingChanged = System.currentTimeMillis();
    }

    public void move() {
        boolean headingChanged = checkBoundaries();
        if (!headingChanged) {
            if (canChangeHeading())
                changeHeading(); 
        }

        posX += speed * Math.cos(heading * Math.PI / 180);
        posY += speed * Math.sin(heading * Math.PI / 180);
    }

    public BufferedImage flip(BufferedImage image) {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-image.getWidth(), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        try {
            BufferedImage result = op.filter(image, null);
            return result;
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public BufferedImage rotateToHeading(BufferedImage image) {
        AffineTransform tx = new AffineTransform();
        double radians = heading * (Math.PI / 180);
        tx.rotate(radians, width, height);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        try {
            BufferedImage result = op.filter(image, null);
            return result;
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean canChangeHeading() {
        return (System.currentTimeMillis() - lastTimeHeadingChanged) > 500;
    }

    public void draw(Graphics g) {
        Sheet sheet = spritesheet.getSheets()[spritesheetIndex];
        BufferedImage clippedDrawable = sprite.getSubimage(sheet.getX(), sheet.getY(), sheet.getWidth(), sheet.getHeight());

        // rotate to the fish's current heading
        clippedDrawable = rotateToHeading(clippedDrawable);

        // anything between headings 90 to 270 then we flip the fish sprite
        if (heading < 270 && heading > 90) {
            clippedDrawable = flip(clippedDrawable);
        }

        Image resultDrawable = clippedDrawable.getScaledInstance(width, height, 0);

        g.drawImage(resultDrawable, posX, posY, null);
    }

}