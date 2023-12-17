package com.sirmaacademy.employeepairproject.runner;

import com.sirmaacademy.employeepairproject.entity.Employee;
import com.sirmaacademy.employeepairproject.filemanipulator.CSVReader;
import com.sirmaacademy.employeepairproject.service.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeRunner implements CommandLineRunner {

    private final EmployeeService employeeService;

    public EmployeeRunner(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) throws Exception {
        CSVReader reader = new CSVReader();
        String fileName = "src/main/resources/csv/input.csv";

        List<Employee> employees = reader.read(fileName);

        for (Employee employee : employees) {
            if (!employeeService.isExist(employee.getId())) {
                employeeService.save(employee);
            }
        }
    }
}
