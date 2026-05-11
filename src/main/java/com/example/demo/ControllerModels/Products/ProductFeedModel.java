package com.example.demo.ControllerModels.Products;

import com.example.demo.Enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductFeedModel {

    private long id;
    private String mainImageUrl;
    private String productName;
    private ProductCategory productCategory;
    private double price;
    private long unitsLeft;
    private long minTreshold;




}
