package com.tropicalbastos.boids.core;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
}