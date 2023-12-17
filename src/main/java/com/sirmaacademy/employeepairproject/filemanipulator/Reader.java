package com.sirmaacademy.employeepairproject.filemanipulator;

import com.sirmaacademy.employeepairproject.entity.Employee;

import java.util.List;

public interface Reader {

    List<Employee> read(String fileName);
}
