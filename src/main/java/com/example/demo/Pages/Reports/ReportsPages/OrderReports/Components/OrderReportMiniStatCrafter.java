package com.example.demo.Pages.Reports.ReportsPages.OrderReports.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Enums.Widths;
import com.example.demo.Pages.Reports.Common.ReportMiniStatHolder;
import com.example.demo.Pages.Reports.Common.ReportsMiniStatCrafter;
import com.example.demo.Services.Orders.OrdersService;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.time.LocalDate;

public class OrderReportMiniStatCrafter {

    CommonComponents commonComponents;
    Common common;
    ReportsMiniStatCrafter miniStatCrafter;

    OrdersService ordersService;

    public OrderReportMiniStatCrafter(CommonComponents commonComponents, Common common,OrdersService ordersService) {
        this.commonComponents = commonComponents;
        this.common = common;

        this.miniStatCrafter = new ReportsMiniStatCrafter(commonComponents,common);

        this.ordersService = ordersService;
    }

    public HorizontalLayout miniStatHolder(LocalDate fromDate, LocalDate toDate, String color, Widths widths){

        ReportMiniStatHolder items = ordersService.getOrderMiniStatData(fromDate,toDate);

        HorizontalLayout miniStatHolders = new HorizontalLayout();
        miniStatHolders.setWidth(widths.getWidth());
        miniStatHolders.addClassName("layout-flex");

        String backgroundColor = common.hexToRgba(color,0.15);

        miniStatHolders.add(
                miniStatCrafter.miniStats(VaadinIcon.CART, "Total orders", items.getValue1ThisMonth(),common.lastMonthTrend(items.getValue1ThisMonth(),items.getValue1LastMonth(),fromDate,true), color, backgroundColor),
                miniStatCrafter.miniStats(VaadinIcon.CLOCK, "Finished orders", items.getValue2ThisMonth(), common.lastMonthTrend(items.getValue2ThisMonth(),items.getValue2LastMonth(),fromDate,true), color, backgroundColor),
                miniStatCrafter.miniStats(VaadinIcon.CHECK, "In progress orders", items.getValue3ThisMonth(), common.lastMonthTrend(items.getValue3ThisMonth(),items.getValue3LastMonth(),fromDate,true), color, backgroundColor),
                miniStatCrafter.miniStats(VaadinIcon.MONEY, "Total revenue",items.getValue4ThisMonth() + " Eur", common.lastMonthTrend(items.getValue4ThisMonth(),items.getValue4LastMonth(),fromDate,true), color, backgroundColor)
        );

        return miniStatHolders;
    }


}
