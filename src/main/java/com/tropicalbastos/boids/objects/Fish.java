package com.tropicalbastos.boids.objects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.tropicalbastos.boids.animation.Spritesheet;
import com.tropicalbastos.boids.core.Renderer;
import com.tropicalbastos.boids.animation.Sheet;

public class Fish implements Drawable {

    private final int WIDTH = 109;
    private final int HEIGHT = 89;
    private final long ANIMATION_DELTA = 50; // millieseconds between each sheet change
    private final int SPEED = 7;
    private final int ANGLE_LIMIT = 360;
    public static final String FISH_TAG = "FISH";

    private int[][] spritesheetData = {{0, 0, WIDTH, HEIGHT}, {109, 0, WIDTH, HEIGHT}, {218, 0, WIDTH, HEIGHT},
                                   {327, 0, WIDTH, HEIGHT}, {436, 0, WIDTH, HEIGHT}, {545, 0, WIDTH, HEIGHT}};
                        
    private Spritesheet spritesheet;
    private int spritesheetIndex;
    private BufferedImage sprite;
    private long lastAnimatedTime;
    public int posX;
    public int posY;
    public long heading;
    private long targetHeading;
    private long targetSpeed;
    private int width;
    private int height;
    private double speed;
    private Renderer context;
    private long lastTimeHeadingChanged;
    private boolean targetSpeedHit;
    private boolean targetHeadingHit;
    public boolean inFlock;

    public Fish(int startPosX, int startPosY, Renderer context) {
        this.context = context;
        spritesheetIndex = 0;
        speed = SPEED;
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
        targetHeading = heading;
        lastTimeHeadingChanged = 0;
        lastAnimatedTime = System.currentTimeMillis();

        targetSpeedHit = true;
        targetHeadingHit = true;
        inFlock = false;

        // downscale width/height
        width = (int) (WIDTH * 0.40);
        height = (int) (HEIGHT * 0.40);
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
            targetHeading = 180;
            headingChanged = true;
        }

        if (noseX < 0) {
            heading = 0;
            targetHeading = 0;
            headingChanged = true;
        }

        if ((posY + sprite.getHeight()) > context.getHeight()) {
            heading = 270;
            headingChanged = true;
        }

        if (posY < 0) {
            heading = 90;
            targetHeading = 90;
            headingChanged = true;
        }

        if (headingChanged)
            lastTimeHeadingChanged = System.currentTimeMillis();

        return headingChanged;
    }

    public void changeHeading() {
        double rand = Math.random();
        if (rand > 0.5) {
            targetHeading = heading + Math.round(Math.random() * 60);

            if (targetHeading > ANGLE_LIMIT) {
                targetHeading = targetHeading - ANGLE_LIMIT;
            }
        } else {
            targetHeading = heading - Math.round(Math.random() * 60);

            if (targetHeading < 0) {
                targetHeading = Math.abs(targetHeading);
                targetHeading = ANGLE_LIMIT - targetHeading;
            }
        }

        targetHeading = Math.abs(targetHeading);

        targetHeadingHit = false;
        lastTimeHeadingChanged = System.currentTimeMillis();
    }

    public void changeSpeed() {
        // fluctuate speed
        if (targetSpeedHit) {
            double rand = Math.random();
            if (rand >= 0.7) {
                targetSpeed = SPEED + 4;
            } else {
                targetSpeed = SPEED;
            }

            targetSpeedHit = false;
        }
    }

    public void move() {
        boolean headingChanged = checkBoundaries();
        if (!headingChanged) {
            if (canChangeHeading() && targetHeadingHit && !inFlock)
                changeHeading(); 
        }

        changeSpeed();

        posX += speed * Math.cos(heading * Math.PI / 180);
        posY += speed * Math.sin(heading * Math.PI / 180);
    }

    public double getAngleFromPoint(Point firstPoint, Point secondPoint) {
        if((secondPoint.x > firstPoint.x)) {
            return (Math.atan2((secondPoint.x - firstPoint.x), (firstPoint.y - secondPoint.y)) * 180 / Math.PI);
        }
        else if((secondPoint.x < firstPoint.x)) {
            return 360 - (Math.atan2((firstPoint.x - secondPoint.x), (firstPoint.y - secondPoint.y)) * 180 / Math.PI);
        }
    
        return Math.atan2(0 ,0);
    }

    public Point getPos() {
        return new Point(posX, posY);
    }

    public void move(Point center, int averageHeading) {
        targetHeading = averageHeading;
        targetHeadingHit = false;
        move();
    }

    public void animate() {
       if ((System.currentTimeMillis() - lastAnimatedTime) > ANIMATION_DELTA) {
           if (spritesheetIndex >= spritesheetData.length - 1) {
               spritesheetIndex = 0;
           } else {
               spritesheetIndex++;
           }

           if (!targetHeadingHit) {
                if (targetHeading != heading) {
                    if (heading >= (targetHeading + 10)) {
                        heading -= 10;
                    } else if (heading <= (targetHeading - 10)) {
                        heading += 10;
                    } else {
                        heading = targetHeading;
                    }
                } else {
                    targetHeadingHit = true;
                }
            }

           if (!targetSpeedHit) {
                if ((int) targetSpeed != (int) speed) {
                    if (speed < targetSpeed) {
                        speed += 0.1;
                    } else if (speed > targetSpeed) {
                        speed -= 0.1;
                    }
                } else {
                    targetSpeedHit = true;
                }
           }

           lastAnimatedTime = System.currentTimeMillis();
       }
    }

    public BufferedImage flip(BufferedImage image) {
        AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
        tx.translate(0, -image.getHeight());
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
        tx.rotate(radians, image.getWidth() / 2, image.getHeight() / 2);
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
        animate();
        Sheet sheet = spritesheet.getSheets()[spritesheetIndex];
        BufferedImage clippedDrawable = sprite.getSubimage(sheet.getX(), sheet.getY(), sheet.getWidth(), sheet.getHeight());

        // anything between headings 90 to 270 then we flip the fish sprite
        if (heading < 270 && heading > 90) {
            clippedDrawable = flip(clippedDrawable);
        }
    
        // rotate to the fish's current heading
        clippedDrawable = rotateToHeading(clippedDrawable);

        Image resultDrawable = clippedDrawable.getScaledInstance(width, height, 0);

        g.drawImage(resultDrawable, posX, posY, null);
    }

    public String getTag() {
        return FISH_TAG;
    }

}