package com.eoc900.views;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
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
import java.io.File;

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
    public int width = 1200;
    public int height = 500;
    public String windowTitle = "Bienvenido a ZENA";
    public String navAnterior;
    public String[][] patientSelectedServices;

    public View(Controller navigation) {
        // The navigation object helps to load other views
        this.navigation = navigation;
    }

    // Ready
    // Admon feature
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
        try {
            File f = new File("src/main/resources/amese_logo.png");

            if (f.exists()) {
                System.out.println("The file exists");

            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }

        JPanel mainFrame = new JPanel();
        mainFrame.setLayout(new BoxLayout(mainFrame, BoxLayout.Y_AXIS));
        JLabel imgLabel = new JLabel(new ImageIcon("src/main/resources/amese_logo.png"));
        JPanel topLogo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        System.out.println(topLogo.getMaximumSize());
        topLogo.add(imgLabel);

        mainFrame.add(topLogo);
        MainMenu menu = new MainMenu(this, navigation);

        JPanel panel = menu.landingMenu();

        this.setSize(1200, 350);
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
        this.setSize(1200, 500);
        this.setVisible(true);
    }

    // Ready
    public void moduleAccountsPaid(String[][] data, int totalPaid) {
        clearWindow();

        JPanel mainFrame = new JPanel();
        mainFrame.setLayout(new BoxLayout(mainFrame, BoxLayout.Y_AXIS));

        JPanel goBack = topGoBackButton();
        mainFrame.add(goBack);

        Paid pp = new Paid(this, navigation, totalPaid);
        JPanel searchSection = pp.displaySearchByName();
        mainFrame.add(searchSection);

        JPanel currentPaidAccounts = pp.displayTablePayments(data);
        mainFrame.add(currentPaidAccounts);

        JPanel buttons = pp.actionButtons();
        mainFrame.add(buttons);

        add(mainFrame);
        this.pack();
        this.setSize(1200, 500);
        this.setVisible(true);
    }

    // Ready
    public void moduleGetPatientAccount(String[][] data) {
        clearWindow();
        JPanel viewAccount = new JPanel();
        viewAccount.setLayout(new BoxLayout(viewAccount, BoxLayout.Y_AXIS));

        JPanel goBack = topGoBackButton();
        viewAccount.add(goBack);
        PatientAccount pa = new PatientAccount(this, navigation, data);
        viewAccount.add(pa.displayFirstSection());
        viewAccount.add(pa.displaySubtitle("Servicios agregados a esta cuenta."));
        viewAccount.add(pa.displayTablePendingPayments());
        viewAccount.add(pa.displayTotalSection());
        viewAccount.add(pa.displayButtons());
        add(viewAccount);
        pack();
        setSize(1200, 300);
        this.setVisible(true);

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

    // ------> NOTE: Ready but needs refactoring
    public void viewCreateTab(String title, String[][] data, Boolean isEdit, String[][] servicesToEdit,
            String folioEdit) {

        if (isEdit) {
            // Just extract what is nesesary from the retrievedServices
            String[] indexes = { "0", "1", "2", "3" };
            String[][] newArr = Multidimentional.reduceArray(servicesToEdit, indexes);

        }

        // We clear the window before rendering
        clearWindow();
        this.setTitle(title);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // -------------------> TAB MAIN BODY

        // GO BACK BUTTON
        JPanel topMenuVolver = topGoBackButton();
        // 1. Generate Random Tab Identifier

        String tabID = folioEdit;
        if (!isEdit) {
            tabID = Helpers.generateRandomCode(7);
        }

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
        if (isEdit) {
            tab.renderServicesAdded(servicesToEdit);
        }

        // TOTAL UPDATE
        JPanel totals = tab.displayTotalsAndRefresh(0.00f);

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

                System.out.println("You are trying to save:");
                String pName = tab.patientName.getText();
                System.out.println(pName);

                if (!isEdit) {
                    // 1. Check if the name of the patient is valid
                    if (pName == null || pName.isEmpty() || pName.equals("Paciente")) {
                        System.out.println("condición cumplida");
                        Alert alert = new Alert("¡Paciente inválido!",
                                "Por favor ingresa un nombre válido de paciente.");
                        alert.popupNormal(350);
                        return;
                    }

                    // 2. Check
                    if (Multidimentional.removeArrayNullValues(tab.servicesAdded, 4).length < 1) {
                        Alert alert = new Alert("¡Cuenta vacía!",
                                "Por favor agrega un servicio antes de abrir una cuenta.");
                        alert.popupNormal(450);
                        return;
                    }

                    TabModel nuevaTab = new TabModel();
                    try {
                        nuevaTab.CreateTab(tab.tabIdentifier, tab.patientName.getText());
                        nuevaTab.insertServices(tab.tabIdentifier, tab.servicesAdded);
                        navigation.setMetaData(tab.tabIdentifier);
                        navigation.show("cuentaPaciente");
                    } catch (SQLException e1) {

                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                        System.out.println(e1);
                    }
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
        mainPanel.add(totals);
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

    // We use clear window everytime we want to remove all the window components
    public void clearWindow() {
        getContentPane().removeAll();
        repaint();
    }
}
