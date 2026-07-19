package com.example.demo.Pages.Reports.ReportsPages.MaterialReport;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Enums.Widths;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Reports.ReportsPages.MaterialReport.Components.BriefMaterialReportPageExplanation;
import com.example.demo.Pages.Reports.ReportsPages.MaterialReport.Components.MaterialReportCharts;
import com.example.demo.Pages.Reports.ReportsPages.MaterialReport.Components.MaterialReportMiniStatCrafter;
import com.example.demo.Services.Material.MaterialService;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "MaterialReport", layout = MainLayout.class)
public class MaterialReportPage extends VerticalLayout implements BeforeEnterObserver {


    CommonComponents commonComponents;
    Common common;
    BriefMaterialReportPageExplanation biefExplanation;
    MaterialService materialService;

    MaterialReportCharts charts;

    MaterialReportMiniStatCrafter orderReportMiniStatCrafter;


    public MaterialReportPage(CommonComponents commonComponents, Common common, MaterialService materialService) {

        this.commonComponents = commonComponents;
        this.common = common;
        this.biefExplanation = new BriefMaterialReportPageExplanation(commonComponents, common);

        this.orderReportMiniStatCrafter = new MaterialReportMiniStatCrafter(commonComponents, common,materialService);

        this.materialService = materialService;

        this.charts = new MaterialReportCharts(commonComponents,common,materialService);


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
                orderReportMiniStatCrafter.miniStatHolder(common.currentMonthStart(), common.nextMonthDate(), "#9768EF", Widths.FULL_WIDTH),
                charts.ProductByStatusChart(common.currentMonthStart(), common.nextMonthDate(),Widths.HALF_WIDTH),
                charts.ProductRevenueAccordingToMonth(common.currentMonthStart(), common.nextMonthDate(),Widths.HALF_WIDTH),
                charts.topCustomerOrder(common.currentMonthStart(), common.nextMonthDate(),Widths.HALF_WIDTH)
//                charts.recentOrdersList(common.currentMonthStart(), common.nextMonthDate(),Widths.HALF_WIDTH)
        );

        return layout;
    }




}


