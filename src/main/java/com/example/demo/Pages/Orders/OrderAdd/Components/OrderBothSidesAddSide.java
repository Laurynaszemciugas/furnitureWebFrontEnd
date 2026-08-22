package com.example.demo.Pages.Orders.OrderAdd.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.OrderAiDto;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.ControllerModels.CommonDtos.User;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.Enums.PayMethod;
import com.example.demo.Enums.PayStatus;
import com.example.demo.Pages.Orders.Page.Components.AssignEmployees;
import com.example.demo.Services.AI.AIService;
import com.example.demo.Services.EmployeeService.EmployeeService;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.function.Consumer;

@Setter
public class OrderBothSidesAddSide {


    CommonComponents commonComponents;
    Common common;
    AssignEmployees assignEmployees;
    EmployeeService employeeService;
    OrderAddProductListAddRemove orderAddProductListAddRemove;
    ProductService productService;
    AIService aiService;

    Binder<Void> binder = new Binder<>();
    // Data fields
    TextField orderCreatedByName = new TextField("Customer");
    TextField orderCreatedByGmail = new TextField("Email");
    TextField phoneNumber = new TextField("Phone");
    TextArea billingAddress = new TextArea("Billing address");
    TextArea orderNote = new TextArea();

    ComboBox<OrderStatus> orderStatusComboBox = new ComboBox<>("Order status");
    ComboBox<PayStatus> payStatus = new ComboBox<>("Payment status");
    ComboBox<PayMethod> payMethod = new ComboBox<>("Payment method");
    DateTimePicker createdDate = new DateTimePicker("Order Created date");
    DateTimePicker estimatedDueDate = new DateTimePicker("Order Due date");


    Orders selectedOrder = new Orders();
    VerticalLayout employeeHolder = new VerticalLayout();

    Consumer<Orders> consumer;

    HorizontalLayout h = new HorizontalLayout();

    public OrderBothSidesAddSide(CommonComponents commonComponents, Common common, EmployeeService employeeService,ProductService productService,AIService aiService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.employeeService = employeeService;
        this.productService = productService;
        this.assignEmployees = new AssignEmployees(commonComponents,common,employeeService);
        this.orderAddProductListAddRemove = new OrderAddProductListAddRemove(commonComponents,common,productService);
        this.aiService = aiService;


        binder();
    }


    public HorizontalLayout briefPageExplanation(){
        HorizontalLayout h = new HorizontalLayout();

        h.addClassName("smooth-panel");

        h.setWidthFull();
        h.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);


        HorizontalLayout buttonHolder = new HorizontalLayout();

        Button cancel = commonComponents.normalThemeButton("Cancel","Orders", ButtonVariant.LUMO_ICON);
        Button createOrder = commonComponents.normalThemeButtonNoNavigate("Create Order", ButtonVariant.LUMO_PRIMARY);

        createOrder.addClickListener(e->{
            if(binder.validate().isOk()){

                selectedOrder.setOrderNote(orderNote.getValue());
                selectedOrder.setOrderStatus(orderStatusComboBox.getValue());
                selectedOrder.setPayStatus(payStatus.getValue());
                selectedOrder.setPayMethod(payMethod.getValue());
                selectedOrder.setCreated(createdDate.getValue());
                selectedOrder.setEstimatedDueDate(estimatedDueDate.getValue());
                selectedOrder.setOrderCreatedByName(orderCreatedByName.getValue());
                selectedOrder.setOrderCreatedByGmail(orderCreatedByGmail.getValue());
                User user = new User();
                user.setGmail(orderCreatedByGmail.getValue());
                selectedOrder.setOrderPlacedBy(user);


                selectedOrder.setBillingAddress(billingAddress.getValue());
                selectedOrder.setPhoneNumber(phoneNumber.getValue());



                consumer.accept(selectedOrder);
            }
            else{
                commonComponents.showNotification("Form is not properly filled",3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.ERROR);
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

    // ====================== Left side =========================================
    public VerticalLayout leftSide(){


        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();



        v.setPadding(false);

        employeeHolder.removeAll();
        employeeHolder.setPadding(false);
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setPadding(false);
        verticalLayout.add(
                assignEmployees.employeeAssignment(selectedOrder,employeeHolder)

        );


        v.add(
                consumerInformation(),
                // remove that listOfPRoducts and place inside the class
                orderAddProductListAddRemove.consumerOrderItems(selectedOrder),
                verticalLayout
        );

        return v;
    }



    public VerticalLayout consumerInformation(){
        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();

        v.addClassName("animated-card");

        v.addClassName("island");

        FormLayout first = new FormLayout();
        first.add(
                orderCreatedByName,
                orderCreatedByGmail,
                phoneNumber
        );
        first.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 3)
        );

        billingAddress.setHeight("120px");
        FormLayout second = new FormLayout();
        second.add(billingAddress);
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


    public VerticalLayout orderSettings(){

        Button aiButton = commonComponents.buttonThemeAndIconNoNavigate("AI",ButtonVariant.LUMO_PRIMARY, VaadinIcon.MAGIC,"WHITE");
        aiButton.setTooltipText("Generate information with a prompt");
        aiButton.getStyle().set("position","absolute").set("right","10px").set("top","10px");



        aiButton.addClickListener(e -> {


            HorizontalLayout component = new HorizontalLayout();
            component.setWidthFull();
            component.addClassName("layout-flex");

            VerticalLayout left = leftSide();
            VerticalLayout right = rightSide();

            left.setWidth("500px");

            right.setWidth("500px");

            component.add(
                    left,
                    right
            );

            component.expand(left);

            component.setPadding(false);
            component.add(
                    left,
                    right
            );


            aiService.dialogTest(new OrderAiDto(), OrderAiDto.class,h,component,this,"Orders");




        });


        VerticalLayout v = new VerticalLayout();
        v.getStyle().set("position","relative");
        v.addClassName("animated-card");

        v.setWidthFull();
        v.setMaxHeight("700px");
        v.addClassName("island");

        orderStatusComboBox.setWidthFull();
        orderStatusComboBox.setItems(OrderStatus.values());
        orderStatusComboBox.setValue(OrderStatus.NEW);
        orderStatusComboBox.setReadOnly(true);


        payStatus.setWidthFull();
        payStatus.setItems(PayStatus.values());


        payMethod.setWidthFull();
        payMethod.setItems(PayMethod.values());


        createdDate.setWidthFull();
        createdDate.setReadOnly(true);
        createdDate.setValue(LocalDateTime.now());


        estimatedDueDate.setWidthFull();


        v.add(
                aiButton,
                commonComponents.spanCrafterWordNoHide("Order settings","activityFeed-name"),
                orderStatusComboBox,
                payStatus,
                payMethod,
                createdDate,
                estimatedDueDate
        );


        return v;
    }

    public VerticalLayout orderNote(){
        VerticalLayout v = new VerticalLayout();

        v.addClassName("animated-card");

        v.setWidthFull();
        v.setHeight("405px");
        v.addClassName("island");


        orderNote.setPlaceholder("Add note here...");
        orderNote.setSizeFull();

        v.add(
                commonComponents.spanCrafterWordNoHide("Order note","activityFeed-name"),
                orderNote

        );


        return v;
    }



    public HorizontalLayout joinLeftRight(){

        h.setWidthFull();
        h.addClassName("layout-flex");

        VerticalLayout left = leftSide();
        VerticalLayout right = rightSide();

        left.setWidth("500px");

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
                orderSettings(),
                orderNote()
        );


        return v;
    }








    public void binder(){
        binder.forField(orderCreatedByName)
                .asRequired("Customer is required")
                .bind(v -> null, (v, value) -> {});

        binder.forField(orderCreatedByGmail)
                .asRequired("Email is required")
                .withValidator(
                        email -> email == null || email.isBlank() ||
                                email.matches("^[A-Za-z0-9+_.-]+@(.+)$"),
                        "Invalid email"
                )
                .bind(v -> null, (v, value) -> {});

        binder.forField(phoneNumber)
                .asRequired("Phone number is required")
                .bind(v -> null, (v, value) -> {});

        binder.forField(billingAddress)
                .asRequired("Address is required")
                .bind(v -> null, (v, value) -> {});


        binder.forField(orderStatusComboBox)
                .asRequired("Order status is required")
                .bind(v -> null, (v, value) -> {});

        binder.forField(payStatus)
                .asRequired("Order pay status is required")
                .bind(v -> null, (v, value) -> {});

        binder.forField(payMethod)
                .asRequired("Order pay method is required")
                .bind(v -> null, (v, value) -> {});

        binder.forField(createdDate)
                .asRequired("Order create date is required")
                .bind(v -> null, (v, value) -> {});

        binder.forField(estimatedDueDate)
                .asRequired("Order due date is required")
                .withValidator(
                        due -> due == null ||
                                createdDate.getValue() == null ||
                                !due.isBefore(createdDate.getValue()),
                        "Due date cannot be before create date"
                )
                .bind(v -> null, (v, value) -> {});



    }



}
