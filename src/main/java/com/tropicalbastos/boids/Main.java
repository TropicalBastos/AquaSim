package com.tropicalbastos.boids;

import java.awt.EventQueue;
import java.awt.Taskbar;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.tropicalbastos.boids.core.Simulation;
import com.tropicalbastos.boids.core.Window;
import com.tropicalbastos.boids.objects.Fish;

public class Main {

    public void setDockIcon() {
        try {
            Taskbar taskbar = Taskbar.getTaskbar();
            BufferedImage image = ImageIO.read(getClass().getClassLoader().getResource("icon.png"));
            taskbar.setIconImage(image);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
  
	public static void main(String[] passedArgs) {
        // because getClass doesn't work in a static context
        Main m = new Main();
        m.setDockIcon();

        // set up our initial simulation
        Simulation sim = Simulation.getInstance();
        Fish f = new Fish(0, 0);
        sim.addFish(f);

        EventQueue.invokeLater(() -> {
            new Window();
        });
    }
}