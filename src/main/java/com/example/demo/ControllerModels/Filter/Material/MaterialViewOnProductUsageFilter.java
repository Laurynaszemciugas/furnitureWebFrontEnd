package com.example.demo.ControllerModels.Filter.Material;

import com.example.demo.Enums.Category;
import com.example.demo.Enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaterialViewOnProductUsageFilter {


    private Long id;
    private String prompt = "ALL";
    private Category productCategory = Category.ALL;

    private int page  = 0;
    private int pageCount = 10;


}
