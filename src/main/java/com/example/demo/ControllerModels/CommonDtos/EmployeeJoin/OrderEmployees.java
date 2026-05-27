package com.example.demo.ControllerModels.CommonDtos.EmployeeJoin;

import com.example.demo.ControllerModels.CommonDtos.Employee;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEmployees {

    private Long id;
    private Employee employee;
    private Orders order;
    private LocalDateTime created;

}
