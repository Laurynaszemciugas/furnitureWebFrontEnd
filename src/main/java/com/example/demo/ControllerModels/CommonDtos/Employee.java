package com.example.demo.ControllerModels.CommonDtos;

import com.example.demo.Enums.EmployeeAcIn;
import com.example.demo.Enums.EmployeeCategory;
import com.example.demo.Enums.EmployeeDepartment;
import com.example.demo.Enums.EmploymentType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {

    private Long id;
    private Double hourlyRate;
    private Long productsFinished;
    private String name;
    private String lastName;
    private String fullName;
    private String gmail;

    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String address;
    private String jobTittle;
    private String employeeId;
    private EmploymentType employmentType;

    private String profileImage;
    private EmployeeAcIn employeeAcIn;
    private EmployeeCategory employeeCategory;
    private EmployeeDepartment employeeDepartment;
    private User user;
    private LocalDateTime created;


}
