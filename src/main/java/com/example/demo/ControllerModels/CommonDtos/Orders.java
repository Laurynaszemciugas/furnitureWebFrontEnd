package com.example.demo.ControllerModels.CommonDtos;

import com.example.demo.ControllerModels.CommonDtos.EmployeeJoin.OrderEmployees;
import com.example.demo.ControllerModels.CommonDtos.OrderJoin.OrderProducts;
import com.example.demo.Enums.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Orders {

    private Long id;
    private List<OrderProducts> productsData;
    private List<OrderEmployees> employees;
    private User user;
    private Double totalPrice;
    private OrderStatus orderStatus;
    private String orderNote;
    private LocalDateTime estimatedDueDate;
    private LocalDateTime created;


}
