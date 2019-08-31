package com.tropicalbastos.boids.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;
import com.tropicalbastos.boids.objects.Fish;

public class Renderer extends JPanel {

    public Renderer() {
        setBackground(Color.CYAN);
    }

    public void pack() {
        Dimension dimension = getParent().getSize();
        setSize(dimension);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        ArrayList<Fish> fish = Simulation.getInstance().getFish();
        for (Fish f : fish.toArray(new Fish[fish.size()])) {
            f.draw(g);
        }
    }
    
}