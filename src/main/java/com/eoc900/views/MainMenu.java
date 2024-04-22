package com.eoc900.views;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.eoc900.controllers.Controller;

import java.awt.event.ActionListener;
import javafx.event.ActionEvent;

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

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        newTab = new JButton("+ Nuevo paciente ");
        myServices = new JButton(" Mis servicios ");
        pendingPayments = new JButton("Pagos pendiendtes");
        payments = new JButton("Pagos");
        // Buttons' events
        landingMenuEvents();
        mainPanel.add(newTab);
        mainPanel.add(myServices);
        mainPanel.add(pendingPayments);
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
