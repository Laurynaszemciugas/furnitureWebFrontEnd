package com.example.demo.Pages.DashBoard.Page;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.DashBoard.DashBoardPageData;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.DashBoard.Components.*;
import com.example.demo.Services.Dashboard.DashBoardService;
import com.example.demo.ChartsGraphs.DashBoard.DashBoardCharts;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;


@Route(value = "DashBoard", layout = MainLayout.class)
public class DashBoardPage extends VerticalLayout implements BeforeEnterObserver {

    CommonComponents commonComponents;
    Common common;
    DashBoardService dashBoardService;




    MiniStatistics miniStatistics;
    ActivityFeed activityFeed;
    GraphStatistics graphStatistics;
    TopEmployees topEmployees;
    MaterialLowNoStock materialLowNoStock;
    QuickAction quickAction;

    DashBoardCharts chart;


    DashBoardPageData data = new DashBoardPageData();



    @Override
    public void beforeEnter(BeforeEnterEvent event) {

        removeAll();
        if(data == null || data.isDataStale()){
            data = dashBoardService.loadDashboardData();
            }

        else{
            System.out.println("data good");
        }


        add(
                loadingOverlay(),
                mainLayout());

    }



    public DashBoardPage(
            CommonComponents commonComponents,
            Common common,
            DashBoardService dashBoardService,
            MiniStatistics miniStatistics,
            ActivityFeed activityFeed,
            GraphStatistics graphStatistics,
            TopEmployees topEmployees,
            MaterialLowNoStock materialLowNoStock,
            QuickAction quickAction,
            DashBoardCharts chart
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


    private Component loadingOverlay() {
        Div overlay = new Div();
        overlay.addClassName("loading-overlay");

        Div loader = new Div();
        loader.addClassName("modern-loader");

        Span text = new Span("Loading dashboard...");
        text.addClassName("loading-text");

        overlay.add(loader, text);

        return overlay;
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
        VerticalLayout graphHolder = graphStatistics.graph(data.getGraphData());
        graphHolder.setWidth("400px");
        HorizontalLayout h = new HorizontalLayout(graphHolder, activityFeed2);
        h.setWidthFull();
        h.setFlexGrow(1, graphHolder);

        h.getStyle().set("flex-wrap","wrap");

        return h;
    }



    // =============================== Low/No MATERIAL top employees quickActions ======================================

    public HorizontalLayout topEmployeMaterialQuickAction(){


        VerticalLayout quick = quickAction.quickActionButtons(data.getLoadQuickActions());


        HorizontalLayout h = new HorizontalLayout(materialLowNoStock.materialLowNoStock(data.getLoadMaterialLowNoStock()), topEmployees.topEmployees(data.getLoadTopEmployees()), quick);
        h.setWidthFull();
        h.setFlexGrow(1, quick);

        h.getStyle().set("flex-wrap","wrap");

        return  h;

    }



























}
