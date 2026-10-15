package com.example.demo.Entity;



import com.example.demo.Entity.ProductJoin.ProductFinishSteps;
import com.example.demo.Entity.ProductJoin.ProductMaterials;
import com.example.demo.Enums.Category;
import com.example.demo.Enums.Status;
import com.example.demo.Enums.Stock;
import com.example.demo.Enums.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {

    private Long id;

    private String color;

    private boolean stockCalculatedManually;

    private String productName;
    private String sku;
    private String description;
    private double price;
    private double discount;
    private double materialCost;
    private Long stockQuantity;
    private Long lowStockThreshold;
    private Category category;
    private Status status;
    private Visibility visibility;
    private Stock stock;

    private List<ProductTags> tags = new ArrayList<>();

    private List<ProductImageData> images = new ArrayList<>();

    private List<ProductMaterials> materials = new ArrayList<>();

    private List<ExtraDetails> extraDetails = new ArrayList<>();

    private List<ProductComments> comments= new ArrayList<>();

    private List<ProductFinishSteps> steps = new ArrayList<>();

    private Boolean multipleItems;

    private List<ProductMultiple> productMultiples = new ArrayList<>();

    @JsonIgnore
    private User user;

    private LocalDateTime created;



}
