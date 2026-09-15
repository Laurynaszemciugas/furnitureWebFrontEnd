package com.example.demo.Pages.EmployeePage.PreviewPage.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.CommonDtos.OrderJoin.OrderProducts;
import com.example.demo.ControllerModels.CommonDtos.OrderJoin.OrderStepsToComplete;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.ControllerModels.CommonDtos.ProductJoin.ProductMaterials;
import com.example.demo.Enums.ImageLogic;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
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

public class ProductInTheOrderUI {

    CommonComponents commonComponents;
    Common common;

    public ProductInTheOrderUI(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }


    public VerticalLayout orderName(Orders currentOrder){
        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();
        v.setSpacing(false);

        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.setAlignItems(FlexComponent.Alignment.CENTER);
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





    public VerticalLayout productsInOrder(Orders currentOrder){
        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");

        HorizontalLayout firstLayer = new HorizontalLayout();
        firstLayer.setWidthFull();
        firstLayer.setAlignItems(FlexComponent.Alignment.CENTER);
        firstLayer.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        Span productsAvailable = new Span(String.format("%d Unique Products",currentOrder.getProductsData().size()));
        productsAvailable.addClassName("stock-badge");
        productsAvailable.addClassName("status-pending");

        firstLayer.add(
                commonComponents.spanCrafter("Product in this order","activityFeed-name"),
                productsAvailable
        );

        v.add(
                firstLayer,
                productPreviewHolder(currentOrder.getProductsData())
        );





        return v;
    }






    public VerticalLayout productPreviewHolder(List<OrderProducts> productsData){

        VerticalLayout v = new VerticalLayout();

        for(var product : productsData){

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

            v.add(productPreview(mainImageUrl,product.getProduct().getProductName(),product.getProduct().getSku(),product.getAmountOfProduct(),totalSteps,totalStepsCompleted,product.getOrderSteps(), product.getProduct().getMaterials()));
        }

        return v;

    }






    public VerticalLayout productPreview(String mainImage, String productName, String productSKU, Long howMany, Long totalSteps, Long totalStepsCompleted, List<OrderStepsToComplete> stepsList, List<ProductMaterials> productMaterials){


        HorizontalLayout stepsMaterialsHolder = new HorizontalLayout();
        stepsMaterialsHolder.setWidthFull();
        stepsMaterialsHolder.setVisible(false);
        stepsMaterialsHolder.addClassName("removetop");
        stepsMaterialsHolder.addClassName("layout-flex");

        // REM
        HorizontalLayout stepsRequired = new HorizontalLayout();
        stepsRequired.setWidthFull();
        stepsRequired.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);







        stepsMaterialsHolder.add(
                manufacturingStepsGrid(stepsList,productMaterials,productName)

        );



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

        double percentage = Double.valueOf(totalStepsCompleted) / Double.valueOf(totalSteps);
        System.out.println(percentage);

        ProgressBar progressBar = new ProgressBar();
        progressBar.setHeight("10px");
        progressBar.setWidth("300px");

        progressBar.setValue(percentage);

        HorizontalLayout allHolder = new HorizontalLayout();
        allHolder.setAlignItems(FlexComponent.Alignment.CENTER);

        allHolder.setWidth("800px");
        allHolder.setPadding(false);
        allHolder.addClassName("layout-flex");

        allHolder.add(
                image,
                nameSku,
                pcs,
                progressBar,
                commonComponents.spanCrafter(String.format("%.0f %s",percentage*100,"%"),"stat-example"),
                commonComponents.spanCrafter(String.format("%d/%d",totalStepsCompleted,totalSteps),"stat-example")
        );

        Button viewDetails = new Button("View details");
        viewDetails.setSuffixComponent(commonComponents.iconCrafter(VaadinIcon.ANGLE_DOWN,"25","blue"));
        viewDetails.addClickListener(e -> {
            if(!stepsMaterialsHolder.isVisible()){
                stepsMaterialsHolder.setVisible(true);
                stepsMaterialsHolder.removeClassName("removetop");
                stepsMaterialsHolder.addClassName("addtop");

            }

            else{

                if(stepsMaterialsHolder.hasClassName("addtop")){
                    stepsMaterialsHolder.removeClassName("addtop");
                    stepsMaterialsHolder.addClassName("removetop");
                }
                else{
                    stepsMaterialsHolder.removeClassName("removetop");
                    stepsMaterialsHolder.addClassName("addtop");
                }


            }


        });

        h.add(
                allHolder,
                viewDetails

        );

        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");


        v.add(
                h,
                stepsMaterialsHolder
        );



        return v;
    }

    public VerticalLayout manufacturingStepsGrid(List<OrderStepsToComplete> stepsList, List<ProductMaterials> productMaterials, String currentProductName){

        Button showMaterials = new Button("Show materials",e-> materialGrid(productMaterials,currentProductName));
        showMaterials.addThemeVariants(ButtonVariant.PRIMARY);

        HorizontalLayout firstLayer = new HorizontalLayout();
        firstLayer.setWidthFull();
        firstLayer.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        firstLayer.add(
                commonComponents.spanCrafter("Manufacturing steps","activityFeed-name"),
                showMaterials
        );

        VerticalLayout v = new VerticalLayout();

        v.add(
                firstLayer
        );




        Grid<OrderStepsToComplete> grid = new Grid<>(OrderStepsToComplete.class,false);

        grid.addClassName("invisible-grid");



        grid.setItems(stepsList);

        grid.addComponentColumn(e->{


            HorizontalLayout h = new HorizontalLayout();
            h.setPadding(false);




            h.add(
                    commonComponents.spanCrafterWordNoHide(e.getStepId().toString(),"stat-example"),
                    commonComponents.spanCrafterWordNoHide(e.getStepDescription(),"stat-example")
            );



            return h;
        }).setHeader("Step").setWidth("400px");

        grid.addComponentColumn(e->{


            Span status = new Span(e.getProductFinishStepStatus().getDisplayName());
            status.getStyle().set("width", "fit-content");
            status.addClassName("stock-badge");



            switch (e.getProductFinishStepStatus()){
                case FINISHED ->status.addClassName("stock-in");
                case IN_PROGRESS -> status.addClassName("status-in-progress");
                case NOT_STARTED  -> status.addClassName("status-pending");

            }

            return status;
        }).setHeader("Status").setAutoWidth(true);

        grid.addComponentColumn(e->{

            HorizontalLayout h = new HorizontalLayout();
            h.setPadding(false);
            h.setAlignItems(FlexComponent.Alignment.CENTER);

            Long totalSteps = e.getStepsNeeded();
            Long totalCompletedSteps = e.getStepsCompleted();

            Span status = commonComponents.spanCrafter(String.format("%d/%d",totalCompletedSteps,totalSteps),"stat-example");

            double percentage = Double.valueOf(totalCompletedSteps) / Double.valueOf(totalSteps);

            ProgressBar progressBar = new ProgressBar();
            progressBar.setHeight("10px");
            progressBar.setWidth("300px");

            progressBar.setValue(percentage);

            h.add(
                    progressBar,
                    status
            );

            return h;
        }).setHeader("Progress").setAutoWidth(true);

        grid.addComponentColumn(e->{


            Image image = commonComponents.imageCrafter(e.getEmployee() == null ? "No_picture.png" : e.getEmployee().getImageUrl(),"50px","50px","50%");

            Span emp = commonComponents.spanCrafter(e.getEmployee() == null ? "Not selected" : e.getEmployee().getFullName(),"stat-example");


            HorizontalLayout h = new HorizontalLayout();
            h.setPadding(false);
            h.setAlignItems(FlexComponent.Alignment.CENTER);
            h.add(
                    image,emp
            );

            return h;
        }).setHeader("Assigned").setAutoWidth(true);



        v.add(
                grid
        );



        return v;

    }

    public void materialGrid(List<ProductMaterials> productMaterials, String currentProductName){

        Dialog dialog = new Dialog();
        dialog.setWidth("1000px");

        dialog.getHeader().add(
                commonComponents.spanCrafter(String.format("%s %s",currentProductName,"materials"),"activityFeed-name")
        );

        VerticalLayout v = new VerticalLayout();

        v.add(
                commonComponents.spanCrafter("Materials used","activityFeed-name")
        );


        Grid<ProductMaterials> grid = new Grid<>(ProductMaterials.class,false);

        grid.addClassName("invisible-grid");



        grid.setItems(productMaterials);

        grid.addComponentColumn(e->{


            HorizontalLayout h = new HorizontalLayout();
            h.setAlignItems(FlexComponent.Alignment.CENTER);
            h.setPadding(false);

            String mainImage = null;
            for(var s : e.getMaterials().getImages()){
                mainImage = s.getImageUrl();
            }

            Image image = commonComponents.imageCrafter(mainImage == null ? "No_picture.png" : mainImage,"80px","80px","5px");

            Span mat = commonComponents.spanCrafter(e.getMaterials().getMaterialName(),"stat-example");




            h.add(
                    image,
                    mat
            );



            return h;
        }).setHeader("Material").setAutoWidth(true);

        grid.addComponentColumn(e->{



            Span needed = commonComponents.spanCrafter(String.format("%d %s",e.getAmountUsed(),"units"),"stat-example");


            return needed;
        }).setHeader("Required amount").setAutoWidth(true);


        grid.addComponentColumn(e->{

            Span stockTitle = commonComponents.spanCrafter(e.getMaterials().getStock().getDisplayName(),"stat-example");
            stockTitle.getStyle().set("width", "fit-content");
            stockTitle.addClassName("stock-badge");



            switch (e.getMaterials().getStock()){
                case In_Stock -> stockTitle.addClassName("stock-in");
                case No_Stock -> stockTitle.addClassName("stock-out");
                case Low_Stock -> stockTitle.addClassName("stock-low");
            }

            return stockTitle;


        }).setHeader("Materials status").setAutoWidth(true);

        grid.addComponentColumn(e->{



            VerticalLayout verticalLayout = new VerticalLayout();




            Span stock = commonComponents.spanCrafter(String.format("In stock %d %s",e.getMaterials().getInStock(),"units"),"stat-example");
            Span minThreshold = commonComponents.spanCrafter(String.format("%s %d","min threshold",e.getMaterials().getMinThresHold()),"stat-example");

            verticalLayout.add(
                    stock,
                    minThreshold
            );






            return verticalLayout;
        }).setHeader("Material stock").setAutoWidth(true);

        grid.addComponentColumn(e->{

            Span possible = commonComponents.spanCrafter("","stat-example");
            possible.getStyle().set("width", "fit-content");
            possible.addClassName("stock-badge");


            boolean enough = true;

            if(e.getAmountUsed() > e.getMaterials().getInStock()){
                enough = false;
            }


            if(enough) {
                possible.addClassName("stock-in");
                possible.setText("Available");
            }
            else{
                possible.addClassName("stock-out");
                possible.setText("Not available");
            }



            return possible;


        }).setHeader("Materials status").setAutoWidth(true);


        v.add(
                grid
        );



        dialog.add(grid);

        dialog.open();

    }



}
