package com.tropicalbastos.boids.widgets;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class OptionsMenu extends JMenu {

    public OptionsMenu() {
        super("Options");

        JMenuItem helpItem = new JMenuItem();
        helpItem.setText("Help");

        JMenuItem quitItem = new JMenuItem();
        quitItem.setText("Quit");
        quitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame[] frames = Frame.getFrames();
                for (Frame frame : frames) {
                    frame.dispose();
                }

                System.exit(0);
            }
        });

        add(helpItem);
        add(quitItem);
    }

}