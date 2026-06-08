package com.example.demo.ControllerModels.Orders;

import com.example.demo.Enums.Category;
import com.example.demo.Enums.Stock;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderAddProducts {

    private Long id;
    @ToString.Exclude
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
