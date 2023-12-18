package com.sirmaacademy.employeepairproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeDataResponse {

    private Long firstEmployeeID;

    private Long secondEmployeeID;

    private Integer daysTogether;

    private Map<Long, Integer> projectIDToDaysMap;
}
