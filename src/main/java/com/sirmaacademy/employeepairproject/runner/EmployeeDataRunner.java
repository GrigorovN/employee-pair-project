package com.sirmaacademy.employeepairproject.runner;

import com.sirmaacademy.employeepairproject.filemanipulator.CSVReader;
import com.sirmaacademy.employeepairproject.service.EmployeeDataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class EmployeeDataRunner implements CommandLineRunner {

    private final EmployeeDataService employeeDataService;

    public EmployeeDataRunner(EmployeeDataService employeeDataService) {
        this.employeeDataService = employeeDataService;
    }

    @Override
    public void run(String... args) throws Exception {
        CSVReader reader = new CSVReader();
        String fileName = "src/main/resources/csv/input.csv";

        employeeDataService.saveFromFile(fileName,reader);
    }
}
