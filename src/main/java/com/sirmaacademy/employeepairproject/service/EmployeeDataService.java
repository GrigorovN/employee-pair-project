package com.sirmaacademy.employeepairproject.service;

import com.sirmaacademy.employeepairproject.dto.EmployeeDataResponse;
import com.sirmaacademy.employeepairproject.entity.EmployeeData;
import com.sirmaacademy.employeepairproject.filemanipulator.Reader;

import java.util.List;

public interface EmployeeDataService {

     EmployeeData save(EmployeeData employeeData);

     void delete(Long id);

     List<EmployeeData> getByEmployeeID(Long employeeID);

     void saveFromFile(String filePath, Reader reader);

     EmployeeDataResponse findPairWithMaxDays();


}
