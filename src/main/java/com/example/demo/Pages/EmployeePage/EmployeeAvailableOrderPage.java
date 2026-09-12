package com.example.demo.Pages.EmployeePage;


import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.ImageViewer;
import com.example.demo.ControllerModels.CommonDtos.OrderJoin.OrderStepsToComplete;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.ControllerModels.CommonDtos.Product;
import com.example.demo.Enums.ImageLogic;
import com.example.demo.Enums.ProductFinishStepStatus;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Services.Orders.OrdersService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Route(value = "EmployeeAvailableOrder/:id", layout = MainLayout.class)
public class EmployeeAvailableOrderPage extends VerticalLayout implements BeforeEnterObserver {


    CommonComponents commonComponents;
    Common common;
    OrdersService ordersService;
    ImageViewer imageViewer;

    int orderId;


    Orders currentOrder = new Orders();

    public EmployeeAvailableOrderPage(CommonComponents commonComponents, Common common,OrdersService ordersService,ImageViewer imageViewer) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.ordersService = ordersService;
        this.imageViewer = imageViewer;




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
                miniStat(),

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

        h.add(
                commonComponents.spanCrafter(String.format("Order # %d",currentOrder.getId()),"stat-value"),
                status
        );

        v.add(
                h,
                commonComponents.spanCrafter("Some name","stat-description")
        );



        return v;
    }

    public HorizontalLayout miniStat(){

        HorizontalLayout h = new HorizontalLayout();
        h.addClassName("island");
        h.setWidthFull();
        h.setAlignItems(Alignment.CENTER);

        h.getStyle().set("gap","20px");

        Image image = new Image("No_picture.png","err");
        image.setHeight("150px");
        image.setWidth("150px");

        Long totalQuantity = 0L;

        for(var s : currentOrder.getProductsData()){
            totalQuantity += s.getAmountOfProduct();
        }

        // get how important each order is

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime dueDate = currentOrder.getEstimatedDueDate();

        System.out.println(today);
        System.out.println(dueDate);

        long howManyDaysLeft = ChronoUnit.DAYS.between(today,dueDate);

        Span priority = new Span();
        priority.getStyle().set("width", "fit-content");
        priority.addClassName("stock-badge");

        if(howManyDaysLeft >= 15){
            priority.setText("Low priority");
            priority.addClassName("stock-in");
        }
        else if(howManyDaysLeft >= 5 && howManyDaysLeft <= 10){

            priority.setText("medium priority");
            priority.addClassName("stock-low");
        }
        else if(howManyDaysLeft >= 0 && howManyDaysLeft <= 4){
            priority.setText("High priority");
            priority.addClassName("stock-out");
        }

        else{
            priority.setText(String.format("%s %d days","OVERDUE",Math.abs(howManyDaysLeft)));
            priority.addClassName("stock-out");
        }

        // get how many steps are there

        Long stepsTotal = 0L;
        Long stepsCompleted = 0L;


        for(var s : currentOrder.getProductsData()){
            for(var stepsList : s.getOrderSteps()){
                stepsTotal += stepsList.getStepsNeeded();
                stepsCompleted += stepsList.getStepsCompleted();
            }
        }

        System.out.println(stepsTotal);
        System.out.println(stepsCompleted = stepsCompleted +1);

        Div orderNamePriority = new Div();
        orderNamePriority.setMaxWidth("150px");
        orderNamePriority.getStyle()
                .set("display", "grid")
                .set("gap", "10px");
        orderNamePriority.add(
                commonComponents.spanCrafterWordNoHide("Dining funiture ssssssssssssssset","stat-example"),commonComponents.spanCrafter(String.format("%d products in this order",totalQuantity),"stat-description"),priority
        );

        h.add(
                image,
                orderNamePriority,
                createProgressCircle(stepsCompleted,stepsTotal),
                miniStatHolder(),
                verticallyMiniStats(VaadinIcon.TRASH,"1253","Test"),
                verticallyMiniStats(VaadinIcon.TRASH,"1253","Test"),
                verticallyMiniStats(VaadinIcon.TRASH,"1253","Test"),
                verticallyMiniStats(VaadinIcon.TRASH,"1253","Test")
        );



        return h;
    }

    public Div miniStatHolder() {
        Div h = new Div();

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime dueDate = currentOrder.getEstimatedDueDate();

        System.out.println(today);
        System.out.println(dueDate);

        long howManyDaysLeft = ChronoUnit.DAYS.between(today,dueDate);

        Long totalQuantity = 0L;

        for(var s : currentOrder.getProductsData()){
            totalQuantity += s.getAmountOfProduct();
        }

        h.getStyle()
                .set("padding","20px")
                .set("display", "grid")
                .set("grid-template-columns", "1fr 1fr 1fr")
                .set("gap", "15px");

        h.add(
                miniStatCrafter(VaadinIcon.CALENDAR, "Created", common.dateFormatterLocalDateTime(currentOrder.getCreated(),"yyyy-MM-dd")),
                miniStatCrafter(VaadinIcon.CALENDAR, "Due date", common.dateFormatterLocalDateTime(currentOrder.getEstimatedDueDate(),"yyyy-MM-dd")),
                miniStatCrafter(VaadinIcon.CLOCK, "Time remaining", howManyDaysLeft + " Days"),
                miniStatCrafter(VaadinIcon.CUBE, "Total products", totalQuantity),
                miniStatCrafter(VaadinIcon.USER, "Costumer", currentOrder.getOrderCreatedByName()),
                miniStatCrafter(VaadinIcon.MAILBOX, "Costumer", currentOrder.getOrderCreatedByGmail())
        );

        return h;
    }


    public HorizontalLayout miniStatCrafter(
            VaadinIcon icon,
            String name,
            Object value
    ) {
        HorizontalLayout h = new HorizontalLayout();
        h.setAlignItems(Alignment.CENTER);

        Div div = new Div();

        div.add(
                commonComponents.spanCrafterWordNoHide(name, "stat-description"),
                commonComponents.spanCrafterWordNoHide(
                        value.toString(),
                        "stat-example"
                )
        );

        h.add(
                commonComponents.iconCrafter(icon, "25px", "grey"),
                div
        );

        return h;
    }




    public Div verticallyMiniStats(VaadinIcon icon,
                                              Object value,
                                              String name){

        Div v = new Div();

        v.getStyle()
                .set("display", "grid")
                .set("place-items", "center");

        VerticalLayout vv = new VerticalLayout();
        vv.setPadding(false);
        vv.setWidth("50px");
        vv.setHeight("50px");
        vv.getStyle().set("border-radius","50%");
        vv.getStyle().set("background-color","Red");

        vv.setAlignItems(Alignment.CENTER);
        vv.setJustifyContentMode(JustifyContentMode.CENTER);

        vv.add(
                commonComponents.iconCrafter(icon,"25px","blue")
        );

        v.add(

                vv,
                commonComponents.spanCrafter(value.toString(),"stat-value"),
                commonComponents.spanCrafter(name,"stat-description")
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

    public Div createProgressCircle(Long completed, Long total) {


        double percentage = (double) completed / total * 100;


        Div holder = new Div();
        holder.getStyle()
                .set("display", "grid")
                .set("justify-content", "center") // horizontal
                .set("align-items", "center");    // vertical

        Div circle = new Div();
        circle.setText(String.format("%.0f ",percentage) + "%");

        circle.getStyle()
                .set("width", "100px")
                .set("height", "100px")
                .set("border-radius", "50%")
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "center")
                .set("font-weight", "bold")
                .set("background",
                        "conic-gradient(var(--lumo-primary-color) "
                                + percentage + "%, #e0e0e0 0)");

        holder.add(
                circle,
                commonComponents.spanCrafter("Overall progress","stat-example"));

        return holder;
    }



}
