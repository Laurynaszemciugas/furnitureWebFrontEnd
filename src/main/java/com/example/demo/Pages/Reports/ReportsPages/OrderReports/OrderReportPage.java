package com.example.demo.Pages.Reports.ReportsPages.OrderReports;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.Enums.Widths;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Reports.Common.CommonBriefPageExplanation;
import com.example.demo.Pages.Reports.Common.FromToDate;
import com.example.demo.Pages.Reports.ReportsPages.OrderReports.Components.OrderReportMiniStatCrafter;
import com.example.demo.Pages.Reports.ReportsPages.OrderReports.Components.BriefOrderReportPageExplanation;
import com.example.demo.Pages.Reports.ReportsPages.OrderReports.Components.OrderReportCharts;
import com.example.demo.Pages.Reports.ReportsPages.OrderReports.DTOS.RecentOrdersReportPage;
import com.example.demo.Pages.Reports.ReportsPages.OrderReports.DTOS.TopCustomerDto;
import com.example.demo.Services.Orders.OrdersService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.util.List;

@Route(value = "OrderReport", layout = MainLayout.class)
public class OrderReportPage extends VerticalLayout implements BeforeEnterObserver {


    CommonComponents commonComponents;
    Common common;
    CommonBriefPageExplanation biefExplanation;
    OrdersService ordersService;

    OrderReportCharts charts;

    OrderReportMiniStatCrafter orderReportMiniStatCrafter;

    VerticalLayout briefExplanationMemory = new VerticalLayout();

    HorizontalLayout layout = new HorizontalLayout();

    public OrderReportPage(CommonComponents commonComponents, Common common, OrdersService ordersService) {

        this.commonComponents = commonComponents;
        this.common = common;
        this.biefExplanation = new CommonBriefPageExplanation(commonComponents, common);

        this.orderReportMiniStatCrafter = new OrderReportMiniStatCrafter(commonComponents, common,ordersService);

        this.ordersService = ordersService;

        this.charts = new OrderReportCharts(commonComponents,common,ordersService);


        briefExplanationMemory.setPadding(false);
        briefExplanationMemory.setWidthFull();
        briefExplanationMemory.add(
                biefExplanation.briefExplanation("Material report")
        );

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
                orderReportMiniStatCrafter.miniStatHolder(common.currentMonthStart(), common.nextMonthDate(), "#035afc", Widths.FULL_WIDTH),
                charts.ordersByStatusChart(common.currentMonthStart(), common.nextMonthDate(),Widths.HALF_WIDTH),
                charts.OrderRevenueAccordingToMonth(common.currentMonthStart(), common.nextMonthDate(),Widths.HALF_WIDTH),
                charts.topCustomerOrder(common.currentMonthStart(), common.nextMonthDate(),Widths.HALF_WIDTH),
                charts.recentOrdersList(common.currentMonthStart(), common.nextMonthDate(),Widths.HALF_WIDTH)
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
                charts.ordersByStatusChart(from, to,Widths.HALF_WIDTH),
                charts.OrderRevenueAccordingToMonth(from, to,Widths.HALF_WIDTH),
                charts.topCustomerOrder(from, to,Widths.HALF_WIDTH),
                charts.recentOrdersList(from, to,Widths.HALF_WIDTH)
        );


    }




}


