package com.example.demo.Entity.EmployeeJoin;

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
public class OrderEmployees {

    private Long id;
    private Employee employee;
    private Orders order;
    private LocalDateTime created;

}
