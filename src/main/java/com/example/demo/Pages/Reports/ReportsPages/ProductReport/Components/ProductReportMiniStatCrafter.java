package com.example.demo.Pages.Reports.ReportsPages.ProductReport.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Enums.Widths;
import com.example.demo.Pages.Reports.Common.ReportMiniStatHolder;
import com.example.demo.Pages.Reports.Common.ReportsMiniStatCrafter;
import com.example.demo.Services.Material.MaterialService;
import com.example.demo.Services.Orders.OrdersService;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.time.LocalDate;

public class ProductReportMiniStatCrafter {

    CommonComponents commonComponents;
    Common common;
    ReportsMiniStatCrafter miniStatCrafter;

    MaterialService materialService;

    public ProductReportMiniStatCrafter(CommonComponents commonComponents, Common common, MaterialService materialService) {
        this.commonComponents = commonComponents;
        this.common = common;

        this.miniStatCrafter = new ReportsMiniStatCrafter(commonComponents,common);

        this.materialService = materialService;
    }

    public HorizontalLayout miniStatHolder(LocalDate fromDate, LocalDate toDate, String color, Widths widths){

        ReportMiniStatHolder items = materialService.getProductMiniStatData(fromDate,toDate);

        HorizontalLayout miniStatHolders = new HorizontalLayout();
        miniStatHolders.setWidth(widths.getWidth());
        miniStatHolders.addClassName("layout-flex");

        String backgroundColor = common.hexToRgba(color,0.15);

        miniStatHolders.add(
                miniStatCrafter.miniStats(VaadinIcon.CART, "Total materials", items.getValue1ThisMonth(),common.lastMonthTrend(items.getValue1ThisMonth(),items.getValue1LastMonth(),fromDate,true), color, backgroundColor),
                miniStatCrafter.miniStats(VaadinIcon.CLOCK, "In stock", items.getValue2ThisMonth(), common.lastMonthTrend(items.getValue2ThisMonth(),items.getValue2LastMonth(),fromDate,true), color, backgroundColor),
                miniStatCrafter.miniStats(VaadinIcon.CHECK, "Low stock materials", items.getValue3ThisMonth(), common.lastMonthTrend(items.getValue3ThisMonth(),items.getValue3LastMonth(),fromDate,true), color, backgroundColor),
                miniStatCrafter.miniStats(VaadinIcon.MONEY, "Inventory value",items.getValue4ThisMonth() + " Eur", common.lastMonthTrend(items.getValue4ThisMonth(),items.getValue4LastMonth(),fromDate,true), color, backgroundColor)
        );

        return miniStatHolders;
    }


}
