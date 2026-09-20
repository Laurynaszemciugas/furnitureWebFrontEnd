package com.example.demo.Pages.EmployeePage.AvailableOrderPage;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.CurrentFilterDisplay;
import com.example.demo.Common.Logic.ImageViewer;
import com.example.demo.Common.Logic.PageStuff.DefaultPageExplanation;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.Common.Paganation;
import com.example.demo.ControllerModels.CommonDtos.EmployeePage.EmployeeOrderProjection;
import com.example.demo.ControllerModels.Filter.EmployeeAvailableOrderFilter.EmployeeAvailableOrderFilter;
import com.example.demo.Pages.ActionLog.Components.ActionLogsBriefExplanation;
import com.example.demo.Pages.EmployeePage.AvailableOrderPage.Components.AvailableOrderFilter;
import com.example.demo.Pages.EmployeePage.Page.Components.DisplayAvailableOrders;
import com.example.demo.Services.Orders.OrdersService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Route(value = "AvailableOrderPage")
public class AvailableOrderPage extends VerticalLayout implements BeforeEnterObserver {





    // main layout
    VerticalLayout verticalLayout = new VerticalLayout();
    VerticalLayout filterMemory = new VerticalLayout();
    Div gridHolder = new Div();

    EmployeeAvailableOrderFilter filterData = new EmployeeAvailableOrderFilter();

    OrdersService ordersService;

    SessionCrafter sessionCrafter;

    CommonComponents commonComponents;
    Common common;

    CurrentFilterDisplay currentFilterDisplay;


    Paganation paganation;

    ActionLogsBriefExplanation actionLogsBriefExplanation;

    AvailableOrderFilter availableOrderFilter;

    DisplayAvailableOrders displayAvailableOrders;

    ImageViewer imageViewer;

    DefaultPageExplanation defaultPageExplanation;

    public AvailableOrderPage(CommonComponents commonComponents, Common common, OrdersService ordersService,ImageViewer imageViewer) {
        this.commonComponents = commonComponents;
        this.common = common;

        this.actionLogsBriefExplanation = new ActionLogsBriefExplanation(commonComponents,common);
        this.paganation = new Paganation();

        this.availableOrderFilter = new AvailableOrderFilter(commonComponents,common);

        this.sessionCrafter = new SessionCrafter();

        this.ordersService = ordersService;

       this.imageViewer = imageViewer;


        this.currentFilterDisplay = new CurrentFilterDisplay(commonComponents,common);

        this.displayAvailableOrders = new DisplayAvailableOrders(commonComponents,common,ordersService,imageViewer);

        this.defaultPageExplanation = new DefaultPageExplanation(commonComponents,common);


        availableOrderFilter.setCurrentFilterDisplay(currentFilterDisplay);



        gridHolder.setWidthFull();
        filterMemory.setWidthFull();
        filterMemory.setPadding(false);

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);


        addClassName("animation-page");


    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        removeAll();

        filterData = sessionCrafter.extractSession("availableOrderEmp",EmployeeAvailableOrderFilter.class) == null ? new EmployeeAvailableOrderFilter() :
                sessionCrafter.extractSession("availableOrderEmp",EmployeeAvailableOrderFilter.class);

        availableOrderFilter.setFilterData(filterData);
        currentFilterDisplay.preLoadFilters(EmployeeAvailableOrderFilter.class,"availableOrderEmp");




        add(mainLayout());

    }

    public VerticalLayout mainLayout() {


        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");


        reloadData();



        availableOrderFilter.setPromptConsumer(e->{
            setNewPage();
            filterData.setPromt(e);
            loadGridValues();
        });

        availableOrderFilter.setOrderStatusConsumer(e->{
            setNewPage();
            filterData.setOrderStatus(e);
            loadGridValues();
        });

        availableOrderFilter.setPrioritynConsumer(e->{
            setNewPage();
            filterData.setPriority(e);
            loadGridValues();
        });

        availableOrderFilter.setSortOrderConsumer(e->{
            setNewPage();
            filterData.setSortOrder(e);
            loadGridValues();
        });


        commonComponents.setPageSelectorConsumer(e->{
            setNewPage();
            filterData.setPageCount(e);
            loadGridValues();
        });



        currentFilterDisplay.setReloadController(e->{
            setNewPage();
            filterData = (EmployeeAvailableOrderFilter) e;
            loadGridValues();
        });

        currentFilterDisplay.setReloadButtons(e->{
            setNewPage();
            reloadData();
        });


        availableOrderFilter.setClearConsumer(e->{
            setNewPage();
            currentFilterDisplay.clearAllData();
            filterData =  new EmployeeAvailableOrderFilter();
            reloadData();
        });





        paganation.setOnPageChange(e->{
            e = e-1;
            filterData.setPage(e);
            loadGridValues();
        });

        return verticalLayout;
    }


    public void reloadData(){



        verticalLayout.removeAll();

        //filterData = new MaterialFilterHolder();

        filterMemory.removeAll();
        filterMemory.add(
                defaultPageExplanation.briefExplanation("Available orders"),
                availableOrderFilter.filters()

        );




        loadGridValues();


        verticalLayout.add(
                filterMemory,
                gridHolder

        );
    }


    public void loadGridValues(){

        UI ui = UI.getCurrent();
        String jwt = sessionCrafter.extractSession("JWT", String.class);

        gridHolder.removeAll();
        gridHolder.add(
                commonComponents.shimmer(5)
        );

        CompletableFuture
                .supplyAsync(()->{

                    List<EmployeeOrderProjection> items = ordersService.findEmployeeActiveOrdersNonLimited(filterData,jwt);
                    common.timer(250);
                    return items;
                })
                .thenAccept(e->{
                    ui.access(() -> {
                        gridHolder.removeAll();
                        gridHolder.add(gridFilterHolder(e));
                    });
                });


        sessionCrafter.createSession("availableOrderEmp",filterData);

        paganation.updateUIFromExternal(filterData.getPage()+1);

    }

    public VerticalLayout gridFilterHolder(List<EmployeeOrderProjection> filterStuff){
        VerticalLayout v = new VerticalLayout();
        v.getStyle().set("position","relative");
        v.setPadding(false);
        v.setWidthFull();

        v.add(
                displayAvailableOrders.availableOrders(filterStuff,false),
                commonComponents.paganationExpander(filterData.getPageCount()),
                paganation.buttonHolder(Math.toIntExact(ordersService.getAmountOfPagesOnAvailableOrders(filterData)))

        );

        return v;
    }





    public void setNewPage(){
        filterData.setPage(0);
        paganation.updateUIFromExternal(1);
    }




}
