package com.example.demo.ControllerModels.Orders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class NewOrderFeedData {

    private Long id;
    private String mainImage;
    private String name;
    private String sku;
    private Long quantity;
    private Long remainingAmount;
    private Double unitPrice;
    private Double total;


}

