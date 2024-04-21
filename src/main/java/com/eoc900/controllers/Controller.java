package com.eoc900.controllers;

import java.sql.SQLException;
import com.eoc900.models.ServiceModel;
import com.eoc900.views.View;

public class Controller {

    String lastVisited;
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

}
