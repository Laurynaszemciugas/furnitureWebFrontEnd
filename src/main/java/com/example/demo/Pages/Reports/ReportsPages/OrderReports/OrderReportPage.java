package com.example.demo.Pages.Reports.ReportsPages.OrderReports;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.Enums.Widths;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Reports.ReportsPages.OrderReports.Components.OrderReportMiniStatCrafter;
import com.example.demo.Pages.Reports.ReportsPages.OrderReports.Components.BriefOrderReportPageExplanation;
import com.example.demo.Pages.Reports.ReportsPages.OrderReports.Components.OrderReportCharts;
import com.example.demo.Services.Orders.OrdersService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "OrderReport", layout = MainLayout.class)
public class OrderReportPage extends VerticalLayout implements BeforeEnterObserver {


    CommonComponents commonComponents;
    Common common;
    BriefOrderReportPageExplanation biefExplanation;
    OrdersService ordersService;

    OrderReportCharts charts;

    OrderReportMiniStatCrafter orderReportMiniStatCrafter;


    public OrderReportPage(CommonComponents commonComponents, Common common, OrdersService ordersService) {

        this.commonComponents = commonComponents;
        this.common = common;
        this.biefExplanation = new BriefOrderReportPageExplanation(commonComponents, common);

        this.orderReportMiniStatCrafter = new OrderReportMiniStatCrafter(commonComponents, common,ordersService);

        this.ordersService = ordersService;

        this.charts = new OrderReportCharts(ordersService);


        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);


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
                filters(),
                orderReportMiniStatCrafter.miniStatHolder(common.currentMonthStart(), common.nextMonthDate(), "#035afc", Widths.FULL_WIDTH),
                charts.ordersByStatusChart(common.currentMonthStart(), common.nextMonthDate(),Widths.HALF_WIDTH),
                charts.OrderRevenueAccordingToMonth(common.currentMonthStart(), common.nextMonthDate(),Widths.HALF_WIDTH)
        );

        return layout;
    }

    public HorizontalLayout filters() {
        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.addClassName("layout-flex");

        ComboBox<OrderStatus> status = new ComboBox<>();
        status.setPlaceholder("Status");

        ComboBox<String> costumer = new ComboBox<>();
        costumer.setPlaceholder("Costumer");


        h.add(status, costumer);


        return h;


    }




}


