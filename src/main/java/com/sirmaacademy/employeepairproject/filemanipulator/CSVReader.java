package com.sirmaacademy.employeepairproject.filemanipulator;

import com.sirmaacademy.employeepairproject.Exception.InvalidCSVInputException;
import com.sirmaacademy.employeepairproject.entity.EmployeeData;
import com.sirmaacademy.employeepairproject.helper.DateHelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CSVReader implements  Reader{
    @Override
    public List<EmployeeData> read(String fileName) {
        List<EmployeeData> employeeDataList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                validateInput(values);
                Long id = Long.parseLong(values[0].trim());
                Long projectID = Long.parseLong(values[1].trim());
                LocalDate dateFrom = DateHelper.parseDate(values[2].trim());
                LocalDate dateTo =DateHelper.parseDate(values[3].trim());


                EmployeeData employeeData =EmployeeData.builder()
                        .employeeID(id)
                        .projectID(projectID)
                        .dateFrom(dateFrom)
                        .dateTo(dateTo)
                        .build();
                employeeDataList.add(employeeData);
            }
        } catch (IOException | InvalidCSVInputException e) {
            e.printStackTrace();
        }

        return employeeDataList;
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
