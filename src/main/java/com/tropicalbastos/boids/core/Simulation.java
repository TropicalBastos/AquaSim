package com.tropicalbastos.boids.core;

import java.awt.Point;
import java.util.ArrayList;
import java.util.stream.Collectors;
import com.tropicalbastos.boids.objects.Fish;

public class Simulation {

    private ArrayList<Fish> fish;
    private static Simulation instance;
    private static boolean initialised = false;

    private final int DETECTION_RADIUS = 200;

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

    public ArrayList<Fish> detectedFish(Fish currentFish) {
        return (ArrayList<Fish>) fish.stream().filter(fi -> {
            if (currentFish == fi)
                return false;

            int fxMin = fi.posX - DETECTION_RADIUS;
            int fyMin = fi.posY - DETECTION_RADIUS;
            int fxMax = fi.posX + DETECTION_RADIUS;
            int fyMax = fi.posY + DETECTION_RADIUS;
            int currentFishX = currentFish.posX;
            int currentFishY = currentFish.posY;

            if (currentFishX <= fxMax && currentFishX >= fxMin &&
                currentFishY <= fyMax && currentFishY >= fyMin) {
                    return true;
                }

            return false;
        }).collect(Collectors.toList());
    }

    public Point centerPoint(ArrayList<Fish> fishArr) {
        Point center = new Point(0, 0);
        int[] xPositions = new int[fishArr.size()];
        int[] yPositions = new int[fishArr.size()];
        int averageX = 0;
        int averageY = 0;

        for (int i = 0; i < fishArr.size(); i++) {
            Fish f = fishArr.get(i);
            xPositions[i] = f.posX;
            yPositions[i] = f.posY;
        }

        for (int i = 0; i < fishArr.size(); i++) {
            averageX += xPositions[i];
            averageY += yPositions[i];
        }

        averageX = averageX / xPositions.length;
        averageY = averageY / yPositions.length;
        
        center.x = averageX;
        center.y = averageY;

        return center;
    }

    public int calculateAverageHeading(ArrayList<Fish> fishArr) {
        int heading = 0;

        for (Fish f : fishArr) {
            heading += f.heading;
        }

        heading = heading / fishArr.size();

        return heading;
    }

    public synchronized void onProcess() {
        int count = fish.size();

        // need to update reference to fish
        for (int i = 0; i < count; i++) {
            Fish f = fish.get(i);
            ArrayList<Fish> detected = detectedFish(f);

            if (detected.size() == 0) {
                f.inFlock = false;
                f.move();
            } else {
                Point center = centerPoint(detected);
                int averageHeading = calculateAverageHeading(detected);
                f.inFlock = true;
                f.move(center, averageHeading);
            }
        }
    }
    
}