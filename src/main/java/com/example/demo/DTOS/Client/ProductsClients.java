package com.example.demo.DTOS.Client;

import com.example.demo.Enums.Category;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductsClients {


    private Long id;
    private String productName;
    private Category category;
    private boolean multiVariants;
    private String nonMultiVariantImage;
    private Object multiVariantImages;
    private Object multiVariantPrices;
    private Object multiVariantDiscounts;
    private Double normalProductPrice;
    private Long normalProductDiscount;

}
