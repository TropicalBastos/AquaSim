package com.tropicalbastos.boids.core;

import java.util.ArrayList;
import com.tropicalbastos.boids.objects.Fish;

public class Simulation {

    private ArrayList<Fish> fish;
    private static Simulation instance;
    private static boolean initialised = false;

    public boolean isRunning;

    private Simulation() {
        isRunning = true;
        fish = new ArrayList<>();
    }

    public static synchronized Simulation getInstance() {
        if (initialised)
            return instance;

        initialised = true;
        instance = new Simulation();
        return instance;
    }

    public synchronized ArrayList<Fish> getFish() {
        return fish;
    }

    public synchronized void setFish(ArrayList<Fish> _fish) {
        this.fish = _fish;
    }

    public synchronized void addFish(Fish _fish) {
        this.fish.add(_fish);
    }

    public synchronized void onProcess() {
        int count = fish.size();

        // need to update reference to fish
        for (int i = 0; i < count; i++) {
            fish.get(i).move();
        }
    }
    
}