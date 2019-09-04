package com.tropicalbastos.boids.objects;

import java.awt.Graphics;

public interface Drawable {

    public String getTag();
    
    public void draw(Graphics g);

}