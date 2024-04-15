package com.eoc900.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;

import com.eoc900.DB;
import com.eoc900.Helpers;

public class TabModel extends DB {

    // Insertar nueva comanda
    public Boolean CreateTab(String code, String patient) throws SQLException {

        // Start connection
        init(false);

        // 1. Generar un número de folio
        // String code = Helpers.generateRandomCode(7);

        // 2. Obtener un string de la fecha actual
        java.sql.Timestamp timestamp = Helpers.getCurrentDate();

        // 3. Obtener nombre del paciente
        String pacientName = patient;

        // 4. Obtener teléfono del paciente
        // String tel = "123043294";

        try {
            String sql = "INSERT INTO `Comandas`(`Folio`, `Nombre_Paciente`,`Abierta_En`, `Cerrada_En`, `Pagada`, `Telefono`) VALUES (?,?,?,?,?,?) ";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setString(1, code); // This would set age
            prepared.setString(2, pacientName);
            // prepared.setNull(3, java.sql.Types.NULL);
            prepared.setTimestamp(3, timestamp);
            prepared.setNull(4, java.sql.Types.NULL);
            prepared.setInt(5, 0);
            prepared.setNull(6, java.sql.Types.NULL);
            prepared.execute();
            System.out.println("CreateTab executed...");

            prepared.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
            conn.close();
        }

        return false;

    }

    public Boolean updateStatus(String folioID, int paid) {
        // Start connection

        try {
            init(false);
            if (paid == 1) {
                String sql = "UPDATE Comandas SET Pagada=? WHERE Folio=?";
                PreparedStatement prepared = conn.prepareStatement(sql);
                prepared.setInt(1, paid); // This would set age
                prepared.setString(2, folioID);
                // prepared.setNull(3, java.sql.Types.NULL);

                prepared.execute();
                System.out.println("Ya se actualizó la comanda");
                prepared.close();

            }

            conn.close();

        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();

        }

        return false;
    }

    public ResultSet getAllPendingTabs(int startAt, int count) throws SQLException {

        init(false);
        {
            try {
                String sql = "SELECT * FROM Comandas LIMIT ?,? WHERE pagada=0";
                PreparedStatement prepared = conn.prepareStatement(sql);
                prepared.setInt(1, startAt); // This would set age
                prepared.setInt(2, count);
                ResultSet result = prepared.executeQuery();
                // 1. declaring the index for the multidimentional array
                int i = 0;

                while (result.next()) {

                    // 2. Declare the array
                    String[] arr = new String[3];

                    // Retrieve data from the result set row by row
                    arr[0] = Integer.toString(result.getInt("Folio")); // Assuming 'id' is a column in your_table
                    arr[1] = result.getString("Nombre_Paciente"); // Assuming 'name' is a column in your_table
                    java.sql.Date date = result.getDate(i);
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    arr[2] = dateFormat.format(date);
                    arr[3] = Integer.toString(result.getInt("Pagada"));
                    // 3. Insert the array to the results property
                    // System.out.println(Arrays.toString(arr));
                    // 4. Increase the index value by +1
                    results[i] = arr;
                    i++;
                }

                System.out.println(i);
                result.close();
                prepared.close();
                conn.close();

                return result;
            } catch (SQLException e) {
                System.out.println("There was a huge error...");
                e.printStackTrace();
                conn.close();
            }
            System.out.println("We shouldn't be getting an error...");
            return null;
        }
    }

    public Boolean insertServices(String folio, String[][] servicesStored) throws SQLException {
        init(false);

        for (int i = 0; i < servicesStored.length; i++) {
            // calculate subtotal
            int ID_Servicio = Integer.valueOf(servicesStored[i][0].trim());
            float precio = Float.parseFloat(servicesStored[i][2].replaceAll("[$ ]", ""));
            int qty = Integer.valueOf(servicesStored[i][3].trim());
            float subtotal = precio * (float) qty;

            // conn.close();

            try {
                String sql = "INSERT INTO Servicios_Seleccionados(`ID_Seleccionados`, `Folio`, `ID_Servicio`, `Qty`, `Precio`, `Sub_Total`) VALUES (default,?,?,?,?,?)";
                PreparedStatement prepared = conn.prepareStatement(sql);
                prepared.setString(1, folio);
                prepared.setInt(2, ID_Servicio);
                prepared.setInt(3, qty);
                prepared.setFloat(4, precio);
                prepared.setFloat(5, subtotal);
                prepared.execute();
                prepared.close();
            } catch (SQLException e) {
                e.printStackTrace();
                conn.close();
                return false;
            }

        }
        conn.close();
        return true;
    }
}
