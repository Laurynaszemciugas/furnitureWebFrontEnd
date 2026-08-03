package com.example.demo.Pages.Reports.ReportsPages.CreatorPage;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.CommonDtos.CreateReport.Report;
import com.example.demo.ControllerModels.CommonDtos.CreateReport.ReportItems;
import com.example.demo.Enums.ReportCategory;
import com.example.demo.Enums.Widget;
import com.example.demo.Enums.Widths;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Material.MaterialAddEdit.Components.ColorSelector;
import com.example.demo.Pages.Reports.ReportsPages.CreatorPage.Components.CustomReportPageBuilder;
import com.example.demo.Pages.Reports.ReportsPages.CreatorPage.Components.LeftSideReportCreate;
import com.example.demo.Pages.Reports.ReportsPages.CreatorPage.Components.RightSideReportCreate;
import com.example.demo.Services.CustomReportService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;


@Route(value = "editReport/:id", layout = MainLayout.class)
public class ReportCreationEditPage extends VerticalLayout implements BeforeEnterObserver {

    CommonComponents commonComponents;
    Common common;
    ColorSelector colorSelector;
    LeftSideReportCreate leftSideReportCreate;
    RightSideReportCreate rightSideReportCreate;

    HorizontalLayout rightSide = new HorizontalLayout();

    CustomReportPageBuilder customReportPageBuilder;

    CustomReportService customReportService;

    int itemChoice = 0;


    public ReportCreationEditPage(CommonComponents commonComponents, Common common, CustomReportPageBuilder customReportPageBuilder, CustomReportService customReportService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.customReportPageBuilder = customReportPageBuilder;
        this.colorSelector = new ColorSelector();
        this.leftSideReportCreate = new LeftSideReportCreate(commonComponents,common,customReportPageBuilder);
        this.rightSideReportCreate = new RightSideReportCreate();
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

        int page = Math.toIntExact( Integer.parseInt(beforeEnterEvent.getRouteParameters().get("id").orElse(null)));

        this.itemChoice = page;

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
                leftSideReportCreate.briefPageExplanation(),
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


        VerticalLayout leftSide = leftSideReportCreate.leftSide(rightSide,customReportService.getReportAccordingToId((long) itemChoice));

        HorizontalLayout rightSides = rightSideReportCreate.rightSideReportCustom(rightSide);

        SplitLayout splitLayout = new SplitLayout(leftSide, rightSides);
        splitLayout.addClassName("smooth-panel");


        splitLayout.setSplitterPosition(35);
        splitLayout.setWidthFull();
        splitLayout.setHeightFull();



        return splitLayout;
    }







}
