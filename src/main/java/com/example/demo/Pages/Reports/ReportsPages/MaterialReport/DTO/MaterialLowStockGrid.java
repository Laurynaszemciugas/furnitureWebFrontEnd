package com.example.demo.Pages.Reports.ReportsPages.MaterialReport.DTO;

import com.example.demo.Enums.Stock;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MaterialLowStockGrid {

    private Long id;
    private String materialName;
    private Long inStock;
    private Long minThreshold;
    private Stock stock;

}
