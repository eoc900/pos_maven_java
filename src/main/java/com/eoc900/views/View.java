package com.eoc900.views;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import com.eoc900.HelloWorld;
import com.eoc900.Helpers;
import com.eoc900.classes.Multidimentional;
import com.eoc900.models.TabModel;

public class View extends JFrame {
    public JTextField[] fields = new JTextField[10];
    public JLabel[] labels = new JLabel[10];
    public int width = 900;
    public int height = 400;
    public String windowTitle = "Bienvenido a ZENA";
    public String navAnterior;
    public String[][] dataModel;

    public View(String title, String viewName) {
        // This will be the constructor method
        // we can call either this method or getView(viewName, assignedTitle)

        if (viewName.length() < 1) {
            return;
        }

    }

    public void getView(String viewName, String assignedTitle) {
        if (viewName.length() < 1) {
            return;
        }
        clearWindow();

        switch (viewName) {
            case "tablaServicios":
                String[][] data = getData();
                viewServicesTable(assignedTitle, data);
                break;
            case "menuPrincipal":
                landingMenu();
                break;
            case "crearComanda":
                String[][] d = getData();
                viewCreateTab(assignedTitle, d);
                break;

            default:
                break;
        }
    }

    public void setDataFromModel(String[][] data) {
        this.dataModel = data;
    }

    public String[][] getData() {
        return this.dataModel;
    }

    public void viewServicesTable(String title, String[][] data) {
        clearWindow();
        this.setTitle(title);
        ServicesTable test = new ServicesTable();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel topMenuVolver = topMenuVolver();
        JScrollPane tabla = test.displayServicesTablePanel(data);

        mainPanel.add(topMenuVolver);
        mainPanel.add(tabla);
        // mainPanel.add(tabla);

        this.setSize(width, height);
        this.add(mainPanel);
        this.setResizable(true);
        this.setVisible(true);
    }

    public void viewCreateTab(String title, String[][] data) {

        // We clear the window before rendering
        clearWindow();
        this.setTitle(title);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // -------------------> TAB MAIN BODY

        // GO BACK BUTTON
        JPanel topMenuVolver = topMenuVolver();
        String tabID = Helpers.generateRandomCode(7);
        Tab tab = new Tab(tabID, data);

        // TAB INPUTS
        JPanel tabInputs = tab.tabInputs();
        tabInputs.setPreferredSize(new Dimension(width, 40));

        // ADDITIONAL INFO SUCH AS TAB IDENTIFIER
        JPanel additional = tab.additionalInfo();
        additional.setPreferredSize(new Dimension(width, 40));

        // CONTROLS
        JPanel controls = tab.displaySelectedControls();
        controls.setPreferredSize(new Dimension(width, 60));

        // SERVICES SELECTED
        JPanel servicesSelected = tab.addedServicesPanel();

        // SERVICE TABLE SECTION
        JScrollPane serviceTable = tab.serviceSelection(data);

        // GENERAL BUTTONS
        JPanel secondSection = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton storing = new JButton("Guardar");
        JButton insertarServicio = tab.insertToServiceList;

        // TAB MAIN BODY <-------------------

        storing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to perform when the button is clicked
                System.out.println("Guardando comanda");
                System.out.println("Folio identificador: " + tab.tabIdentifier);
                System.out.println("Nombre del paciente: " + tab.patientName.getText());
                // displayTotals(Preview.total(tab.servicesAdded));
                // tab.CreateTab(tab.tabIdentifier, tab.patientName);
                TabModel nuevaTab = new TabModel();
                try {
                    nuevaTab.CreateTab(tab.tabIdentifier, tab.patientName.getText());
                    // nuevaTab.insertServices(tab.tabIdentifier, tab.servicesAdded);
                    // nuevaTab.removeServices("IFYFG1A");
                    // tab.servicesStored = nuevaTab.retrieveServices("28GST00");
                    // System.out.println(Arrays.deepToString(tab.servicesStored));
                    // String[][] testinArr =
                    // Multidimentional.removeArrayNullValues(tab.servicesAdded, 4);
                    // System.out.println(Arrays.deepToString(testinArr));
                    nuevaTab.updateTelefono("IFYFG1A", "524611246975");
                } catch (SQLException e1) {

                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    System.out.println(e1);
                }

            }
        });

        ListSelectionModel selectionModel = tab.servicesTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            // ------> This event paints the selected item controls

            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    // The idea is to visualize the selected row in certain area where the user
                    // adjust more or less qty

                    int row = tab.servicesTable.getSelectedRow();
                    System.out.println("You have selected the row numer: " + row);

                    String id = (String) tab.servicesTable.getModel().getValueAt(row, 0);
                    String serviceName = (String) tab.servicesTable.getModel().getValueAt(row, 1);
                    String price = (String) tab.servicesTable.getModel().getValueAt(row, 2);
                    // System.out.println(tab.campoCantidad.getText());
                    // String qty = tab.campoCantidad.getText();
                    tab.pushIntoLastRowSelected(id, serviceName, price, "1");
                    tab.displaySelectedControls();

                }

                return;
            }
        });

        secondSection.add(storing);

        mainPanel.add(topMenuVolver);

        mainPanel.add(tabInputs);

        mainPanel.add(controls);
        mainPanel.add(servicesSelected);

        mainPanel.add(serviceTable);

        mainPanel.add(secondSection);

        mainPanel.add(additional);

        this.add(mainPanel);
        this.setResizable(true);
        this.pack();
        this.setVisible(true);
    }

    public void displayTotals(JPanel totals) {
        clearWindow();
        this.add(totals);
        this.setVisible(true);
    }

    public void addService(String title) {

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

        mainMenu(this);

        add(fields[0]);
        add(fields[1]);
        add(labels[0]);
        add(labels[1]);
        add(insert);

        setVisible(true);

    }

    // Landing menu: what the user might see first
    public void landingMenu() {
        // As it is not only a menu but also a big view we will need to clear the window
        clearWindow();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JButton newTab = new JButton("+ Nuevo paciente ");
        JButton misServicios = new JButton(" Mis servicios ");
        misServicios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to perform when the button is clicked

                getView("tablaServicios", "Bienvenido al menú de Inicio");

            }
        });

        newTab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to perform when the button is clicked

                getView("crearComanda", "Ingresar paciente");

            }
        });

        panel.add(newTab);
        panel.add(misServicios);
        this.add(panel);
        this.setVisible(true);
    }

    // Botón de volver menú superior
    public JPanel topMenuVolver() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBounds(0, 0, width, 100);
        JButton volver = new JButton("Menú principal");

        volver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to perform when the button is clicked

                getView("menuPrincipal", "Bienvenido al menú de Inicio.");

            }
        });

        volver.setBounds(30, 30, 100, 50);
        panel.add(volver);
        return panel;
    }

    //

    // Top Menu
    public JPanel topMenu() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JButton verServicios = new JButton("Servicios");

        JButton agregarServicio = new JButton("+ Servicio");
        JButton checkout = new JButton("Cuenta");
        JButton printBtn = new JButton("Imprimir");
        agregarServicio.setBounds(10, 10, 100, 50);
        checkout.setBounds(10, 70, 100, 50);
        printBtn.setBounds(10, 140, 100, 50);
        verServicios.setBounds(10, 190, 100, 50);

        // Add an ActionListener to the button
        checkout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to perform when the button is clicked

                // clearWindow();
                // topMenu();

            }
        });

        // Add an ActionListener to the button
        agregarServicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to perform when the button is clicked

                // clearWindow();
                // lateralMenu(j);
                // addService("Agregar Servicio 2");

            }
        });

        // Add an ActionListener to the button
        verServicios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to perform when the button is clicked

                // clearWindow();
                // lateralMenu(j);

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

        panel.add(agregarServicio);
        panel.add(checkout);
        panel.add(printBtn);
        panel.add(verServicios);

        return panel;

    }

    // Lateral Menu
    public void lateralMenu(JFrame j) {

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 100, 300);

        JButton verServicios = new JButton("Servicios");

        JButton agregarServicio = new JButton("+ Servicio");
        JButton checkout = new JButton("Cuenta");
        JButton printBtn = new JButton("Imprimir");
        agregarServicio.setBounds(10, 10, 100, 50);
        checkout.setBounds(10, 70, 100, 50);
        printBtn.setBounds(10, 140, 100, 50);
        verServicios.setBounds(10, 190, 100, 50);

        // Add an ActionListener to the button
        checkout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to perform when the button is clicked

                clearWindow();
                lateralMenu(j);

            }
        });

        // Add an ActionListener to the button
        agregarServicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to perform when the button is clicked

                clearWindow();
                lateralMenu(j);
                addService("Agregar Servicio 2");

            }
        });

        // Add an ActionListener to the button
        verServicios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to perform when the button is clicked

                clearWindow();
                lateralMenu(j);

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

        panel.add(agregarServicio);
        panel.add(checkout);
        panel.add(printBtn);
        panel.add(verServicios);

        j.add(panel);

    }

    public void mainMenu(JFrame j) {

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 100, 300);

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
                mainMenu(j);

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

        panel.add(agregarServicio);
        panel.add(checkout);
        panel.add(printBtn);

        j.add(panel);

    }

    // We use clear window everytime we want to remove all the window components
    public void clearWindow() {
        getContentPane().removeAll();
        repaint();
    }
}
