package com.sirmaacademy.employeepairproject.controller;

import com.sirmaacademy.employeepairproject.Exception.InvalidCSVInputException;
import com.sirmaacademy.employeepairproject.dto.EmployeeDataResponse;
import com.sirmaacademy.employeepairproject.entity.EmployeeData;
import com.sirmaacademy.employeepairproject.filemanipulator.CSVReader;
import com.sirmaacademy.employeepairproject.service.EmployeeDataService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path ="api/v1/employee")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeDataService employeeDataService;


    @PostMapping(path = "/add")
    public ResponseEntity<Object> addEmployee(@Valid @RequestBody EmployeeData employeeData) {
        try {
            EmployeeData savedEmployeeData = employeeDataService.save(employeeData);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployeeData);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage() );
        }
    }

    @GetMapping(path = "/pair/longest")
    public ResponseEntity<EmployeeDataResponse> getMaxPairForAllProject() {
        EmployeeDataResponse response = employeeDataService.findPairWithMaxDays();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(path = "/delete/{id}")
    public HttpStatus deleteEmployeeData(@PathVariable Long id) {
        employeeDataService.delete(id);
        return HttpStatus.ACCEPTED;
    }

    @GetMapping(path = "/add/csv")
    public ResponseEntity<String> addFromFile(@RequestParam("filepath") String filepath) {
        if (!filepath.endsWith(".csv")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file format. Only CSV files are allowed.");
        }

        try {
            employeeDataService.saveFromFile(filepath, new CSVReader());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("File successfully processed.");
        } catch (InvalidCSVInputException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid CSV file: " + e.getMessage());
        }
    }
}
