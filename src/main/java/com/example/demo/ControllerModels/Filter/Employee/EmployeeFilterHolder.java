package com.example.demo.ControllerModels.Filter.Employee;


import com.example.demo.Enums.EmployeeAcIn;
import com.example.demo.Enums.EmployeeRole;
import com.example.demo.Enums.EmployeeDepartment;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeFilterHolder {

    private EmployeeAcIn employeeAcIn = EmployeeAcIn.ALL;
    private String promt = "ALL";
    private EmployeeRole employeeCategory = EmployeeRole.ALL;
    private EmployeeDepartment employeeDepartment = EmployeeDepartment.ALL;
    private Double hourlyRate = 0.0;
    private LocalDate fromJoined = LocalDate.of(1000,12,12);
    private LocalDate toJoined = LocalDate.of(1000,12,12);


    private int page = 0;
    private int pageCount = 5;

}
