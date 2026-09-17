package com.example.demo.ControllerModels.CommonDtos.OrderStepsJoin;

import com.example.demo.ControllerModels.CommonDtos.OrderJoin.OrderStepsToComplete;
import com.example.demo.ControllerModels.CommonDtos.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderStepCompletionLogs {


    private Long id;

    private User employee;

    private String thingThatWasDone;


    private OrderStepsToComplete orderStepsToComplete;




    private LocalDateTime created;


}
