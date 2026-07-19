package com.example.demo.ControllerModels.CommonDtos;

import com.example.demo.Enums.Type;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StockMovement {

    private Long id;

    private Type type;

    private Long amountTakeAdd;

    private Materials materials;

    private User user;

    private LocalDateTime created;



}
