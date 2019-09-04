package com.tropicalbastos.boids.core;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import com.tropicalbastos.boids.objects.Fish;
import com.tropicalbastos.boids.objects.FloorTile;

public class Window extends JFrame implements ComponentListener {

    private Renderer renderer;
    private final int NUM_FLOOR_TILES = 4;
    private final int FLOOR_TILE_HEIGHT = 50;

    public Window() {
        setSize(1000, 400);
        setTitle("Boids");

        renderer = new Renderer();
        add(renderer);
        createFloor();
        renderer.pack();

        renderer.setVisible(true);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                Simulation.getInstance().isRunning = false;
            }
        });

        addComponentListener(this);

        // set up our initial simulation
        Simulation sim = Simulation.getInstance();
        Fish f = new Fish(20, 20, renderer);
        sim.addFish(f);

        setVisible(true);
        
        Thread t = new Thread(new UpdateEngine(renderer));
        t.start();
    }

    public void createFloor() {
        renderer.removeDrawablesOfTag(FloorTile.FLOOR_TILE_TAG);

        int windowWidth = getWidth();
        int floorTileWidth = windowWidth / NUM_FLOOR_TILES;
        int floorTileHeight = FLOOR_TILE_HEIGHT;

        int floorTileX = 0;
        int floorTileY = getHeight() - floorTileHeight;

        for (int i = 0; i <= NUM_FLOOR_TILES; i++) {
            FloorTile floorTile = new FloorTile(floorTileWidth, floorTileHeight, floorTileX, floorTileY);
            floorTileX += floorTileWidth;
            renderer.addDrawable(floorTile);
        }
    }

    public Renderer getRenderer() {
        return renderer;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        createFloor();
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

	}
    
}