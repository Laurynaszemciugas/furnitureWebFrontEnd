package com.example.demo.Pages.Reports.ReportsPages.ProductReportpPage.DTO;

import com.example.demo.Enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductReportPieChart {

    private Category category;
    private Long value;

}
