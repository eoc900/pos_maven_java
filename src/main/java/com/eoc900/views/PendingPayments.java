package com.eoc900.views;

import java.awt.ComponentOrientation;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.eoc900.DB;
import com.eoc900.classes.Multidimentional;
import com.eoc900.controllers.Controller;
import com.eoc900.models.TabModel;

public class PendingPayments {
    JFrame frame;
    Controller navigation;
    String[][] lastRowSelected;
    int countPendingPayments;
    JTextField buscarNombre;
    JButton buscar;
    JButton verPaciente;
    JTable pagosPendientes;
    JPanel tableSection;
    JPanel featureSearch;
    JLabel title;
    JScrollPane sp;

    public PendingPayments(JFrame window, Controller nav, int total) {
        this.frame = window;
        this.navigation = nav;
        setNumber(total);
    }

    public JPanel displaySearchByName() {

        featureSearch = new JPanel();
        featureSearch.setLayout(new BoxLayout(featureSearch, BoxLayout.Y_AXIS));
        featureSearch.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        title = new JLabel("Hay " + countPendingPayments + " cuentas pendientes");
        title.setFont(new java.awt.Font("Seriff", java.awt.Font.BOLD, 18));
        title.setHorizontalAlignment(JLabel.CENTER);
        JPanel sectionTitle = new JPanel();
        sectionTitle.add(title);

        JPanel sectionOne = new JPanel();
        sectionOne.setLayout(new BoxLayout(sectionOne, BoxLayout.X_AXIS));

        buscarNombre = new JTextField();
        buscar = new JButton("Buscar");

        sectionOne.add(buscarNombre);
        sectionOne.add(buscar);

        featureSearch.add(sectionTitle);
        featureSearch.add(sectionOne);
        searchByNameEvents();

        return featureSearch;

    }

    public void refreshTable(String[][] data) {
        System.out.println("Trataste de borrar");
        // Column Names
        String[] columnNames = { "Folio", "Nombre Paciente", "Fecha", "Estado" };
        System.out.println(tableSection);
        tableSection.removeAll();
        tableSection.revalidate();
        tableSection.repaint();

        pagosPendientes = new JTable(data, columnNames);
        sp = new JScrollPane(pagosPendientes);
        tableSection.add(sp);
        frame.setSize(900, 500);
        // // Initializing the JTable
        tableEvents();
        // pagosPendientes.setVisible(false);

    }

    public JPanel displayTablePendingPayments(String[][] data) {
        tableSection = new JPanel();
        tableSection.setLayout(new BoxLayout(tableSection, BoxLayout.Y_AXIS));
        String[] columnNames = { "Folio", "Nombre Paciente", "Fecha", "Estado" };
        pagosPendientes = new JTable(data, columnNames);
        sp = new JScrollPane(pagosPendientes);
        tableSection.add(sp);
        tableEvents();

        return tableSection;
    }

    public JPanel actionButtons() {
        JPanel buttonsSection = new JPanel();
        verPaciente = new JButton("Ver paciente");
        buttonsSection.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        buttonsSection.add(verPaciente);
        return buttonsSection;
    }

    public void setNumber(int num) {
        this.countPendingPayments = num;
    }

    public void tableEvents() {
        ListSelectionModel selectionModel = pagosPendientes.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int row = pagosPendientes.getSelectedRow();
                    String folio = (String) pagosPendientes.getModel().getValueAt(row, 0);

                    System.out.println("Estás solicitando el siguiente folio: " + folio);

                    navigation.setMetaData(folio);
                    navigation.show("cuentaPaciente");

                }
            }
        });
    }

    public void searchByNameEvents() {
        buscar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String patient = buscarNombre.getText();
                System.out.println("Buscando el nombre de:");
                System.out.println(patient);

                TabModel db = new TabModel();
                db.init(false);
                String[][] accounts = Multidimentional.removeArrayNullValues(db.getUnpaidAccountByPatientName(patient),
                        4);
                System.out.println(Arrays.deepToString(accounts));
                refreshTable(accounts);

            }

        });
    }

}