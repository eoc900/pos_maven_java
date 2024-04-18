package com.eoc900.views;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javafx.scene.text.Font;

import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Service {

    JTextField[] fields = new JTextField[10];
    JLabel[] labels = new JLabel[10];
    JPanel featureAddService;
    JButton insertService;
    String[][] lastRowSelected = new String[1][3];
    int width;
    int height;
    int marginTop;
    int marginBottom;
    int marginLeft;
    int marginRight;

    public Service(int windowWidth, int windowHeight) {
        this.width = windowWidth;
        this.height = windowHeight;
    }

    public JPanel addService() {

        featureAddService = new JPanel();
        featureAddService.setLayout(new BoxLayout(featureAddService, BoxLayout.Y_AXIS));
        featureAddService.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel title = new JLabel("Sección de servicios.");
        title.setFont(new java.awt.Font("Seriff", java.awt.Font.BOLD, 18));
        title.setHorizontalAlignment(JLabel.CENTER);
        JPanel sectionTitle = new JPanel();
        sectionTitle.add(title);
        JPanel sectionOne = new JPanel();
        sectionOne.setSize(this.width, 100);

        // Inputs
        fields[0] = new JTextField(16);
        fields[0].setBounds(50, 200, 200, 30);
        labels[0] = new JLabel("Servicio");
        labels[0].setBounds(50, 70, 100, 30);
        fields[1] = new JTextField(16);
        fields[1].setBounds(50, 170, 200, 30);
        labels[1] = new JLabel("Precio");
        labels[1].setBounds(50, 140, 100, 30);
        sectionOne.add(labels[0]);
        sectionOne.add(fields[0]);
        sectionOne.add(labels[1]);
        sectionOne.add(fields[1]);
        // Add two buttons to our pane
        insertService = new JButton("Insertar");
        insertService.setBounds(200, height - 100, 100, 30);

        // Add an ActionListener to the button
        insertService.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to perform when the button is clicked

                System.out.println("Quieres insertar el siguiente valor: " + fields[0].getText());

            }
        });
        sectionOne.add(insertService);
        featureAddService.add(sectionTitle);
        featureAddService.add(sectionOne);

        // featureAddService.setVisible(true);

        return featureAddService;

    }
}
