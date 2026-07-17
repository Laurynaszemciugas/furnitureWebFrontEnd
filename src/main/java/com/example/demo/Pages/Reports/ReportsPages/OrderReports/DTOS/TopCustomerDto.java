package com.example.demo.Pages.Reports.ReportsPages.OrderReports.DTOS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopCustomerDto {

    private Long id;
    private String name;
    private Long orders;
    private Double revenue;
    private Double averageRevenue;


}
