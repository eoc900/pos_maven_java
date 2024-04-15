package com.eoc900;

import java.util.Arrays;

import com.eoc900.models.Service;
import com.eoc900.models.TabModel;
import com.eoc900.views.View;
import com.eoc900.classes.Multidimentional;

public class App {
    public static void main(String[] args) throws Exception {

        Service myService = new Service();
        myService.getServices(0, 20);

        View test = new View("Bienvenido", "tablaServicios");
        test.setDataFromModel(myService.results);
        test.getView("tablaServicios", "Servicios disponibles");

        // Testing the new added method to search needle
        String[][] emptyArr = { { "A", "wassa" }, { "B", "hey" }, { "C", "cool" }, { "D", "Hi" } };
        String remove = "A";
        Multidimentional arr = new Multidimentional();
        String[][] res = arr.removeItemFromArray(0, "F", emptyArr);
        System.out.println(Arrays.deepToString(res));

        // System.out.println(newArr);
        // java.lang.Integer response = Helpers.findNeedleIndex(0, "hello", emptyArr);
        // System.out.println(response);

    }
}
