package com.example.demo.Pages.Orders.OrderAdd;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Error.ErrorResponse;
import com.example.demo.ControllerModels.Orders.OrderAddProducts;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.Enums.PayMethod;
import com.example.demo.Enums.PayStatus;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Orders.Components.AssignEmployees;
import com.example.demo.Pages.Orders.Components.OrderProductAddRemove.OrderBothSidesAddSide;
import com.example.demo.ServerDBCall.EmployeeCalls.EmployeeCalls;
import com.example.demo.Services.Orders.OrdersService;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.springframework.web.client.HttpClientErrorException;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Route(value = "OrdersAdd", layout = MainLayout.class)
public class OrderAdd extends VerticalLayout implements BeforeEnterObserver {


    CommonComponents commonComponents;
    Common common;
    ProductService productService;
    AssignEmployees assignEmployees;
    EmployeeCalls employeeCalls;
    OrdersService ordersService;

    OrderBothSidesAddSide orderBothSidesAddSide;

    List<OrderAddProducts> listOfProducts = new ArrayList<>();

    public OrderAdd(
            CommonComponents commonComponents,
            Common common,
            ProductService productService,
            EmployeeCalls employeeCalls,
            OrdersService ordersService
            ) {

        this.commonComponents = commonComponents;
        this.common = common;
        this.productService = productService;
        this.employeeCalls = employeeCalls;
        this.assignEmployees = new AssignEmployees(commonComponents,common,employeeCalls);
        this.ordersService = ordersService;
        this.orderBothSidesAddSide = new OrderBothSidesAddSide(commonComponents,common,employeeCalls,productService);

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);

        saveData();

        listOfProducts.addAll(productService.getProductsForAddOrder());




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
                    orderBothSidesAddSide.briefPageExplanation(),
                joinLeftRight()
        );

        return verticalLayout;
    }

    public void saveData(){
        orderBothSidesAddSide.setConsumer(e->{
                ordersService.saveNewOrder(e);


        });
    }



    public HorizontalLayout joinLeftRight(){
        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.addClassName("layout-flex");

        VerticalLayout left = orderBothSidesAddSide.leftSide();
        VerticalLayout right = rightSide();

        left.setWidth("1000px");

        right.setWidth("500px");

        h.add(
                left,
                right
        );

        h.expand(left);


        return h;
    }











    public VerticalLayout rightSide(){
        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();
        v.setPadding(false);

        v.add(
                orderBothSidesAddSide.orderSettings(),
                orderBothSidesAddSide.orderNote()
        );


        return v;
    }







}
