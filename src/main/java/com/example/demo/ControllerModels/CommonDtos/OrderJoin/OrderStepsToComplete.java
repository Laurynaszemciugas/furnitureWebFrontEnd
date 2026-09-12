package com.example.demo.ControllerModels.CommonDtos.OrderJoin;


import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.ControllerModels.CommonDtos.ProductJoin.ProductFinishSteps;
import com.example.demo.ControllerModels.CommonDtos.User;
import com.example.demo.Enums.ProductFinishStepStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderStepsToComplete {

    private Long id;

    private ProductFinishSteps productFinishSteps;

    private Long stepsNeeded;

    private Long stepsCompleted;

    private User employee;

    private ProductFinishStepStatus productFinishStepStatus;

    private Orders order;


}
