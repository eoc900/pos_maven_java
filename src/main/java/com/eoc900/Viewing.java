package com.eoc900;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Viewing extends JFrame {
    public JTextField[] fields = new JTextField[10];
    public JLabel[] labels = new JLabel[10];
    public int width = 800;
    public int height = 500;

    public void addService(String title) {
        setTitle("Agregar servicios");
        setSize(width, height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Set layout manager to FlowLayout
        setLayout(null);

        // Inputs
        fields[0] = new JTextField(16);
        fields[0].setBounds(50, 100, 200, 30);
        labels[0] = new JLabel("Servicio");
        labels[0].setBounds(50, 70, 100, 30);
        fields[1] = new JTextField(16);
        fields[1].setBounds(50, 170, 200, 30);
        labels[1] = new JLabel("Precio");
        labels[1].setBounds(50, 140, 100, 30);

        // Add two buttons to our pane
        JButton insert = new JButton("Insertar");
        insert.setBounds(width - 200, height - 100, 100, 30);

        // Add an ActionListener to the button
        insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to perform when the button is clicked

                System.out.println("Quieres insertar el siguiente valor: " + fields[0].getText());

            }
        });

        mainMenu();

        add(fields[0]);
        add(fields[1]);
        add(labels[0]);
        add(labels[1]);
        add(insert);

        setVisible(true);

    }

    // Top menu
    public void mainMenu() {

        JButton agregarServicio = new JButton("+ Servicio");
        JButton checkout = new JButton("Cuenta");
        JButton printBtn = new JButton("Imprimir");
        agregarServicio.setBounds(50, 10, 100, 50);
        checkout.setBounds(155, 10, 100, 50);
        printBtn.setBounds(340, 10, 100, 50);

        // Add an ActionListener to the button
        checkout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to perform when the button is clicked

                clearWindow();
                mainMenu();

            }
        });

        // Add an ActionListener to the button
        agregarServicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to perform when the button is clicked

                clearWindow();
                addService("Agregar Servicio 2");

            }
        });

        // Add an ActionListener to the button
        printBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to perform when the button is clicked
                System.out.println("Imprimiendo...");
                HelloWorld printer = new HelloWorld();
                String printerName = "Printer-58 USB Printing Support";
                try {
                    printer.justPrint(printerName);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    System.out.println(e1);
                }

            }
        });

        add(agregarServicio);
        add(checkout);
        add(printBtn);

    }

    // We use clear window everytime we want to remove all the window components
    public void clearWindow() {
        getContentPane().removeAll();
        repaint();
    }
}
