package com.tropicalbastos.boids.core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import com.tropicalbastos.boids.objects.Fish;

public class Renderer extends Canvas {

    public Renderer() {
        setBackground(new Color(0x006994));
    }

    public void pack() {
        Dimension dimension = getParent().getSize();
        setSize(dimension);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        ArrayList<Fish> fish = Simulation.getInstance().getFish();
        for (Fish f : fish) {
            f.draw(g);
        }
    }
    
}