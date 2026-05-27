package com.example.demo.ControllerModels.CommonDtos;

import com.example.demo.Enums.EmployeeCategory;
import com.example.demo.Enums.EnabledDisabled;
import lombok.*;

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

    private String profileImage;

    private String password;

    private EnabledDisabled enabledDisabled;
    private EmployeeCategory employeeCategory;
    private User user;
    private LocalDateTime created;


}
