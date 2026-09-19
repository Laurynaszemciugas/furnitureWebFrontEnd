package com.example.demo.Pages.EmployeePage.PreviewPage.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.ImageViewer;
import com.example.demo.ControllerModels.CommonDtos.EmployeeJoin.OrderEmployees;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.Enums.ImageLogic;
import com.example.demo.Services.Orders.OrdersService;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.shared.Tooltip;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class OrderDashboardMiniStat {

    CommonComponents commonComponents;
    Common common;
    OrdersService ordersService;

    ImageViewer imageViewer;

    Orders currentOrder;

    public OrderDashboardMiniStat(CommonComponents commonComponents, Common common, OrdersService ordersService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.ordersService = ordersService;

        this.imageViewer = new ImageViewer(commonComponents,common);

    }


    public HorizontalLayout miniStat(Orders orders){

        currentOrder = orders;



        HorizontalLayout h = new HorizontalLayout();
        h.addClassName("layout-flex");
        h.addClassName("island");
        h.setWidthFull();
        h.setAlignItems(FlexComponent.Alignment.CENTER);
        h.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        h.getStyle().set("gap","20px");


        List<String> images = new ArrayList<>();

        for(var s : currentOrder.getProductsData()){

            for(var img : s.getProduct().getImages()){
                if(img.getImageLogic().equals(ImageLogic.Main)){
                    images.add(img.getImageUrl());
                }
            }

        }

        Image image = new Image(images.isEmpty() ? "No_picture.png" : images.get(0) ,"Order product image");
        image.setHeight("150px");
        image.setWidth("150px");

        image.addClickListener(e->{
            if(!images.isEmpty()) {
                imageViewer.popOver(images, images.get(0));
            }
            else{
                commonComponents.showNotification("No images are available",3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.ERROR);
            }
        });



        Long totalQuantity = 0L;

        for(var s : currentOrder.getProductsData()){
            totalQuantity++;
        }

        // get how important each order is


        Span priority = new Span(currentOrder.getPriority().getDisplayName());
        priority.getStyle().set("width", "fit-content");
        priority.addClassName("stock-badge");

        switch (currentOrder.getPriority()){

            case OVERDUE -> priority.addClassName("status-cancelled");
            case LOW_PRIORITY -> priority.addClassName("status-finished");
            case MEDIUM_PRIORITY -> priority.addClassName("status-in-progress");
            default -> priority.addClassName("status-none");


        }

        // get how many steps are there

        Long stepsTotal = 0L;
        Long stepsCompleted = 0L;

        Long test = 0L;
        Long sumOfCompletedSteps = 0L;

        Long totalProducts = 0L;

        for(var s : currentOrder.getProductsData()){
            List<Long> numbers = new ArrayList<>();
            totalProducts += s.getAmountOfProduct();


            System.out.println(s.getProduct().getProductName() + " SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");








            for(var stepsList : s.getOrderSteps()){



                stepsTotal += stepsList.getStepsNeeded();
                stepsCompleted += stepsList.getStepsCompleted();

                if(stepsList.getStepsCompleted().equals(stepsList.getStepsNeeded())){

                }


                numbers.add(
                    stepsList.getStepsCompleted()
                );





            }



            Long minNum =numbers.stream().min(Long::compare).orElse(totalProducts);
            sumOfCompletedSteps += minNum;




        }



        Div orderNamePriority = new Div();

        orderNamePriority.setMaxWidth("150px");
        orderNamePriority.getStyle()
                .set("display", "grid")
                .set("gap", "10px");
        orderNamePriority.add(
                commonComponents.spanCrafterWordNoHide("Dining funiture ssssssssssssssset","stat-example"),commonComponents.spanCrafter(String.format("%d unique products",totalQuantity),"stat-description"),priority
        );


        // vertical stuff




        h.add(
                image,
                orderNamePriority,
                createProgressCircle(stepsCompleted,stepsTotal),
                miniStatHolder(),
                verticallyMiniStats(VaadinIcon.CHECK_CIRCLE_O,String.format("%s/%s",sumOfCompletedSteps,totalProducts),"Fully completed products"),
                verticallyMiniStats(VaadinIcon.LIST,String.format("%s/%s",stepsCompleted,stepsTotal),"Steps completed"),
                activeEmployee(currentOrder.getEmployees())
        );



        return h;
    }

    public HorizontalLayout activeEmployee(List<OrderEmployees> orderEmployees){

        HorizontalLayout h = new HorizontalLayout();
        h.addClassName("layout-flex");
        h.setWidth("150px");

        for(var s : orderEmployees){

            VerticalLayout employeeImageName = new VerticalLayout();
            employeeImageName.setWidth("60px");
            employeeImageName.setPadding(false);
            employeeImageName.setSpacing(false);
            employeeImageName.setAlignItems(FlexComponent.Alignment.CENTER);


                    employeeImageName.add(
                            commonComponents.imageCrafter(s.getEmployee().getProfileImage(),"50px","50px","50%"),
                            commonComponents.spanCrafter(s.getEmployee().getFullName(),"stat-description")

                    );

            h.add(
                    employeeImageName
            );
        }


        return h;
    }

    public Div miniStatHolder() {
        Div h = new Div();


        LocalDateTime today = LocalDateTime.now();
        LocalDateTime dueDate = currentOrder.getEstimatedDueDate();



        String daysMarker = "Err";

        Long timeReference = 0L;


        Long minutes = ChronoUnit.MINUTES.between(today,dueDate);

        minutes = Math.abs(minutes);

        if(minutes >= 0 && minutes <= 59){
            daysMarker = Math.abs(ChronoUnit.MINUTES.between(today,dueDate)) + " Minutes";
            timeReference = ChronoUnit.MINUTES.between(today,dueDate);
        }

        else if(minutes >= 60 &&  minutes <= 1440){
            daysMarker = Math.abs(ChronoUnit.HOURS.between(today,dueDate)) + " Hours";
            timeReference = ChronoUnit.HOURS.between(today,dueDate);
        }

        else{
            daysMarker = Math.abs(ChronoUnit.DAYS.between(today,dueDate)) + " Days";
            timeReference = ChronoUnit.DAYS.between(today,dueDate);
        }








        if(timeReference <= 0){

            switch (currentOrder.getOrderStatus()){
                case Finished -> daysMarker = "Finished";
                case LACK_OF_SUPPLY -> daysMarker = "No supply";
                case CANCELLED -> daysMarker = "Cancelled";
                case In_Progress -> daysMarker = String.format("%s %s","Overdue",daysMarker);
                case Pending -> daysMarker = String.format("%s %s","Overdue",daysMarker);
            }


        }



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
                miniStatCrafter(VaadinIcon.CALENDAR, "Created date", common.dateFormatterLocalDateTime(currentOrder.getCreated(),"yyyy-MM-dd")),
                miniStatCrafter(VaadinIcon.CALENDAR, "Due date", common.dateFormatterLocalDateTime(currentOrder.getEstimatedDueDate(),"yyyy-MM-dd MM:ss")),
                miniStatCrafter(VaadinIcon.CLOCK, "Time remaining", daysMarker),
                miniStatCrafter(VaadinIcon.CUBE, "Total products", totalQuantity),
                miniStatCrafter(VaadinIcon.USER, "Costumer full name", currentOrder.getOrderCreatedByName()),
                miniStatCrafter(VaadinIcon.MAILBOX, "Estimated Finish time",currentOrder.getEstimatedFinishTimeMinutes() == null ? "Unknown" : currentOrder.getEstimatedFinishTimeMinutes().toString())
        );
        return h;
    }


    public HorizontalLayout miniStatCrafter(
            VaadinIcon icon,
            String name,
            Object value
    ) {
        HorizontalLayout h = new HorizontalLayout();
        h.setAlignItems(FlexComponent.Alignment.CENTER);
        Div div = new Div();

        Tooltip.forComponent(h)
                .withText(value.toString());


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
        v.getStyle().set("flex", "1 1 52px");
        //h.getStyle().set("max-width", "620px");
        v.getStyle().set("min-width", "52px");


        v.getStyle()

                .set("place-items", "center");

        VerticalLayout vv = new VerticalLayout();
        vv.setPadding(false);
        vv.setWidth("50px");
        vv.setHeight("50px");
        vv.getStyle().set("border-radius","50%");
        vv.getStyle().set("background-color", "rgba(34, 117, 243, 0.15)");

        vv.setAlignItems(FlexComponent.Alignment.CENTER);
        vv.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

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







    public Div createProgressCircle(Long completed, Long total) {


        double percentage = 100;

        if(total != 0) {
            percentage = (double) completed / total * 100;

        }



        Div holder = new Div();
        holder.getStyle()
                .set("display", "grid")
                .set("justify-content", "center") // horizontal
                .set("align-items", "center");    // vertical

        Div circle = new Div();
        circle.setText(percentage == 0 ? "Not started" : String.format("%.0f ",percentage) + "%");

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
