package com.sirmaacademy.employeepairproject.filemanipulator;

import com.sirmaacademy.employeepairproject.Exception.InvalidCSVInputException;
import com.sirmaacademy.employeepairproject.entity.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CSVReader implements  Reader{
    @Override
    public List<Employee> read(String fileName) {
        List<Employee> employees = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                validateInput(values);
                Long id = Long.parseLong(values[0].trim());
                Long projectID = Long.parseLong(values[1].trim());
                LocalDate dateFrom = parseDate(values[2].trim());
                LocalDate dateTo = parseDate(values[3].trim());


                Employee employee = new Employee(id,projectID,dateFrom,dateTo);
                employees.add(employee);
            }
        } catch (IOException | InvalidCSVInputException e) {
            e.printStackTrace();
        }

        return employees;
    }
    private LocalDate parseDate(String dateStr) {
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

    private void validateInput(String [] values) throws InvalidCSVInputException {

        if (values.length != 4) {
            throw new InvalidCSVInputException("Invalid number of fields");
        }

        try {
            Long.parseLong(values[0].trim()); // Validate ID as a Long
            Long.parseLong(values[1].trim());// Validate projectID as a Long
        }
        catch (NumberFormatException e) {
            throw new InvalidCSVInputException("Invalid data format: " + e.getMessage());
        }
    }
}
