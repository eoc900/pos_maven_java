package com.eoc900;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.Arrays;
import java.util.Calendar;
import java.lang.Integer;

public class Helpers {

    public static String generateRandomCode(int len) { // len usually set with value 7
        String SALTCHARS = "ABCDEFGHIJKLMNPQRSTUVWXYZ1234567890"; // There is no o letter
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < len) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    public static java.sql.Timestamp getCurrentDate() { // to insert to database
        Calendar calendar = Calendar.getInstance();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTimeInMillis());
        return timestamp;
    }

    public static float getTotal(String[][] data, int priceIndex, int qtyIndex) {
        Float total = 0f;

        for (int i = 0; i < data.length; i++) {
            Float price = Float.parseFloat(data[i][priceIndex].replaceAll("[$]", ""));
            total += (price * Float.parseFloat(data[i][3]));
        }
        return total;
    }

}
