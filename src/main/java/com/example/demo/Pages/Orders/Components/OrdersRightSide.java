package com.example.demo.Pages.Orders.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.CommonDtos.Employee;
import com.example.demo.ControllerModels.CommonDtos.EmployeeJoin.OrderEmployees;
import com.example.demo.ControllerModels.CommonDtos.OrderJoin.OrderProducts;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.DTOS.ComboBoxEmployees;
import com.example.demo.Enums.EmployeeCategory;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.ServerDBCall.EmployeeCalls.EmployeeCalls;
import com.example.demo.ServerDBCall.OrderCalls.OrderCalls;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import lombok.SneakyThrows;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class OrdersRightSide {

    CommonComponents commonComponents;
    Common common;
    OrderCalls orderCalls;
    EmployeeCalls employeeCalls;


    // refrence to the leftside component
    OrdersLeftSide ordersLeftSide;


    Consumer<Long> consumer;
    Orders selectedOrder = new Orders();
    VerticalLayout orderItemsHolder = new VerticalLayout();
    VerticalLayout timeLineHoder = new VerticalLayout();
    VerticalLayout employeeHolder = new VerticalLayout();
    VerticalLayout noteHolder = new VerticalLayout();
    VerticalLayout employeeAssigmentHolder = new VerticalLayout();

    Span dueDateForChange;

    List<ComboBoxEmployees> listEmployees = new ArrayList<>();

    Long employeeId;
    String employeeName;
    EmployeeCategory employeeCategoryNew;
    String employeeProfilePic;

    @SneakyThrows
    public OrdersRightSide(CommonComponents commonComponents, Common common, OrderCalls orderCalls, EmployeeCalls employeeCalls) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.orderCalls = orderCalls;
        this.employeeCalls = employeeCalls;


        listEmployees.addAll(employeeCalls.getMiniEmployeeData());

    }


    public void setOrdersLeftSide(OrdersLeftSide ordersLeftSide){
        this.ordersLeftSide = ordersLeftSide;
    }


    // =============== Right side =========================
    public VerticalLayout rightSideOrderInfo(){
        VerticalLayout rightSide = new VerticalLayout();
        rightSide.setVisible(false);


        HorizontalLayout firstLayer = new HorizontalLayout();
        firstLayer.setWidthFull();
        firstLayer.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        Span status = commonComponents.spanCrafter("","stock-badge");

        HorizontalLayout secondLayer = new HorizontalLayout();
        secondLayer.setPadding(false);
        secondLayer.setWidthFull();
        secondLayer.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        Span orderId = commonComponents.spanCrafter("","activityFeed-name");
        Span itemCount = commonComponents.spanCrafter("","stat-title");
        Span totalCount = commonComponents.spanCrafter("","stat-title");

        HorizontalLayout thirdLayer = new HorizontalLayout();
        thirdLayer.setPadding(false);
        thirdLayer.setWidthFull();
        thirdLayer.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        Span created = commonComponents.spanCrafter("","stat-title");
        Span totalCost = commonComponents.spanCrafter("","activityFeed-name");



        ordersLeftSide.setConsumer(e->{
            rightSide.setVisible(true);
            rightSide.addClassName("island");
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
            int size = selectedOrder.getProductsData().size();
            int totalProductCount = 0;
            for(var s : selectedOrder.getProductsData()) {
                totalProductCount += s.getAmountOfProduct();
            }
            String items = size != 0 ? String.format("%s: %d","Product count", totalProductCount) : "No products";
            String uniqueProducts = size != 0 ? String.format("%s: %d","Unique product count", size) : "No unique products";

            orderId.setText(id);
            itemCount.setText(items);
            totalCount.setText(uniqueProducts);


            String createdDate = common.dateFormatter(selectedOrder.getCreated(),"MMMM d, yyyy ● h:mma");
            double costTotal = selectedOrder.getTotalPrice();

            created.setText(String.format("%s - %s", "Order created",createdDate));
            totalCost.setText(String.format("%s %.2f %s","Total",costTotal,"Eur"));


            orderItemsHolder.removeAll();
            orderItemsHolder.setPadding(false);
            orderItemsHolder.setSpacing(false);
            orderItemsHolder.add(
                    orderListRight(selectedOrder.getProductsData(), selectedOrder.getTotalPrice())
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
                        loadEmployees(s.getId(),
                                s.getEmployee().getFullName(),
                                s.getEmployee().getEmployeeCategory(),
                                s.getEmployee().getProfileImage())
                );
            }

            employeeAssigmentHolder.removeAll();
            employeeAssigmentHolder.add(
                    employeeAssignment()
            );


            noteHolder.removeAll();
            noteHolder.add(noteCrafter(selectedOrder.getOrderNote()));



        });









        firstLayer.add(
                commonComponents.spanCrafterWordNoHide("Order details","activityFeed-name"),
                status
        );

        secondLayer.add(
                orderId,
                itemCount,
                totalCount
        );
        thirdLayer.add(
                created,
                totalCost
        );








        rightSide.add(
                firstLayer,
                secondLayer,
                thirdLayer,
                orderItemsHolder,
                timeLineHoder,
                employeeAssigmentHolder,
                noteHolder

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
        Span total = commonComponents.spanCrafterWordNoHide("0.0","stat-example");

        if(orderProducts!=null){
            total.setText(String.format("%.2f %s",totalCost,"Eur"));
        }

        firstLayer.add(
                name,
                total);




        v.add(
                commonComponents.spanCrafter("Order items","stat-example")
        );

        for (var s : orderProducts) {
            v.add(
                    orderListItemsCraft(s.getAmountOfProduct(), s.getProduct().getProductName(), s.getCost())
            );
        }

        v.add(
                firstLayer
        );


        return  v;
    }

    public HorizontalLayout orderListItemsCraft(Long amount, String productName, double cost){
        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        Span first = commonComponents.spanCrafterWordNoHide(String.format("%s %d %s","●",amount,productName),"stat-title");
        Span second = commonComponents.spanCrafterWordNoHide(String.format("%.2f %s",cost,"Eur"),"stat-title");

        h.add(
                first,
                second
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

    public VerticalLayout employeeAssignment(){

        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();

        HorizontalLayout employeeLayer = new HorizontalLayout();
        employeeLayer.setWidthFull();
        employeeLayer.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        Button addEmployee = commonComponents.buttonThemeAndIconNoNavigate("Add employee", ButtonVariant.LUMO_PRIMARY, VaadinIcon.PLUS,"White");

        if(selectedOrder.getOrderStatus().equals(OrderStatus.Finished)){
            addEmployee.setEnabled(false);
        }

        addEmployee.addClickListener(e->{
            showEmployeeDialog();
        });

        employeeLayer.add(
                commonComponents.spanCrafterWordNoHide("Assign Employees","activityFeed-name"),
                addEmployee

        );

        v.add(
                employeeLayer,
                employeeHolder
        );







        return  v;

    }



    public HorizontalLayout loadEmployees(Long id, String name, EmployeeCategory employeeCategory, String profilePic){

        HorizontalLayout h = new HorizontalLayout();
        h.setAlignItems(FlexComponent.Alignment.CENTER);
        h.addClassName("island");
        h.setWidthFull();

        Button button = commonComponents.buttonThemeAndIconNoNavigate("", ButtonVariant.LUMO_ICON, VaadinIcon.TRASH,"Blue");
        button.setVisible(false);

        if(selectedOrder.getOrderStatus().equals(OrderStatus.Finished)){
            button.setEnabled(false);
        }

        h.getElement().addEventListener("mouseenter", e -> {
            button.setVisible(true);
        });


        h.getElement().addEventListener("mouseleave", e -> {
            button.setVisible(false);

        });

        button.addClickListener(e->{
            h.getParent().ifPresent(parent ->{
                ((HasComponents) parent).remove(h);
            });

                selectedOrder.getEmployees().removeIf(emp -> emp.getEmployee().getId().equals(id));



        });

        String url = "";
        if(profilePic != null){
            url = profilePic;
        }
        else{
            url = "No_picture.png";
        }
        Image image = commonComponents.imageCrafter(url,"50px","50px","20px");
        Span fullName = commonComponents.spanCrafterWordNoHide(name,"stat-example");
        Span role = commonComponents.spanCrafterWordNoHide(employeeCategory.toString(),"stat-title");

        h.add(
                image,
                fullName,
                commonComponents.spaceFiller(),
                role,
                button
        );


        return h;
    }



    public Dialog showEmployeeDialog(){

        Dialog dialog = new Dialog("Select employee ");


        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();

        HorizontalLayout empDisplay = new HorizontalLayout();

        ComboBox<ComboBoxEmployees> comboBox = new ComboBox<>("Select employee");
        comboBox.setItems(listEmployees);
        comboBox.setItemLabelGenerator(ComboBoxEmployees::getFullName);
        Button button = new Button("Add");

        comboBox.addValueChangeListener(e->{
            employeeId = e.getValue().getId();
            employeeName = e.getValue().getFullName();
            employeeCategoryNew = e.getValue().getEmployeeCategory();
            employeeProfilePic = e.getValue().getProfileImage();

            empDisplay.removeAll();

            Span empID = new Span(employeeId.toString());

            empDisplay.add(
                    empID
            );

            v.removeAll();
            v.add(
                    comboBox,
                    empDisplay,
                    button
            );

        });


        button.addClickListener(e->{

            employeeHolder.add(
                    loadEmployees(
                            employeeId,
                            employeeName,
                            employeeCategoryNew,
                            employeeProfilePic)
            );

            OrderEmployees employees = new OrderEmployees();
            employees.setId(employeeId);

            Employee employee = new Employee();
            employee.setId(employeeId);


            employees.setEmployee(employee);

            selectedOrder.getEmployees().add(employees);


            dialog.close();
        });

        v.add(
                comboBox,
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
            note.setEnabled(false);
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





}
