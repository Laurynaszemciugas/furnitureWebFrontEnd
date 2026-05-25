package com.example.demo.Pages.Orders.Page;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Paganation;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.ControllerModels.CommonDtos.Product;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.ServerDBCall.OrderCalls.OrderCalls;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


@Route(value = "Orders", layout = MainLayout.class)
public class OrdersPage extends VerticalLayout implements BeforeEnterObserver {

    CommonComponents commonComponents;
    Common common;
    Paganation paganation;
    OrderCalls orderCalls;

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
            feed.add(createOrderPreview(s.getId(),s.getOrderStatus()));
        }


        Scroller scroller = new Scroller(feed);
        scroller.setMaxHeight("800px");
        scroller.setWidthFull();


        return scroller;

    }

    public VerticalLayout createOrderPreview(Long orderId, OrderStatus orderStatus){
        VerticalLayout preview = new VerticalLayout();
        preview.getStyle()
                .set("padding-left","40px")
                .set("padding-right","40px");
        preview.setWidthFull();
        preview.addClassName("island");
        preview.setHeight("120px");

        Span status = commonComponents.spanCrafterWordNoHide(orderStatus.toString(),"stock-badge");

        switch (orderStatus){
            case In_Progress -> status.addClassName("status-in-progress");
            case Pending -> status.addClassName("status-pending");
            case Finished -> status.addClassName("status-finished");
        }

        HorizontalLayout firstLayer = new HorizontalLayout();

        firstLayer.setJustifyContentMode(JustifyContentMode.BETWEEN);
        firstLayer.setWidthFull();
        firstLayer.add(
                commonComponents.spanCrafterWordNoHide(String.format("%s: %d", "Id", orderId),"stat-example"),
                status
        );

        preview.add(
                firstLayer
        );




        return preview;
    }



    // =============== Right side =========================
    public VerticalLayout rightSideOrderInfo(){
        VerticalLayout RightSide = new VerticalLayout();
        RightSide.addClassName("island");


        return RightSide;
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











