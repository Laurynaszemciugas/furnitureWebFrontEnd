package com.example.demo.Common;

import com.example.demo.Enums.Category;
import com.example.demo.Enums.MaterialGrainPatterns;
import com.example.demo.Enums.MaterialTextures;
import com.example.demo.Enums.MaterialType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductAiDto {

    private String productName = "None";
    private String sku = "None";
    private String description = "None";

    private Double price = 0.0;
    private Long discount = 0L;

    private Long stockQuantity = 0L;
    private Long lowThreshold = 0L;
    private Category category = Category.ALL;


}



