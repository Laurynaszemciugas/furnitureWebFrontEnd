package com.example.demo.Pages.Reports.ReportsPages.CreatorPage.DTOS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomReportFeed {

    private Long id;
    private String reportName;
    private String reportColor;

}
