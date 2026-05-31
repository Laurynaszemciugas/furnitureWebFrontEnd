package com.example.demo.Pages.Orders.Page;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Paganation;
import com.example.demo.ControllerModels.CommonDtos.OrderJoin.OrderProducts;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.ControllerModels.CommonDtos.Product;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Orders.Components.OrdersLeftSide;
import com.example.demo.Pages.Orders.Components.OrdersRightSide;
import com.example.demo.ServerDBCall.EmployeeCalls.EmployeeCalls;
import com.example.demo.ServerDBCall.OrderCalls.OrderCalls;
import com.vaadin.flow.component.button.Button;
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

    OrdersRightSide ordersRightSide;
    OrdersLeftSide ordersLeftSide;




    public OrdersPage(CommonComponents commonComponents, Common common, OrderCalls orderCalls,EmployeeCalls employeeCalls) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.orderCalls = orderCalls;

        this.ordersRightSide = new OrdersRightSide(commonComponents,common,orderCalls,employeeCalls);
        this.ordersLeftSide = new OrdersLeftSide(commonComponents,common,orderCalls);


        this.ordersRightSide.setOrdersLeftSide(ordersLeftSide);

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);

        add(mainLayout());
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        removeAll();


        add(mainLayout());

    }




    public VerticalLayout mainLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");
        verticalLayout.addClassName("main-island");

        verticalLayout.add(
                briefExplanation(),
                leftAndRightSideHolder());



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













    @SneakyThrows
    public HorizontalLayout leftAndRightSideHolder(){
        HorizontalLayout sidesHolder = new HorizontalLayout();
        sidesHolder.setWidthFull();
        sidesHolder.addClassName("layout-flex");

        VerticalLayout left = ordersLeftSide.leftSideTheListOfOrders();
        VerticalLayout right = ordersRightSide.rightSideOrderInfo();

        // LEFT SIDE
        left.setWidth("450px");

        // RIGHT SIDE
        right.setMaxWidth("1050px");


        sidesHolder.add(left, right);
        sidesHolder.expand(left);



        return sidesHolder;
    }


    public VerticalLayout briefExplanation(){
        VerticalLayout v = new VerticalLayout();
        v.add(commonComponents.biefPageExplanation("Orders"));
        return v;
    }



}











