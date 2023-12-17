package com.sirmaacademy.employeepairproject.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "employees")
@AllArgsConstructor
@Getter
@Setter
@Builder
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Long id;

    @Column(name = "project_id",nullable = false)
    @NotNull
    @Positive(message = "Project ID must be a positive or zero value")
    private Long projectID;

    @Column(name = "date_from",nullable = false)
    private LocalDate dateFrom;

    @Column(name = "date_to",nullable = false)
    private LocalDate dateTo;
}
