package com.eoc900.views;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javafx.scene.paint.Color;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Vector;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ServicesTable {

    public JTable j;

    public void displayServicesTable(JFrame f, String[][] data) {

        // Frame Title
        f.setTitle("JTable Example");

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBounds(100, 500, 700, 700);

        // Data to be displayed in the JTable
        // String[][] data = {{name, bla},{name, bla}} // <------ this is the format

        // Column Names
        String[] columnNames = { "ID", "Servicio", "Precio" };

        // Initializing the JTable
        j = new JTable(data, columnNames);
        // j.setBounds(0, 200, 200, 300);
        panel.add(j);
        // adding it to JScrollPane
        JScrollPane sp = new JScrollPane(panel);
        // j.setFillsViewportHeight(true);

        f.add(sp);
        // f.pack();
        // f.add(sp);
        // Frame Size
        // f.setSize(500, 200);
        // Frame Visible = true

    }

    public JScrollPane displayServicesTablePanel(String[][] data) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Data to be displayed in the JTable
        // String[][] data = {{name, bla},{name, bla}} // <------ this is the format

        // Column Names
        String[] columnNames = { "ID", "Servicio", "Precio" };

        // Initializing the JTable
        j = new JTable(data, columnNames);
        // j.setBounds(0, 200, 200, 300);
        // panel.add(j);
        // adding it to JScrollPane
        JScrollPane sp = new JScrollPane(j);
        // j.setFillsViewportHeight(true);

        // f.pack();
        // f.add(sp);
        // Frame Size
        // f.setSize(500, 200);
        // Frame Visible = true

        return sp;

    }

}
