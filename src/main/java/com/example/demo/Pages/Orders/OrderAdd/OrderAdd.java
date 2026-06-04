package com.example.demo.Pages.Orders.OrderAdd;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Orders.OrderAddProducts;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.Enums.PayMethod;
import com.example.demo.Enums.PayStatus;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
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



    Binder<Void> binder = new Binder<>();
    // Data fields
    ComboBox<String> selectCustomer = new ComboBox<>("Customer");
    TextField customerGmail = new TextField("Email");
    TextField customerPhoneNumber = new TextField("Phone");
    TextArea customerAddress = new TextArea("Billing address");
    TextArea orderNote = new TextArea();
    ComboBox<PayStatus> payStatus = new ComboBox<>("Pay status");
    ComboBox<PayMethod> payMethod = new ComboBox<>("Pay method");
    ComboBox<OrderStatus> orderStatus = new ComboBox<>("Order status");
    DateTimePicker orderDue = new DateTimePicker("Order due");
    List<OrderAddProducts> listOfProducts = new ArrayList<>();


    Span totalValueOfItems;


    public OrderAdd(
            CommonComponents commonComponents,
            Common common,
            ProductService productService) {

        this.commonComponents = commonComponents;
        this.common = common;
        this.productService = productService;

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);
        binder();

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
                briefPageExplanation(),
                joinLeftRight()
        );

        return verticalLayout;
    }

    public HorizontalLayout briefPageExplanation(){
        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.setJustifyContentMode(JustifyContentMode.BETWEEN);


        HorizontalLayout buttonHolder = new HorizontalLayout();

        Button cancel = commonComponents.normalThemeButton("Cancel","Orders", ButtonVariant.LUMO_ICON);
        Button createOrder = commonComponents.normalThemeButtonNoNavigate("Create Order", ButtonVariant.LUMO_PRIMARY);

        createOrder.addClickListener(e->{
            if(binder.validate().isOk()){
                System.out.println("good");
            }
        });

        buttonHolder.add(
                cancel,
                createOrder
        );
        h.add(
                commonComponents.biefPageExplanation("Add Order"),
                buttonHolder

        );
        return h;
    }

    public HorizontalLayout joinLeftRight(){
        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.addClassName("layout-flex");

        VerticalLayout left = leftSide();
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



    // ====================== Left side =========================================
    public VerticalLayout leftSide(){
        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();
        v.addClassName("island");


        v.add(
                consumerInformation(),
                consumerOrderItems()
        );

        return v;
    }



    public VerticalLayout consumerInformation(){
        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();
        v.addClassName("island");

        FormLayout first = new FormLayout();
        first.add(
                selectCustomer,
                customerGmail,
                customerPhoneNumber
        );
        first.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 3)
        );

        customerAddress.setHeight("120px");
        FormLayout second = new FormLayout();
        second.add(customerAddress);
        second.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1)
        );

        v.add(
                commonComponents.spanCrafterWordNoHide("Consumer information","activityFeed-name"),
                first,
                second
        );

        return  v;
    }

    public VerticalLayout consumerOrderItems(){
        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();
        v.addClassName("island");


        Grid<OrderAddProducts> orderItems = new Grid<>(OrderAddProducts.class,false);
        orderItems.setItems(listOfProducts);

        orderItems.addComponentColumn(e->{

            System.out.println(e.getMainImage());

            Image image = commonComponents.imageCrafter(e.getMainImage() == null ? "No_picture.png" : e.getMainImage() ,"50px","50px","50px");

            return  image;

        }).setAutoWidth(true);

        totalValueOfItems = commonComponents.spanCrafterWordNoHide(String.format("%s: %.2f %s","Total", 100.20,"Eur"),"stat-example");
        Button addProduct = commonComponents.buttonThemeAndIconNoNavigate("Create Order", ButtonVariant.LUMO_PRIMARY, VaadinIcon.PLUS,"white");

        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.setJustifyContentMode(JustifyContentMode.BETWEEN);

        h.add(
                addProduct,
                totalValueOfItems
        );

        v.add(
                commonComponents.spanCrafterWordNoHide("Order items","activityFeed-name"),
                orderItems,
                h
        );

        return  v;
    }













    public VerticalLayout rightSide(){
        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();
        v.addClassName("island");


        return v;
    }


    public void binder(){
        binder.forField(selectCustomer)
                .asRequired("Customer is required")
                .bind(v -> null, (v, value) -> {});

        binder.forField(customerGmail)
                .asRequired("Email is required")
                .withValidator(
                        email -> email == null || email.isBlank() ||
                                email.matches("^[A-Za-z0-9+_.-]+@(.+)$"),
                        "Invalid email"
                )
                .bind(v -> null, (v, value) -> {});

        binder.forField(customerPhoneNumber)
                .asRequired("Phone number is required")
                .bind(v -> null, (v, value) -> {});

        binder.forField(customerAddress)
                .asRequired("Address is required")
                .bind(v -> null, (v, value) -> {});

        binder.forField(payStatus)
                .asRequired("Pay status is required")
                .bind(v -> null, (v, value) -> {});

        binder.forField(payMethod)
                .asRequired("Pay method is required")
                .bind(v -> null, (v, value) -> {});

        binder.forField(orderStatus)
                .asRequired("Order status is required")
                .bind(v -> null, (v, value) -> {});

        binder.forField(orderDue)
                .asRequired("Order due date is required")
                .bind(v -> null, (v, value) -> {});
    }



}
