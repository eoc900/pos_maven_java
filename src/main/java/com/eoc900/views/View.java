package com.eoc900.views;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
import com.eoc900.models.ServiceModel;
import com.eoc900.models.TabModel;
import com.eoc900.controllers.Controller;
import com.eoc900.views.ServicesTable;
import com.eoc900.views.MainMenu;
import com.eoc900.views.PendingPayments;

import com.eoc900.DB;

public class View extends JFrame {
    public Controller navigation;
    public JTextField[] fields = new JTextField[10];
    public JLabel[] labels = new JLabel[10];
    public int width = 900;
    public int height = 500;
    public String windowTitle = "Bienvenido a ZENA";
    public String navAnterior;
    public String[][] dataModel;

    public View(Controller navigation) {
        this.navigation = navigation;
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
            case "agregarServicio":

                break;

            default:
                break;
        }
    }

    // Ready
    public void moduleService(String[][] data) {
        clearWindow();
        // 1. Create a main frame
        JPanel mainFrame = new JPanel();
        mainFrame.setLayout(new BoxLayout(mainFrame, BoxLayout.Y_AXIS));

        // 2. Bring the first section
        ServicesTable services = new ServicesTable(this);

        // 3. Place the go back button
        JPanel goBack = topGoBackButton();

        setSize(width, height);
        mainFrame.add(goBack);
        mainFrame.add(services.addService());
        JScrollPane currentServices = services.displayServicesTablePanel(data);
        services.serviceEvents();
        mainFrame.add(services.tableSection);
        add(mainFrame);
        // EVENTS DECLARATION

        this.pack();
        setVisible(true);
    }

    // Landing menu: what the user might see first
    // Ready
    public void landingMenu() {
        // As it is not only a menu but also a big view we will need to clear the window
        clearWindow();
        // 1. Create a main frame
        JPanel mainFrame = new JPanel();
        mainFrame.setLayout(new BoxLayout(mainFrame, BoxLayout.Y_AXIS));
        MainMenu menu = new MainMenu(this, navigation);
        JPanel panel = menu.landingMenu();

        this.setSize(300, 150);
        mainFrame.add(panel);
        add(mainFrame);
        this.setVisible(true);
    }

    // Listing the current pending payments
    // Ready
    public void modulePendingPayments(String[][] data, int totalPending) {
        clearWindow();

        JPanel mainFrame = new JPanel();
        mainFrame.setLayout(new BoxLayout(mainFrame, BoxLayout.Y_AXIS));

        JPanel goBack = topGoBackButton();
        mainFrame.add(goBack);

        PendingPayments pp = new PendingPayments(this, navigation, totalPending);
        JPanel searchSection = pp.displaySearchByName();
        mainFrame.add(searchSection);

        JPanel currentPendingAccounts = pp.displayTablePendingPayments(data);
        mainFrame.add(currentPendingAccounts);

        JPanel buttons = pp.actionButtons();
        mainFrame.add(buttons);

        add(mainFrame);
        this.pack();
        this.setSize(900, 500);
        this.setVisible(true);
    }

    public void moduleGetPatientAccount(String[][] data) {
        clearWindow();
        JPanel viewAccount = new JPanel();
        viewAccount.setLayout(new BoxLayout(viewAccount, BoxLayout.Y_AXIS));
        PatientAccount pa = new PatientAccount(this, navigation, data);
        viewAccount.add(pa.displayFirstSection());
        viewAccount.add(pa.displaySubtitle("Servicios agregados a esta cuenta."));
        viewAccount.add(pa.displayTablePendingPayments());
        viewAccount.add(pa.displayTotalSection());
        viewAccount.add(pa.displayButtons());
        add(viewAccount);
        this.setSize(900, 500);
        this.setVisible(true);
        pack();

    }

    // Top button go back
    // Ready
    public JPanel topGoBackButton() {

        JPanel goBackSection = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton back = new JButton("Menú principal");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigation.show("menuPrincipal");
            }
        });

        goBackSection.add(back);
        return goBackSection;
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
        ServicesTable test = new ServicesTable(this);
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
        JPanel topMenuVolver = topGoBackButton();
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

                TabModel nuevaTab = new TabModel();
                try {
                    nuevaTab.CreateTab(tab.tabIdentifier, tab.patientName.getText());
                    nuevaTab.insertServices(tab.tabIdentifier, tab.servicesAdded);
                    // tab.servicesStored = nuevaTab.retrieveServices("28GST00");

                    // System.out.println(Arrays.deepToString(testinArr));
                    // nuevaTab.updateTelefono("IFYFG1A", "524611246975");
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
                // addService("Agregar Servicio 2");

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
                // addService("Agregar Servicio 2");

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
