package com.example.demo.ControllerModels.Products;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductPageMiniStat {

    private Long totalProducts;
    private Long visible;
    private Long nonVisible;
    private Long recentlyAdded;

}
