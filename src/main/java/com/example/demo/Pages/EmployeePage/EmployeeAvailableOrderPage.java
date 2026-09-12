package com.example.demo.Pages.EmployeePage;


import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.ImageViewer;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.EmployeePage.Components.OrderMiniStat;
import com.example.demo.Services.Orders.OrdersService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Route(value = "EmployeeAvailableOrder/:id", layout = MainLayout.class)
public class EmployeeAvailableOrderPage extends VerticalLayout implements BeforeEnterObserver {


    CommonComponents commonComponents;
    Common common;
    OrdersService ordersService;
    ImageViewer imageViewer;

    OrderMiniStat orderMiniStat;

    int orderId;


    Orders currentOrder = new Orders();

    public EmployeeAvailableOrderPage(CommonComponents commonComponents, Common common,OrdersService ordersService,ImageViewer imageViewer) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.ordersService = ordersService;
        this.imageViewer = imageViewer;
        this.orderMiniStat = new OrderMiniStat(commonComponents,common,ordersService);




        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);




        addClassName("animation-page");

    }



    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        removeAll();

        int id = Integer.parseInt(beforeEnterEvent.getRouteParameters().get("id").orElse(null));

        this.orderId = id;

        currentOrder = ordersService.getSelectedOrder(Long.valueOf(id));





        add(mainLayout());

    }

    public VerticalLayout mainLayout() {

        VerticalLayout verticalLayout = new VerticalLayout();


        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");


        verticalLayout.add(

                firstLayer(),
                orderName(),
                orderMiniStat.miniStat(currentOrder),

                test(currentOrder)

        );

        return verticalLayout;
    }

    public HorizontalLayout firstLayer(){
        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.setAlignItems(Alignment.CENTER);

        Button back = new Button("Back to orders");
        back.setPrefixComponent(commonComponents.iconCrafter(VaadinIcon.ANGLE_LEFT,"25px","blue"));

        h.add(
                back
        );

        return h;
    }

    public VerticalLayout orderName(){
        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();
        v.setSpacing(false);

        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.setAlignItems(Alignment.CENTER);
        h.setPadding(false);

        Span status = new Span(currentOrder.getOrderStatus().getDisplayName());
        status.getStyle().set("width", "fit-content");
        status.addClassName("stock-badge");



        switch (currentOrder.getOrderStatus()){
            case Finished -> status.addClassName("stock-in");
            case In_Progress -> status.addClassName("status-in-progress");
            case CANCELLED -> status.addClassName("stock-out");
            case Pending -> status.addClassName("status-pending");
            default -> status.addClassName("status-none");

        }

        h.add(
                commonComponents.spanCrafter(String.format("Order #%d",currentOrder.getId()),"stat-value"),
                status
        );

        v.add(
                h,
                commonComponents.spanCrafter("Some name","stat-description")
        );



        return v;
    }

    public VerticalLayout test(Orders orders){
        VerticalLayout v = new VerticalLayout();

        for(var products : orders.getProductsData()){
            v.add(
                    new Span(products.getProduct().getProductName())
            );

            for(var steps : products.getOrderSteps()){
                v.add(
                        new Span(steps.getStepDescription())
                );
            }

        }



        return v;
    }



}
