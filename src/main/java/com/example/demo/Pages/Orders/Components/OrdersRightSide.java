package com.example.demo.Pages.Orders.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.ObjectConverter;
import com.example.demo.ControllerModels.CommonDtos.Employee;
import com.example.demo.ControllerModels.CommonDtos.EmployeeJoin.OrderEmployees;
import com.example.demo.ControllerModels.CommonDtos.OrderJoin.OrderProducts;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.DTOS.ComboBoxEmployees;
import com.example.demo.Enums.EmployeeCategory;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.Enums.PayMethod;
import com.example.demo.Enums.PayStatus;
import com.example.demo.Pages.Orders.Components.OrderProductAddRemove.OrderAddProductListAddRemove;
import com.example.demo.ServerDBCall.EmployeeCalls.EmployeeCalls;
import com.example.demo.ServerDBCall.OrderCalls.OrderCalls;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import lombok.SneakyThrows;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class OrdersRightSide {

    CommonComponents commonComponents;
    Common common;
    OrderCalls orderCalls;
    EmployeeCalls employeeCalls;
    AssignEmployees assignEmployees;
    ProductService productService;
    ObjectConverter objectConverter;


    // refrence to the leftside component
    OrdersLeftSide ordersLeftSide;


    Orders selectedOrder = new Orders();
    VerticalLayout orderItemsHolder = new VerticalLayout();
    VerticalLayout timeLineHoder = new VerticalLayout();
    VerticalLayout employeeHolder = new VerticalLayout();
    VerticalLayout noteHolder = new VerticalLayout();
    VerticalLayout employeeAssigmentHolder = new VerticalLayout();

    VerticalLayout rightSide;

    Span status;

    // total values to be changed
    Span totalCost;
    Span total;

    Span dueDateForChange;
    Span addressAuto;

    ComboBox<PayStatus> payStatusComboBox;
    ComboBox<PayMethod> payMethodComboBox;

    List<ComboBoxEmployees> listEmployees = new ArrayList<>();

    OrderAddProductListAddRemove orderAddProductListAddRemove;


    Consumer<Orders> ordersConsumer;

    @SneakyThrows
    public OrdersRightSide(
            CommonComponents commonComponents,
            Common common,
            OrderCalls orderCalls,
            EmployeeCalls employeeCalls,
            ProductService productService
    ) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.orderCalls = orderCalls;
        this.employeeCalls = employeeCalls;
        this.assignEmployees = new AssignEmployees(commonComponents,common,employeeCalls);
        this.orderAddProductListAddRemove = new OrderAddProductListAddRemove(commonComponents,common,productService);
        this.productService = productService;


        listEmployees.addAll(employeeCalls.getMiniEmployeeData());

    }


    public void setOrdersLeftSide(OrdersLeftSide ordersLeftSide){
        this.ordersLeftSide = ordersLeftSide;
    }

    public void setOrderConsumer(Consumer<Orders> ordersConsumer){
        this.ordersConsumer = ordersConsumer;
    }

    // =============== Right side =========================
    public VerticalLayout rightSideOrderInfo(){
        rightSide = new VerticalLayout();
        rightSide.setVisible(false);
        rightSide.addClassName("island-dis");
        rightSide.getStyle().set("position","relative");

        HorizontalLayout firstLayer = new HorizontalLayout();
        firstLayer.setWidthFull();
        firstLayer.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        status = commonComponents.spanCrafter("","stock-badge");

        HorizontalLayout secondLayer = new HorizontalLayout();
        secondLayer.setPadding(false);
        secondLayer.setWidthFull();
        secondLayer.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        Span orderId = commonComponents.spanCrafter("","activityFeed-name");

        VerticalLayout userData = new VerticalLayout();
        userData.addClassName("island");
        userData.setPadding(false);
        userData.setWidthFull();


        addressAuto = commonComponents.spanCrafter("none","stat-title");
        TextArea address = new TextArea("Address");
        address.setHeight("120px");
        address.setWidthFull();
        address.addValueChangeListener(e->{
            selectedOrder.setBillingAddress(e.getValue());
            addressAuto.setText(
                    selectedOrder.getBillingAddress() == null
                            ? "Buyer address: None"
                            : String.format("Buyer address: %s", selectedOrder.getBillingAddress())
            );
        });

        HorizontalLayout userSmallInfo = new HorizontalLayout();
        userSmallInfo.setPadding(false);
        userSmallInfo.setWidthFull();
        userSmallInfo.setJustifyContentMode(FlexComponent.JustifyContentMode.AROUND);




        HorizontalLayout thirdLayer = new HorizontalLayout();
        thirdLayer.setPadding(false);
        thirdLayer.setWidthFull();
        thirdLayer.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        Span created = commonComponents.spanCrafter("","stat-title");
        Span buyerName =  commonComponents.spanCrafterWordNoHide(String.format("%s %s", "Buyer name:" ,selectedOrder.getOrderPlacedBy() == null ? "None" : selectedOrder.getOrderPlacedBy().getName()),"stat-title");
        Span phoneNumber = commonComponents.spanCrafterWordNoHide(String.format("%s %s", "Buyer phone number:" ,selectedOrder.getPhoneNumber() == null ? "None" : selectedOrder.getPhoneNumber()),"stat-title");
        totalCost = commonComponents.spanCrafter("","activityFeed-name");




        // CONSUMMER

        ordersLeftSide.setConsumer(e->{

            rightSide.setVisible(true);
            rightSide.addClassName("island-dis");
            try {
                selectedOrder = orderCalls.getAnOrderFromId(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }

            String orderStatus = selectedOrder.getOrderStatus() !=null ? selectedOrder.getOrderStatus().getDisplayName() : "None";
            status.getStyle().set("padding","8px");
            status.setText(orderStatus);

            status.removeClassName("status-in-progress");
            status.removeClassName("status-pending");
            status.removeClassName("status-finished");
            status.removeClassName("status-none");

            if(selectedOrder.getOrderStatus() !=null) {
                switch (selectedOrder.getOrderStatus()) {
                    case In_Progress -> status.addClassName("status-in-progress");
                    case Pending -> status.addClassName("status-pending");
                    case Finished -> status.addClassName("status-finished");
                }
            }
            else{
                status.addClassName("status-none");
            }

            String id = selectedOrder.getId() !=null ? "ORD-" + selectedOrder.getId() : "ORD-NULL";


            orderId.setText(id);
            orderId.getStyle().set("color","blue");

            String createdDate = common.dateFormatter(selectedOrder.getCreated(),"MMMM d, yyyy ● h:mma");
            double costTotal = selectedOrder.getTotalPrice() == null ? 0.0 : selectedOrder.getTotalPrice();

            created.setText(String.format("%s - %s", "Order created",createdDate));
            totalCost.setText(String.format("%s %.2f %s","Total",costTotal,"Eur"));


            orderItemsHolder.removeAll();
            orderItemsHolder.setPadding(false);
            orderItemsHolder.setSpacing(false);
            orderItemsHolder.add(
                    orderAddProductListAddRemove.consumerOrderItems(selectedOrder)
            );


            timeLineHoder.removeAll();
            timeLineHoder.setPadding(false);
            timeLineHoder.setSpacing(false);
            timeLineHoder.add(
                    timeline(selectedOrder.getCreated(),selectedOrder.getEstimatedDueDate())
            );


            employeeHolder.removeAll();
            employeeHolder.setPadding(false);
            for(var s : selectedOrder.getEmployees()){
                employeeHolder.add(
                        assignEmployees.loadEmployees(
                                s.getEmployee().getId(),
                                s.getEmployee().getFullName(),
                                s.getEmployee().getEmployeeCategory(),
                                s.getEmployee().getProfileImage(),
                                selectedOrder
                                ));
            }


            // assign employee add remove
            employeeAssigmentHolder.removeAll();
            employeeAssigmentHolder.add(
                    assignEmployees.employeeAssignment(selectedOrder,employeeHolder)
            );


            noteHolder.removeAll();
            noteHolder.add(noteCrafter(selectedOrder.getOrderNote()));



             buyerName.setText(String.format("%s %s", "Buyer name:" ,selectedOrder.getOrderPlacedBy() == null ? "None" : selectedOrder.getOrderPlacedBy().getName()));
             phoneNumber.setText(String.format("%s %s", "Buyer phone number:" ,selectedOrder.getPhoneNumber() == null ? "None" : selectedOrder.getPhoneNumber()));
            addressAuto.setText(
                    selectedOrder.getBillingAddress() == null
                            ? "Buyer address: None"
                            : String.format("Buyer address: %s", selectedOrder.getBillingAddress())
            );
            address.setValue(selectedOrder.getBillingAddress() == null ? "None" : selectedOrder.getBillingAddress());

            payStatusComboBox.setValue(selectedOrder.getPayStatus() == null ? null : selectedOrder.getPayStatus());
            payMethodComboBox.setValue(selectedOrder.getPayMethod() == null ? null : selectedOrder.getPayMethod());
        });






        Button save = commonComponents.normalThemeButtonNoNavigate("Save",ButtonVariant.LUMO_PRIMARY);

        Button exit = commonComponents.buttonThemeAndIconNoNavigate("",ButtonVariant.ERROR,VaadinIcon.CLOSE,"Red");
        exit.getStyle().set("position","absolute").set("right","10px").set("top","10px");
        exit.addClickListener(e->{
           hideRightSide();
        });

        rightSide.add(exit);

        save.addClickListener(e->{
                ordersConsumer.accept(selectedOrder);
        });



        firstLayer.add(
                commonComponents.spanCrafterWordNoHide("Order details","activityFeed-name"),
                status
        );



        secondLayer.add(
                orderId
        );









        userSmallInfo.add(
                buyerName,
                phoneNumber,
                addressAuto

        );

        userData.add(
                commonComponents.spanCrafterWordNoHide("Buyer info","activityFeed-name"),
                userSmallInfo,
                address

        );




        thirdLayer.add(
                created,
                totalCost
        );








        rightSide.add(
                save,

                firstLayer,
                secondLayer,
                userData,
                thirdLayer,
                orderItemsHolder,
                timeLineHoder,
                employeeAssigmentHolder,
                noteHolder,
                orderStatusButtons(),
                actionButtons()

        );




        return rightSide;
    }

    public VerticalLayout orderListRight(List<OrderProducts> orderProducts, double totalCost){
        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();
        v.addClassName("island");

        HorizontalLayout firstLayer = new HorizontalLayout();
        firstLayer.setWidthFull();
        firstLayer.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        Span name = commonComponents.spanCrafterWordNoHide("Total","stat-example");
        total = commonComponents.spanCrafterWordNoHide("0.0","stat-example");

        if(orderProducts!=null){
            total.setText(String.format("%.2f %s",totalCost,"Eur"));
        }


        v.add(
                orderAddProductListAddRemove.consumerOrderItems(selectedOrder)
        );


        return  v;
    }

    public HorizontalLayout orderListItemsCraft(Long id ,Long amount, String productName, double cost){
        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        h.setHeight("50px");
        h.setAlignItems(FlexComponent.Alignment.CENTER);

        Button button = commonComponents.buttonThemeAndIconNoNavigate("", ButtonVariant.LUMO_ICON, VaadinIcon.TRASH,"Blue");
        button.setVisible(false);

        button.addClickListener(e->{
            selectedOrder.getProductsData().removeIf(product -> product.getId().equals(id));

            h.getParent().ifPresent(parent ->{
                ((HasComponents) parent).remove(h);
            });

            commonComponents.showNotification("Removed product " +  productName,3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.LUMO_SUCCESS);

            updateTotal();
        });

        h.getElement().addEventListener("mouseenter", e -> {
            if(!selectedOrder.getOrderStatus().equals(OrderStatus.Finished)){
                button.setVisible(true);
            }
        });


        h.getElement().addEventListener("mouseleave", e -> {
            if(!selectedOrder.getOrderStatus().equals(OrderStatus.Finished)){
                button.setVisible(false);
            }


        });

        Span name  = commonComponents.spanCrafterWordNoHide(String.format("%s %s","●",productName),"stat-title");

        IntegerField amountCounter = new IntegerField();
        amountCounter.setStep(1);
        amountCounter.setMax(100);
        amountCounter.setMin(1);
        amountCounter.setStepButtonsVisible(true);
        amountCounter.setValue(Math.toIntExact(amount == null ? 0 : amount));

        if(selectedOrder.getOrderStatus().equals(OrderStatus.Finished)){
            amountCounter.setReadOnly(true);
        }


        double estimatedCost = amount == null ? 0 : amount * cost;
        Span costOfProducts = commonComponents.spanCrafterWordNoHide(String.format("%.2f %s",estimatedCost,"Eur"),"stat-title");

        amountCounter.addValueChangeListener(e->{
           if(e.getValue() <= 0){
               commonComponents.showNotification("Cannot make a product quanitity less or equal than 0 ",3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.LUMO_ERROR);
               amountCounter.setValue(1);
           }
           else if(e.getValue() >100){
               commonComponents.showNotification("Cannot make product quantity more than 100 ",3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.LUMO_ERROR);
               amountCounter.setValue(1);
           }

           else{
               for(var s : selectedOrder.getProductsData()){
                   if(s.getId().equals(id)){
                       System.out.println(e.getValue());
                       s.setAmountOfProduct(e.getValue().longValue());
                   }
               }
           }

           double newCost = e.getValue() * cost;

           costOfProducts.setText(String.format("%.2f %s",newCost,"Eur"));


            updateTotal();

        });



        HorizontalLayout secondHolder = new HorizontalLayout();
        secondHolder.setAlignItems(FlexComponent.Alignment.CENTER);
        secondHolder.add(
                costOfProducts,
                button);

        h.add(
                name,
                amountCounter,
                secondHolder
        );

        return h;

    }

    public  VerticalLayout timeline(LocalDateTime created, LocalDateTime dueDate){

        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");
        v.setWidthFull();

        v.add(
                commonComponents.spanCrafter("Timeline","stat-example")
        );

        HorizontalLayout h = new HorizontalLayout();
        h.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        h.setWidthFull();


        VerticalLayout orderPlaced =new VerticalLayout();
        orderPlaced.setWidthFull();

        orderPlaced.add(
                commonComponents.spanCrafter("Order Placed","stat-title"),
                commonComponents.spanCrafter(common.dateFormatter(created,"MMMM d, yyyy ● h:mma"),"stat-title")

        );


        VerticalLayout orderDue =new VerticalLayout();
        orderDue.setWidthFull();


        dueDateForChange = commonComponents.spanCrafter(common.dateFormatter(dueDate,"MMMM d, yyyy ● h:mma"),"stat-title");
        Button editDate = commonComponents.buttonThemeAndIconNoNavigate("Edit", ButtonVariant.LUMO_ICON, VaadinIcon.PENCIL,"Blue");

        if(selectedOrder.getOrderStatus().equals(OrderStatus.Finished)){
            editDate.setEnabled(false);
        }

        editDate.addClickListener(e->{
            showEditDateDialog();
        });

        orderDue.add(
                commonComponents.spanCrafter("Due Date","stat-title"),
                dueDateForChange,
                editDate

        );



        h.add(
                orderPlaced,
                orderDue
        );

        v.add(h);



        return  v;
    }


    public Dialog showEditDateDialog(){

        Dialog dialog = new Dialog("Change Due Date");


        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();

        DateTimePicker datePicker = new DateTimePicker("Select date");
        datePicker.setValue(selectedOrder.getEstimatedDueDate());



        Button button = new Button("Change");
        button.addClickListener(e->{
            LocalDateTime newDate = datePicker.getValue();
            dueDateForChange.setText(common.dateFormatter(newDate,"MMMM d, yyyy ● h:mma"));
            selectedOrder.setEstimatedDueDate(newDate);
            dialog.close();
        });

        v.add(
                datePicker,
                button
        );

        dialog.add(
                v
        );


        dialog.open();

        return  dialog;

    }












    public VerticalLayout noteCrafter(String text){

        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();
        v.addClassName("island");


        TextArea note = new TextArea();
        note.setPlaceholder("Add special instructions or notes...");
        note.setWidthFull();
        note.setMinHeight("100px");

        if(text != null) {
            note.setValue(text);
        }
        if(selectedOrder.getOrderStatus().equals(OrderStatus.Finished)){
            note.setReadOnly(true);
        }
        note.addValueChangeListener(e->{
           selectedOrder.setOrderNote(note.getValue());


            System.out.println(selectedOrder.getOrderNote());
        });


        v.add(
                commonComponents.doubleValueRow(
                        commonComponents.spanCrafter("Add note","stat-example")
                        ,commonComponents.spanCrafter("(Optional)","stat-title")),
                note
        );



        return v;
    }


    public HorizontalLayout actionButtons(){

        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.addClassName("layout-flex");

        h.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        Button pending = commonComponents.buttonThemeAndIconNoNavigate("Mark as Pending", ButtonVariant.LUMO_ICON, VaadinIcon.CLOCK,"Blue");

        pending.addClickListener(e->{
           selectedOrder.setOrderStatus(OrderStatus.Pending);
           commonComponents.showNotification("Order was set to Pending",3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.LUMO_SUCCESS);
            changeStatusDisplay(OrderStatus.Pending);
        });

        Button inProgress = commonComponents.buttonThemeAndIconNoNavigate("Mark as In Progress", ButtonVariant.LUMO_PRIMARY, VaadinIcon.HOURGLASS_START,"White");

        inProgress.addClickListener(e->{
            selectedOrder.setOrderStatus(OrderStatus.In_Progress);
            commonComponents.showNotification("Order was set to In Progress",3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.LUMO_SUCCESS);
            changeStatusDisplay(OrderStatus.In_Progress);
        });

        Button finished = commonComponents.buttonThemeAndIconNoNavigate("Mark as Finished", ButtonVariant.LUMO_SUCCESS, VaadinIcon.CHECK,"green");

        finished.addClickListener(e->{
            selectedOrder.setOrderStatus(OrderStatus.Finished);
            commonComponents.showNotification("Order was set to Finished",3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.LUMO_SUCCESS);
            changeStatusDisplay(OrderStatus.Finished);
        });


        h.add(
                pending,
                inProgress,
                finished
        );


        return h;
    }


    public HorizontalLayout orderStatusButtons(){

        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.addClassName("layout-flex");

        payStatusComboBox = new ComboBox<>("Paid/Unpaid");
        payStatusComboBox.setItems(Arrays.stream(PayStatus.values()).filter(e-> e!= PayStatus.ALL).toList());


        payMethodComboBox = new ComboBox<>("Pay method");
        payMethodComboBox.setItems(Arrays.stream(PayMethod.values()).filter(e-> e != PayMethod.ALL).toList());

        payStatusComboBox.addValueChangeListener(e->{

            if(e.isFromClient()) {
                System.out.println(e.getValue());
                selectedOrder.setPayStatus(e.getValue());
                commonComponents.showNotification("Order was set as " + e.getValue(), 3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.LUMO_SUCCESS);
                System.out.println(selectedOrder.getPayStatus());
            }
        });

        payMethodComboBox.addValueChangeListener(e->{

            if(e.isFromClient()) {
                selectedOrder.setPayMethod(e.getValue());
                commonComponents.showNotification("Order was set as " + e.getValue(), 3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.LUMO_SUCCESS);
            }
        });


        h.add(
                payStatusComboBox,
                payMethodComboBox
        );


        return h;
    }

    public void changeStatusDisplay(OrderStatus orderStatus){

        status.setText(orderStatus.getDisplayName());

        status.removeClassName("status-in-progress");
        status.removeClassName("status-pending");
        status.removeClassName("status-finished");
        status.removeClassName("status-none");

        switch (selectedOrder.getOrderStatus()) {
            case In_Progress -> status.addClassName("status-in-progress");
            case Pending -> status.addClassName("status-pending");
            case Finished -> status.addClassName("status-finished");
        }
    }


    public void updateTotal(){
        double totalValue = 0.0;

        for(var s : selectedOrder.getProductsData()){
            totalValue += s.getAmountOfProduct() * s.getCost();
        }

        totalCost.setText(String.format("%s %.2f %s","Total",totalValue,"Eur"));
        total.setText(String.format("%.2f %s",totalValue,"Eur"));
    }




    public void hideRightSide() {
        rightSide.removeClassName("island-dis");
       rightSide.setVisible(false);
    }

}
