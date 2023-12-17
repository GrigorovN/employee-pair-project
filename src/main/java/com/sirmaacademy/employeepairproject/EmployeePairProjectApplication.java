package com.sirmaacademy.employeepairproject;

import com.sirmaacademy.employeepairproject.entity.Employee;
import com.sirmaacademy.employeepairproject.filemanipulator.CSVReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class EmployeePairProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeePairProjectApplication.class, args);
		CSVReader reader = new CSVReader();
		String fileName = "src/main/resources/csv/input.csv";

		List<Employee> employees = reader.read(fileName);
		for (Employee employee : employees) {
			System.out.println(employee);
		}

	}

}
