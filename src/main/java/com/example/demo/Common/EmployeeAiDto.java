package com.example.demo.Common;

import com.example.demo.ControllerModels.CommonDtos.User;
import com.example.demo.Enums.EmployeeAcIn;
import com.example.demo.Enums.EmployeeDepartment;
import com.example.demo.Enums.EmployeeRole;
import com.example.demo.Enums.EmploymentType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class EmployeeAiDto {



        private Double hourlySalary = 0.0;
        private String name = "None";
        private String lastName = "None";
        private String emailAddress = "None";

        private String phoneNumber = "None";
        private LocalDate dateOfBirth = LocalDate.of(1000,12,12);
        private String address = "None";
        private EmploymentType employmentType = EmploymentType.ALL;

        private EmployeeRole role = EmployeeRole.ALL;
        private EmployeeDepartment department = EmployeeDepartment.ALL;


}
