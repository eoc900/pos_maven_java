package com.eoc900.views;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.eoc900.models.ServiceModel;

import javafx.scene.text.Font;

import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;

public class Service {
    JFrame frame;
    JTextField[] fields = new JTextField[10];
    JLabel[] labels = new JLabel[10];
    JPanel featureAddService;
    JButton insertService;
    JButton updateService;
    String[] lastRowSelected = new String[3];
    int width;
    int height;
    int marginTop;
    int marginBottom;
    int marginLeft;
    int marginRight;
    public char[] randomString;

    public Service(JFrame window) {
        this.frame = window;
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

        updateService = new JButton("Actualizar");
        updateService.setBounds(200, height - 100, 100, 30);
        updateService.setVisible(false);

        sectionOne.add(insertService);
        sectionOne.add(updateService);
        featureAddService.add(sectionTitle);
        featureAddService.add(sectionOne);

        // featureAddService.setVisible(true);

        return featureAddService;

    }

}
