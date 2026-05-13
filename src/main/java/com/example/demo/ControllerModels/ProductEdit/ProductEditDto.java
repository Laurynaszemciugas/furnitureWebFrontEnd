package com.example.demo.ControllerModels.ProductEdit;

import com.example.demo.ControllerModels.Common.GridMaterials;
import com.example.demo.ControllerModels.Common.ImagesData;
import com.example.demo.Enums.Category;
import com.example.demo.Enums.Status;
import com.example.demo.Enums.Tags;
import com.example.demo.Enums.Visibility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEditDto {


    private List<ImagesData> images;
    private String productName;
    private String sku;
    private String description;
    private double price;
    private double discount;
    private double materialCost;
    private long stockQuantity;
    private long lowStockThreshold;
    private Category category;
    private List<Tags> tags;
    private Status status;
    private Visibility visibility;
    private List<GridMaterials> materials;




}
