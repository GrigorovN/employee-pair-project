package com.sirmaacademy.employeepairproject.service.impl;

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
    public EmployeeData save(EmployeeData employeeData) {
        return employeeDataRepository.save(employeeData);
    }

    @Override
    public void delete(Long id) {
            //TODO
    }

    @Override
    public List<EmployeeData> getByEmployeeID(Long employeeID) {
        return employeeDataRepository.findByEmployeeID(employeeID);
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
    public EmployeeDataResponse findPairWithMaxDays() {
        List<EmployeeDataResponse> allPairsWithTotalDays = findAllPairsWithTotalDays();

        EmployeeDataResponse maxPair = null;
        int maxDaysTogether = 0;
        Map<Long, Integer> maxProjectDaysMap = new HashMap<>();

        for (EmployeeDataResponse response : allPairsWithTotalDays) {
            if (response.getDaysTogether() > maxDaysTogether) {
                maxDaysTogether = response.getDaysTogether();
                maxPair = response;
                maxProjectDaysMap = response.getProjectIDToDaysMap();
            }
        }

        if (maxPair != null) {
            maxPair.setProjectIDToDaysMap(maxProjectDaysMap);
        }

        return maxPair;
    }

    public List<EmployeeDataResponse> findAllPairsWithTotalDays() {
        List<EmployeeDataResponse> allPairsWithTotalDays = new ArrayList<>();

        // List of all projects
        List<Long> allProjectIds =employeeDataRepository.findAllProjectIDs();

        // Map to store total days for each pair and project across all projects
        Map<List<Long>, Map<Long, Integer>> totalDaysMap = new HashMap<>();

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
                        int totalDaysTogether = calculateDaysTogether(firstEmployee, secondEmployee);

                        // Initialize the map for the specific pair if not present
                        totalDaysMap.putIfAbsent(employeePair, new HashMap<>());

                        // Add or update total days in the map for the specific project
                        totalDaysMap.get(employeePair).merge(projectID, totalDaysTogether, Integer::sum);
                    }
                }
            }
        }

        // Convert the map entries to EmployeeDataResponse objects
        totalDaysMap.forEach((pair, projectDaysMap) ->
                allPairsWithTotalDays.add(new EmployeeDataResponse(
                        pair.get(0),
                        pair.get(1),
                        calculateTotalDaysForPairForAllProjects(projectDaysMap),
                        projectDaysMap))
        );

        return allPairsWithTotalDays;
    }

    private int calculateTotalDaysForPairForAllProjects(Map<Long, Integer> projectDaysMap) {
        return projectDaysMap.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    private int calculateDaysTogether(EmployeeData firstEmployee, EmployeeData secondEmployee) {
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
