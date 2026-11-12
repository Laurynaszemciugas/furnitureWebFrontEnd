package com.example.demo.FilterDTO.Client;

import com.example.demo.Enums.Category;
import com.example.demo.Enums.Stock;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClientProductsFilter {

    private String prompt;  // Searches names descriptions and the extra detail like width height depth
    private List<Category> categories;
    private Double priceFrom;
    private Double priceTo;
    private List<Long> materials;
    private List<String> colors; // searches according to color in the product
    private Stock stocks;

}
