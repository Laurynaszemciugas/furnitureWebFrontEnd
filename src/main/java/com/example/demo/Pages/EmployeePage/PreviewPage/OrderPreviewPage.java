package com.example.demo.Pages.EmployeePage.PreviewPage;


import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.ImageViewer;
import com.example.demo.Entity.Orders;
import com.example.demo.MainLayouts.Admin.MainLayout;
import com.example.demo.Pages.EmployeePage.Components.PageDesc;
import com.example.demo.Pages.EmployeePage.PreviewPage.Components.OrderDashboardMiniStat;
import com.example.demo.Pages.EmployeePage.PreviewPage.Components.ProductInTheOrderUI;
import com.example.demo.Services.Orders.OrdersService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "OrderPreview/:id")
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

    ProductInTheOrderUI productInTheOrderComp;


    public OrderPreviewPage(CommonComponents commonComponents, Common common, OrdersService ordersService, ImageViewer imageViewer) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.ordersService = ordersService;
        this.imageViewer = imageViewer;
        this.orderMiniStat = new OrderDashboardMiniStat(commonComponents,common,ordersService);

        this.pageDesc = new PageDesc(commonComponents,common);
        this.productInTheOrderComp = new ProductInTheOrderUI(commonComponents,common);



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
