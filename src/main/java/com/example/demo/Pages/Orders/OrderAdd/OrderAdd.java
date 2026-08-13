package com.example.demo.Pages.Orders.OrderAdd;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Orders.OrderAddProducts;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Orders.Page.Components.AssignEmployees;
import com.example.demo.Pages.Orders.OrderAdd.Components.OrderBothSidesAddSide;
import com.example.demo.Services.AI.AIService;
import com.example.demo.Services.EmployeeService.EmployeeService;
import com.example.demo.Services.Orders.OrdersService;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;


@Route(value = "OrdersAdd", layout = MainLayout.class)
public class OrderAdd extends VerticalLayout implements BeforeEnterObserver {


    CommonComponents commonComponents;
    Common common;
    ProductService productService;
    AssignEmployees assignEmployees;
    EmployeeService employeeService;
    OrdersService ordersService;
    AIService aiService;

    OrderBothSidesAddSide orderBothSidesAddSide;
    List<OrderAddProducts> listOfProducts = new ArrayList<>();

    public OrderAdd(
            CommonComponents commonComponents,
            Common common,
            ProductService productService,
            EmployeeService employeeService,
            OrdersService ordersService,
            AIService aiService
            ) {

        this.commonComponents = commonComponents;
        this.common = common;
        this.productService = productService;
        this.employeeService = employeeService;
        this.assignEmployees = new AssignEmployees(commonComponents,common,employeeService);
        this.ordersService = ordersService;
        this.aiService = aiService;
        this.orderBothSidesAddSide = new OrderBothSidesAddSide(commonComponents,common,employeeService,productService,aiService);

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);

        saveData();

        listOfProducts.addAll(productService.getProductsForAddOrder());

        addClassName("animation-page");




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

        verticalLayout.add(
                    orderBothSidesAddSide.briefPageExplanation(),
                    orderBothSidesAddSide.joinLeftRight()
        );

        return verticalLayout;
    }

    public void saveData(){
        orderBothSidesAddSide.setConsumer(e->{
                ordersService.saveNewOrder(e);


        });
    }











}
