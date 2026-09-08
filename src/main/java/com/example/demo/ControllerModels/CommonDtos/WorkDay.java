package com.example.demo.ControllerModels.CommonDtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkDay {



    private Long id;


    private Employee employee;

    private LocalDateTime workDayCreated;


    private LocalDateTime workDayEnd;

    private Long workedForMinutes;

    private User user;


}
