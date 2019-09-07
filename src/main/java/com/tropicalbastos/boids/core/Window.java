package com.tropicalbastos.boids.core;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import com.tropicalbastos.boids.objects.Fish;
import com.tropicalbastos.boids.objects.FloorTile;
import com.tropicalbastos.boids.objects.Tiki;
import com.tropicalbastos.boids.widgets.OptionsMenu;

public class Window extends JFrame implements ComponentListener {

    private Renderer renderer;
    private JMenuBar menuBar;
    private final int NUM_FLOOR_TILES = 4;
    private final int FLOOR_TILE_HEIGHT = 50;

    public Window() {
        setSize(1000, 400);
        setTitle("Boids");

        // set up menu
        menuBar = new JMenuBar();
        menuBar.add(new OptionsMenu());
        setJMenuBar(menuBar);

        renderer = new Renderer();
        add(renderer);
        createTiki();
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

    public void createTiki() {
        renderer.removeDrawablesOfTag(Tiki.TIKI_TAG);
        int w = getWidth();
        int h = getHeight();
        int tikiWidth = w / 8;
        int tikiHeight = tikiWidth;
        int tikiPosX = w - (w / 4);
        int tikiPosY = h - (tikiHeight + 70);
        renderer.addDrawable(new Tiki(tikiWidth, tikiHeight, tikiPosX, tikiPosY));
    }

    public void createFloor() {
        renderer.removeDrawablesOfTag(FloorTile.FLOOR_TILE_TAG);

        int windowWidth = getWidth();
        int floorTileWidth = windowWidth / NUM_FLOOR_TILES;
        int floorTileHeight = FLOOR_TILE_HEIGHT;

        int floorTileX = 0;
        int floorTileY = (getHeight() - menuBar.getHeight()) - floorTileHeight;

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
        createTiki();
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