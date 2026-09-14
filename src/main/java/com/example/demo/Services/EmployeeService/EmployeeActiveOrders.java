package com.example.demo.Services.EmployeeService;


import com.example.demo.ControllerModels.CommonDtos.Employee;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeActiveOrders {

    private Long id;

    private Orders order;

    private Employee employee;


    private LocalDateTime created;

}
