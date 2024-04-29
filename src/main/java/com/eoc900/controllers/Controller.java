package com.eoc900.controllers;

import java.sql.SQLException;
import java.util.Arrays;

import com.eoc900.classes.Multidimentional;
import com.eoc900.models.ServiceModel;
import com.eoc900.models.TabModel;
import com.eoc900.views.View;

public class Controller {

    String lastVisited;
    String metaData;
    View generalWindow;

    public Controller() {
        this.generalWindow = new View(this);
    }

    public void show(String actionName) {

        switch (actionName) {
            case "services":
                managerServicios();
                break;
            case "menuPrincipal":
                managerMenuPrincipal();
                break;
            case "nuevoCliente":
                managerNuevoCliente();
                break;
            case "pagosPendientes":
                managerPendingPayments();
                break;
            case "pagados":
                managerPayments();
                break;
            case "cuentaPaciente":
                managerGetTabInfo();
                break;
            default:
                break;
        }
    }

    public void managerServicios() {
        ServiceModel db = new ServiceModel();
        db.init(false);
        try {
            db.getServices(0, 19); // stores it results in an array named results
        } catch (SQLException e) {
            e.printStackTrace();
        }
        generalWindow.moduleService(db.results);
    }

    public void managerMenuPrincipal() {
        generalWindow.landingMenu();
    }

    public void managerNuevoCliente() {
        ServiceModel db = new ServiceModel();
        db.init(false);
        try {
            db.getServices(0, 19);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        generalWindow.viewCreateTab("Nuevo paciente", db.results);
    }

    public void managerPendingPayments() {
        TabModel db = new TabModel();
        db.init(false);
        String[][] res = Multidimentional.removeArrayNullValues(db.retrievePendingAccounts(0), 4);
        int num = db.retrievePendingAccountsNumber(0);
        generalWindow.modulePendingPayments(res, num);
    }

    // To append it to the action so the manager function gets an extra parameter
    // used in managerGetTabInfo for example
    public void setMetaData(String text) {
        this.metaData = text;
    }

    public void managerGetTabInfo() {
        String folio = this.metaData;
        TabModel tm = new TabModel();
        String[][] res = Multidimentional.removeArrayNullValues(tm.getTabInformation(folio), 11);
        generalWindow.moduleGetPatientAccount(res);
    }

    public void managerPayments() {
        TabModel db = new TabModel();
        db.init(false);
        String[][] res = Multidimentional.removeArrayNullValues(db.retrievePaidAccounts(1), 4);
        int num = db.retrievePaidAccountsNumber(1);
        generalWindow.moduleAccountsPaid(res, num);
    }

}
