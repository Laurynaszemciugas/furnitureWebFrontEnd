package com.example.demo.pages.DashBoard;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.DashBoard.*;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Services.DashBoard.DashBoardService;
import com.example.demo.chart;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;


@Route(value = "DashBoard", layout = MainLayout.class)
public class DashBoard extends VerticalLayout implements BeforeEnterObserver {

    CommonComponents commonComponents;
    Common common;
    DashBoardService dashBoardService;




    MiniStatistics miniStatistics;
    ActivityFeed activityFeed;
    GraphStatistics graphStatistics;
    TopEmployees topEmployees;
    MaterialLowNoStock materialLowNoStock;
    QuickAction quickAction;


    //test

    chart chart;


    DashBoardPageData data = new DashBoardPageData();




    @Override
    public void beforeEnter(BeforeEnterEvent event) {

        removeAll();
        data = dashBoardService.loadDashboardData();

        add(mainLayout());

    }



    public DashBoard(
            CommonComponents commonComponents,
            Common common,
            DashBoardService dashBoardService,
            MiniStatistics miniStatistics,
            ActivityFeed activityFeed,
            GraphStatistics graphStatistics,
            TopEmployees topEmployees,
            MaterialLowNoStock materialLowNoStock,
            QuickAction quickAction,
            chart chart
    ){
        this.commonComponents = commonComponents;
        this.common = common;
        this.dashBoardService = dashBoardService;
        this.miniStatistics = miniStatistics;
        this.activityFeed = activityFeed;
        this.graphStatistics = graphStatistics;
        this.topEmployees = topEmployees;
        this.materialLowNoStock = materialLowNoStock;
        this.quickAction = quickAction;

        this.chart = chart;

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(Alignment.CENTER);



    }


    // main layout all stuff go here

    public VerticalLayout mainLayout(){
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top","5px");
        verticalLayout.addClassName("main-island");




        verticalLayout.add(
                miniStatistics.miniStats(data.getMiniStatOne(),
                        data.getMiniStatTwo(),
                        data.getMiniStatThree(),
                        data.getMiniStatFour()),

                activityGraphHolder(),
                topEmployeMaterialQuickAction());

        verticalLayout.setAlignItems(Alignment.CENTER);

        return verticalLayout;
    }


    // graph and the activitylog holder
    public HorizontalLayout activityGraphHolder(){
        VerticalLayout activityFeed2 = activityFeed.activityFeedCrafter(data.getLoadActivityList());
        VerticalLayout graphHolder = graphStatistics.graph();
        graphHolder.setWidth("400px");
        HorizontalLayout h = new HorizontalLayout(graphHolder, activityFeed2);
        h.setWidthFull();
        h.setFlexGrow(1, graphHolder);

        h.getStyle().set("flex-wrap","wrap");

        return h;
    }



    // =============================== Low/No MATERIAL top employees quickActions ======================================

    public HorizontalLayout topEmployeMaterialQuickAction(){


        VerticalLayout quick = quickAction.quickActionButtons();


        HorizontalLayout h = new HorizontalLayout(materialLowNoStock.materialLowNoStock(data.getLoadMaterialLowNoStock()), topEmployees.topEmployees(), quick);
        h.setWidthFull();
        h.setFlexGrow(1, quick);

        h.getStyle().set("flex-wrap","wrap");

        return  h;

    }



























}
