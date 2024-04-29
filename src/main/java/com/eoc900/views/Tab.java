package com.eoc900.views;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.eoc900.Helpers;
import com.eoc900.classes.Multidimentional;

import javax.swing.JFrame;
import javafx.event.ActionEvent;

public class Tab {
    public String tabIdentifier;
    // Components of the controls JPanel
    public String[][] servicesAvailable;
    public String[][] servicesAdded; // [servide_id, qty]
    public String[][] servicesStored = {};
    public String[] lastRowSelected = {};
    public JPanel controlsPanel;
    public JButton addOneUnit;
    public JButton removeOneUnit;
    public JButton insertToServiceList;
    public JPanel addedServicesSection;
    public JTable addedServicesTable;
    public JButton removeService;
    public JLabel controlIdText;
    public JLabel controlServicioText;
    public JLabel controlPrecioText;
    public JTextField campoCantidad;
    public JPanel totalSection;
    public Float sumOfTotal;

    // Components of the controls JPanel

    public JTextField patientName;
    public JTable servicesTable;

    public Tab(String tID, String[][] data) {
        // each time that the createTab view is open we get new values
        this.tabIdentifier = tID;
        this.servicesAvailable = data;
        servicesAdded = new String[data.length][4];
        controlsPanel = new JPanel();
        // controlsPanel.setLayout(new BoxLayout(selectedList, BoxLayout.Y_AXIS));
    }

    public JPanel tabInputs() {
        // JPanel formPanel = new JPanel();
        // formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        JPanel firstSection = new JPanel();
        firstSection.setLayout(new BoxLayout(firstSection, BoxLayout.X_AXIS));

        JLabel nombre = new JLabel("Nombre del paciente: ");
        patientName = new JTextField("Paciente");
        nombre.setBounds(10, 50, 90, 40);
        patientName.setBounds(100, 50, 200, 40);

        firstSection.add(nombre);
        firstSection.add(patientName);

        return firstSection;

    }

    public JPanel additionalInfo() {
        JPanel firstSection = new JPanel();
        firstSection.setLayout(new BoxLayout(firstSection, BoxLayout.X_AXIS));
        JLabel identificador = new JLabel(tabIdentifier);
        firstSection.add(identificador);

        return firstSection;

    }

    public JScrollPane serviceSelection(String[][] services) {
        String[] columnNames = { "ID", "Servicio", "Precio" };
        servicesTable = new JTable(services, columnNames);
        JScrollPane sp = new JScrollPane(servicesTable);
        return sp;
    }

    public void pushIntoLastRowSelected(String id, String serviceName, String price, String qty) {
        String[] item = { id, serviceName, price, qty };
        lastRowSelected = item;
    }

    public JPanel displaySelectedControls() {

        // STEP: removing all components from the panel because we have to options: No
        // service selected yet and controls
        // STEP: check if there are values stored inside lastRowSelected[]

        System.out.println(this.lastRowSelected.length);

        if (this.lastRowSelected.length < 1) { // gets assigned when user click on a value inside the servicesTable

            JLabel alertNoServiceSelected = new JLabel("Selecciona algún servicio.");
            controlsPanel.add(alertNoServiceSelected);
        }

        if (this.lastRowSelected.length > 1) { // In case that there is a service selected
            controlsPanel.removeAll();
            controlsPanel.revalidate();
            controlsPanel.repaint();
            // controlsPanel.setLayout(new BoxLayout(selectedList, BoxLayout.X_AXIS));
            controlIdText = new JLabel(this.lastRowSelected[0]);
            controlServicioText = new JLabel(this.lastRowSelected[1]);
            controlPrecioText = new JLabel(this.lastRowSelected[2]);
            int cantidad = Integer.parseInt(this.lastRowSelected[3]);
            campoCantidad = new JTextField("" + cantidad, cantidad);
            addOneUnit = new JButton("+");
            removeOneUnit = new JButton("-");
            insertToServiceList = new JButton("agregar");
            removeService = new JButton("remover");
            removeService.setVisible(false);

            // Remove item
            removeService.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    System.out.println("Haz solicitado borrar un servicio...");
                    servicesAdded = Multidimentional.removeItemFromArray(0, lastRowSelected[0], servicesAdded);
                    renderServicesAdded(servicesAdded);
                    System.out.println(Arrays.deepToString(servicesAdded));

                }
            });

            // Insert item
            insertToServiceList.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {

                    servicesAdded = Multidimentional.addIfItemNotFound(lastRowSelected, 0, lastRowSelected[0],
                            servicesAdded);

                    renderServicesAdded(servicesAdded);

                    System.out.println("Debemos ver el arreglo multidimensional actualizado");
                    System.out.println(Arrays.deepToString(servicesAdded));
                    String[][] cleared = Multidimentional.removeArrayNullValues(servicesAdded, 4);
                    sumOfTotal = Helpers.getTotal(Multidimentional.removeArrayNullValues(servicesAdded, 4), 2, 3);
                    System.out.println("Nueva suma es de :");
                    System.out.println("$" + sumOfTotal);
                    totalSection = displayTotalsAndRefresh(sumOfTotal);
                }

            });

            // decrease quantity
            removeOneUnit.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    // TODO Auto-generated method stub
                    String num = campoCantidad.getText();
                    Integer current;
                    current = Integer.valueOf(num);
                    if (Integer.valueOf(num) - 1 >= 1) {
                        current = Integer.valueOf(num) - 1;
                    }

                    lastRowSelected[3] = Integer.toString(current);
                    campoCantidad.setText(Integer.toString(current));
                }

            });

            // Increasing quantity
            addOneUnit.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    // TODO Auto-generated method stub
                    String num = campoCantidad.getText();
                    Integer current = Integer.valueOf(num) + 1;
                    lastRowSelected[3] = Integer.toString(current);
                    campoCantidad.setText(Integer.toString(current));
                }

            });

            controlsPanel.add(controlIdText);
            controlsPanel.add(controlServicioText);
            controlsPanel.add(controlPrecioText);
            controlsPanel.add(addOneUnit);
            controlsPanel.add(campoCantidad);
            controlsPanel.add(removeOneUnit);
            controlsPanel.add(insertToServiceList);
            controlsPanel.add(removeService);

        }

        return controlsPanel;
    }

    // ------> Currently working on this function
    public JScrollPane renderAddedServicesAsScroll(String[][] currentServicesAdded) {

        String[] columns = { "ID", "Servicio", "Precio", "Cantidad", "Subtotal" };
        addedServicesTable = new JTable(currentServicesAdded, columns);
        JScrollPane scroll = new JScrollPane(addedServicesTable);

        return scroll;

    }

    public JPanel addedServicesPanel() {
        addedServicesSection = new JPanel();
        addedServicesSection.setLayout(new BoxLayout(addedServicesSection, BoxLayout.X_AXIS));
        return addedServicesSection;
    }

    public void updateControlsSection(String Id, String servicio, String cantidad, String price) {
        campoCantidad.setText(cantidad);
        controlServicioText.setText(servicio);
        controlPrecioText.setText(price);
        controlIdText.setText(Id);
    }

    public void renderServicesAdded(String[][] currentServicesAdded) {
        addedServicesSection.removeAll();
        // add your elements
        addedServicesSection.revalidate();
        addedServicesSection.repaint();
        String[] columns = { "ID", "Servicio", "Precio", "Cantidad" };
        addedServicesTable = new JTable(currentServicesAdded, columns);

        ListSelectionModel selectionModel = addedServicesTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    removeService.setVisible(true);
                    int row = addedServicesTable.getSelectedRow();
                    String id = (String) addedServicesTable.getModel().getValueAt(row, 0);
                    String serviceName = (String) addedServicesTable.getModel().getValueAt(row, 1);
                    String price = (String) addedServicesTable.getModel().getValueAt(row, 2);
                    String qty = (String) addedServicesTable.getModel().getValueAt(row, 3);
                    insertToServiceList.setText("Actualizar");
                    pushIntoLastRowSelected(id, serviceName, price, qty);
                    updateControlsSection(id, serviceName, qty, price);
                    servicesAdded = Multidimentional.updateItemOnArray(0, lastRowSelected[0], lastRowSelected,
                            servicesAdded);
                    // displaySelectedControls();
                    // renderServicesAdded(servicesAdded);

                }
            }
        });

        JScrollPane scroll = new JScrollPane(addedServicesTable);
        addedServicesSection.add(scroll);
    }

    public JPanel displayTotalsAndRefresh(Float total) {

        if (totalSection == null) {
            totalSection = new JPanel();
        }
        totalSection.removeAll();
        totalSection.revalidate();
        totalSection.repaint();

        totalSection.setLayout(new BoxLayout(totalSection, BoxLayout.X_AXIS));
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel textTotal = new JLabel("Total: ");

        JLabel sum = new JLabel("$" + total);
        left.add(textTotal);
        right.add(sum);
        totalSection.add(left);
        totalSection.add(right);
        return totalSection;
    }
}
