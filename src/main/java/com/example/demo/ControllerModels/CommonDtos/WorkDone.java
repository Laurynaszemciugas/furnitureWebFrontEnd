package com.example.demo.ControllerModels.CommonDtos;

import com.example.demo.ControllerModels.CommonDtos.OrderJoin.OrderStepsToComplete;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkDone {


    private Long id;

    private WorkDay workDay;


    private Employee employee;

    private LocalDateTime started;



    private OrderStepsToComplete orderStepsToComplete;

    private Orders order;

    private String whatWasDone;



    private String employeeNote;




}
