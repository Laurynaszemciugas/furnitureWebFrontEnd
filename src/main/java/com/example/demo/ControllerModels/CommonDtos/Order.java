package com.example.demo.ControllerModels.CommonDtos;

import com.example.demo.ControllerModels.CommonDtos.OrderJoin.OrderProducts;
import com.example.demo.Enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long id;
    private List<OrderProducts> productsData;
    private List<Employee> employees; // treat like material it has its class and leave it dont add join stuff
    private User user;
    private Double totalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime estimatedDueDate;
    private LocalDateTime created;


}
