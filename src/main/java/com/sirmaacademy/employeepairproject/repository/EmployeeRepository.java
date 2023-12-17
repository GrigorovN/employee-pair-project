package com.sirmaacademy.employeepairproject.repository;

import com.sirmaacademy.employeepairproject.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
