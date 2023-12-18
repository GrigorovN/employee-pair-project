package com.sirmaacademy.employeepairproject.repository;

import com.sirmaacademy.employeepairproject.entity.EmployeeData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmployeeDataRepository extends JpaRepository<EmployeeData, Long> {

    List<EmployeeData> findByEmployeeID (Long employeeID);

    List<EmployeeData> findByProjectID (Long projectID);

    @Query("SELECT DISTINCT ed.projectID FROM EmployeeData ed")
    List<Long> findAllProjectIDs ();
}
