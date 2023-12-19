package com.sirmaacademy.employeepairproject.repository;

import com.sirmaacademy.employeepairproject.entity.EmployeeData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EmployeeDataRepository extends JpaRepository<EmployeeData, Long> {

    Optional<List<EmployeeData>> findByEmployeeID (Long employeeID);

    Optional<List<EmployeeData>> findByProjectID (Long projectID);

    @Query("SELECT DISTINCT ed.projectID FROM EmployeeData ed")
    List<Long> findAllProjectIDs ();
}
