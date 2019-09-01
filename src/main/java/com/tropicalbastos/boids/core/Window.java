package com.tropicalbastos.boids.core;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import com.tropicalbastos.boids.objects.Fish;

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

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                Simulation.getInstance().isRunning = false;
            }
        });

        // set up our initial simulation
        Simulation sim = Simulation.getInstance();
        Fish f = new Fish(20, 20, renderer);
        sim.addFish(f);

        setVisible(true);
        
        Thread t = new Thread(new UpdateEngine(renderer));
        t.start();
    }

    public Renderer getRenderer() {
        return renderer;
    }
    
}