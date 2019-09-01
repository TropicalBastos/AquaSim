package com.tropicalbastos.boids.core;

import java.util.concurrent.locks.ReentrantLock;

public class UpdateEngine implements Runnable {

    private Renderer renderer;
    private ReentrantLock lock;

    public UpdateEngine(Renderer renderer) {
        this.renderer = renderer;
        lock = new ReentrantLock();
    }

    @Override
    public void run() {
        int ticks = 0;
        Simulation sim = Simulation.getInstance();

        while (sim.isRunning) {
            ticks++;

            if (ticks == 3) {
                lock.lock();
                sim.onProcess();
                renderer.repaint();
                ticks = 0;
                lock.unlock();
            }

            try {
                Thread.sleep(10);
            } catch (Exception e) {
            }
        }
    }
}