package com.example.demo.ControllerModels.Products;

import com.example.demo.Enums.Category;
import com.example.demo.Enums.ProductCategory;
import lombok.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductFeedModel {

    private Long id;
    private String imageUrl;
    private String productName;
    private Category category;
    private double price;
    private Long stockQuantity;
    private Long lowStockThreshold;
}
