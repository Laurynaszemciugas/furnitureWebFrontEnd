package com.example.demo.Pages.Orders.Page;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.CurrentFilterDisplay;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.Common.Paganation;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.ControllerModels.Filter.Order.OrderFilterHolder;
import com.example.demo.ControllerModels.Material.MaterialBriefDto;
import com.example.demo.ControllerModels.Orders.OrdersFeedData;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Orders.Page.Components.*;
import com.example.demo.Services.EmployeeService.EmployeeService;
import com.example.demo.Services.Orders.OrdersService;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.springframework.core.annotation.Order;

import javax.swing.plaf.PanelUI;
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

    OrdersService ordersService;

    VerticalLayout filterMemory = new VerticalLayout();
    ProductService productService;

    SessionCrafter sessionCrafter;

    // filter data

    OrderFilterHolder filterData = new OrderFilterHolder();

    VerticalLayout verticalLayout;

    CurrentFilterDisplay currentFilterDisplay;

    Div feedHolder = new Div();
    HorizontalLayout reviewLeftRightSide = new HorizontalLayout();

    Orders newSelectedOrder = new Orders();


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


        this.ordersRightSide.setOrdersLeftSide(ordersLeftSide);

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

        reviewLeftRightSide.setVisible(false);



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



        updateFeed();

        filterMemory.removeAll();
        filterMemory.add(
                orderFilters.filters()

        );

        leftSide.add(
                filterMemory,
                feedHolder,
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
                newOrders(),
                orderMiniStats.miniStatHolder(ordersService.getMiniStats(common.dateCrafter(0,0,0,0,true),common.dateCrafter(0,1,0,0,true))),
                sidesHolder);
    }


    public VerticalLayout newOrders(){

        VerticalLayout main = new VerticalLayout();
        main.setPadding(false);
        main.setWidthFull();

        HorizontalLayout v = new HorizontalLayout();
        v.setAlignItems(FlexComponent.Alignment.CENTER);
        v.setJustifyContentMode(JustifyContentMode.BETWEEN);
        v.setWidthFull();
        v.addClassName("island");


        Span amount = new Span("1");

        Div amountOfNewOrders = new Div();
        amountOfNewOrders.add(amount);

        amountOfNewOrders.getStyle()
                .set("background-color", "#3960e8")
                .set("color", "white")
                .set("position", "absolute")
                .set("top", "-6px")
                .set("right", "-6px")
                .set("width", "28px")
                .set("height", "28px")
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "center")
                .set("border-radius", "50%")
                .set("font-size", "15px")
                .set("font-weight", "700")
                .set("line-height", "1")
                .set("border", "2px solid white");

        amountOfNewOrders.add(amount);




        VerticalLayout iconHolder = new VerticalLayout();
        iconHolder.getStyle().set("position","relative");
        iconHolder.setHeight("80px");
        iconHolder.setWidth("80px");
        iconHolder.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        iconHolder.setAlignItems(FlexComponent.Alignment.CENTER);
        iconHolder.addClassName("statIconBack");
        iconHolder.getStyle().set("background-color","rgba(59, 130, 246, 0.08)");
        iconHolder.getStyle().set("border","1px solid rgba(59, 130, 246, 0.22)");
        Icon icon = commonComponents.iconCrafter(VaadinIcon.BELL_O,"30px","bLUE");
        iconHolder.add(icon,amountOfNewOrders);


        Span ordersText = commonComponents.spanCrafterWordNoHide("4 new order waiting for approval","activityFeed-name");

        HorizontalLayout bellTextHolder = new HorizontalLayout();
        bellTextHolder.setAlignItems(FlexComponent.Alignment.CENTER);
        bellTextHolder.setPadding(false);
        bellTextHolder.add(
                iconHolder,
                ordersText
        );




        Button reviewOrders = new Button("Review orders");
        reviewOrders.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        reviewOrders.addClickListener(e->{

            if(reviewLeftRightSide.isVisible()){
                reviewLeftRightSide.setVisible(false);
            }
            else{
                reviewLeftRightSide.setVisible(true);
            }


        });

        v.add(
                bellTextHolder,
                reviewOrders
        );

        main.add(
                v,
                reviewLeftRight()
        );


        return main;


    }




    public HorizontalLayout reviewLeftRight(){
        reviewLeftRightSide.setWidthFull();
        reviewLeftRightSide.addClassName("island");
        reviewLeftRightSide.addClassName("layout-flex");

        VerticalLayout left = newOrderLeftSide();
        left.setWidth("250px");
        VerticalLayout right = newOrderRightSide();
        right.setWidth("1000px");


        reviewLeftRightSide.add(

                left,
                right

        );

        reviewLeftRightSide.expand(left);

        return reviewLeftRightSide;

    }


    public VerticalLayout newOrderLeftSide(){

        VerticalLayout v = new VerticalLayout();

        String jwt = sessionCrafter.extractSession("JWT", String.class);
        v.setWidthFull();
        v.addClassName("island");

        v.add(
                ordersLeftSide.newOrderFeedHolder(ordersService.getOrderFeedData(filterData,jwt))
        );



        return v;

    }



    public VerticalLayout newOrderRightSide(){


        VerticalLayout v = new VerticalLayout();

        Span titleOfTePreview = new Span();

        ordersLeftSide.setNewOrderList(e->{
            newSelectedOrder = ordersService.getSelectedOrder(e);

            titleOfTePreview.setText( "Order preview - NWO-"+ newSelectedOrder.getId());

            v.setVisible(true);
        });

        v.setWidthFull();
        v.addClassName("island");
        v.setVisible(false);


        v.add(
                titleOfTePreview
        );






        return v;

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











