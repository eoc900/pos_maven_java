package com.eoc900.models;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import com.eoc900.DB;

public class Reports extends DB {
    String title;
    int defaultDays = 1;
    String[] rangos = { "diario", "semana", "mes", "anio" };
    String rangoSeleccionado;
    String daysForQuery;

    public Reports(String rango) {

    }

    public boolean checkValidRange(String word) {
        boolean isValid = false;
        String lower = word.toLowerCase();
        for (int i = 0; i < this.rangos.length; i++) {
            if (rangos[i] == lower) {
                return true;
            }
        }
        return false;
    }

    public int daysInRange(String range) { // Whenever we want to get the exact
        // values of the past number of days
        int days = defaultDays;

        if (!checkValidRange(range)) {
            return days;
        }
        switch (range.toLowerCase()) {
            case "diario":
                days = 1;
                break;
            case "semana":
                days = 7;
                break;
            case "mes":
                days = 30;
                break;
            case "anio":
                days = 365;
                break;
            default:
                break;
        }

        return days;
    }

    public int getCurrentMonthDays() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);
        String[] vals = date.split("-");
        int month = Integer.parseInt(vals[0]);
        int year = Integer.parseInt(vals[1]);
        YearMonth yearMonthObject = YearMonth.of(year, month);
        int daysInMonth = yearMonthObject.lengthOfMonth(); // 28
        return daysInMonth;
    }

    public int[] getThisMonthMaxMin() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);
        int max = Integer.valueOf(date);
        int[] numbers = { 1, max };
        return numbers;
    }

}
