package com.example.demo.Pages.Reports.ReportsPages.ProductReportpPage;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Enums.Widths;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Reports.Common.CommonBriefPageExplanation;
import com.example.demo.Pages.Reports.Common.FromToDate;
import com.example.demo.Pages.Reports.ReportsPages.OrderReports.Components.OrderReportCharts;
import com.example.demo.Pages.Reports.ReportsPages.OrderReports.Components.OrderReportMiniStatCrafter;
import com.example.demo.Pages.Reports.ReportsPages.ProductReportpPage.Components.ProductReportCharts;
import com.example.demo.Pages.Reports.ReportsPages.ProductReportpPage.Components.ProductReportMiniStatCrafter;
import com.example.demo.Services.Orders.OrdersService;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;

@Route(value = "ProductReport", layout = MainLayout.class)
public class ProductReportPage extends VerticalLayout implements BeforeEnterObserver {


    CommonComponents commonComponents;
    Common common;
    CommonBriefPageExplanation biefExplanation;
    ProductService productService;

    ProductReportCharts charts;

    ProductReportMiniStatCrafter productReportMiniStatCrafter;

    VerticalLayout briefExplanationMemory = new VerticalLayout();

    HorizontalLayout layout = new HorizontalLayout();

    public ProductReportPage(CommonComponents commonComponents, Common common, ProductService productService) {

        this.commonComponents = commonComponents;
        this.common = common;
        this.biefExplanation = new CommonBriefPageExplanation(commonComponents, common);

        this.productReportMiniStatCrafter = new ProductReportMiniStatCrafter(commonComponents, common,productService);

        this.productService = productService;

        this.charts = new ProductReportCharts(commonComponents,common,productService);


        briefExplanationMemory.setPadding(false);
        briefExplanationMemory.setWidthFull();
        briefExplanationMemory.add(
                biefExplanation.briefExplanation("Product report","#47B25D")
        );

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

        biefExplanation.setFromToDateConsumer(e->{
            if(e.getFrom() == null && e.getTo() == null){
                updateReports(new FromToDate(common.currentMonthStart(),common.nextMonthDate()));
            }
            else {
                updateReports(e);
            }
        });

        layout.setMaxWidth("1650px");
        layout.setPadding(true);
        layout.getStyle().set("margin-top", "5px");

        layout.addClassName("layout-flex");


        layout.add(
                briefExplanationMemory,
                productReportMiniStatCrafter.miniStatHolder(common.currentMonthStart(), common.nextMonthDate(), "#47B25D", Widths.FULL_WIDTH),
                charts.OrderRevenueAccordingToMonth(common.currentMonthStart(), common.nextMonthDate(),"#47B25D",Widths.HALF_WIDTH),
                charts.productByCategory(common.currentMonthStart(), common.nextMonthDate(),Widths.HALF_WIDTH),
                charts.lowStockAlerts(common.currentMonthStart(), common.nextMonthDate(),Widths.HALF_WIDTH),
                charts.productPerformance(common.currentMonthStart(), common.nextMonthDate(),Widths.HALF_WIDTH)
        );

        return layout;
    }

    public void updateReports(FromToDate fromToDate){
        layout.removeAll();

        LocalDate from  = fromToDate.getFrom();

        LocalDate to = fromToDate.getTo();

        layout.add(
                briefExplanationMemory,
                productReportMiniStatCrafter.miniStatHolder(from, to, "#47B25D", Widths.FULL_WIDTH),
                charts.OrderRevenueAccordingToMonth(from, to,"#47B25D",Widths.HALF_WIDTH),
                charts.productByCategory(from, to,Widths.HALF_WIDTH),
                charts.lowStockAlerts(from, to,Widths.HALF_WIDTH),
                charts.productPerformance(from, to,Widths.HALF_WIDTH)
        );


    }




}


