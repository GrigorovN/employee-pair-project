package com.sirmaacademy.employeepairproject.runner;

import com.sirmaacademy.employeepairproject.entity.EmployeeData;
import com.sirmaacademy.employeepairproject.filemanipulator.CSVReader;
import com.sirmaacademy.employeepairproject.service.EmployeeDataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

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

        List<EmployeeData> employeeDataList = reader.read(fileName);


        for (EmployeeData employeeData : employeeDataList) {
           List<EmployeeData> stored = employeeDataService.getByEmployeeID(employeeData.getEmployeeID());
            // Check if there is employee with same ID
            if (stored.isEmpty()) {
                employeeDataService.save(employeeData);
            }
            else {
                // Check if an entry with the same projectID already exists
                boolean projectExists = false;
                for (EmployeeData data : stored) {
                    if (Objects.equals(data.getProjectID(), employeeData.getProjectID())) {
                        projectExists =true;
                        break;
                    }
                }
                if (!projectExists) {
                    employeeDataService.save(employeeData);
                }
            }
        }
    }
}
