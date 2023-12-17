package com.sirmaacademy.employeepairproject.service.impl;

import com.sirmaacademy.employeepairproject.Exception.NotFoundException;
import com.sirmaacademy.employeepairproject.entity.EmployeeData;
import com.sirmaacademy.employeepairproject.repository.EmployeeDataRepository;
import com.sirmaacademy.employeepairproject.service.EmployeeDataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeDataServiceImpl implements EmployeeDataService {

    private final EmployeeDataRepository employeeDataRepository;

    public EmployeeDataServiceImpl(EmployeeDataRepository employeeDataRepository) {
        this.employeeDataRepository = employeeDataRepository;
    }

    @Override
    public List<EmployeeData> getAll() {
        return null;
    }

    @Override
    public EmployeeData save(EmployeeData employeeData) {
        return employeeDataRepository.save(employeeData);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public EmployeeData getById(Long id) {

        return employeeDataRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Employee with id = %d not found", id)));
    }

    @Override
    public List<EmployeeData> getByEmployeeID(Long employeeID) {
        return employeeDataRepository.findByEmployeeID(employeeID);
    }

    @Override
    public boolean isExist(Long id) {
        if (employeeDataRepository.findById(id).isEmpty()) {
            return false;
        }
        return true;
    }

}
