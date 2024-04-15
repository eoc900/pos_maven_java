package com.eoc900;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;

public class Simple extends JFrame {

    public JTextField[] fields = new JTextField[10];
    public JLabel[] labels = new JLabel[10];

    public void init() {
        System.out.println("The init method was executed");

        setTitle("Servicios");
        setSize(450, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Set layout manager to FlowLayout
        setLayout(null);

        // Add two buttons to our pane
        JButton start = new JButton("Insertar");
        JButton stop = new JButton("Stop");

        // Adjust the size and color of the stop button
        stop.setBounds(100, 200, 100, 30);
        start.setBounds(200, 200, 100, 30);

        // create a object of JTextField with 16 columns
        fields[0] = new JTextField(16);
        fields[0].setBounds(150, 50, 200, 30);
        labels[0] = new JLabel("Servicio");
        labels[0].setBounds(150, 20, 100, 30);
        fields[1] = new JTextField(16);
        fields[1].setBounds(150, 120, 200, 30);
        labels[1] = new JLabel("Precio");
        labels[1].setBounds(150, 100, 100, 30);

        // Add an ActionListener to the button
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to perform when the button is clicked

                System.out.println("Quieres insertar el siguiente valor: " + fields[0].getText());

            }
        });

        // Add an ActionListener to the button
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to perform when the button is clicked
                System.out.println("Button clicked!");

            }
        });

        add(fields[0]);
        add(fields[1]);
        add(labels[0]);
        add(labels[1]);
        add(stop);
        add(start);

        System.out.println("Testing if the button text appears");
        setVisible(true);

    }
}
