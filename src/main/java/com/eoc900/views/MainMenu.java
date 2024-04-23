package com.eoc900.views;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.plaf.FontUIResource;

import com.eoc900.controllers.Controller;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javafx.event.ActionEvent;
import javafx.scene.layout.BorderStroke;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javax.swing.border.LineBorder;

public class MainMenu {
    Controller navigation;
    JFrame frame;
    JPanel mainpanel;
    JButton newTab; // Creates a new
    JButton myServices;
    JButton pendingPayments;
    JButton payments;

    public MainMenu(JFrame window, Controller nav) {
        this.frame = window;
        this.navigation = nav;
    }

    public JPanel landingMenu() {

        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        newTab = new JButton("+ Nuevo paciente ");
        myServices = new JButton(" Mis servicios ");
        pendingPayments = new JButton("Cuentas pendientes");
        payments = new JButton("Cuentas cerradas");
        // Buttons' events
        landingMenuEvents();
        newTab.setPreferredSize(new Dimension(400, 50));
        newTab.setBorder(BorderFactory.createLineBorder(java.awt.Color.GRAY));
        newTab.setFont(new FontUIResource("Arial", java.awt.Font.BOLD, 20));
        pendingPayments.setPreferredSize(new Dimension(150, 50));
        myServices.setPreferredSize(new Dimension(150, 50));
        payments.setPreferredSize(new Dimension(150, 50));
        mainPanel.add(newTab);
        mainPanel.add(pendingPayments);
        mainPanel.add(myServices);

        mainPanel.add(payments);

        return mainPanel;
    }

    public void landingMenuEvents() {
        // New controller for the "routing"
        // Controller control = new Controller();

        myServices.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // Action to perform when the button is clicked
                navigation.show("services");
            }
        });

        newTab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // Action to perform when the button is clicked

                navigation.show("nuevoCliente");

            }
        });

        pendingPayments.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // Action to perform when the button is clicked

                navigation.show("pagosPendientes");

            }
        });

        payments.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // Action to perform when the button is clicked

                navigation.show("pagados");

            }
        });
    }

}
