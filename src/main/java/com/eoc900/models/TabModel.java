package com.eoc900.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Arrays;

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

    public Boolean updateTelefono(String folioID, String telefono) {
        // Start connection

        try {
            init(false);

            String sql = "UPDATE Comandas SET Telefono=? WHERE Folio=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setString(1, telefono); // This would set age
            prepared.setString(2, folioID);
            // prepared.setNull(3, java.sql.Types.NULL);

            prepared.execute();
            System.out.println("Ya se actualizó el teléfono");
            prepared.close();
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

    public ResultSet getAllPaidTabs(int startAt, int count) throws SQLException {

        init(false);
        {
            try {
                String sql = "SELECT * FROM Comandas LIMIT ?,? WHERE pagada=1";
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

    public void insertServices(String folio, String[][] servicesStored) throws SQLException {
        init(false);
        System.out.println(Arrays.deepToString(servicesStored));

        for (int i = 0; i < servicesStored.length; i++) {

            try {
                // calculate subtotal

                if (servicesStored[i][0] != null && !servicesStored[i][0].isEmpty()) {

                    int ID1 = Integer.valueOf(servicesStored[i][0].trim());

                    float precio1 = Float.parseFloat(servicesStored[i][2].replaceAll("[$ ]",
                            ""));
                    int qty1 = Integer.valueOf(servicesStored[i][3].trim());
                    float subtotal1 = precio1 * (float) qty1;
                    System.out.println(ID1 + "-" + precio1 + "-" + qty1 + "-" + subtotal1);
                    String sql = "INSERT INTO Servicios_Seleccionados(`ID_Seleccionados`,`Folio`, `ID_Servicio`, `Qty`, `Precio`, `Sub_Total`) VALUES (default,?,?,?,?,?)";
                    PreparedStatement prepared = conn.prepareStatement(sql);
                    prepared.setString(1, folio);
                    prepared.setInt(2, ID1);
                    prepared.setInt(3, qty1);
                    prepared.setFloat(4, precio1);
                    prepared.setFloat(5, subtotal1);
                    prepared.execute();
                    prepared.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                conn.close();
                // return false;
            }

        }

        conn.close();

    }

    public void removeServices(String folio) throws SQLException {
        init(false);
        try {
            String sql = "DELETE FROM Servicios_Seleccionados WHERE folio=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setString(1, folio);
            prepared.execute();
            prepared.close();
        } catch (SQLException e) {
            e.printStackTrace();
            conn.close();
        }
        conn.close();
    }

    public String[][] retrieveServices(String folio) throws SQLException {

        init(false);

        try {
            String[][] results = new String[19][5];
            String sql = "SELECT S.ID_Servicio, S1.Servicio, S1.Precio, S.Qty, S.Sub_Total FROM Servicios_Seleccionados S LEFT JOIN Servicios S1 ON S.ID_Servicio=S1.ID_Servicio WHERE S.folio=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setString(1, folio); // This would set age
            ResultSet result = prepared.executeQuery();
            // 1. declaring the index for the multidimentional array
            int i = 0;
            while (result.next()) {

                // 2. Declare the array
                String[] arr = new String[5];
                arr[0] = Integer.toString(result.getInt("ID_Servicio"));
                arr[1] = result.getString("Servicio");
                arr[2] = Float.toString(result.getFloat("Precio"));
                arr[3] = Integer.toString(result.getInt("Qty"));
                arr[4] = Float.toString(result.getFloat("Sub_Total"));
                results[i] = arr;
                i++;
            }
            return results;
        } catch (SQLException e) {
            e.printStackTrace();
            conn.close();
        }
        return null;
    }

    // FUNCIONAL
    public String[][] retrievePendingAccounts(int estado) {
        init(false);

        try {
            String tag = "pendiente";
            String[][] results = new String[19][4];
            String sql = "SELECT * FROM Comandas WHERE pagada=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, estado); // This would set age
            ResultSet result = prepared.executeQuery();
            // 1. declaring the index for the multidimentional array
            int i = 0;
            while (result.next()) {
                // // 2. Declare the array
                String[] arr = new String[4];
                // // Retrieve data from the result set row by row
                arr[0] = result.getString("Folio"); // Assuming 'id' is a column in
                // your_table
                arr[1] = result.getString("Nombre_Paciente"); // Assuming 'name' is a column
                // in your_table
                java.sql.Date date = result.getDate("Abierta_En");
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                arr[2] = dateFormat.format(date);
                int pagada = result.getInt("Pagada");
                if (pagada == 1) {
                    tag = "pagado";
                }
                arr[3] = tag;
                results[i] = arr;
                i++;
            }
            result.close();
            prepared.close();
            conn.close();
            return results;
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }

    // FUNCIONAL
    public String[][] retrievePaidAccounts(int estado) {
        init(false);

        try {
            String tag = "pendiente";
            String[][] results = new String[19][4];
            String sql = "SELECT * FROM Comandas WHERE pagada=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, estado); // This would set age
            ResultSet result = prepared.executeQuery();
            // 1. declaring the index for the multidimentional array
            int i = 0;
            while (result.next()) {
                // // 2. Declare the array
                String[] arr = new String[4];
                // // Retrieve data from the result set row by row
                arr[0] = result.getString("Folio"); // Assuming 'id' is a column in
                // your_table
                arr[1] = result.getString("Nombre_Paciente"); // Assuming 'name' is a column
                // in your_table
                java.sql.Date date = result.getDate("Abierta_En");
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                arr[2] = dateFormat.format(date);
                int pagada = result.getInt("Pagada");
                if (pagada == 1) {
                    tag = "pagado";
                }
                arr[3] = tag;
                results[i] = arr;
                i++;
            }
            result.close();
            prepared.close();
            conn.close();
            return results;
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }

    // FUNCIONAL
    public int retrievePendingAccountsNumber(int estado) {
        init(false);

        try {
            String[][] results = new String[19][4];
            String sql = "SELECT COUNT(*) AS num FROM Comandas WHERE pagada=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, estado); // This would set age
            ResultSet result = prepared.executeQuery();
            int numero = 0;
            while (result.next()) {
                numero = result.getInt("num");
            }
            result.close();
            prepared.close();
            conn.close();
            return numero;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // FUNCIONAL
    public int retrievePaidAccountsNumber(int estado) {
        init(false);

        try {
            String[][] results = new String[19][4];
            String sql = "SELECT COUNT(*) AS num FROM Comandas WHERE pagada=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setInt(1, estado); // This would set age
            ResultSet result = prepared.executeQuery();
            int numero = 0;
            while (result.next()) {
                numero = result.getInt("num");
            }
            result.close();
            prepared.close();
            conn.close();
            return numero;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Funcional
    public String[][] getUnpaidAccountByPatientName(String patient) {
        init(false);

        try {
            String tag = "pendiente";
            String[][] results = new String[19][4];
            String sql = "SELECT Folio, Nombre_Paciente, Abierta_En, Pagada FROM Comandas WHERE Nombre_Paciente LIKE ? AND Pagada=0";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setString(1, "%" + patient + "%"); // This would set age
            ResultSet result = prepared.executeQuery();
            int i = 0;

            while (result.next()) {
                // 2. Declare the array
                String[] arr = new String[4];

                arr[0] = result.getString("Folio");
                arr[1] = result.getString("Nombre_Paciente");
                java.sql.Date date = result.getDate("Abierta_En");
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                arr[2] = dateFormat.format(date);
                int pagada = result.getInt("Pagada");
                if (pagada == 1) {
                    tag = "pagado";
                }
                arr[3] = tag;
                results[i] = arr;
                i++;
            }
            result.close();
            prepared.close();
            conn.close();
            return results;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Funcional
    public String[][] getPaidAccountByPatientName(String patient) {
        init(false);

        try {
            String tag = "pendiente";
            String[][] results = new String[19][4];
            String sql = "SELECT Folio, Nombre_Paciente, Abierta_En, Pagada FROM Comandas WHERE Nombre_Paciente LIKE ? AND Pagada=1";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setString(1, "%" + patient + "%"); // This would set age
            ResultSet result = prepared.executeQuery();
            int i = 0;

            while (result.next()) {
                // 2. Declare the array
                String[] arr = new String[4];

                arr[0] = result.getString("Folio");
                arr[1] = result.getString("Nombre_Paciente");
                java.sql.Date date = result.getDate("Abierta_En");
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                arr[2] = dateFormat.format(date);
                int pagada = result.getInt("Pagada");
                if (pagada == 1) {
                    tag = "Pagado";
                }
                arr[3] = tag;
                results[i] = arr;
                i++;
            }
            result.close();
            prepared.close();
            conn.close();
            return results;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] getPatientInformation(String folio) {
        try {
            String tag = "pendiente";

            String sql = "SELECT Folio, Nombre_Paciente, Abierta_En, Pagada FROM Comandas WHERE Folio=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setString(1, folio); // This would set age
            ResultSet result = prepared.executeQuery();
            int i = 0;
            String[] arr = new String[3];
            while (result.next()) {
                // 2. Declare the array

                arr[0] = result.getString("Nombre_Paciente");
                java.sql.Date date = result.getDate("Abierta_En");
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                arr[1] = dateFormat.format(date);
                int pagada = result.getInt("Pagada");
                if (pagada == 1) {
                    tag = "Pagado";
                }
                arr[2] = tag;

            }
            result.close();
            prepared.close();
            conn.close();
            return arr;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[][] getTabInformation(String folio) {
        init(false);

        try {
            String tag = "pendiente";
            String[][] results = new String[19][11]; // still needs to be defined
            String sql = "SELECT C.Folio, C.Nombre_Paciente, C.Abierta_En, C.Cerrada_En, C.Pagada, C.Telefono, S.ID_Servicio,SE.Servicio,"
                    +
                    "S.Qty, S.Precio, S.Sub_Total FROM Comandas C INNER JOIN Servicios_Seleccionados S ON C.Folio=S.Folio LEFT JOIN "
                    +
                    "Servicios SE ON S.ID_Servicio=SE.ID_Servicio WHERE C.Folio=?";
            PreparedStatement prepared = conn.prepareStatement(sql);
            prepared.setString(1, folio); // This would set age
            ResultSet result = prepared.executeQuery();
            int i = 0;

            while (result.next()) {
                // 2. Declare the array
                String[] arr = new String[11];

                // -----> Store the Account information in a multidimentional array
                arr[0] = result.getString("Folio"); // Assuming 'id' is a column in your_table
                arr[1] = result.getString("Nombre_Paciente"); // Assuming 'name' is a column in your_table
                java.sql.Date date = result.getDate("Abierta_En");
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                arr[2] = dateFormat.format(date);
                if (result.getDate("Cerrada_En") == null) {
                    arr[3] = "Abierta";
                } else {
                    java.sql.Date cerrada = result.getDate("Cerrada_En");
                    DateFormat formatting = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                    arr[3] = formatting.format(cerrada);
                }

                arr[4] = Integer.toString(result.getInt("Pagada"));
                arr[5] = result.getString("Telefono");
                arr[6] = Integer.toString(result.getInt("ID_Servicio"));
                arr[7] = result.getString("Servicio");
                arr[8] = Integer.toString(result.getInt("Qty"));
                arr[9] = Float.toString(result.getFloat("Precio"));
                arr[10] = Float.toString(result.getFloat("Sub_Total"));

                results[i] = arr;
                // -----> Store the Account information in a multidimentional array
                i++;
            }

            System.out.println(i);
            result.close();
            prepared.close();
            conn.close();

            return results;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
