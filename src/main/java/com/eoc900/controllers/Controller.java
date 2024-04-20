package com.eoc900.controllers;

import java.sql.SQLException;
import com.eoc900.models.ServiceModel;
import com.eoc900.views.View;

public class Controller {

    String lastVisited;

    public void show(String actionName) {

        switch (actionName) {
            case "adminServicios":
                managerServicios();
                break;
            default:

                break;
        }

    }

    public void managerServicios() {
        View view = new View("", "");
        ServiceModel db = new ServiceModel();
        db.init(false);
        try {
            db.getServices(0, 19); // stores it results in an array named results
        } catch (SQLException e) {
            e.printStackTrace();
        }
        view.moduleService(db.results);
    }

}
