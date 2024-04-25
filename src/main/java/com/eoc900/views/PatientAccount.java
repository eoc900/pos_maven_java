package com.eoc900.views;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.eoc900.Printing;
import com.eoc900.classes.Multidimentional;
import com.eoc900.controllers.Controller;
import com.eoc900.models.TabModel;

class PatientAccount {
    JFrame window;
    Controller navigation;
    JLabel folioText;
    JLabel subtitle;
    String folio;
    Float total;
    int accountStatus;
    String[] lastRowSelected;
    String[][] generalInformation;
    String[][] arregloTabla;
    JLabel nombrePaciente;
    JLabel totalToBeP;
    JButton marcarPagado;
    JButton editar;
    JButton imprimir;
    JTable serviciosCuenta;
    JPanel firstSection;
    JPanel subtitleSection;
    JPanel tableSection;
    JPanel totalSection;
    JScrollPane sp;

    public PatientAccount(JFrame window, Controller nav, String[][] data) {
        // 1. We first get a general m-array containing services added and account info
        // 2. The indexes array is just defining which elements of the array behind we
        // want to extract
        // NOTE: point number two is meant for a reduced array used in the services
        // table.

        this.window = window;
        this.navigation = nav;
        this.generalInformation = data;
        this.folio = data[0][0];
        this.folioText = new JLabel("No. Folio " + this.folio);

        String[] indexes = { "7", "8", "9", "10" };
        String[][] updated = Multidimentional.reduceArray(data,
                indexes);
        this.arregloTabla = updated;
        // 3. We need to set the status as int so we can display or not some features
        this.accountStatus = Integer.parseInt(data[0][4]);
        System.out.println(Arrays.deepToString(data));
    }

    public JPanel displayFirstSection() {
        firstSection = new JPanel();
        firstSection.setLayout(new BoxLayout(firstSection, BoxLayout.X_AXIS));
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        nombrePaciente = new JLabel(this.generalInformation[0][1]);

        left.add(nombrePaciente);
        right.add(folioText);
        firstSection.add(left);
        firstSection.add(right);
        return firstSection;
    }

    public JPanel displaySubtitle(String subtitle) {
        subtitleSection = new JPanel();
        subtitleSection.setLayout(new BoxLayout(subtitleSection, BoxLayout.X_AXIS));
        this.subtitle = new JLabel(subtitle);
        subtitleSection.add(this.subtitle);
        return subtitleSection;
    }

    public JPanel displayTablePendingPayments() {
        tableSection = new JPanel();
        tableSection.setLayout(new BoxLayout(tableSection, BoxLayout.Y_AXIS));
        String[] columnNames = { "Servicio", "Qty.", "Precio", "Sub_Total" };
        serviciosCuenta = new JTable(this.arregloTabla, columnNames);
        sp = new JScrollPane(serviciosCuenta);
        tableSection.add(sp);
        tableEvents();
        return tableSection;
    }

    public JPanel displayTotalSection() {
        totalSection = new JPanel();
        totalSection.setLayout(new BoxLayout(totalSection, BoxLayout.X_AXIS));
        JLabel totTitle = new JLabel("Total:");
        total = calculateTotal(arregloTabla);
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalToBeP = new JLabel("$" + total);
        left.add(totTitle);
        right.add(totalToBeP);

        totalSection.add(left);
        totalSection.add(right);
        return totalSection;
    }

    public JPanel displayButtons() {
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        marcarPagado = new JButton("Marcar Pagado");
        editar = new JButton("Editar");
        imprimir = new JButton("Imprimir");
        accountEvents();

        if (this.accountStatus == 0) {
            buttons.add(marcarPagado);
            buttons.add(editar);
        }

        buttons.add(imprimir);

        return buttons;
    }

    public Float calculateTotal(String[][] tableServices) {
        Float total = 0f;
        for (int i = 0; i < tableServices.length; i++) {
            total += (Float.parseFloat(tableServices[i][1]) * Float.parseFloat(tableServices[i][2]));
        }
        return total;
    }

    public void accountEvents() {
        marcarPagado.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                TabModel db = new TabModel();
                db.updateStatus(folio, 1);
            }

        });

        imprimir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("You are requesting to print something...");
                System.out.println(Arrays.deepToString(generalInformation));
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String date = dtf.format(now);
                System.out.println(date);
                Printing printer = new Printing("Consultorio ZENA", nombrePaciente.getText(),
                        "Centro Médico Quirúrgico", "461 1234567", date, generalInformation, total);
                System.out.println(printer.mainTitle);
                System.out.println(Arrays.toString(printer.returnPrintersAvailable()));
            }
        });
    }

    public void tableEvents() {

    }

    public void refreshTable() {
    }

}