package com.example.demo.ControllerModels.Orders;

import com.example.demo.Enums.Category;
import com.example.demo.Enums.Stock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderAddProducts {

    private Long id;
    private String mainImage;
    private String productName;
    private String sku;
    private Category category;
    private Long stockQuantity;
    private Long lowStockThreshold;
    private Stock stock;
    private Double price;
    private Long amountSelected;

}
