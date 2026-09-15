package com.example.demo.Pages.EmployeePage.PreviewPage;


import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.ImageViewer;
import com.example.demo.ControllerModels.CommonDtos.OrderJoin.OrderProducts;
import com.example.demo.ControllerModels.CommonDtos.OrderJoin.OrderStepsToComplete;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.ControllerModels.CommonDtos.ProductJoin.ProductMaterials;
import com.example.demo.Enums.ImageLogic;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.EmployeePage.Components.PageDesc;
import com.example.demo.Pages.EmployeePage.PreviewPage.Components.OrderDashboardMiniStat;
import com.example.demo.Pages.EmployeePage.PreviewPage.Components.ProductInTheOrderComp;
import com.example.demo.Services.Orders.OrdersService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "OrderPreview/:id", layout = MainLayout.class)
public class OrderPreviewPage extends VerticalLayout implements BeforeEnterObserver {


    CommonComponents commonComponents;
    Common common;
    OrdersService ordersService;
    ImageViewer imageViewer;

    OrderDashboardMiniStat orderMiniStat;

    int orderId;


    Orders currentOrder = new Orders();


    // stuff for the page

    PageDesc pageDesc;

    ProductInTheOrderComp productInTheOrderComp;

    public OrderPreviewPage(CommonComponents commonComponents, Common common, OrdersService ordersService, ImageViewer imageViewer) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.ordersService = ordersService;
        this.imageViewer = imageViewer;
        this.orderMiniStat = new OrderDashboardMiniStat(commonComponents,common,ordersService);

        this.pageDesc = new PageDesc(commonComponents,common);
        this.productInTheOrderComp = new ProductInTheOrderComp(commonComponents,common);




        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(Alignment.CENTER);




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

                pageDesc.orderPreviewDesc(),
                productInTheOrderComp.orderName(currentOrder),
                orderMiniStat.miniStat(currentOrder),

                productInTheOrderComp.productsInOrder(currentOrder)

        );

        return verticalLayout;
    }






}
