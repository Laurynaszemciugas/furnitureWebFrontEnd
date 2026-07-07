package com.example.demo.ControllerModels.Employee;

import com.example.demo.Enums.EmployeeAcIn;
import com.example.demo.Enums.EmployeeRole;
import com.example.demo.Enums.EmployeeDepartment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeBriefDto {

    private Long id;
    private String profileImage;
    private String fullName;
    private String gmail;
    private EmployeeAcIn employeeAcIn;
    private EmployeeRole employeeCategory;
    private EmployeeDepartment employeeDepartment;
    private Double hourlyRate;
    private LocalDateTime created;

}
