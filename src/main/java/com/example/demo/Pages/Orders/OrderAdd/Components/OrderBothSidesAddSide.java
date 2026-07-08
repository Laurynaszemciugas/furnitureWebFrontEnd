package com.example.demo.Pages.Orders.OrderAdd.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.ControllerModels.CommonDtos.User;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.Enums.PayMethod;
import com.example.demo.Enums.PayStatus;
import com.example.demo.Pages.Orders.Page.Components.AssignEmployees;
import com.example.demo.Services.EmployeeService.EmployeeService;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
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

    Binder<Void> binder = new Binder<>();
    // Data fields
    TextField selectCustomer = new TextField("Customer");
    TextField customerGmail = new TextField("Email");
    TextField customerPhoneNumber = new TextField("Phone");
    TextArea customerAddress = new TextArea("Billing address");
    TextArea orderNote = new TextArea();

    ComboBox<OrderStatus> orderStatusComboBox = new ComboBox<>("Order status");
    ComboBox<PayStatus> payStatusComboBox = new ComboBox<>("Payment status");
    ComboBox<PayMethod> payMethodComboBox = new ComboBox<>("Payment method");
    DateTimePicker createdDate = new DateTimePicker("Order Created date");
    DateTimePicker dueDate = new DateTimePicker("Order Due date");


    Orders selectedOrder = new Orders();
    VerticalLayout employeeHolder = new VerticalLayout();

    Consumer<Orders> consumer;

    public OrderBothSidesAddSide(CommonComponents commonComponents, Common common, EmployeeService employeeService,ProductService productService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.employeeService = employeeService;
        this.productService = productService;
        this.assignEmployees = new AssignEmployees(commonComponents,common,employeeService);
        this.orderAddProductListAddRemove = new OrderAddProductListAddRemove(commonComponents,common,productService);


        binder();
    }


    public HorizontalLayout briefPageExplanation(){
        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);


        HorizontalLayout buttonHolder = new HorizontalLayout();

        Button cancel = commonComponents.normalThemeButton("Cancel","Orders", ButtonVariant.LUMO_ICON);
        Button createOrder = commonComponents.normalThemeButtonNoNavigate("Create Order", ButtonVariant.LUMO_PRIMARY);

        createOrder.addClickListener(e->{
            if(binder.validate().isOk()){

                selectedOrder.setOrderNote(orderNote.getValue());
                selectedOrder.setOrderStatus(orderStatusComboBox.getValue());
                selectedOrder.setPayStatus(payStatusComboBox.getValue());
                selectedOrder.setPayMethod(payMethodComboBox.getValue());
                selectedOrder.setCreated(createdDate.getValue());
                selectedOrder.setEstimatedDueDate(dueDate.getValue());
                selectedOrder.setOrderCreatedByName(selectCustomer.getValue());
                selectedOrder.setOrderCreatedByGmail(customerGmail.getValue());
                User user = new User();
                user.setGmail(customerGmail.getValue());
                selectedOrder.setOrderPlacedBy(user);


                selectedOrder.setBillingAddress(customerAddress.getValue());
                selectedOrder.setPhoneNumber(customerPhoneNumber.getValue());



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


    public VerticalLayout orderSettings(){
        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();
        v.setMaxHeight("700px");
        v.addClassName("island");

        orderStatusComboBox.setWidthFull();
        orderStatusComboBox.setItems(OrderStatus.values());
        orderStatusComboBox.setValue(OrderStatus.Pending);


        payStatusComboBox.setWidthFull();
        payStatusComboBox.setItems(PayStatus.values());


        payMethodComboBox.setWidthFull();
        payMethodComboBox.setItems(PayMethod.values());


        createdDate.setWidthFull();
        createdDate.setReadOnly(true);
        createdDate.setValue(LocalDateTime.now());


        dueDate.setWidthFull();


        v.add(
                commonComponents.spanCrafterWordNoHide("Order settings","activityFeed-name"),
                orderStatusComboBox,
                payStatusComboBox,
                payMethodComboBox,
                createdDate,
                dueDate
        );


        return v;
    }

    public VerticalLayout orderNote(){
        VerticalLayout v = new VerticalLayout();
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


        binder.forField(orderStatusComboBox)
                .asRequired("Order status is required")
                .bind(v -> null, (v, value) -> {});

        binder.forField(payStatusComboBox)
                .asRequired("Order pay status is required")
                .bind(v -> null, (v, value) -> {});

        binder.forField(payMethodComboBox)
                .asRequired("Order pay method is required")
                .bind(v -> null, (v, value) -> {});

        binder.forField(createdDate)
                .asRequired("Order create date is required")
                .bind(v -> null, (v, value) -> {});

        binder.forField(dueDate)
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
