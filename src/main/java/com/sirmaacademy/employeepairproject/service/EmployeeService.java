package com.sirmaacademy.employeepairproject.service;

import com.sirmaacademy.employeepairproject.entity.Employee;

import java.util.List;

public interface EmployeeService {
     List<Employee> getAll();
     Employee save(Employee employee);
     void delete(Long id);
     Employee getById(Long id);

     boolean isExist(Long id);
}
