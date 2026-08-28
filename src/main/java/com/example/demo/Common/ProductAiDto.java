package com.example.demo.Common;

import com.example.demo.ControllerModels.CommonDtos.ExtraDetails;
import com.example.demo.ControllerModels.CommonDtos.ProductJoin.ProductFinishSteps;
import com.example.demo.Enums.Category;
import com.example.demo.Enums.MaterialGrainPatterns;
import com.example.demo.Enums.MaterialTextures;
import com.example.demo.Enums.MaterialType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
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

    private List<ProductFinishSteps> productFinishStepsList = List.of(new ProductFinishSteps(null,0L,"auto_Fill","auto_Fill",null) );

    private List<ExtraDetails> extraDetails = List.of(new ExtraDetails(null,"auto_Fill","auto_Fill",null,null,null));


    public ProductAiDto(String productName, String sku, String description, Double price, Long discount, Long stockQuantity, Long lowThreshold, Category category, List<ProductFinishSteps> productFinishStepsList, List<ExtraDetails> extraDetails) {
        this.productName = productName;
        this.sku = sku;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.stockQuantity = stockQuantity;
        this.lowThreshold = lowThreshold;
        this.category = category;
        this.productFinishStepsList = productFinishStepsList;
        this.extraDetails = extraDetails;
    }

    @JsonCreator
    public ProductAiDto() {
    }



}



