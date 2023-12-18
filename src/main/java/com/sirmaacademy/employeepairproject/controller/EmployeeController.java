package com.sirmaacademy.employeepairproject.controller;

import com.sirmaacademy.employeepairproject.dto.EmployeeDataResponse;
import com.sirmaacademy.employeepairproject.entity.EmployeeData;
import com.sirmaacademy.employeepairproject.service.EmployeeDataService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path ="api/v1/employee")
public class EmployeeController {

    private final EmployeeDataService employeeDataService;

    public EmployeeController(EmployeeDataService employeeDataService) {
        this.employeeDataService = employeeDataService;
    }

    @PostMapping(path= "/add")
    public ResponseEntity<EmployeeData> addEmployee (@Valid @RequestBody EmployeeData employeeData) {
        EmployeeData savedEmployeeData = employeeDataService.save(employeeData);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployeeData);
    }

    @GetMapping(path = "/pair/longest")
    public ResponseEntity<EmployeeDataResponse> getMaxPairForAllProject (){
            EmployeeDataResponse response = employeeDataService.findPairWithMaxDays();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
