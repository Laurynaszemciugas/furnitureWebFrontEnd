package com.example.demo.ControllerModels.Common;

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
public class ProductDataEditAddDto {


    private List<ImagesData> images;
    private String productName;
    private String sku;
    private String description;
    private double price;
    private double discount;
    private double materialCost;
    private Long stockQuantity;
    private Long lowStockThreshold;
    private Category category;
    private List<Tags> tags;
    private Status status;
    private Visibility visibility;
    private List<GridMaterials> materials;
    private List<ExtraDetails> extraDetails;




}
