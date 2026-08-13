package com.example.demo.Common;

import com.example.demo.ControllerModels.CommonDtos.EmployeeJoin.OrderEmployees;
import com.example.demo.ControllerModels.CommonDtos.OrderJoin.OrderProducts;
import com.example.demo.ControllerModels.CommonDtos.User;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.Enums.PayMethod;
import com.example.demo.Enums.PayStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderAiDto {



    private String orderNote = "None";
    private LocalDateTime estimatedDueDate = LocalDateTime.now();
    private String phoneNumber = "None";
    private String billingAddress = "None";
    private String orderCreatedByName = "None";
    private String orderCreatedByGmail = "None";



    }
