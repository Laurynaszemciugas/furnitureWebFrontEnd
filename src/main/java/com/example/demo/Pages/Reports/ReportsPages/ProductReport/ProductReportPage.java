package com.example.demo.Pages.Reports.ReportsPages.ProductReport;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Enums.Widths;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Reports.ReportsPages.ProductReport.Components.BriefProductReportPageExplanation;
import com.example.demo.Pages.Reports.ReportsPages.ProductReport.Components.ProductReportCharts;
import com.example.demo.Pages.Reports.ReportsPages.ProductReport.Components.ProductReportMiniStatCrafter;
import com.example.demo.Services.Material.MaterialService;
import com.example.demo.Services.Orders.OrdersService;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "ProductReport", layout = MainLayout.class)
public class ProductReportPage extends VerticalLayout implements BeforeEnterObserver {


    CommonComponents commonComponents;
    Common common;
    BriefProductReportPageExplanation biefExplanation;
    MaterialService materialService;

    ProductReportCharts charts;

    ProductReportMiniStatCrafter orderReportMiniStatCrafter;


    public ProductReportPage(CommonComponents commonComponents, Common common, MaterialService materialService) {

        this.commonComponents = commonComponents;
        this.common = common;
        this.biefExplanation = new BriefProductReportPageExplanation(commonComponents, common);

        this.orderReportMiniStatCrafter = new ProductReportMiniStatCrafter(commonComponents, common,materialService);

        this.materialService = materialService;

        this.charts = new ProductReportCharts(commonComponents,common,materialService);


        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(Alignment.CENTER);


        addClassName("animation-page");

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        removeAll();


        add(mainLayout());

    }

    public HorizontalLayout mainLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setMaxWidth("1650px");
        layout.setPadding(true);
        layout.getStyle().set("margin-top", "5px");

        layout.addClassName("layout-flex");


        layout.add(
                biefExplanation.briefExplanation(),
                orderReportMiniStatCrafter.miniStatHolder(common.currentMonthStart(), common.nextMonthDate(), "#035afc", Widths.FULL_WIDTH)
//                charts.ordersByStatusChart(common.currentMonthStart(), common.nextMonthDate(),Widths.HALF_WIDTH),
//                charts.OrderRevenueAccordingToMonth(common.currentMonthStart(), common.nextMonthDate(),Widths.HALF_WIDTH),
//                charts.topCustomerOrder(common.currentMonthStart(), common.nextMonthDate(),Widths.HALF_WIDTH),
//                charts.recentOrdersList(common.currentMonthStart(), common.nextMonthDate(),Widths.HALF_WIDTH)
        );

        return layout;
    }




}


