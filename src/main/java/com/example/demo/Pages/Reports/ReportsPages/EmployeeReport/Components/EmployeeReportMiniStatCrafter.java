package com.example.demo.Pages.Reports.ReportsPages.EmployeeReport.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Pages.Reports.Common.ReportMiniStatHolder;
import com.example.demo.Pages.Reports.Common.ReportsMiniStatCrafter;
import com.example.demo.Services.EmployeeService.EmployeeService;
import com.example.demo.Services.Material.MaterialService;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.time.LocalDate;

public class EmployeeReportMiniStatCrafter {

    CommonComponents commonComponents;
    Common common;
    ReportsMiniStatCrafter miniStatCrafter;

    EmployeeService employeeService;

    public EmployeeReportMiniStatCrafter(CommonComponents commonComponents, Common common, EmployeeService employeeService) {
        this.commonComponents = commonComponents;
        this.common = common;

        this.miniStatCrafter = new ReportsMiniStatCrafter(commonComponents,common);

        this.employeeService = employeeService;
    }

    public HorizontalLayout miniStatHolder(LocalDate fromDate, LocalDate toDate, String color, String widths, String jwt) {

        ReportMiniStatHolder items = employeeService.getReportEmployeeMiNIStats(fromDate, toDate, jwt);

        HorizontalLayout miniStatHolders = new HorizontalLayout();

        miniStatHolders.addClassName("layout-flex");

        miniStatHolders.setWidth(widths);
        miniStatHolders.setMinWidth("320px");


        String backgroundColor = common.hexToRgba(color, 0.15);

        miniStatHolders.add(
                miniStatCrafter.miniStats(VaadinIcon.CART, "Total employees", items.getValue1ThisMonth(), common.lastMonthTrend(items.getValue1ThisMonth(), items.getValue1LastMonth(), fromDate, true), color, backgroundColor),
                miniStatCrafter.miniStats(VaadinIcon.CLOCK, "Top performer", items.getValue2ThisMonth(), common.lastMonthTrend(items.getValue2LastMonth(), items.getValue2LastMonth(), fromDate, false), color, backgroundColor),
                miniStatCrafter.miniStats(VaadinIcon.CHECK, "Average hours worked", items.getValue3ThisMonth(), common.lastMonthTrend(items.getValue3ThisMonth(), items.getValue3LastMonth(), fromDate, true), color, backgroundColor),
                miniStatCrafter.miniStats(VaadinIcon.MONEY, "Labor cost", items.getValue4ThisMonth() + " Eur", common.lastMonthTrend(items.getValue4ThisMonth(), items.getValue4LastMonth(), fromDate, true), color, backgroundColor)
        );

        return miniStatHolders;
    }


}
