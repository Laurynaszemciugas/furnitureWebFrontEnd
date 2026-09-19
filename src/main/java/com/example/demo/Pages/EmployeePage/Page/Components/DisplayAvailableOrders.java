package com.example.demo.Pages.EmployeePage.Page.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.ImageViewer;
import com.example.demo.ControllerModels.CommonDtos.EmployeePage.EmployeeOrderProjection;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.Services.Orders.OrdersService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.Arrays;
import java.util.List;

public class DisplayAvailableOrders {

    CommonComponents commonComponents;
    Common common;

    OrdersService ordersService;

    ImageViewer imageViewer;


    public DisplayAvailableOrders(CommonComponents commonComponents, Common common, OrdersService ordersService, ImageViewer imageViewer) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.ordersService = ordersService;
        this.imageViewer = imageViewer;
    }


    public VerticalLayout availableOrders(List<EmployeeOrderProjection> list){

        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");



        Grid<EmployeeOrderProjection> grid = new Grid<>(EmployeeOrderProjection.class,false);
        grid.setItems(list);
        grid.setHeightFull();
        grid.setHeight("513px");

        grid.setWidthFull();
        grid.setColumnReorderingAllowed(false);

        Span span = commonComponents.spanCrafter( ordersService.findHowManyItemsAreAvailable()+ " available","stat-example");
        span.addClassNames("new-badge","status-pending");


        Button viewAll = new Button("View all");
        viewAll.setSuffixComponent(VaadinIcon.ANGLE_RIGHT.create());

        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.setPadding(false);
        h.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        h.add(
                commonComponents.doubleValueRow(commonComponents.spanCrafter("Available orders","activityFeed-name"),span),
                commonComponents.spaceFiller(),
                viewAll
        );


        grid.addComponentColumn(e -> {

            String images = (String) e.getImages();

            List<String> imageList = images == null || images.isBlank()
                    ? List.of()
                    : Arrays.stream(images.split(","))
                    .filter(s -> !s.isBlank())
                    .toList();


            Image image = commonComponents.imageCrafter(
                    imageList.isEmpty() ? "No_picture.png":imageList.get(0),
                    "150px",
                    "150px",
                    "5px"
            );



            image.addClickListener(ee->{
                imageViewer.popOver(imageList,"33232");
            });
            HorizontalLayout hh = new HorizontalLayout();
            hh.setWidthFull();
            hh.setAlignItems(FlexComponent.Alignment.CENTER);



            HorizontalLayout first = new HorizontalLayout();


            Span statusDisplay = commonComponents.spanCrafter(e.getPriority().getDisplayName(),"stat-example");
            statusDisplay.getStyle().set("width", "fit-content");
            statusDisplay.addClassName("stock-badge");

            switch (e.getPriority()){

                case OVERDUE -> statusDisplay.addClassName("status-cancelled");
                case LOW_PRIORITY -> statusDisplay.addClassName("status-finished");
                case MEDIUM_PRIORITY -> statusDisplay.addClassName("status-in-progress");
                default -> statusDisplay.addClassName("status-none");


            }


            first.add(
                    new VerticalLayout(commonComponents.spanCrafter("#" + e.getId(),"activityFeed-name"), statusDisplay)

            );

            HorizontalLayout second = new HorizontalLayout();

            second.add(
                    miniStats("Quantity",String.valueOf(e.getAmountOfItems())),
                    miniStats("Created",common.dateFormatterLocalDateTime(e.getCreated(),"dd MMM yyyy, HH:mm")),
                    miniStats("Due date",common.dateFormatterLocalDateTime(e.getDueDate(),"dd MMM yyyy, HH:mm")),
                    miniStats("Materials",e.getOrderStatus() == OrderStatus.LACK_OF_SUPPLY ? "Not available" : "Available"),
                    miniStats("Estimated Finish time",e.getEstimatedFinishTimeMinutes() == null ? "Unknown" : e.getEstimatedFinishTimeMinutes().toString()),
                    employeeOnTheProject(e.getEmployeeImages().toString(), e.getEmployeeImages().toString())
            );

            VerticalLayout allHolder = new VerticalLayout();
            allHolder.setSpacing(false);
            allHolder.add(first,second);



            hh.add(
                    image,
                    allHolder
            );

            hh.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

            return hh;
        }).setFlexGrow(1);



        grid.addComponentColumn(e->{


            VerticalLayout buttonHolder = new VerticalLayout();

            Button viewDetails = new Button("View details");

            viewDetails.addClickListener(ee->{
                Orders orders = ordersService.getSelectedOrder(e.getId());

                for(var s : orders.getProductsData()){

                    System.out.println(s.getProduct().getProductName());
                    for(var steps : s.getProduct().getSteps()){
                        System.out.println(steps.getStepName());
                    }
                }

                //openDetailsOfTheOrder(orders);

                UI.getCurrent().navigate("OrderPreview/" + orders.getId());


            });

            Button acceptOrders = commonComponents.normalThemeButtonNoNavigate("Accept order", ButtonVariant.LUMO_PRIMARY);

            acceptOrders.addClickListener(ee->{
                ordersService.acceptOrderEmployee(e.getId());
            });

            if(e.getOrderStatus().equals(OrderStatus.LACK_OF_SUPPLY)){
                acceptOrders.setEnabled(false);
            }

            buttonHolder.add(
                    viewDetails,
                    acceptOrders
            );

            return buttonHolder;

        }).setFlexGrow(0).setWidth("180px");





        v.add(
                h,
                grid
        );


        return v;

    }

    public VerticalLayout employeeOnTheProject(String employeeImages, String employees){


        // extract data from concat due to its working with , csv
        List<String> employeeImagesList = employeeImages == null || employeeImages.isBlank()
                ? List.of()
                : Arrays.stream(employeeImages.split(","))
                .filter(s -> !s.isBlank())
                .distinct()
                .toList();

        List<String> employeesList = employees == null || employees.isBlank()
                ? List.of()
                : Arrays.stream(employees.split(","))
                .filter(s -> !s.isBlank())
                .distinct()
                .toList();

        VerticalLayout v = new VerticalLayout();

        v.add(
                commonComponents.spanCrafter("Employee working on this order","stat-description")
        );

        HorizontalLayout h = new HorizontalLayout();


        for(var s : employeeImagesList){
            h.add(
                    commonComponents.imageCrafter(s,"50px","50px","50%")
            );
        }

        v.add(
                h
        );


        return v;


    }

    public VerticalLayout miniStats(String name, String value){

        VerticalLayout v = new VerticalLayout();

        v.add(
                commonComponents.spanCrafter(name,"stat-description"),
                commonComponents.spanCrafter(value,"stat-example")
        );

        return v;
    }


}
