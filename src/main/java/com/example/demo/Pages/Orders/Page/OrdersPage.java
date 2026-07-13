package com.example.demo.Pages.Orders.Page;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.CurrentFilterDisplay;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.Common.Paganation;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.ControllerModels.Filter.Order.OrderFilterHolder;
import com.example.demo.ControllerModels.Material.MaterialBriefDto;
import com.example.demo.ControllerModels.Orders.NewOrderFeedData;
import com.example.demo.ControllerModels.Orders.OrdersFeedData;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Orders.Page.Components.*;
import com.example.demo.Services.EmployeeService.EmployeeService;
import com.example.demo.Services.Orders.OrdersService;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.springframework.core.annotation.Order;

import javax.swing.plaf.PanelUI;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Route(value = "Orders", layout = MainLayout.class)
public class OrdersPage extends VerticalLayout implements BeforeEnterObserver {

    CommonComponents commonComponents;
    Common common;
    EmployeeService employeeService;
    Paganation paganation;

    OrdersRightSide ordersRightSide;
    OrdersLeftSide ordersLeftSide;
    OrderFilters orderFilters;
    BriefOrderPageExplanation briefOrderPageExplanation;
    OrderMiniStats orderMiniStats;

    OrdersService ordersService;

    VerticalLayout filterMemory = new VerticalLayout();
    ProductService productService;

    SessionCrafter sessionCrafter;

    // filter data

    OrderFilterHolder filterData = new OrderFilterHolder();

    VerticalLayout verticalLayout;

    CurrentFilterDisplay currentFilterDisplay;

    Div feedHolder = new Div();
    HorizontalLayout reviewLeftRightSide = new HorizontalLayout();


    Orders newSelectedOrder = new Orders();


    public OrdersPage(CommonComponents commonComponents, Common common, EmployeeService employeeService, OrdersService ordersService, ProductService productService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.ordersService = ordersService;

        this.productService = productService;
        this.ordersRightSide = new OrdersRightSide(commonComponents,common,employeeService,productService,ordersService);
        this.ordersLeftSide = new OrdersLeftSide(commonComponents,common);
        this.orderFilters = new OrderFilters(commonComponents, common,employeeService,productService);
        this.briefOrderPageExplanation = new BriefOrderPageExplanation(commonComponents,common);
        this.paganation = new Paganation();
        this.orderMiniStats = new OrderMiniStats(commonComponents,common);
        this.sessionCrafter = new SessionCrafter();


        this.ordersRightSide.setOrdersLeftSide(ordersLeftSide);

        this.currentFilterDisplay = new CurrentFilterDisplay(commonComponents,common);
        this.orderFilters.setCurrentFilterDisplay(currentFilterDisplay);

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);


        modifyRequests();


        filterMemory.setPadding(false);
        filterMemory.setWidthFull();
        feedHolder.setWidthFull();
        feedHolder.setWidthFull();




        addClassName("animation-page");

        reviewLeftRightSide.setVisible(false);



    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        removeAll();


        add(mainLayout());

    }




    public VerticalLayout mainLayout() {
        verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");

        addUIData();

        ordersService.setSuccess(e->{
            ordersRightSide.hideRightSide();
            setNewPage();
            updateFeed();
        });


        // get info that user tries to save order

        ordersRightSide.setOrderConsumer(e->{
            ordersService.saveEditedData(e);


        });



        return verticalLayout;
    }



    public void modifyRequests(){


        orderFilters.setOrderStatusConsumer(e->{
            filterData.setOrderStatusChoice(e);
            setNewPage();
            updateFeed();


        });

        orderFilters.setFromDateConsumer(e->{
            filterData.setDateFromChoice(e);
            setNewPage();
            updateFeed();
        });

        orderFilters.setToDateConsumer(e->{
            filterData.setDateToChoice(e);
            setNewPage();
            updateFeed();
        });
        orderFilters.setFromCostConsumer(e->{
            filterData.setPriceFromChoice(e);
            setNewPage();
            updateFeed();
        });
        orderFilters.setToCostConsumer(e->{
            filterData.setPriceToChoice(e);
            setNewPage();
            updateFeed();
        });

        orderFilters.setPrompt(e->{
            filterData.setPromptChoice(e);
            setNewPage();
            updateFeed();
        });

        orderFilters.setAmountOfProductsConsumer(e->{
            filterData.setAmountOfProductsChoice(e);
            setNewPage();
            updateFeed();
        });

        orderFilters.setEmployeeId(e->{
            filterData.setEmployee(e);
            setNewPage();
            updateFeed();
        });

        orderFilters.setProductId(e->{
            filterData.setProducts(e);
            setNewPage();
            updateFeed();
        });

        orderFilters.setClearFilters(e->{
            currentFilterDisplay.clearAllData();
            addUIData();
        });
        paganation.setOnPageChange(e->{
            e = e-1;
            filterData.setPage(e);
            updateFeed();
        });

        //needed
        currentFilterDisplay.setReloadController(e->{
            filterData = (OrderFilterHolder) e;
            setNewPage();
            updateFeed();
        });


    }








    public void addUIData(){

        verticalLayout.removeAll();


        filterData = new OrderFilterHolder();



        HorizontalLayout sidesHolder = new HorizontalLayout();
        sidesHolder.setWidthFull();
        sidesHolder.addClassName("layout-flex");


        VerticalLayout leftSide = new VerticalLayout();
        leftSide.setPadding(false);
        leftSide.setWidthFull();
        leftSide.setMaxHeight("1000px");





        filterMemory.removeAll();
        filterMemory.add(
                orderFilters.filters()

        );

        leftSide.add(
                feedHolder,
                paganation.buttonHolder(Math.toIntExact(ordersService.getPageCount(filterData))));

        VerticalLayout right = ordersRightSide.rightSideOrderInfo();

        // LEFT SIDE
        leftSide.setWidth("200px");

        // RIGHT SIDE
        right.setWidth("1000px");


        sidesHolder.add(leftSide, right);
        sidesHolder.expand(leftSide);



        verticalLayout.add(
                briefOrderPageExplanation.briefExplanation(),
                newOrders(),
                orderMiniStats.miniStatHolder(ordersService.getMiniStats(common.dateCrafter(0,0,0,0,true),common.dateCrafter(0,1,0,0,true))),
                filterMemory,
                sidesHolder);

        updateFeed();

    }


    public VerticalLayout newOrders(){

        Long amountOfNewOrderValue = ordersService.getNewOrderCount();

        VerticalLayout main = new VerticalLayout();
        main.setPadding(false);
        main.setWidthFull();

        HorizontalLayout v = new HorizontalLayout();
        v.setAlignItems(FlexComponent.Alignment.CENTER);
        v.setJustifyContentMode(JustifyContentMode.BETWEEN);
        v.setWidthFull();
        v.addClassName("island");


        Span amount = new Span(amountOfNewOrderValue.toString());

        Div amountOfNewOrders = new Div();
        amountOfNewOrders.add(amount);

        amountOfNewOrders.getStyle()
                .set("background-color", "#3960e8")
                .set("color", "white")
                .set("position", "absolute")
                .set("top", "-6px")
                .set("right", "-6px")
                .set("width", "28px")
                .set("height", "28px")
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "center")
                .set("border-radius", "50%")
                .set("font-size", "15px")
                .set("font-weight", "700")
                .set("line-height", "1")
                .set("border", "2px solid white");

        amountOfNewOrders.add(amount);




        VerticalLayout iconHolder = new VerticalLayout();
        iconHolder.getStyle().set("position","relative");
        iconHolder.setHeight("80px");
        iconHolder.setWidth("80px");
        iconHolder.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        iconHolder.setAlignItems(FlexComponent.Alignment.CENTER);
        iconHolder.addClassName("statIconBack");
        iconHolder.getStyle().set("background-color","rgba(59, 130, 246, 0.08)");
        iconHolder.getStyle().set("border","1px solid rgba(59, 130, 246, 0.22)");
        Icon icon = commonComponents.iconCrafter(VaadinIcon.BELL_O,"30px","bLUE");
        iconHolder.add(icon,amountOfNewOrders);


        Span ordersText = commonComponents.spanCrafterWordNoHide(amountOfNewOrderValue + " new order waiting for approval","activityFeed-name");

        if(amountOfNewOrderValue == 0){
            ordersText.setText("No new orders received");
        }

        HorizontalLayout bellTextHolder = new HorizontalLayout();
        bellTextHolder.setAlignItems(FlexComponent.Alignment.CENTER);
        bellTextHolder.setPadding(false);
        bellTextHolder.add(
                iconHolder,
                ordersText
        );




        Button reviewOrders = new Button("Review orders");
        reviewOrders.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        if(amountOfNewOrderValue == 0){
            reviewOrders.setEnabled(false);
        }

        reviewOrders.addClickListener(e->{

            if(reviewLeftRightSide.isVisible()){
                reviewLeftRightSide.setVisible(false);
            }
            else{
                reviewLeftRightSide.setVisible(true);
            }


        });

        v.add(
                bellTextHolder,
                reviewOrders
        );

        main.add(
                v,
                reviewLeftRight()
        );


        return main;


    }




    public HorizontalLayout reviewLeftRight(){
        reviewLeftRightSide.setWidthFull();
        reviewLeftRightSide.addClassName("layout-flex");

        VerticalLayout left = newOrderLeftSide();
        left.setWidth("200px");
        VerticalLayout right = newOrderRightSide();
        right.setWidth("1000px");


        reviewLeftRightSide.add(

                left,
                right

        );

        reviewLeftRightSide.expand(left);

        return reviewLeftRightSide;

    }


    public VerticalLayout newOrderLeftSide(){

        VerticalLayout v = new VerticalLayout();
        v.setPadding(false);

        String jwt = sessionCrafter.extractSession("JWT", String.class);
        v.setWidthFull();

        OrderFilterHolder newFilter = new OrderFilterHolder();
        newFilter.setOrderStatusChoice(OrderStatus.NEW);
        newFilter.setPage(0);
        newFilter.setPageCount(4);

        v.add(
                ordersLeftSide.newOrderFeedHolder(ordersService.getOrderFeedData(newFilter, jwt)),
                paganation.buttonHolder(Math.toIntExact(ordersService.getPageCount(newFilter)))
        );



        return v;

    }



    public VerticalLayout newOrderRightSide(){


        VerticalLayout v = new VerticalLayout();

        Span titleOfTePreview = commonComponents.spanCrafter("","activityFeed-name");

        HorizontalLayout constumerMiniInfo = new HorizontalLayout();
        constumerMiniInfo.setWidthFull();



        ordersLeftSide.setNewOrderList(e->{

            // clear the layout
            v.removeAll();
            constumerMiniInfo.removeAll();

            newSelectedOrder = ordersService.getSelectedOrder(e);

            titleOfTePreview.setText( "Order preview - NWO-"+ newSelectedOrder.getId());
            String customerName =
                    newSelectedOrder.getOrderPlacedBy() == null
                            || newSelectedOrder.getOrderPlacedBy().getFullName() == null
                            || newSelectedOrder.getOrderPlacedBy().getFullName().isBlank()
                            ? "Unknown"
                            : newSelectedOrder.getOrderPlacedBy().getFullName();

            constumerMiniInfo.add(
                    cardCrafter(
                            VaadinIcon.USER,
                            "Customer",
                            customerName,
                            newSelectedOrder.getOrderCreatedByGmail(),
                            newSelectedOrder.getPhoneNumber()
                    )
            );
            constumerMiniInfo.add(cardCrafter(VaadinIcon.LOCATION_ARROW_CIRCLE,"Delivery address",newSelectedOrder.getBillingAddress(),"",""));
            constumerMiniInfo.add(locationCard(VaadinIcon.CALENDAR,"Due date",newSelectedOrder.getEstimatedDueDate()));


            v.setVisible(true);

            v.add(
                    titleOfTePreview,
                    constumerMiniInfo,
                    newOrdersGrid(newSelectedOrder.getId())

            );

        });

        v.setWidthFull();
        v.addClassName("island");
        v.setVisible(false);









        return v;

    }

    public HorizontalLayout cardCrafter(
            VaadinIcon icon,
            String title,
            String info1,
            String info2,
            String info3
    ) {
        HorizontalLayout v = new HorizontalLayout();
        v.setSpacing(true);
        v.setPadding(false);
        v.setWidthFull();
        v.addClassName("island");

        VerticalLayout iconHolder = new VerticalLayout();
        iconHolder.setWidth("40px");
        iconHolder.setMinWidth("40px");
        iconHolder.setPadding(false);
        iconHolder.setSpacing(false);
        iconHolder.getStyle().set("flex-shrink", "0");

        iconHolder.add(commonComponents.iconCrafter(icon, "20px", "black"));

        VerticalLayout text = new VerticalLayout();
        text.setPadding(false);
        text.setSpacing(false);
        text.setWidthFull();

        // Important: allows the text section to shrink
        text.getStyle()
                .set("min-width", "0")
                .set("overflow", "hidden");

        Span line1 = commonComponents.spanCrafter(title, "stat-title");
        Span line2 = commonComponents.spanCrafter(info1, "stat-description");
        Span line3 = commonComponents.spanCrafter(info2, "stat-description");
        Span line4 = commonComponents.spanCrafter(info3, "stat-description");

        line1.getStyle().set("white-space", "normal").set("overflow-wrap", "anywhere");
        line2.getStyle().set("white-space", "normal").set("overflow-wrap", "anywhere");
        line3.getStyle().set("white-space", "normal").set("overflow-wrap", "anywhere");
        line4.getStyle().set("white-space", "normal").set("overflow-wrap", "anywhere");

        text.add(line1, line2, line3, line4);

        v.add(iconHolder, text);
        v.expand(text);

        return v;
    }


    public HorizontalLayout locationCard(VaadinIcon icon, String title, LocalDateTime localDateTime){

        HorizontalLayout v = new HorizontalLayout();
        v.setSpacing(true);
        v.setPadding(false);
        v.setWidthFull();
        v.addClassName("island");

        // ICON
        VerticalLayout iconHolder = new VerticalLayout();
        iconHolder.setWidth("40px");
        iconHolder.setPadding(false);
        iconHolder.setSpacing(false);

        iconHolder.add(commonComponents.iconCrafter(icon, "20px", "black"));

        // TEXT
        VerticalLayout text = new VerticalLayout();
        text.setPadding(false);
        text.setSpacing(false);

        Span line1 = commonComponents.spanCrafter(title, "stat-title");
        DateTimePicker dateTimePicker = new DateTimePicker();
        dateTimePicker.setValue(localDateTime);
        Button changeDate = new Button("Change date");
        changeDate.setEnabled(false);

        dateTimePicker.addValueChangeListener(e->{
            changeDate.setEnabled(true);
        });

        changeDate.addClickListener(e->{
            newSelectedOrder.setEstimatedDueDate(dateTimePicker.getValue());
            commonComponents.showNotification("Data changed to " + dateTimePicker.getValue(),3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.SUCCESS);
        });


        text.add(line1, dateTimePicker, changeDate);

        v.add(iconHolder, text);

        return v;
    }

    public VerticalLayout newOrdersGrid(Long id){

        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();
        v.setPadding(false);

        Grid<NewOrderFeedData> ordersGrid = new Grid<>(NewOrderFeedData.class,false);

        List<NewOrderFeedData> stuff = ordersService.getSelectedNewOrdersGridData(id);
        ordersGrid.setItems(stuff);

        ordersGrid.addComponentColumn(e->{

            HorizontalLayout h = new HorizontalLayout();
            h.setAlignItems(Alignment.CENTER);

            Image image = commonComponents.imageCrafter(e.getMainImage(),"70px","70px","10px");
            Span span = commonComponents.spanCrafter(e.getName(),"s");

            h.add(
                    image,
                    span
            );

            return h;

        }).setAutoWidth(true).setHeader("Item");


        ordersGrid.addComponentColumn(e->{

            Span span = commonComponents.spanCrafter(e.getSku(),"s");


            return span;

        }).setAutoWidth(true).setHeader("SKU");

        ordersGrid.addComponentColumn(e->{

            VerticalLayout vv = new VerticalLayout();



            Span span = commonComponents.spanCrafter( "Ordered: " + e.getQuantity().toString(),"s");
            Span span2 = commonComponents.spanCrafter("Remaining: " + e.getRemainingAmount().toString(),"s");

            vv.add(
                    span,
                    span2
            );


            return vv;

        }).setAutoWidth(true).setHeader("Quantity/Stock");


        ordersGrid.addComponentColumn(e->{

            Span span = commonComponents.spanCrafter(e.getUnitPrice() + " Eur","s");


            return span;

        }).setAutoWidth(true).setHeader("Unit price");

        ordersGrid.addComponentColumn(e->{

            Span span = commonComponents.spanCrafter(e.getTotal() + " Eur","s");


            return span;

        }).setAutoWidth(true).setHeader("Total");


        ordersGrid.addComponentColumn(e->{

            Long taken = e.getQuantity();
            Long remaining = e.getRemainingAmount();

            Span span = commonComponents.spanCrafter( "","s");
            span.addClassName("stock-badge");
            span.getStyle()
                    .set("width", "fit-content");


            if(taken < remaining) {
                span.addClassName("stock-in");
                span.setText("Pass");
            }
            else{
                span.addClassName("stock-out");
                span.setText("Not enough stock");
            }


            return span;

        }).setAutoWidth(true).setHeader("Exists");




        HorizontalLayout sumHolder = new HorizontalLayout();
        sumHolder.setWidthFull();
        sumHolder.setJustifyContentMode(JustifyContentMode.END);

        Double sum = stuff.stream().mapToDouble(NewOrderFeedData::getTotal).sum();

        Span totalSum = commonComponents.spanCrafter("Total- " +  sum + " Eur"  ,"activityFeed-name");
        sumHolder.add(
                totalSum
        );

        HorizontalLayout actionHolder = new HorizontalLayout();
        actionHolder.setWidthFull();
        actionHolder.setJustifyContentMode(JustifyContentMode.END);

        Button reject = new Button("Reject");
        reject.addThemeVariants(ButtonVariant.ERROR);

        Button accept = new Button("Accept order");
        accept.addThemeVariants(ButtonVariant.PRIMARY);


        actionHolder.add(
                reject,accept
        );



        v.add(ordersGrid,
                sumHolder,
                noteCrafter("User note",newSelectedOrder.getUserNote(),"User"),
                noteCrafter("System note",newSelectedOrder.getServerNote(),"System"),
                actionHolder);


        return v;


    }


    public HorizontalLayout noteHolder(){
        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();


        return  h;
    }


    public VerticalLayout noteCrafter(String noteTitleValue,String note, String byWho){
        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();
        v.setPadding(false);
        v.setSpacing(false);

        HorizontalLayout iconTextHolder = new HorizontalLayout();
        iconTextHolder.setWidthFull();
        iconTextHolder.setPadding(false);
        iconTextHolder.setSpacing(true);
        iconTextHolder.setAlignItems(FlexComponent.Alignment.CENTER);

        VerticalLayout icon = new VerticalLayout(
                commonComponents.iconCrafter(
                        VaadinIcon.CLIPBOARD_TEXT,
                        "20px",
                        "black"
                )
        );

        icon.setPadding(false);
        icon.setSpacing(false);
        icon.setWidth("fit-content");

        Span noteTitle = commonComponents.spanCrafter(
                noteTitleValue,
                "activityFeed-name"
        );

        noteTitle.getStyle().set("width", "fit-content");

        VerticalLayout spanHolder = new VerticalLayout(noteTitle);
        spanHolder.setPadding(false);
        spanHolder.setSpacing(false);
        spanHolder.setWidth("fit-content");

        iconTextHolder.add(icon, spanHolder);

        TextArea textArea = new TextArea();
        textArea.setReadOnly(true);
        textArea.setWidthFull();

        if(note == null) {
            textArea.setValue("No note was provided by " + byWho);

        }
        else{
            textArea.setValue(note);
        }




        v.add(
                iconTextHolder,
                textArea
        );

        return v;
    }


    public void updateFeed(){

        UI ui = UI.getCurrent();
        String jwt = sessionCrafter.extractSession("JWT", String.class);

        feedHolder.removeAll();
        feedHolder.add(commonComponents.shimmer(3));



        CompletableFuture
                .supplyAsync(()->{

                    List<OrdersFeedData> items = ordersService.getOrderFeedData(filterData,jwt);
                    common.timer(250);
                    return items;
                })
                .thenAccept(e->{
                    ui.access(() -> {
                        feedHolder.removeAll();
                         feedHolder.add(ordersLeftSide.orderFeedHolder(e));
                    });
                });


    }


//    public void updateUIData(){
//
//        verticalLayout.removeAll();
//
//        HorizontalLayout sidesHolder = new HorizontalLayout();
//        sidesHolder.setWidthFull();
//        sidesHolder.addClassName("layout-flex");
//
//
//        VerticalLayout leftSide = new VerticalLayout();
//        leftSide.setWidthFull();
//        leftSide.setMaxHeight("1000px");
//
//
//
////        Scroller left = ordersLeftSide.orderFeedHolder(
////                ordersService.getOrderFeedData(filterData));
//
//        updateFeed();
//
//        leftSide.add(
//                filterMemory,
//                left,
//                paganation.buttonHolder(
//                        Math.toIntExact((ordersService.getPageCount(filterData)))));
//
//        VerticalLayout right = ordersRightSide.rightSideOrderInfo();
//
//        // LEFT SIDE
//        leftSide.setWidth("250px");
//
//        // RIGHT SIDE
//        right.setMaxWidth("1000px");
//
//
//        sidesHolder.add(leftSide, right);
//        sidesHolder.expand(leftSide);
//
//
//
//
//
//        verticalLayout.add(
//                briefOrderPageExplanation.briefExplanation(),
//                orderMiniStats.miniStatHolder(ordersService.getMiniStats(common.dateCrafter(0,0,0,0,true),common.dateCrafter(0,1,0,0,true))),
//                sidesHolder);
//    }

    public void setNewPage(){
        filterData.setPage(0);
        paganation.updateUIFromExternal(1);
    }



}











