package com.example.demo.Pages.EmployeePage.Page.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.CommonDtos.OrderJoin.OrderStepsToComplete;
import com.example.demo.ControllerModels.CommonDtos.ProductJoin.ProductMaterials;
import com.example.demo.Enums.ImageLogic;
import com.example.demo.Enums.Priority;
import com.example.demo.Services.EmployeeService.EmployeeActiveOrders;
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
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.shared.Tooltip;

import java.util.List;

public class DisplayActiveOrders {

    CommonComponents commonComponents;
    Common common;

    OrdersService ordersService;

    public DisplayActiveOrders(CommonComponents commonComponents, Common common, OrdersService ordersService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.ordersService = ordersService;
    }

    public VerticalLayout myActiveOrders(List<EmployeeActiveOrders> employeeActiveOrders){

        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();
        v.addClassName("island");
        v.addClassName("layout-flex");




        Span span = commonComponents.spanCrafter(ordersService.findHowManyItemsAreActive()+ " available","stat-example");
        span.addClassNames("new-badge","status-in-progress");


        Button viewAll = new Button("View all", e-> common.customNavigate("AvailableOrderPage"));
        viewAll.setSuffixComponent(VaadinIcon.ANGLE_RIGHT.create());

        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.setPadding(false);
        h.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        h.add(
                commonComponents.doubleValueRow(commonComponents.spanCrafter("My active orders","activityFeed-name"),span),
                commonComponents.spaceFiller(),
                viewAll
        );


        Grid<EmployeeActiveOrders> grid = new Grid<>(EmployeeActiveOrders.class,false);
        grid.setItems(employeeActiveOrders);
        grid.setAllRowsVisible(true);

        grid.addComponentColumn(e->{


            VerticalLayout va = new VerticalLayout();
            va.setPadding(false);

            for(var product : e.getOrder().getProductsData()){

                String mainImageUrl = "";

                Long totalSteps = 0L;
                Long totalStepsCompleted = 0L;

                for(var images : product.getProduct().getImages()){
                    if(images.getImageLogic().equals(ImageLogic.Main)){
                        mainImageUrl = images.getImageUrl();
                    }
                }

                for(var steps : product.getOrderSteps()){

                    totalSteps += steps.getStepsNeeded();
                    totalStepsCompleted += steps.getStepsCompleted();

                }

                va.add(activeOrdersPreview(e.getOrder().getPriority(), e.getOrder().getId(),mainImageUrl,product.getProduct().getProductName(),product.getProduct().getSku(),product.getAmountOfProduct(),totalSteps,totalStepsCompleted,product.getOrderSteps(), product.getProduct().getMaterials()));
            }

            return va;


        }).setFlexGrow(1);







        v.add(
                h,
                grid
        );



        return v;

    }


    public VerticalLayout activeOrdersPreview(Priority priority, Long id, String mainImage, String productName, String productSKU, Long howMany, Long totalSteps, Long totalStepsCompleted, List<OrderStepsToComplete> stepsList, List<ProductMaterials> productMaterials){




        // REM
        HorizontalLayout stepsRequired = new HorizontalLayout();
        stepsRequired.setWidthFull();
        stepsRequired.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);







        HorizontalLayout h = new HorizontalLayout();
        h.setAlignItems(FlexComponent.Alignment.CENTER);
        h.setWidthFull();
        h.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        h.addClassName("layout-flex");

        Image image = commonComponents.imageCrafter(mainImage,"100px","100px","5px");

        VerticalLayout nameSku = new VerticalLayout();
        nameSku.setMaxWidth("150px");

        Span productNameSpan =  commonComponents.spanCrafterWordNoHide(productName,"stat-example");
        Tooltip.forComponent(productNameSpan)
                .withText(productName);

        Span productSKUSpan = commonComponents.spanCrafterWordNoHide(productSKU,"stat-description");
        Tooltip.forComponent(productSKUSpan)
                .withText(productSKU);

        nameSku.add(
                productNameSpan,
                productSKUSpan
        );

        Span pcs = new Span(String.format("%d %s",howMany,"Pcs"));
        pcs.getStyle().set("width", "fit-content");
        pcs.addClassNames("stock-badge","status-pending");


        // progress bar

        double percentage = 1;

        if(totalSteps != 0) {
            percentage = Double.valueOf(totalStepsCompleted) / Double.valueOf(totalSteps);

        }

        ProgressBar progressBar = new ProgressBar();
        progressBar.setHeight("10px");
        progressBar.setWidth("300px");

        progressBar.setValue(percentage);


        Span statusDisplay = commonComponents.spanCrafter(priority.getDisplayName(),"stat-example");
        statusDisplay.getStyle().set("width", "fit-content");
        statusDisplay.addClassName("stock-badge");

        switch (priority){

            case OVERDUE -> statusDisplay.addClassName("status-cancelled");
            case LOW_PRIORITY -> statusDisplay.addClassName("status-finished");
            case MEDIUM_PRIORITY -> statusDisplay.addClassName("status-in-progress");
            default -> statusDisplay.addClassName("status-none");


        }


        HorizontalLayout allHolder = new HorizontalLayout();
        allHolder.setAlignItems(FlexComponent.Alignment.CENTER);

        allHolder.setWidth("1000px");
        allHolder.setPadding(false);
        allHolder.addClassName("layout-flex");

        allHolder.add(
                image,
                nameSku,
                statusDisplay,
                pcs,
                progressBar,
                commonComponents.spanCrafter(String.format("%.0f %s",percentage*100,"%"),"stat-example"),
                commonComponents.spanCrafter(String.format("%d/%d",totalStepsCompleted,totalSteps),"stat-example")
        );

        Button viewDetails = new Button("Continue work");
        viewDetails.addThemeVariants(ButtonVariant.PRIMARY);
        viewDetails.addClickListener(e->{
            UI.getCurrent().navigate("OrderActive/" + id );
        });


        h.add(
                allHolder,
                viewDetails

        );

        VerticalLayout v = new VerticalLayout();


        v.add(
                h
        );



        return v;
    }



}
