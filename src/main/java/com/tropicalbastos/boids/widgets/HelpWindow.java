package com.tropicalbastos.boids.widgets;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class HelpWindow extends JFrame {

    private FlowLayout layout;
    private JPanel panel;

    public HelpWindow() {
        super("Help");
        setSize(360, 200);
        setResizable(false);
        setLocationRelativeTo(null);

        panel = new JPanel();
        Border padding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        panel.setSize(getWidth(), getHeight());
        panel.setBorder(padding);

        layout = new FlowLayout();
        panel.setLayout(layout);

        add(panel);
        initText();
    }

    public void initText() {
        JLabel title = new JLabel("Welcome to Aquarium Simulator", JLabel.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));

        JPanel titlePane = new JPanel();
        titlePane.setLayout(new BorderLayout());
        titlePane.add(title, BorderLayout.CENTER);
        titlePane.setSize(new Dimension(panel.getWidth(), 50));

        JTextArea description = new JTextArea("To start, click anywhere on the aquarium to spawn fish and watch them flock!");
        description.setLineWrap(true);
        description.setEditable(false);
        description.setOpaque(false);
        description.setSize(new Dimension(panel.getWidth() - 40, 50));

        JLabel footer = new JLabel("By @TropicalBastos");
        footer.setSize(panel.getWidth(), 50);
        footer.setPreferredSize(new Dimension(getWidth() - 40, 50));
        footer.setHorizontalAlignment(JLabel.LEFT);
        footer.setAlignmentX(JLabel.LEFT_ALIGNMENT);

        panel.add(titlePane);
        panel.add(Box.createRigidArea(new Dimension(getWidth(), 20)));
        panel.add(description);
        panel.add(Box.createRigidArea(new Dimension(getWidth(), 5)));
        panel.add(footer);  
    }

}