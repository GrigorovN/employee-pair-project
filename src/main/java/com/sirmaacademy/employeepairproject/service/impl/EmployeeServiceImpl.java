package com.sirmaacademy.employeepairproject.service.impl;

import com.sirmaacademy.employeepairproject.Exception.NotFoundException;
import com.sirmaacademy.employeepairproject.entity.Employee;
import com.sirmaacademy.employeepairproject.repository.EmployeeRepository;
import com.sirmaacademy.employeepairproject.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAll() {
        return null;
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Employee getById(Long id) {

        return employeeRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Employee with id = %d not found", id)));
    }

    @Override
    public boolean isExist(Long id) {
        if (employeeRepository.findById(id).isEmpty()) {
            return false;
        }
        return true;
    }

}
