package com.example.demo.Pages.Orders.Page;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.CurrentFilterDisplay;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.Common.Paganation;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.ControllerModels.Filter.Order.OrderFilterHolder;
import com.example.demo.ControllerModels.Material.MaterialBriefDto;
import com.example.demo.ControllerModels.Orders.NewOrderFeedData;
import com.example.demo.ControllerModels.Orders.OrdersFeedData;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Orders.Page.Components.*;
import com.example.demo.Services.EmployeeService.EmployeeService;
import com.example.demo.Services.Orders.OrdersService;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.springframework.core.annotation.Order;

import javax.swing.plaf.PanelUI;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Route(value = "Orders", layout = MainLayout.class)
public class OrdersPage extends VerticalLayout implements BeforeEnterObserver {

    CommonComponents commonComponents;
    Common common;
    EmployeeService employeeService;
    Paganation paganation;

    OrdersRightSide ordersRightSide;
    OrdersLeftSide ordersLeftSide;
    OrderFilters orderFilters;
    BriefOrderPageExplanation briefOrderPageExplanation;
    OrderMiniStats orderMiniStats;

    NewOrderFeed newOrderFeed;

    OrdersService ordersService;

    VerticalLayout filterMemory = new VerticalLayout();
    ProductService productService;

    SessionCrafter sessionCrafter;

    // filter data

    OrderFilterHolder filterData = new OrderFilterHolder();

    VerticalLayout verticalLayout;

    CurrentFilterDisplay currentFilterDisplay;

    Div feedHolder = new Div();





    public OrdersPage(CommonComponents commonComponents, Common common, EmployeeService employeeService, OrdersService ordersService, ProductService productService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.ordersService = ordersService;

        this.productService = productService;
        this.ordersRightSide = new OrdersRightSide(commonComponents,common,employeeService,productService,ordersService);
        this.ordersLeftSide = new OrdersLeftSide(commonComponents,common);
        this.orderFilters = new OrderFilters(commonComponents, common,employeeService,productService);
        this.briefOrderPageExplanation = new BriefOrderPageExplanation(commonComponents,common);
        this.paganation = new Paganation();
        this.orderMiniStats = new OrderMiniStats(commonComponents,common);
        this.sessionCrafter = new SessionCrafter();

        this.newOrderFeed = new NewOrderFeed(commonComponents,common,ordersService);


        this.ordersRightSide.setOrdersLeftSide(ordersLeftSide);
        this.newOrderFeed.setOrdersLeftSide(ordersLeftSide);


        this.currentFilterDisplay = new CurrentFilterDisplay(commonComponents,common);
        this.orderFilters.setCurrentFilterDisplay(currentFilterDisplay);

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);


        modifyRequests();


        filterMemory.setPadding(false);
        filterMemory.setWidthFull();
        feedHolder.setWidthFull();
        feedHolder.setWidthFull();




        addClassName("animation-page");




    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        removeAll();


        add(mainLayout());

    }




    public VerticalLayout mainLayout() {
        verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");

        addUIData();

        ordersService.setSuccess(e->{
            ordersRightSide.hideRightSide();
            setNewPage();
            updateFeed();
        });


        // get info that user tries to save order

        ordersRightSide.setOrderConsumer(e->{
            ordersService.saveEditedData(e);


        });



        return verticalLayout;
    }



    public void modifyRequests(){


        orderFilters.setOrderStatusConsumer(e->{
            filterData.setOrderStatusChoice(e);
            setNewPage();
            updateFeed();


        });

        orderFilters.setFromDateConsumer(e->{
            filterData.setDateFromChoice(e);
            setNewPage();
            updateFeed();
        });

        orderFilters.setToDateConsumer(e->{
            filterData.setDateToChoice(e);
            setNewPage();
            updateFeed();
        });
        orderFilters.setFromCostConsumer(e->{
            filterData.setPriceFromChoice(e);
            setNewPage();
            updateFeed();
        });
        orderFilters.setToCostConsumer(e->{
            filterData.setPriceToChoice(e);
            setNewPage();
            updateFeed();
        });

        orderFilters.setPrompt(e->{
            filterData.setPromptChoice(e);
            setNewPage();
            updateFeed();
        });

        orderFilters.setAmountOfProductsConsumer(e->{
            filterData.setAmountOfProductsChoice(e);
            setNewPage();
            updateFeed();
        });

        orderFilters.setEmployeeId(e->{
            filterData.setEmployee(e);
            setNewPage();
            updateFeed();
        });

        orderFilters.setProductId(e->{
            filterData.setProducts(e);
            setNewPage();
            updateFeed();
        });

        orderFilters.setClearFilters(e->{
            currentFilterDisplay.clearAllData();
            addUIData();
        });
        paganation.setOnPageChange(e->{
            e = e-1;
            filterData.setPage(e);
            updateFeed();
        });

        //needed
        currentFilterDisplay.setReloadController(e->{
            filterData = (OrderFilterHolder) e;
            setNewPage();
            updateFeed();
        });


    }








    public void addUIData(){

        verticalLayout.removeAll();


        filterData = new OrderFilterHolder();



        HorizontalLayout sidesHolder = new HorizontalLayout();
        sidesHolder.setWidthFull();
        sidesHolder.addClassName("layout-flex");


        VerticalLayout leftSide = new VerticalLayout();
        leftSide.setPadding(false);
        leftSide.setWidthFull();
        leftSide.setMaxHeight("1000px");





        filterMemory.removeAll();
        filterMemory.add(
                orderFilters.filters()

        );

        leftSide.add(
                feedHolder,
                paganation.buttonHolder(Math.toIntExact(ordersService.getPageCount(filterData))));

        VerticalLayout right = ordersRightSide.rightSideOrderInfo();

        // LEFT SIDE
        leftSide.setWidth("200px");

        // RIGHT SIDE
        right.setWidth("1000px");


        sidesHolder.add(leftSide, right);
        sidesHolder.expand(leftSide);



        verticalLayout.add(
                briefOrderPageExplanation.briefExplanation(),
                newOrderFeed.newOrders(),
                orderMiniStats.miniStatHolder(ordersService.getMiniStats(common.dateCrafter(0,0,0,0,true),common.dateCrafter(0,1,0,0,true))),
                filterMemory,
                sidesHolder);

        updateFeed();

    }





    public void updateFeed(){

        UI ui = UI.getCurrent();
        String jwt = sessionCrafter.extractSession("JWT", String.class);

        feedHolder.removeAll();
        feedHolder.add(commonComponents.shimmer(3));



        CompletableFuture
                .supplyAsync(()->{

                    List<OrdersFeedData> items = ordersService.getOrderFeedData(filterData,jwt);
                    common.timer(250);
                    return items;
                })
                .thenAccept(e->{
                    ui.access(() -> {
                        feedHolder.removeAll();
                         feedHolder.add(ordersLeftSide.orderFeedHolder(e));
                    });
                });


    }


//    public void updateUIData(){
//
//        verticalLayout.removeAll();
//
//        HorizontalLayout sidesHolder = new HorizontalLayout();
//        sidesHolder.setWidthFull();
//        sidesHolder.addClassName("layout-flex");
//
//
//        VerticalLayout leftSide = new VerticalLayout();
//        leftSide.setWidthFull();
//        leftSide.setMaxHeight("1000px");
//
//
//
////        Scroller left = ordersLeftSide.orderFeedHolder(
////                ordersService.getOrderFeedData(filterData));
//
//        updateFeed();
//
//        leftSide.add(
//                filterMemory,
//                left,
//                paganation.buttonHolder(
//                        Math.toIntExact((ordersService.getPageCount(filterData)))));
//
//        VerticalLayout right = ordersRightSide.rightSideOrderInfo();
//
//        // LEFT SIDE
//        leftSide.setWidth("250px");
//
//        // RIGHT SIDE
//        right.setMaxWidth("1000px");
//
//
//        sidesHolder.add(leftSide, right);
//        sidesHolder.expand(leftSide);
//
//
//
//
//
//        verticalLayout.add(
//                briefOrderPageExplanation.briefExplanation(),
//                orderMiniStats.miniStatHolder(ordersService.getMiniStats(common.dateCrafter(0,0,0,0,true),common.dateCrafter(0,1,0,0,true))),
//                sidesHolder);
//    }

    public void setNewPage(){
        filterData.setPage(0);
        paganation.updateUIFromExternal(1);
    }



}











