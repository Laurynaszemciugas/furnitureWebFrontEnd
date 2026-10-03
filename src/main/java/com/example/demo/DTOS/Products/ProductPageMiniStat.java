package com.example.demo.DTOS.Products;

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
