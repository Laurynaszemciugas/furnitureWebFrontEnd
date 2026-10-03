package com.example.demo.Entity.OrderJoin;


import com.example.demo.Entity.OrderStepsJoin.OrderStepCompletionLogs;
import com.example.demo.Entity.User;
import com.example.demo.Enums.ProductFinishStepStatus;
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
public class OrderStepsToComplete {

    private Long id;

    // the steps refrence removed so user could save previous steps and add new ones ofcouse new ones will now be present in already created orders

    private Long stepRealId = null;
    private Long stepId = null;
    private String stepName = null;
    private String stepDescription = null;

    private Long stepsNeeded;

    private Long stepsCompleted;

    private User employee;

    private ProductFinishStepStatus productFinishStepStatus;

    private OrderProducts orderProducts;

    private List<OrderStepCompletionLogs> orderStepCompletionLogs;

    private LocalDateTime created;


}
