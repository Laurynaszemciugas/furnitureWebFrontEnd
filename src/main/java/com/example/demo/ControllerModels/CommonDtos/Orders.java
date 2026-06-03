package com.example.demo.ControllerModels.CommonDtos;

import com.example.demo.ControllerModels.CommonDtos.EmployeeJoin.OrderEmployees;
import com.example.demo.ControllerModels.CommonDtos.OrderJoin.OrderProducts;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.Enums.PayMethod;
import com.example.demo.Enums.PayStatus;
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
    private User orderPlacedBy;
    private Double totalPrice;
    private OrderStatus orderStatus;
    private String orderNote;
    private LocalDateTime estimatedDueDate;
    private String phoneNumber;
    private PayStatus payStatus;
    private PayMethod payMethod;
    private String billingAddress;
    private LocalDateTime created;


}
