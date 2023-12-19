package com.sirmaacademy.employeepairproject.controller;

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

    @DeleteMapping(path = "/delete/{id}")
    public HttpStatus deleteEmployeeData(@PathVariable Long id){
        employeeDataService.delete(id);
        return HttpStatus.ACCEPTED;
    }

    @GetMapping(path = "/add/csv")
    public HttpStatus addFromFile(@RequestParam("filepath") String filepath) {
        employeeDataService.saveFromFile(filepath, new CSVReader());
        return HttpStatus.ACCEPTED;
    }
}
