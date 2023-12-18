package com.sirmaacademy.employeepairproject.service.impl;

import com.sirmaacademy.employeepairproject.Exception.NotFoundException;
import com.sirmaacademy.employeepairproject.dto.EmployeeDataResponse;
import com.sirmaacademy.employeepairproject.entity.EmployeeData;
import com.sirmaacademy.employeepairproject.filemanipulator.Reader;
import com.sirmaacademy.employeepairproject.repository.EmployeeDataRepository;
import com.sirmaacademy.employeepairproject.service.EmployeeDataService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

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

    @Override
    public void saveFromFile(String filePath, Reader reader) {
        List<EmployeeData> employeeDataList = reader.read(filePath);


        for (EmployeeData employeeData : employeeDataList) {
            List<EmployeeData> stored = employeeDataRepository.findByEmployeeID(employeeData.getEmployeeID());
            // Check if there is employee with same ID
            if (stored.isEmpty()) {
                employeeDataRepository.save(employeeData);
            }
            else {
                // Check if an entry with the same projectID already exists
              boolean projectExists = stored.stream().anyMatch(data -> Objects.equals(data.getProjectID(),employeeData.getProjectID()));

                if (!projectExists) {
                    employeeDataRepository.save(employeeData);
                }
            }
        }
    }

    @Override
    public EmployeeDataResponse findPairWithMaxDays(Long projectID) {
        List<EmployeeData> employeesOnProject = employeeDataRepository.findByProjectID(projectID);
        EmployeeDataResponse pairWithMaxDays = null;
        int maxDaysTogether = 0;

        //Compare all employees who have worked on same project
        for (int i = 0; i < employeesOnProject.size(); i++) {
            EmployeeData firstEmployee = employeesOnProject.get(i);

            for (int j = i + 1; j < employeesOnProject.size(); j++) {
                EmployeeData secondEmployee = employeesOnProject.get(j);

                if (workedTogether(firstEmployee, secondEmployee)) {
                    int totalDaysTogether = calculateTotalDaysTogether(firstEmployee, secondEmployee);

                    if (totalDaysTogether > maxDaysTogether) {
                        maxDaysTogether = totalDaysTogether;
                        pairWithMaxDays = new EmployeeDataResponse(
                                firstEmployee.getEmployeeID(),
                                secondEmployee.getEmployeeID(),
                                totalDaysTogether );
                    }
                }
            }
        }
        return pairWithMaxDays;
    }

    @Override
    public EmployeeDataResponse findPairWithMaxDays() {
        List<EmployeeDataResponse> allPairsWithTotalDays = findAllPairsWithTotalDays();

        EmployeeDataResponse maxPair = null;
        int maxDaysTogether = 0;
        for (EmployeeDataResponse response : allPairsWithTotalDays) {
            if (response.getDaysTogether() > maxDaysTogether) {
                maxDaysTogether = response.getDaysTogether();
                maxPair = response;
            }
        }

        return maxPair;
    }

    public List<EmployeeDataResponse> findAllPairsWithTotalDays() {
        List<EmployeeDataResponse> allPairsWithTotalDays = new ArrayList<>();

        // List of all projects
        List<Long> allProjectIds =employeeDataRepository.findAllProjectIDs();

        // Map to store total days for each pair across all projects
        Map<List<Long>, Integer> totalDaysMap = new HashMap<>();

        for (Long projectID : allProjectIds) {
            // List of all employees that work on that project
            List<EmployeeData> employeesOnProject = employeeDataRepository.findByProjectID(projectID);

            //Compare all employees who have worked on same project did they work together
            for (int i = 0; i < employeesOnProject.size(); i++) {
                EmployeeData firstEmployee = employeesOnProject.get(i);

                for (int j = i + 1; j < employeesOnProject.size(); j++) {
                    EmployeeData secondEmployee = employeesOnProject.get(j);

                    if (workedTogether(firstEmployee, secondEmployee)) {

                        // Create a sorted list for the employee pair to prevent duplication in the map
                        List<Long> employeePair = Arrays.asList(
                                firstEmployee.getEmployeeID(),
                                secondEmployee.getEmployeeID());
                        employeePair.sort(Comparator.naturalOrder());

                        // Calculate total days together for the specific project
                        int totalDaysTogether = calculateTotalDaysTogether(firstEmployee, secondEmployee);

                        // Add or update total days in the map
                        totalDaysMap.merge(employeePair,totalDaysTogether,Integer::sum);
                    }
                }
            }
        }

        // Convert the map entries to EmployeeDataResponse objects
        totalDaysMap.forEach((pair, days) ->
                allPairsWithTotalDays.add(new EmployeeDataResponse(pair.get(0), pair.get(1), days))
        );

        return allPairsWithTotalDays;
    }

    private int calculateTotalDaysTogether(EmployeeData firstEmployee, EmployeeData secondEmployee) {
        LocalDate startDate = (firstEmployee.getDateFrom().isAfter(secondEmployee.getDateFrom())) ?
                firstEmployee.getDateFrom() : secondEmployee.getDateFrom();
        LocalDate endDate = (firstEmployee.getDateTo().isBefore(secondEmployee.getDateTo())) ?
                firstEmployee.getDateTo() : secondEmployee.getDateTo();

        return (int) startDate.datesUntil(endDate.plusDays(1)).count();
    }


    private boolean workedTogether(EmployeeData firstEmployee, EmployeeData secondEmployee) {
        LocalDate firstStartDate =firstEmployee.getDateFrom();
        LocalDate firstEndDate =firstEmployee.getDateTo();
        LocalDate secondStartDate =secondEmployee.getDateFrom();
        LocalDate secondEndDate =secondEmployee.getDateTo();

        return firstStartDate.isBefore(secondEndDate) && firstEndDate.isAfter(secondStartDate);
    }

}
