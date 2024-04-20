package com.eoc900.views;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.eoc900.models.ServiceModel;
import com.eoc900.views.Service;

import javafx.event.ActionEvent;
import javafx.scene.paint.Color;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ServicesTable extends Service {

    public JTable j;
    public JFrame frame;
    public JPanel tableSection;
    public String[] lastRowSelectedST = {};
    public JButton insert;
    public JButton update;
    public String randomString;

    public ServicesTable(JFrame window) {
        super(window);
        this.frame = extracted(window);
    }

    private static JFrame extracted(JFrame window) {
        return window;
    }

    public void pushIntoLastRowSelected(String id, String serviceName, String price) {
        String[] item = { id, serviceName, price };
        lastRowSelectedST = item;
    }

    public void displayServicesTable(JFrame f, String[][] data) {

        // Frame Title
        extracted(f).setTitle("JTable Example");

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

        extracted(f).add(sp);
        // f.pack();
        // f.add(sp);
        // Frame Size
        // f.setSize(500, 200);
        // Frame Visible = true

    }

    public void refreshTable(String[][] data) {
        // Column Names
        String[] columnNames = { "ID", "Servicio", "Precio" };
        tableSection.removeAll();
        tableSection.revalidate();
        tableSection.repaint();

        j = new JTable(data, columnNames);
        JScrollPane sp = new JScrollPane(j);
        tableSection.add(sp);
        // Initializing the JTable
        tableEvents();

    }

    public JScrollPane displayServicesTablePanel(String[][] data) {

        tableSection = new JPanel();
        tableSection.setLayout(new BoxLayout(tableSection, BoxLayout.Y_AXIS));

        // Data to be displayed in the JTable
        // String[][] data = {{name, bla},{name, bla}} // <------ this is the format

        // Column Names
        String[] columnNames = { "ID", "Servicio", "Precio" };

        // Initializing the JTable
        j = new JTable(data, columnNames);
        JScrollPane sp = new JScrollPane(j);
        tableSection.add(sp);
        tableEvents();
        // serviceEvents();
        return sp;

    }

    public void tableEvents() {

        // Click on the table rows
        ListSelectionModel selectionModel = j.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    System.out.println("Service name");
                    System.out.println(fields[0]);

                    // The idea is to visualize the selected row in certain area where the user
                    // adjust more or less qty

                    int row = j.getSelectedRow();
                    System.out.println("You have selected the row numer: " + row);

                    String id = (String) j.getModel().getValueAt(row, 0);
                    String serviceName = (String) j.getModel().getValueAt(row, 1);
                    String price = (String) j.getModel().getValueAt(row, 2);

                    // Store last selected
                    pushIntoLastRowSelected(id, serviceName, price.replaceAll("[$ ]", ""));
                    // // Change values of the inputs
                    fields[0].setText(serviceName);
                    fields[1].setText(price.replaceAll("[$ ]", ""));
                    // // Hide the insert button
                    insertService.setVisible(false);
                    // // Show the update button
                    updateService.setVisible(true);
                    frame.pack();

                }

                return;
            }
        });
    }

    public void serviceEvents() {

        // // On insert
        insertService.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // Action to perform when the button is clicked
                System.out.println("Quieres insertar el siguiente valor: " +
                        fields[0].getText());

            }
        });
        // //// On update
        // // Add an ActionListener to the button

        updateService.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent arg0) {

                System.out.println("Update service clicked");
                // TODO Auto-generated method stub
                ServiceModel db = new ServiceModel();
                Integer id = Integer.valueOf(lastRowSelectedST[0]);
                Float price = Float.valueOf(fields[1].getText());
                System.out.println(Arrays.toString(lastRowSelectedST));

                try {
                    db.updateService(id, fields[0].getText(), price);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                try {
                    db.getServices(0, 19);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                refreshTable(db.results);

            }

        });

    }

}
