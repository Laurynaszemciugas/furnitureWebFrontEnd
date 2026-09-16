package com.example.demo.Pages.EmployeePage.PreviewPage;


import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.ImageViewer;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.EmployeePage.Components.PageDesc;
import com.example.demo.Pages.EmployeePage.PreviewPage.Components.ActiveOrderUI;
import com.example.demo.Pages.EmployeePage.PreviewPage.Components.OrderDashboardMiniStat;
import com.example.demo.Pages.EmployeePage.PreviewPage.Components.ProductInTheOrderUI;
import com.example.demo.Services.Orders.OrdersService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "OrderActive/:id", layout = MainLayout.class)
public class OrderActivePage extends VerticalLayout implements BeforeEnterObserver {


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


    ActiveOrderUI activeOrderUI;


    public OrderActivePage(CommonComponents commonComponents, Common common, OrdersService ordersService, ImageViewer imageViewer) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.ordersService = ordersService;
        this.imageViewer = imageViewer;
        this.orderMiniStat = new OrderDashboardMiniStat(commonComponents,common,ordersService);

        this.pageDesc = new PageDesc(commonComponents,common);
        this.productInTheOrderComp = new ProductInTheOrderUI(commonComponents,common);


        this.activeOrderUI = new ActiveOrderUI(commonComponents,common,ordersService);


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


        VerticalLayout one = new VerticalLayout();
        one.setPadding(false);

        one.add(
                pageDesc.orderPreviewDesc(),
                activeOrderUI.orderName(currentOrder)
        );

        VerticalLayout two = new VerticalLayout();
        two.setPadding(false);

        two.add(
                orderMiniStat.miniStat(currentOrder)
        );

        VerticalLayout three = new VerticalLayout();
        three.setPadding(false);

        three.add(
                activeOrderUI.activeOrders(currentOrder)
        );


        verticalLayout.add(

                one,
                two,
                three



        );





        activeOrderUI.setReloadOutSide(e->{


            two.removeAll();


            Orders newOrder = ordersService.getSelectedOrder(Long.valueOf(orderId));

            two.add(


                    orderMiniStat.miniStat(newOrder)


            );
        });

        return verticalLayout;
    }






}
