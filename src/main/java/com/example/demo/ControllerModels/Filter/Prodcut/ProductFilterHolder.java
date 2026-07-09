package com.example.demo.ControllerModels.Filter.Prodcut;

import com.example.demo.Enums.Category;
import com.example.demo.Enums.ProductCategory;
import com.example.demo.Enums.Stock;
import com.example.demo.Enums.Visibility;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductFilterHolder {

    private String prompt = "ALL";
    private Stock stockChoice = Stock.ALL;
    private Category category = Category.ALL;
    private Visibility visibility = Visibility.Visible;
    private LocalDate createdFrom = LocalDate.of(1000,12,12);
    private LocalDate createdTo = LocalDate.of(1000,12,12);
    private Long discount = 0L;
    private Double price = 0.0;
    private Long materialId = 0L;
    private int page = 0;
    private int pageCount = 20;




}
