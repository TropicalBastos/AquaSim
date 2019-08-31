package com.tropicalbastos.boids.core;

import javax.swing.JFrame;

public class Window extends JFrame {

    private Renderer renderer;

    public Window() {
        setSize(500, 500);
        setTitle("Boids");

        renderer = new Renderer();
        add(renderer);
        renderer.pack();

        renderer.setVisible(true);
        setVisible(true);
    }
    
}