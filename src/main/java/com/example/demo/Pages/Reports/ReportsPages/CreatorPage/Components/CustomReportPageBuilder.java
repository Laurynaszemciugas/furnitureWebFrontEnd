package com.example.demo.Pages.Reports.ReportsPages.CreatorPage.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.ControllerModels.CommonDtos.CreateReport.Report;
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
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
    SessionCrafter sessionCrafter;



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

        this.sessionCrafter = new SessionCrafter();


    }





    public void updateScene(HorizontalLayout layout, String color, List<ReportItems> reportItemsList){



        HorizontalLayout holder = new HorizontalLayout();
        holder.setWidthFull();
        holder.setPadding(false);
        holder.addClassName("layout-flex");

        UI ui = UI.getCurrent();
        String jwt = sessionCrafter.extractSession("JWT", String.class);

        layout.removeAll();
        layout.add(commonComponents.shimmer(5));

        CompletableFuture.runAsync(() -> {



        for(var s : reportItemsList){

            Component component = switch (s.getWidget()) {

                case ORDER_MINI_STATS ->
                        orderReportMiniStatCrafter.miniStatHolder(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                color,
                                s.getWidth() == Widths.CUSTOM ? s.getUserPreferredWidth() : s.getWidth().getWidth(),
                                jwt
                        );

                case ORDER_BY_STATUS ->
                        orderReportCharts.ordersByStatusChart(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                s.getWidth(),
                                jwt
                        );

                case ORDER_VALUE_OVER_TIME ->
                        orderReportCharts.OrderRevenueAccordingToMonth(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                s.getWidth(),
                                jwt
                        );

                case ORDER_TOP_CUSTOMERS ->
                        orderReportCharts.topCustomerOrder(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                s.getWidth(),
                                jwt
                        );

                case ORDER_RECENT_ORDERS ->
                        orderReportCharts.recentOrdersList(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                s.getWidth(),
                                jwt
                        );

                case PRODUCT_MINI_STATS ->
                        productReportMiniStatCrafter.miniStatHolder(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                color,
                                s.getWidth(),
                                jwt
                        );

                case PRODUCT_TOP_SELLING_PRODUCTS ->
                        productReportCharts.OrderRevenueAccordingToMonth(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                color,
                                s.getWidth(),
                                jwt
                        );

                case PRODUCT_BY_CATEGORY ->
                        productReportCharts.productByCategory(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                s.getWidth(),
                                jwt
                        );

                case PRODUCT_LOW_STOCK ->
                        productReportCharts.lowStockAlerts(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                s.getWidth(),
                                jwt
                        );

                case PRODUCT_PERFORMANCE ->
                        productReportCharts.productPerformance(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                s.getWidth(),
                                jwt
                        );

                case MATERIAL_MINI_STATS ->
                        new MaterialReportMiniStatCrafter(commonComponents, common, materialService)
                        .miniStatHolder(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                color,
                                s.getWidth(),
                                jwt
                        );

                case MATERIAL_BY_STATUS ->
                        new MaterialReportCharts(commonComponents, common, materialService)
                        .ProductByStatusChart(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                s.getWidth(),
                                jwt
                        );

                case MATERIAL_USAGE_OVERTIME ->
                        new MaterialReportCharts(commonComponents, common, materialService)
                                .ProductRevenueAccordingToMonth(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                s.getWidth(),
                                        jwt
                        );

                case MATERIAL_LOW_STOCK ->
                        new MaterialReportCharts(commonComponents, common, materialService)
                        .topCustomerOrder(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                s.getWidth(),
                                jwt
                        );

                case MATERIAL_RECENT_MOVEMENT ->
                        new MaterialReportCharts(commonComponents, common, materialService)
                        .materialStockMovement(
                                common.currentMonthStart(),
                                common.nextMonthDate(),
                                s.getWidth(),
                                jwt
                        );
            };
            layoutAdd(component, holder , s.getWidth(), s.getUserPreferredWidth());

        }

        common.timer(250);

            ui.access(() -> {
                layout.removeAll();
                layout.add(holder);
            });

        });


    }



    public void layoutAdd(Component component,HorizontalLayout holder, Widths widths, String userWidth){


        component.getStyle().set("position","relative");
        Span span = new Span(widths == Widths.CUSTOM ? userWidth : widths.getName());

        component.addClassName("island-layout");
        component.getStyle().set("resize", "horizontal");
        component.getStyle().set("overflow", "hidden");
        component.getStyle().set("padding", "8px");

        span.getStyle()
                .set("position","absolute")
                .set("top","5px")
                .set("right","5px")
                .set("z-index","100");

        span.addClassName("tag-badge");


        String widhth = widths == Widths.CUSTOM ? userWidth : widths.getWidth();


        if(component instanceof HasComponents h){
            if(h instanceof Div d) {
                holder.add(wrapperForDiv(d,span,widhth));
            }
            else{
                h.add(span);
                holder.add(
                        component
                );
            }
        }






    }

    public VerticalLayout wrapperForDiv(Div div,Span span, String widths){


        VerticalLayout v = new VerticalLayout();
        v.setWidth(widths);
        v.setPadding(false);
        v.setSpacing(false);
        v.getStyle().set("position","relative");

        v.addClassName("island-layout");
        v.getStyle().set("resize", "horizontal");
        v.getStyle().set("overflow", "hidden");
        v.getStyle().set("padding", "8px");




        div.setWidthFull();



        v.add(
                span,
                div
        );



        return v;
    }


    public void loadData(Report report, HorizontalLayout rightSide){

        updateScene(rightSide, report.getReportColor(),report.getReportItemsList());


    }








}
