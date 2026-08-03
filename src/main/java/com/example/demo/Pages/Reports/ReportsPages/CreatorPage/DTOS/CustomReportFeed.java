package com.example.demo.Pages.Reports.ReportsPages.CreatorPage.DTOS;

import com.example.demo.Enums.DashboardWidget;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomReportFeed {

    private Long id;
    private String reportName;
    private DashboardWidget dashboardWidget;
    private String reportColor;
    private String description;
    private LocalDateTime created;

}
