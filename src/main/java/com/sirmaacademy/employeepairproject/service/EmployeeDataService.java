package com.sirmaacademy.employeepairproject.service;

import com.sirmaacademy.employeepairproject.entity.EmployeeData;

import java.util.List;

public interface EmployeeDataService {
     List<EmployeeData> getAll();
     EmployeeData save(EmployeeData employeeData);
     void delete(Long id);
     EmployeeData getById(Long id);
     List<EmployeeData> getByEmployeeID(Long employeeID);

     boolean isExist(Long id);
}
