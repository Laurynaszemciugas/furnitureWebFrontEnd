package com.example.demo.ControllerModels.CommonDtos;



import com.example.demo.ControllerModels.CommonDtos.ProductJoin.ProductMaterials;
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
public class Product {

    private Long id;
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

    private List<ImagesData> images = new ArrayList<>();

    private List<ProductMaterials> materials = new ArrayList<>();

    private List<ExtraDetails> extraDetails = new ArrayList<>();

    private List<Comments> comments= new ArrayList<>();

    @JsonIgnore
    private User user;

    private LocalDateTime created;



}
