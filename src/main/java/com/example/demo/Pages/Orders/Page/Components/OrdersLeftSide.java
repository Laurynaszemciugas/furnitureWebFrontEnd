package com.example.demo.Pages.Orders.Page.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Orders.OrdersFeedData;
import com.example.demo.Enums.OrderStatus;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

public class OrdersLeftSide {


    CommonComponents commonComponents;
    Common common;



    Consumer<Long> consumer;
    public void setConsumer(Consumer<Long> consumer){
        this.consumer = consumer;
    }

    public OrdersLeftSide(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;


    }




    @SneakyThrows
    public VerticalLayout orderFeedHolder(List<OrdersFeedData> ordersFeedData){

        VerticalLayout feed = new VerticalLayout();
        feed.setPadding(false);
        feed.setWidthFull();
        feed.addClassName("smooth-panel");



        if(ordersFeedData != null && !ordersFeedData.isEmpty()) {
            for (var s : ordersFeedData) {
                feed.add(createOrderPreview(s.getId(), s.getOrderStatus(), s.getProductCount(), s.getEstimatedDueDate(), s.getCreated(),s.getTotalPrice(), s.getAddress(), s.getGmail()));
            }
        }
        else{
            feed.add(commonComponents.noDataFound());

        }


//        Scroller scroller = new Scroller(feed);
//        scroller.setMaxHeight("800px");
//        scroller.setWidthFull();


        return feed;

    }

    public VerticalLayout createOrderPreview(Long orderId, OrderStatus orderStatus, Long products, LocalDateTime dueDate, LocalDateTime created,Double price, String address, String gmail){
        VerticalLayout preview = new VerticalLayout();
        preview.setPadding(false);

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
        firstLayer.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        firstLayer.setAlignItems(FlexComponent.Alignment.CENTER);
        firstLayer.setWidthFull();
        firstLayer.add(
                commonComponents.spanCrafter(String.format("%s-%d", "ORD", orderId),"stat-blue"),
                status
        );

        // ====================== second layer =====================================

        TextField productNames = new TextField();
        productNames.setReadOnly(true);
        Long number = 0l;
        if(products != null){
            number = products;
        }
        productNames.setValue(String.format("%d %s",number,"Items"));


        HorizontalLayout secondLayer = new HorizontalLayout();
        secondLayer.addClassName("layout-flex");
        secondLayer.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        secondLayer.setAlignItems(FlexComponent.Alignment.CENTER);
        secondLayer.setWidthFull();
        secondLayer.add(
                productNames,
                commonComponents.spanCrafter(String.format("%s: %s","Created date",common.dateFormatter(created,"MMMM d, yyyy ● h:mma")),"stat-title"),
                commonComponents.spanCrafter(String.format("%s: %s","Due date",common.dateFormatter(dueDate,"MMMM d, yyyy ● h:mma")),"stat-title"),
                commonComponents.spanCrafter(String.format("%.2f %s",price,"Eur"),"stat-example")

        );


        HorizontalLayout thirdLayer = new HorizontalLayout();
        thirdLayer.addClassName("layout-flex");
        thirdLayer.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        thirdLayer.setAlignItems(FlexComponent.Alignment.CENTER);
        thirdLayer.setWidthFull();
        thirdLayer.add(
                commonComponents.spanCrafter(String.format("Address: %s", address),"stat-title"),
                commonComponents.spanCrafter(String.format("gmail: %s", gmail),"stat-title")
        );





        Icon icon = VaadinIcon.ANGLE_RIGHT.create();
        icon.setColor("grey");
        icon.setSize("25px");
        icon.getStyle().set("position","absolute").set("right","5px").set("bottom","45%");

        preview.add(
                firstLayer,
                secondLayer,
                thirdLayer,
                icon
        );


        preview.addClickListener(e->{
            consumer.accept(orderId);
        });


        return preview;
    }





}
