package com.example.demo.Pages.Reports.ReportsPages.OrderReports;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Common.MiniStatHolder;
import com.example.demo.Enums.Export;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Reports.Common.OrderReport.OrderReportMiniStatCrafter;
import com.example.demo.Pages.Reports.Common.ReportsMiniStatCrafter;
import com.example.demo.Pages.Reports.Page.Components.BriefReportPageExplanation;
import com.example.demo.Pages.Reports.Page.Components.MiniReportCard;
import com.example.demo.Pages.Reports.ReportsPages.OrderReports.Components.BriefOrderReportPageExplanation;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
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

    OrderReportMiniStatCrafter orderReportMiniStatCrafter;


    public OrderReportPage(CommonComponents commonComponents, Common common) {

        this.commonComponents = commonComponents;
        this.common = common;
        this.biefExplanation = new BriefOrderReportPageExplanation(commonComponents,common);

        this.orderReportMiniStatCrafter = new OrderReportMiniStatCrafter(commonComponents,common);


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

    public VerticalLayout mainLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");



        verticalLayout.add(
        biefExplanation.briefExplanation(),
                filters(),
                orderReportMiniStatCrafter.miniStatHolder("#035afc")
        );

        return verticalLayout;
    }

    public HorizontalLayout filters(){
        HorizontalLayout h = new HorizontalLayout();
        h.addClassName("layout-flex");
        h.setPadding(false);

        ComboBox<OrderStatus> status = new ComboBox<>();
        status.setPlaceholder("Status");

        ComboBox<String> costumer = new ComboBox<>();
        costumer.setPlaceholder("Costumer");


        h.add(status,costumer);


        return h;


    }






}
