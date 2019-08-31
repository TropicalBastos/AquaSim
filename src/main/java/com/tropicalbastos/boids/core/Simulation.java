package com.tropicalbastos.boids.core;

import java.util.ArrayList;
import com.tropicalbastos.boids.objects.Fish;

public class Simulation {

    private ArrayList<Fish> fish;
    private static Simulation instance;
    private static boolean initialised = false;

    private Simulation() {
        fish = new ArrayList<>();
    }

    public static Simulation getInstance() {
        if (initialised)
            return instance;

        initialised = true;
        instance = new Simulation();
        return instance;
    }

    public ArrayList<Fish> getFish() {
        return fish;
    }

    public void setFish(ArrayList<Fish> _fish) {
        this.fish = _fish;
    }

    public void addFish(Fish _fish) {
        this.fish.add(_fish);
    }
    
}