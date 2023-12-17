package com.sirmaacademy.employeepairproject.filemanipulator;

import com.sirmaacademy.employeepairproject.entity.EmployeeData;

import java.util.List;

public interface Reader {

    List<EmployeeData> read(String fileName);
}
