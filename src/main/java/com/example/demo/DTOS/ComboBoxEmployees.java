package com.example.demo.DTOS;

import com.example.demo.Enums.EmployeeCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComboBoxEmployees {


    private Long id;
    private String fullName;
    private EmployeeCategory employeeCategory;


}
