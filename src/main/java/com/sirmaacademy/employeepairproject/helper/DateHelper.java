package com.sirmaacademy.employeepairproject.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateHelper {

    public static LocalDate parseDate(String dateStr) {
        // Check if the dateStr is NULL
        if (dateStr == null || dateStr.trim().equalsIgnoreCase("NULL")) {
            return LocalDate.now();
        }

        String[] dateFormats = {"yyyy-MM-dd", "dd-MM-yyyy", "MM/dd/yyyy"};

        // Try parsing the date using each format
        for (String format : dateFormats) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH);
                return LocalDate.parse(dateStr, formatter);
            } catch (Exception ignored) {
                // If parsing fails, ignore and try the next format
            }
        }

        // If none of the formats worked
        throw new IllegalArgumentException("Invalid date format: " + dateStr);
    }


}
