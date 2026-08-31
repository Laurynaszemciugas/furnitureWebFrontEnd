package com.example.demo.Pages.Reports.ReportsPages.CreatorPage;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.BreadCrums.BreadCrumsDto;
import com.example.demo.Common.ColorSelector;
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



@Route(value = "reportCreator")
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
        layout.getStyle().set("position","relative");
        layout.addClassName("layout-flex");


        layout.add(
                commonComponents.breadCrums(new BreadCrumsDto("Reports", "Reports"),new BreadCrumsDto("Create new report", null)),
                leftSideReportCreate.briefPageExplanation("Create custom report","Create report"),
                leftRightJoin(),
                leftSideReportCreate.leftSideDialog(
                        rightSide,
                        null,
                        true,
                        common.currentMonthStart(),
                        common.nextMonthDate())

        );

        leftSideReportCreate.setReportAddedEdited(e->{
            customReportService.saveNewReport(e);
        });

        customReportService.setSuccess(ee->{
            UI.getCurrent().navigate("Reports");
        });




        return layout;
    }





    public HorizontalLayout leftRightJoin() {






        HorizontalLayout rightSides = rightSideReportCreate.rightSideReportCustom(rightSide,true);
        rightSides.setPadding(false);
        rightSides.getStyle().set("position","relative");










        return rightSides;
    }







}
