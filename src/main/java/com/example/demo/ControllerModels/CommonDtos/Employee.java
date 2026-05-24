package com.example.demo.ControllerModels.CommonDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private Long id;
    private Double hourlyRate;
    private Long productsFinished;
    private String Role;

    private User user;
    private LocalDateTime created;


}
