package com.example.demo.Entity.OrderStepsJoin;

import com.example.demo.Entity.OrderJoin.OrderStepsToComplete;
import com.example.demo.Entity.User;
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
