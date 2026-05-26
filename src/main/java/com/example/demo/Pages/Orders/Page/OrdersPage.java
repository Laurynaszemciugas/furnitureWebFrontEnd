package com.example.demo.Pages.Orders.Page;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Paganation;
import com.example.demo.ControllerModels.CommonDtos.OrderJoin.OrderProducts;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.ControllerModels.CommonDtos.Product;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.ServerDBCall.OrderCalls.OrderCalls;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
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
    Paganation paganation;
    OrderCalls orderCalls;


    Consumer<Long> consumer;
    Orders selectedOrder = new Orders();

    public void setConsumer(Consumer<Long> consumer){
        this.consumer = consumer;
    }

    public OrdersPage(CommonComponents commonComponents, Common common, OrderCalls orderCalls) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.orderCalls = orderCalls;
        this.paganation = new Paganation();
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

        return verticalLayout;
    }


    public VerticalLayout leftSideTheListOfOrders(){
        VerticalLayout leftSide = new VerticalLayout();
        leftSide.addClassName("island");

        HorizontalLayout nameOfGrids = new HorizontalLayout();
        nameOfGrids.addClassName("layout-flex");
        nameOfGrids.setWidthFull();

        TextField searchField = commonComponents.textFieldCrafter("Search orders..","", VaadinIcon.SEARCH);
        Button showMoreFilters = new Button(commonComponents.iconCrafter(VaadinIcon.FILTER,"30px","grey"));
        showMoreFilters.addClassName("transparent-button");

        nameOfGrids.add(
                commonComponents.spanCrafterWordNoHide("Orders List","activityFeed-name"),
                commonComponents.spaceFiller(),
                searchField,
                showMoreFilters
        );


        Button allButton = commonComponents.normalButtonNoNavigate("All", "transparent-button");
        allButton.addClassName("active");
        Button pendingButton = commonComponents.normalButtonNoNavigate("Pending", "transparent-button");
        Button inProgressButton = commonComponents.normalButtonNoNavigate("In Progress", "transparent-button");
        Button finishedButton = commonComponents.normalButtonNoNavigate("Finished", "transparent-button");

        List<Button> buttons = List.of(allButton,pendingButton,inProgressButton,finishedButton);

        for(var s : buttons){

            s.addClickListener(e->{
                buttons.forEach(b-> b.removeClassName("active"));
                s.addClassName("active");
            });

        }




        HorizontalLayout buttonsHolder = new HorizontalLayout();
        buttonsHolder.add(
                allButton,
                pendingButton,
                inProgressButton,
                finishedButton

        );

        leftSide.add(
                nameOfGrids,
                buttonsHolder,
                orderFeedHolder(),
                paganation.buttonHolder(5)

        );


        return leftSide;
    }


    @SneakyThrows
    public Scroller orderFeedHolder(){

        VerticalLayout feed = new VerticalLayout();
        feed.setWidthFull();

        List<Orders> orders = orderCalls.getAllOders();

        for(var s : orders){
            feed.add(createOrderPreview(s.getId(),s.getOrderStatus(),s.getProductsData(), s.getEstimatedDueDate(),s.getCreated()));
        }


        Scroller scroller = new Scroller(feed);
        scroller.setMaxHeight("800px");
        scroller.setWidthFull();


        return scroller;

    }

    public VerticalLayout createOrderPreview(Long orderId, OrderStatus orderStatus, List<OrderProducts> products, LocalDateTime dueDate, LocalDateTime created){
        VerticalLayout preview = new VerticalLayout();
        preview.getStyle()
                .set("padding-left","20px")
                .set("padding-right","40px")
                .set("position","relative");
        preview.addClassName("island-hover");
        preview.setWidthFull();



        // ====================== first layer =====================================

        Span status = commonComponents.spanCrafter(orderStatus.getDisplayName(),"stock-badge");
        status.getStyle().set("padding","8px");

        switch (orderStatus){
            case In_Progress -> status.addClassName("status-in-progress");
            case Pending -> status.addClassName("status-pending");
            case Finished -> status.addClassName("status-finished");
        }

        HorizontalLayout firstLayer = new HorizontalLayout();
        firstLayer.addClassName("layout-flex");
        firstLayer.setJustifyContentMode(JustifyContentMode.BETWEEN);
        firstLayer.setAlignItems(Alignment.CENTER);
        firstLayer.setWidthFull();
        firstLayer.add(
                commonComponents.spanCrafter(String.format("%s-%d", "ORD", orderId),"stat-blue"),
                status
        );

        // ====================== second layer =====================================

        ComboBox<String> productNames = new ComboBox<>();
        for(var s : products){
            productNames.getListDataView().addItem(s.getProduct().getProductName());
        }
        if(products.isEmpty()){
            productNames.setItems("No Items");
            productNames.setValue("No Items");
           productNames.setEnabled(false);

        }
        else{
            int productCount = products.size();;
            productNames.setPlaceholder(String.format("%d %s",productCount,"Items"));
        }

        HorizontalLayout secondLayer = new HorizontalLayout();
        secondLayer.addClassName("layout-flex");
        secondLayer.setJustifyContentMode(JustifyContentMode.BETWEEN);
        secondLayer.setAlignItems(Alignment.CENTER);
        secondLayer.setWidthFull();
        secondLayer.add(
                productNames,
                commonComponents.spanCrafter(String.format("%s: %s","Created date",dateFormatter(created,"MMMM d, yyyy ● h:mma")),"stat-title"),
                commonComponents.spanCrafter(String.format("%s: %s","Due date",dateFormatter(dueDate,"MMMM d, yyyy ● h:mma")),"stat-title")

        );

        Icon icon = VaadinIcon.ANGLE_RIGHT.create();
        icon.setColor("grey");
        icon.setSize("25px");
        icon.getStyle().set("position","absolute").set("right","5px").set("bottom","45%");

        preview.add(
                firstLayer,
                secondLayer,
                icon
        );


        preview.addClickListener(e->{
            System.out.println(orderId);
            consumer.accept(orderId);
        });


        return preview;
    }

    public String dateFormatter(LocalDateTime localDateTime, String format){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(dateTimeFormatter);
    }



    // =============== Right side =========================
    public VerticalLayout rightSideOrderInfo(){
        VerticalLayout rightSide = new VerticalLayout();
        rightSide.setVisible(false);


        HorizontalLayout firstLayer = new HorizontalLayout();
        firstLayer.setWidthFull();
        firstLayer.setJustifyContentMode(JustifyContentMode.BETWEEN);

        Span status = commonComponents.spanCrafter("","stock-badge");

        HorizontalLayout secondLayer = new HorizontalLayout();
        secondLayer.setWidthFull();
        secondLayer.setJustifyContentMode(JustifyContentMode.BETWEEN);

        Span orderId = commonComponents.spanCrafter("","activityFeed-name");
        Span itemCount = commonComponents.spanCrafter("","stat-title");


        setConsumer(e->{
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
            String items = size != 0 ? String.valueOf(size) : "No products";

            orderId.setText(id);
            itemCount.setText(items);




        });









        firstLayer.add(
                commonComponents.spanCrafterWordNoHide("Order details","activityFeed-name"),
                status
        );

        secondLayer.add(
                orderId,
                itemCount
        );







        rightSide.add(
                firstLayer,
                secondLayer
        );




        return rightSide;
    }

    public HorizontalLayout leftAndRightSideHolder(){
        HorizontalLayout sidesHolder = new HorizontalLayout();
        sidesHolder.setWidthFull();
        sidesHolder.addClassName("layout-flex");

        VerticalLayout left = leftSideTheListOfOrders();
        VerticalLayout right = rightSideOrderInfo();

        // LEFT SIDE
        left.setWidth("740px");

        // RIGHT SIDE
        right.setMaxWidth("700px");


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











