package com.tropicalbastos.boids.core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.tropicalbastos.boids.objects.Drawable;
import com.tropicalbastos.boids.objects.Fish;

public class Renderer extends Canvas implements MouseListener {

    private ArrayList<Drawable> drawables;
    private final int BACKGROUND_COLOUR = 0x5d99dd;

    public Renderer() {
        setBackground(new Color(BACKGROUND_COLOUR));
        addMouseListener(this);
        drawables = new ArrayList<>();
    }

    public void pack() {
        Dimension dimension = getParent().getSize();
        setSize(dimension);
    }

    public void addDrawable(Drawable d) {
        drawables.add(d);
    }

    public void removeDrawable(Drawable d) {
        drawables = (ArrayList<Drawable>) 
            drawables
                .stream()
                .filter(d2 -> !d2.equals(d))
                .collect(Collectors.toList());
    }

    public void removeDrawablesOfTag(String tag) {
        drawables = (ArrayList<Drawable>) 
            drawables
                .stream()
                .filter(d2 -> d2.getTag() != tag)
                .collect(Collectors.toList());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // draw our inanimate components first
        for (Drawable drawable : drawables) {
            drawable.draw(g);
        }

        // then our animate fish
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