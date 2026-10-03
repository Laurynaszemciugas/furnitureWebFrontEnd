package com.example.demo.Entity.OrderJoin;

import com.example.demo.Entity.Product;
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

    private List<OrderStepsToComplete> orderSteps;

    private LocalDateTime created;

}
