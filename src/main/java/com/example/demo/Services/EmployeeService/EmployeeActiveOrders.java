package com.example.demo.Services.EmployeeService;


import com.example.demo.Entity.Employee;
import com.example.demo.Entity.Orders;
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
