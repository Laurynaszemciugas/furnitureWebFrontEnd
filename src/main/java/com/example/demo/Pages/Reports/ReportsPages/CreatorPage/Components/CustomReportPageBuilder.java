package com.example.demo.Pages.Reports.ReportsPages.CreatorPage.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.CommonDtos.CreateReport.ReportItems;
import com.example.demo.ControllerModels.Orders.OrderReportPieChart;
import com.example.demo.Enums.Widths;
import com.example.demo.Pages.Reports.Common.ReportsMiniStatCrafter;
import com.example.demo.Pages.Reports.ReportsPages.MaterialReport.Components.MaterialReportCharts;
import com.example.demo.Pages.Reports.ReportsPages.MaterialReport.Components.MaterialReportMiniStatCrafter;
import com.example.demo.Pages.Reports.ReportsPages.OrderReports.Components.OrderReportCharts;
import com.example.demo.Pages.Reports.ReportsPages.OrderReports.Components.OrderReportMiniStatCrafter;
import com.example.demo.Pages.Reports.ReportsPages.ProductReportpPage.Components.ProductReportCharts;
import com.example.demo.Pages.Reports.ReportsPages.ProductReportpPage.Components.ProductReportMiniStatCrafter;
import com.example.demo.Services.EmployeeService.EmployeeService;
import com.example.demo.Services.Material.MaterialService;
import com.example.demo.Services.Orders.OrdersService;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;
import java.util.List;

@Service
public class CustomReportPageBuilder {

    // components required to build UI
    CommonComponents commonComponents;
    Common common;

    MaterialReportCharts materialReportCharts;
    MaterialReportMiniStatCrafter materialReportMiniStatCrafter;

    OrderReportCharts orderReportCharts;
    OrderReportMiniStatCrafter orderReportMiniStatCrafter;

    ProductReportCharts productReportCharts;
    ProductReportMiniStatCrafter productReportMiniStatCrafter;


    // servies

    MaterialService materialService;
    OrdersService ordersService;
    ProductService productService;
    EmployeeService employeeService;


    public CustomReportPageBuilder(CommonComponents commonComponents, Common common, MaterialService materialService, OrdersService ordersService, ProductService productService, EmployeeService employeeService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.materialService = materialService;
        this.ordersService = ordersService;
        this.productService = productService;
        this.employeeService = employeeService;

        this.materialReportCharts = new MaterialReportCharts(commonComponents,common,materialService);
        this.materialReportMiniStatCrafter = new MaterialReportMiniStatCrafter(commonComponents,common,materialService);

        this.orderReportCharts = new OrderReportCharts(commonComponents,common,ordersService);
        this.orderReportMiniStatCrafter = new OrderReportMiniStatCrafter(commonComponents,common,ordersService);

        this.productReportCharts = new ProductReportCharts(commonComponents,common,productService);
        this.productReportMiniStatCrafter = new ProductReportMiniStatCrafter(commonComponents,common,productService);

    }





    public void updateScene(HorizontalLayout layout, String color, List<ReportItems> reportItemsList){

        layout.removeAll();

        for(var s : reportItemsList){

            Component component = switch (s.getWidget()) {

                case ORDER_MINI_STATS ->
                        orderReportMiniStatCrafter.miniStatHolder(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                color,
                                s.getWidth()
                        );

                case ORDER_BY_STATUS ->
                        orderReportCharts.ordersByStatusChart(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                s.getWidth()
                        );

                case ORDER_VALUE_OVER_TIME ->
                        orderReportCharts.OrderRevenueAccordingToMonth(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                s.getWidth()
                        );

                case ORDER_TOP_CUSTOMERS ->
                        orderReportCharts.topCustomerOrder(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                s.getWidth()
                        );

                case ORDER_RECENT_ORDERS ->
                        orderReportCharts.recentOrdersList(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                s.getWidth()
                        );

                case PRODUCT_MINI_STATS ->
                        productReportMiniStatCrafter.miniStatHolder(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                color,
                                s.getWidth()
                        );

                case PRODUCT_TOP_SELLING_PRODUCTS ->
                        productReportCharts.OrderRevenueAccordingToMonth(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                color,
                                s.getWidth()
                        );

                case PRODUCT_BY_CATEGORY ->
                        productReportCharts.productByCategory(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                s.getWidth()
                        );

                case PRODUCT_LOW_STOCK ->
                        productReportCharts.lowStockAlerts(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                s.getWidth()
                        );

                case PRODUCT_PERFORMANCE ->
                        productReportCharts.productPerformance(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                s.getWidth()
                        );

                case MATERIAL_MINI_STATS ->
                        new MaterialReportMiniStatCrafter(commonComponents, common, materialService)
                        .miniStatHolder(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                color,
                                s.getWidth()
                        );

                case MATERIAL_BY_STATUS ->
                        new MaterialReportCharts(commonComponents, common, materialService)
                        .ProductByStatusChart(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                s.getWidth()
                        );

                case MATERIAL_USAGE_OVERTIME ->
                        new MaterialReportCharts(commonComponents, common, materialService)
                                .ProductRevenueAccordingToMonth(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                s.getWidth()
                        );

                case MATERIAL_LOW_STOCK ->
                        new MaterialReportCharts(commonComponents, common, materialService)
                        .topCustomerOrder(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                s.getWidth()
                        );

                case MATERIAL_RECENT_MOVEMENT ->
                        new MaterialReportCharts(commonComponents, common, materialService)
                        .materialStockMovement(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                s.getWidth()
                        );
            };
            layoutAdd(component, layout, s.getWidth());

        }


    }



    public void layoutAdd(Component component, HorizontalLayout layout, Widths widths){

        component.getStyle().set("position","relative");
        Span span = new Span(widths.getName());

        span.getStyle()
                .set("position","absolute")
                .set("top","-10px")
                .set("left","0px")
                .set("z-index","100");

        span.addClassName("tag-badge");


        if(component instanceof HasComponents h){
            if(h instanceof Div d) {
                layout.add(wrapperForDiv(d,span,widths));
            }
            else{
                h.add(span);
                layout.add(
                        component
                );
            }
        }






    }

    public VerticalLayout wrapperForDiv(Div div,Span span, Widths widths){


        VerticalLayout v = new VerticalLayout();
        v.setWidth(widths.getWidth());
        v.setPadding(false);
        v.setSpacing(false);
        v.getStyle().set("position","relative");





        div.setWidthFull();



        v.add(
                span,
                div
        );



        return v;
    }











}
