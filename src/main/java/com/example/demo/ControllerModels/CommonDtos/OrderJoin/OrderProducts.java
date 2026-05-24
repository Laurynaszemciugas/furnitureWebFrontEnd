package com.example.demo.ControllerModels.CommonDtos.OrderJoin;

import com.example.demo.ControllerModels.CommonDtos.Product;
import com.example.demo.ControllerModels.CommonDtos.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderProducts {

    private Long id;
    private Product product;
    private Long amountOfProduct;
    private Double cost;
    private LocalDateTime created;

}
