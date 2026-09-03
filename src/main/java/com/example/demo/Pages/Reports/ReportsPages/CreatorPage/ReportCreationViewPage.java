package com.example.demo.Pages.Reports.ReportsPages.CreatorPage;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.BreadCrums.BreadCrumsDto;
import com.example.demo.ControllerModels.CommonDtos.CreateReport.Report;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Common.ColorSelector;
import com.example.demo.Pages.Reports.Common.CommonBriefPageExplanation;
import com.example.demo.Pages.Reports.ReportsPages.CreatorPage.Components.CustomReportPageBuilder;
import com.example.demo.Pages.Reports.ReportsPages.CreatorPage.Components.LeftSideReportCreate;
import com.example.demo.Pages.Reports.ReportsPages.CreatorPage.Components.RightSideReportCreate;
import com.example.demo.Services.CustomReportService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;


@Route(value = "viewCustomReport/:id", layout = MainLayout.class)
public class ReportCreationViewPage extends VerticalLayout implements BeforeEnterObserver {

    CommonComponents commonComponents;
    Common common;
    ColorSelector colorSelector;
    LeftSideReportCreate leftSideReportCreate;
    RightSideReportCreate rightSideReportCreate;

    HorizontalLayout rightSide = new HorizontalLayout();

    CustomReportPageBuilder customReportPageBuilder;

    CustomReportService customReportService;

    CommonBriefPageExplanation commonBriefPageExplanation;

    int itemChoice = 0;

    Report report = new Report();

    HorizontalLayout layout = new HorizontalLayout();

    VerticalLayout filterMemory = new VerticalLayout();


    public ReportCreationViewPage(CommonComponents commonComponents, Common common, CustomReportPageBuilder customReportPageBuilder, CustomReportService customReportService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.customReportPageBuilder = customReportPageBuilder;
        this.colorSelector = new ColorSelector();
        this.leftSideReportCreate = new LeftSideReportCreate(commonComponents,common,customReportPageBuilder);
        this.rightSideReportCreate = new RightSideReportCreate(commonComponents,common);
        this.customReportService = customReportService;

        this.commonBriefPageExplanation = new CommonBriefPageExplanation(commonComponents,common);

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


            report = customReportService.getReportAccordingToId((long) itemChoice);



        filterMemory.add(
                commonComponents.breadCrums(new BreadCrumsDto("Reports", "Reports"),new BreadCrumsDto("View custom report", null)),
                commonBriefPageExplanation.briefExplanation(report.getReportName(), report.getReportColor())
        );


        add(mainLayout());

    }

    public HorizontalLayout mainLayout() {


        layout.setWidthFull();

        layout.setFlexGrow(1);
        //layout.setWidth("1650px"); // no limit to the size
        layout.setPadding(true);
        layout.getStyle().set("margin-top", "5px");

        layout.addClassName("layout-flex");


        layout.add(
                filterMemory,
                leftRightJoin()
        );







        return layout;
    }





    public HorizontalLayout leftRightJoin() {

        commonBriefPageExplanation.setFromToDateConsumer(e->{
            updateData(e.getFrom(),e.getTo());
        });




        // load data using left not efficiant way but because left side is used in edit and add new one it is used here this makes things less compicated in the long run
        leftSideReportCreate.leftSide(rightSide,report,false,common.currentMonthStart(),common.nextMonthDate());


        HorizontalLayout rightSides = rightSideReportCreate.rightSideReportCustom(rightSide,false);



        return rightSides;
    }

    public void updateData(LocalDate from, LocalDate to) {

        layout.removeAll();

        // load data using left not efficiant way but because left side is used in edit and add new one it is used here this makes things less compicated in the long run
        leftSideReportCreate.leftSide(rightSide,report,false,from,to);


        HorizontalLayout rightSides = rightSideReportCreate.rightSideReportCustom(rightSide,false);


        layout.add(
                filterMemory,
                rightSides
        );

    }







}
