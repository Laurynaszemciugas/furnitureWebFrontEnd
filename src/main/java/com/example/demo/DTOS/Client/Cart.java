package com.example.demo.DTOS.Client;

import com.example.demo.Enums.Category;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cart {

    private Long productId;
    private Long multiVariantId;
    private String productName;
    private String imageUrl;
    private String color;
    private Category category;
    private Double price;
    private Long discount;
    private Long amountSelected;



}
