package com.eoc900.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import com.eoc900.DB;

public class ServiceModel extends DB {

    public ResultSet getServices(int startAt, int count) throws SQLException {

        init(false);
        // one of the methods to get information from the database
        // each time a method is called it will have a string that will refer to the
        // table database
        // for example this getServices method will assign -> "servicios" to
        // currentTable property
        setCurrentTable("Servicios");

        try {
            String sql = "SELECT * FROM Servicios LIMIT ?,?";
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
                arr[0] = Integer.toString(result.getInt("ID_Servicio")); // Assuming 'id' is a column in your_table
                arr[1] = result.getString("Servicio"); // Assuming 'name' is a column in your_table
                arr[2] = " $" + Float.toString(result.getFloat("Precio"));
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
