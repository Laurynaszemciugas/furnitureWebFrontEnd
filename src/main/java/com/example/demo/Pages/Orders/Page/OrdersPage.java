package com.example.demo.Pages.Orders.Page;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.CurrentFilterDisplay;
import com.example.demo.Common.Paganation;
import com.example.demo.ControllerModels.Filter.Material.MaterialFilterHolder;
import com.example.demo.ControllerModels.Filter.Order.OrderFilterHolder;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Orders.Components.BriefOrderPageExplanation;
import com.example.demo.Pages.Orders.Components.OrderFilters;
import com.example.demo.Pages.Orders.Components.OrdersLeftSide;
import com.example.demo.Pages.Orders.Components.OrdersRightSide;
import com.example.demo.ServerDBCall.CommonCalls.CommonCalls;
import com.example.demo.ServerDBCall.EmployeeCalls.EmployeeCalls;
import com.example.demo.ServerDBCall.OrderCalls.OrderCalls;
import com.example.demo.ServerDBCall.ProductPage.ProductsCall;
import com.example.demo.Services.Orders.OrdersService;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;


@Route(value = "Orders", layout = MainLayout.class)
public class OrdersPage extends VerticalLayout implements BeforeEnterObserver {

    CommonComponents commonComponents;
    Common common;
    OrderCalls orderCalls;
    EmployeeCalls employeeCalls;
    ProductsCall productsCall;
    Paganation paganation;

    OrdersRightSide ordersRightSide;
    OrdersLeftSide ordersLeftSide;
    OrderFilters orderFilters;
    BriefOrderPageExplanation briefOrderPageExplanation;

    OrdersService ordersService;

    VerticalLayout filterMemory = new VerticalLayout();
    ProductService productService;

    // filter data

    OrderFilterHolder filterData = new OrderFilterHolder();

    VerticalLayout verticalLayout;

    CurrentFilterDisplay currentFilterDisplay;



    public OrdersPage(CommonComponents commonComponents, Common common, OrderCalls orderCalls,EmployeeCalls employeeCalls,OrdersService ordersService,ProductService productService,ProductsCall productsCall) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.orderCalls = orderCalls;
        this.ordersService = ordersService;
        this.productsCall = productsCall;

        this.productService = productService;
        this.ordersRightSide = new OrdersRightSide(commonComponents,common,orderCalls,employeeCalls,productService);
        this.ordersLeftSide = new OrdersLeftSide(commonComponents,common,orderCalls);
        this.orderFilters = new OrderFilters(commonComponents, common,employeeCalls,productsCall);
        this.briefOrderPageExplanation = new BriefOrderPageExplanation(commonComponents,common);
        this.paganation = new Paganation();


        this.ordersRightSide.setOrdersLeftSide(ordersLeftSide);

        this.currentFilterDisplay = new CurrentFilterDisplay(commonComponents,common);
        this.orderFilters.setCurrentFilterDisplay(currentFilterDisplay);

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);


        modifyRequests();

        filterMemory.add(
                orderFilters.moreFilters(),
                orderFilters.Buttons()
        );
        filterMemory.setPadding(false);

        add(mainLayout());
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
        verticalLayout.addClassName("main-island");

        addUIData();

        ordersService.setOrderIsSaved(e->{
            ordersRightSide.hideRightSide();
            setNewPage();
            updateUIData();
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
            updateUIData();


        });

        orderFilters.setFromDateConsumer(e->{
            filterData.setDateFromChoice(e);
            setNewPage();
            updateUIData();
        });

        orderFilters.setToDateConsumer(e->{
            filterData.setDateToChoice(e);
            setNewPage();
            updateUIData();
        });
        orderFilters.setFromCostConsumer(e->{
            filterData.setPriceFromChoice(e);
            setNewPage();
            updateUIData();
        });
        orderFilters.setToCostConsumer(e->{
            filterData.setPriceToChoice(e);
            setNewPage();
            updateUIData();
        });

        orderFilters.setPrompt(e->{
            filterData.setPromptChoice(e);
            setNewPage();
            updateUIData();
        });

        orderFilters.setAmountOfProductsConsumer(e->{
            filterData.setAmountOfProductsChoice(e);
            setNewPage();
            updateUIData();
        });

        orderFilters.setEmployeeId(e->{
            filterData.setEmployee(e);
            setNewPage();
            updateUIData();
        });

        orderFilters.setProductId(e->{
            filterData.setProducts(e);
            setNewPage();
            updateUIData();
        });

        orderFilters.setClearFilters(e->{
            currentFilterDisplay.clearAllData();
            addUIData();
        });
        paganation.setOnPageChange(e->{
            e = e-1;
            filterData.setPage(e);
            updateUIData();
        });

        //needed
        currentFilterDisplay.setReloadController(e->{
            System.out.println("reset");
            filterData = (OrderFilterHolder) e;
            setNewPage();
            updateUIData();
        });


    }








    public void addUIData(){

        verticalLayout.removeAll();


        filterData = new OrderFilterHolder();


        paganation.updateUIFromExternal(1);

        HorizontalLayout sidesHolder = new HorizontalLayout();
        sidesHolder.setWidthFull();
        sidesHolder.addClassName("layout-flex");


        VerticalLayout leftSide = new VerticalLayout();
        leftSide.setWidthFull();
        leftSide.addClassName("island");
        leftSide.setMaxHeight("1000px");


        Scroller left = ordersLeftSide.orderFeedHolder(
                ordersService.getOrderFeedData(filterData));

        filterMemory.removeAll();
        filterMemory.add(
                orderFilters.moreFilters(),
                orderFilters.Buttons()
        );

        leftSide.add(
                filterMemory,
                left,
                paganation.buttonHolder(Math.toIntExact(ordersService.getPageCount(filterData))));

        VerticalLayout right = ordersRightSide.rightSideOrderInfo();

        // LEFT SIDE
        leftSide.setWidth("250px");

        // RIGHT SIDE
        right.setWidth("1000px");


        sidesHolder.add(leftSide, right);
        sidesHolder.expand(leftSide);





        verticalLayout.add(
                briefOrderPageExplanation.briefExplanation(),
                sidesHolder);
    }

    public void updateUIData(){

        verticalLayout.removeAll();

        HorizontalLayout sidesHolder = new HorizontalLayout();
        sidesHolder.setWidthFull();
        sidesHolder.addClassName("layout-flex");


        VerticalLayout leftSide = new VerticalLayout();
        leftSide.setWidthFull();
        leftSide.addClassName("island");
        leftSide.setMaxHeight("1000px");



        Scroller left = ordersLeftSide.orderFeedHolder(
                ordersService.getOrderFeedData(filterData));

        leftSide.add(
                filterMemory,
                left,
                paganation.buttonHolder(
                        Math.toIntExact((ordersService.getPageCount(filterData)))));

        VerticalLayout right = ordersRightSide.rightSideOrderInfo();

        // LEFT SIDE
        leftSide.setWidth("250px");

        // RIGHT SIDE
        right.setMaxWidth("1000px");


        sidesHolder.add(leftSide, right);
        sidesHolder.expand(leftSide);





        verticalLayout.add(
                briefOrderPageExplanation.briefExplanation(),
                sidesHolder);
    }

    public void setNewPage(){
        filterData.setPage(0);
        paganation.updateUIFromExternal(1);
    }



}











