package com.tropicalbastos.boids.widgets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;

import com.tropicalbastos.boids.core.Window;
import com.tropicalbastos.boids.objects.Drawable;

public class Cursor implements Drawable {

    private final int CURSOR_SIZE = 25;
    private Window parent;

    public Cursor(Window parent) {
        this.parent = parent;
    }

    @Override
    public String getTag() {
        return "CURSOR";
    }

    @Override
    public void draw(Graphics g) {
        Point mousePos = MouseInfo.getPointerInfo().getLocation();
        int mousePosX = (int) (mousePos.getX() - parent.getX());
        int mousePosY = (int) (mousePos.getY() - parent.getY());
        mousePosX -= CURSOR_SIZE / 2;
        mousePosY -= CURSOR_SIZE * 2.5;
        g.setColor(Color.green);
        g.drawArc(mousePosX, mousePosY, CURSOR_SIZE, CURSOR_SIZE, 0, 360);
    }

}