package com.example.demo.Pages.Reports.ReportsPages.CreatorPage;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.CommonDtos.CreateReport.Report;
import com.example.demo.ControllerModels.CommonDtos.CreateReport.ReportItems;
import com.example.demo.ControllerModels.CommonDtos.User;
import com.example.demo.Enums.ReportCategory;
import com.example.demo.Enums.Widget;
import com.example.demo.Enums.Widths;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Material.MaterialAddEdit.Components.ColorSelector;
import com.example.demo.Pages.Reports.ReportsPages.CreatorPage.Components.CustomReportPageBuilder;
import com.example.demo.Pages.Reports.ReportsPages.CreatorPage.Components.LeftSideReportCreate;
import com.example.demo.Pages.Reports.ReportsPages.CreatorPage.Components.RightSideReportCreate;
import com.example.demo.Pages.Reports.ReportsPages.MaterialReport.MaterialReportPage;
import com.example.demo.Services.CustomReportService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.util.*;


@Route(value = "reportCreator", layout = MainLayout.class)
public class ReportCreationPage extends VerticalLayout implements BeforeEnterObserver {

    CommonComponents commonComponents;
    Common common;
    ColorSelector colorSelector;
    LeftSideReportCreate leftSideReportCreate;
    RightSideReportCreate rightSideReportCreate;

    HorizontalLayout rightSide = new HorizontalLayout();

    CustomReportPageBuilder customReportPageBuilder;

    CustomReportService customReportService;


    public ReportCreationPage(CommonComponents commonComponents, Common common,CustomReportPageBuilder customReportPageBuilder,CustomReportService customReportService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.customReportPageBuilder = customReportPageBuilder;
        this.colorSelector = new ColorSelector();
        this.leftSideReportCreate = new LeftSideReportCreate(commonComponents,common,customReportPageBuilder);
        this.rightSideReportCreate = new RightSideReportCreate(commonComponents,common);
        this.customReportService = customReportService;

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

        layout.setWidthFull();

        layout.setFlexGrow(1);
        //layout.setWidth("1650px");
        layout.setPadding(true);
        layout.getStyle().set("margin-top", "5px");

        layout.addClassName("layout-flex");


        layout.add(
                leftSideReportCreate.briefPageExplanation("Create custom report","Create report"),
                leftRightJoin()
        );

        leftSideReportCreate.setReportAddedEdited(e->{
            customReportService.saveNewReport(e);
        });

        customReportService.setSuccess(ee->{
            UI.getCurrent().navigate("Reports");
        });




        return layout;
    }





    public SplitLayout leftRightJoin() {





        VerticalLayout leftSide = leftSideReportCreate.leftSide(rightSide,null,true,common.currentMonthStart(),common.nextMonthDate());

        HorizontalLayout rightSides = rightSideReportCreate.rightSideReportCustom(rightSide,true);
        rightSides.setPadding(false);

        SplitLayout splitLayout = new SplitLayout(leftSide, rightSides);
        splitLayout.addClassName("smooth-panel");


        splitLayout.setSplitterPosition(35);
        splitLayout.setWidthFull();
        splitLayout.setHeightFull();



        return splitLayout;
    }







}
