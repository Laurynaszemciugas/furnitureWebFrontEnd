package com.example.demo.Pages.Orders.Page;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Paganation;
import com.example.demo.ControllerModels.CommonDtos.OrderJoin.OrderProducts;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.ControllerModels.CommonDtos.Product;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Orders.Components.BriefOrderPageExplanation;
import com.example.demo.Pages.Orders.Components.OrderFilters;
import com.example.demo.Pages.Orders.Components.OrdersLeftSide;
import com.example.demo.Pages.Orders.Components.OrdersRightSide;
import com.example.demo.ServerDBCall.EmployeeCalls.EmployeeCalls;
import com.example.demo.ServerDBCall.OrderCalls.OrderCalls;
import com.example.demo.Services.Orders.OrdersService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import lombok.SneakyThrows;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


@Route(value = "Orders", layout = MainLayout.class)
public class OrdersPage extends VerticalLayout implements BeforeEnterObserver {

    CommonComponents commonComponents;
    Common common;
    OrderCalls orderCalls;
    EmployeeCalls employeeCalls;
    Paganation paganation;

    OrdersRightSide ordersRightSide;
    OrdersLeftSide ordersLeftSide;
    OrderFilters orderFilters;
    BriefOrderPageExplanation briefOrderPageExplanation;

    OrdersService ordersService;

    VerticalLayout filterMemory = new VerticalLayout();


    // filter data

    OrderStatus orderStatusChoice = OrderStatus.ALL;
    Double priceFromChoice = 0.0;
    Double priceToChoice = 0.0;
    LocalDate dateFromChoice = LocalDate.of(1000,12,12);
    LocalDate dateToChoice = LocalDate.of(1000,12,12);
    Long amountOfProductsChoice = 0l;
    int pageChoice = 0;
    int pageCountChoice = 5;

    VerticalLayout verticalLayout;


    public OrdersPage(CommonComponents commonComponents, Common common, OrderCalls orderCalls,EmployeeCalls employeeCalls,OrdersService ordersService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.orderCalls = orderCalls;
        this.ordersService = ordersService;

        this.ordersRightSide = new OrdersRightSide(commonComponents,common,orderCalls,employeeCalls);
        this.ordersLeftSide = new OrdersLeftSide(commonComponents,common,orderCalls);
        this.orderFilters = new OrderFilters(commonComponents, common);
        this.briefOrderPageExplanation = new BriefOrderPageExplanation(commonComponents,common);
        this.paganation = new Paganation();


        this.ordersRightSide.setOrdersLeftSide(ordersLeftSide);

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



        // get info that user tries to save order
        ordersRightSide.setOrderConsumer(e->{
            try {
                String answer = orderCalls.saveModifiedOrder(e);
                commonComponents.showNotification(answer,3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.LUMO_SUCCESS);
                ordersRightSide.hideRightSide();

            } catch (Exception ex) {
                commonComponents.showNotification(ex.getMessage(), 3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.LUMO_ERROR);
            }
        });



        return verticalLayout;
    }



    public void modifyRequests(){


        orderFilters.setOrderStatusConsumer(e->{
            orderStatusChoice = e;
            setNewPage();
            updateUIData();


        });

        orderFilters.setFromDateConsumer(e->{
            dateFromChoice = e;
            setNewPage();
            updateUIData();
        });

        orderFilters.setToDateConsumer(e->{
            dateToChoice = e;
            setNewPage();
            updateUIData();
        });
        orderFilters.setFromCostConsumer(e->{
            priceFromChoice = e;
            setNewPage();
            updateUIData();
        });
        orderFilters.setToCostConsumer(e->{
            priceToChoice = e;
            setNewPage();
            updateUIData();
        });

        orderFilters.setAmountOfProductsConsumer(e->{
            amountOfProductsChoice = e;
            setNewPage();
            updateUIData();
        });
        orderFilters.setClearFilters(e->{
            addUIData();
        });
        paganation.setOnPageChange(e->{
            pageChoice = e-1;
            updateUIData();
        });

    }








    public void addUIData(){

        verticalLayout.removeAll();


        orderStatusChoice = OrderStatus.ALL;
        priceFromChoice = 0.0;
        priceToChoice = 0.0;
        dateFromChoice = LocalDate.of(1000,12,12);
        dateToChoice = LocalDate.of(1000,12,12);
        amountOfProductsChoice = 0l;
        pageChoice = 0;

        HorizontalLayout sidesHolder = new HorizontalLayout();
        sidesHolder.setWidthFull();
        sidesHolder.addClassName("layout-flex");


        VerticalLayout leftSide = new VerticalLayout();
        leftSide.setWidthFull();
        leftSide.addClassName("island");

        Scroller left = ordersLeftSide.orderFeedHolder(
                ordersService.getOrderFeedData(
                        orderStatusChoice,
                        priceFromChoice,
                        priceToChoice ,
                        dateFromChoice,
                        dateToChoice,
                        amountOfProductsChoice,
                        pageChoice,
                        pageCountChoice

                ));

        filterMemory.removeAll();
        filterMemory.add(
                orderFilters.moreFilters(),
                orderFilters.Buttons()
        );

        leftSide.add(
                filterMemory,
                left,
                paganation.buttonHolder(
                        (ordersService.getPageCount(
                                orderStatusChoice,
                                priceFromChoice,
                                priceToChoice,
                                dateFromChoice,
                                dateToChoice,
                                amountOfProductsChoice)).intValue())

        );

        VerticalLayout right = ordersRightSide.rightSideOrderInfo();

        // LEFT SIDE
        leftSide.setWidth("250px");

        // RIGHT SIDE
        right.setMaxWidth("1050px");


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



        Scroller left = ordersLeftSide.orderFeedHolder(
                ordersService.getOrderFeedData(
                        orderStatusChoice,
                        priceFromChoice,
                        priceToChoice ,
                        dateFromChoice,
                        dateToChoice,
                        amountOfProductsChoice,
                        pageChoice,
                        pageCountChoice

                ));

        leftSide.add(
                filterMemory,
                left,
                paganation.buttonHolder(
                        (ordersService.getPageCount(
                                orderStatusChoice,
                                priceFromChoice,
                                priceToChoice,
                                dateFromChoice,
                                dateToChoice,
                                amountOfProductsChoice)).intValue())

        );

        VerticalLayout right = ordersRightSide.rightSideOrderInfo();

        // LEFT SIDE
        leftSide.setWidth("250px");

        // RIGHT SIDE
        right.setMaxWidth("1050px");


        sidesHolder.add(leftSide, right);
        sidesHolder.expand(leftSide);





        verticalLayout.add(
                briefOrderPageExplanation.briefExplanation(),
                sidesHolder);
    }

    public void setNewPage(){
        pageChoice = 0;
        paganation.updateUIFromExternal(1);
    }



}











