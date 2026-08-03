package com.example.demo.ControllerModels.CommonDtos.CreateReport;

import com.example.demo.ControllerModels.CommonDtos.User;
import com.example.demo.Enums.DashboardWidget;
import com.example.demo.Enums.ReportCategory;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Report {

    private Long id;

    private String reportName;
    private String reportColor;
    private String description;

    private ReportCategory reportCategory;

    private DashboardWidget dashboardWidget;

    private User user;

    private List<ReportItems> reportItemsList = new ArrayList<>();

    private LocalDateTime created;

}
