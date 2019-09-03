package com.tropicalbastos.boids.core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import com.tropicalbastos.boids.objects.Fish;

public class Renderer extends Canvas implements MouseListener {

    public Renderer() {
        setBackground(new Color(0x006994));
        addMouseListener(this);
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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Simulation sim = Simulation.getInstance();
        Point point = e.getPoint();
        Fish f = new Fish((int) point.getX(),(int) point.getY(), this);
        sim.addFish(f);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

	}
    
}