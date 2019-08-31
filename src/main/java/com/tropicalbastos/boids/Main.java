package com.tropicalbastos.boids;

import java.awt.EventQueue;
import com.tropicalbastos.boids.core.Simulation;
import com.tropicalbastos.boids.core.Window;
import com.tropicalbastos.boids.objects.Fish;

public class Main {
  
	public static void main(String[] passedArgs) {
        // set up our initial simulation
        Simulation sim = Simulation.getInstance();
        Fish f = new Fish(0, 0);
        sim.addFish(f);

        EventQueue.invokeLater(() -> {
            new Window();
        });
    }
}