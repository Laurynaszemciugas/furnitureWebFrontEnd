package com.example.demo.Pages.Reports.ReportsPages.CreatorPage.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.CommonDtos.CreateReport.ReportItems;
import com.example.demo.ControllerModels.Orders.OrderReportPieChart;
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
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.springframework.stereotype.Service;

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

            switch (s.getWidget()){

                case ORDER_MINI_STATS -> layout.add(orderReportMiniStatCrafter.miniStatHolder(common.currentMonthStart(),common.nextMonthDate(),color,s.getWidth()));
                case ORDER_BY_STATUS -> layout.add(orderReportCharts.ordersByStatusChart(common.currentMonthStart(),common.nextMonthDate(),s.getWidth()));
                case ORDER_VALUE_OVER_TIME -> layout.add(orderReportCharts.OrderRevenueAccordingToMonth(common.currentMonthStart(),common.nextMonthDate(),s.getWidth()));
                case ORDER_TOP_CUSTOMERS -> layout.add(orderReportCharts.topCustomerOrder(common.currentMonthStart(),common.nextMonthDate(),s.getWidth()));
                case ORDER_RECENT_ORDERS -> layout.add(orderReportCharts.recentOrdersList(common.currentMonthStart(),common.nextMonthDate(),s.getWidth()));


            }


        }


    }









}
