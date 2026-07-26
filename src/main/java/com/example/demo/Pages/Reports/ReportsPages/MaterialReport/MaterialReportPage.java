package com.example.demo.Pages.Reports.ReportsPages.MaterialReport;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Enums.Widths;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Reports.Common.CommonBriefPageExplanation;
import com.example.demo.Pages.Reports.Common.FromToDate;
import com.example.demo.Pages.Reports.ReportsPages.MaterialReport.Components.MaterialReportCharts;
import com.example.demo.Pages.Reports.ReportsPages.MaterialReport.Components.MaterialReportMiniStatCrafter;
import com.example.demo.Services.Material.MaterialService;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;

@Route(value = "MaterialReport", layout = MainLayout.class)
public class MaterialReportPage extends VerticalLayout implements BeforeEnterObserver {


    CommonComponents commonComponents;
    Common common;
    CommonBriefPageExplanation biefExplanation;
    MaterialService materialService;

    MaterialReportCharts charts;

    MaterialReportMiniStatCrafter orderReportMiniStatCrafter;

    HorizontalLayout layout = new HorizontalLayout();

    VerticalLayout briefExplanationMemory = new VerticalLayout();

    public MaterialReportPage(CommonComponents commonComponents, Common common, MaterialService materialService) {

        this.commonComponents = commonComponents;
        this.common = common;
        this.biefExplanation = new CommonBriefPageExplanation(commonComponents, common);

        this.orderReportMiniStatCrafter = new MaterialReportMiniStatCrafter(commonComponents, common,materialService);

        this.materialService = materialService;

        this.charts = new MaterialReportCharts(commonComponents,common,materialService);


        briefExplanationMemory.setPadding(false);
        briefExplanationMemory.setWidthFull();
        briefExplanationMemory.add(
                biefExplanation.briefExplanation("Material report","#9768EF")
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
                orderReportMiniStatCrafter.miniStatHolder(common.currentMonthStart(), common.nextMonthDate(), "#9768EF", Widths.FULL_WIDTH),
                charts.ProductByStatusChart(common.currentMonthStart(), common.nextMonthDate(),Widths.HALF_WIDTH),
                charts.ProductRevenueAccordingToMonth(common.currentMonthStart(), common.nextMonthDate(),Widths.HALF_WIDTH),
                charts.topCustomerOrder(common.currentMonthStart(), common.nextMonthDate(),Widths.HALF_WIDTH),
                charts.materialStockMovement(common.currentMonthStart(), common.nextMonthDate(),Widths.HALF_WIDTH)
        );

        return layout;
    }

    public void updateReports(FromToDate fromToDate){
        layout.removeAll();

        LocalDate from  = fromToDate.getFrom();

        LocalDate to = fromToDate.getTo();

        layout.add(
                briefExplanationMemory,
                orderReportMiniStatCrafter.miniStatHolder(from, to, "#9768EF", Widths.FULL_WIDTH),
                charts.ProductByStatusChart(from, to,Widths.HALF_WIDTH),
                charts.ProductRevenueAccordingToMonth(from, to,Widths.HALF_WIDTH),
                charts.topCustomerOrder(from, to,Widths.HALF_WIDTH),
                charts.materialStockMovement(from, to,Widths.HALF_WIDTH)
        );


    }




}


