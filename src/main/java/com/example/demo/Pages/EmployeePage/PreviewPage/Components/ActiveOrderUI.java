package com.example.demo.Pages.EmployeePage.PreviewPage.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.CommonDtos.OrderJoin.OrderProducts;
import com.example.demo.ControllerModels.CommonDtos.OrderJoin.OrderStepsToComplete;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.ControllerModels.CommonDtos.ProductJoin.ProductMaterials;
import com.example.demo.ControllerModels.CommonDtos.User;
import com.example.demo.Enums.ImageLogic;
import com.example.demo.Enums.ProductFinishStepStatus;
import com.example.demo.Services.Orders.OrdersService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.component.textfield.IntegerField;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ActiveOrderUI {

    CommonComponents commonComponents;
    Common common;

    OrdersService ordersService;

    Consumer<Boolean> reload;
    Consumer<Boolean> reloadOutSide;


    Long currentOrderId = 0L;

    Map<String, Dialog> dialogMemory = new HashMap<>();

    public ActiveOrderUI(CommonComponents commonComponents, Common common, OrdersService ordersService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.ordersService = ordersService;
    }


    public void setReload(Consumer<Boolean> reload) {
        this.reload = reload;
    }

    public void setReloadOutSide(Consumer<Boolean> reloadOutSide) {
        this.reloadOutSide = reloadOutSide;
    }

    public VerticalLayout orderName(Orders currentOrder){

        currentOrderId = currentOrder.getId();

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
                commonComponents.spanCrafter(String.format("Active Order #%d",currentOrder.getId()),"stat-value"),
                status
        );

        v.add(
                h,
                commonComponents.spanCrafter("Some name","stat-description")
        );



        return v;
    }


    public VerticalLayout activeOrders(Orders currentOrder){
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
                activeOrderHolder(currentOrder.getProductsData())
        );





        return v;
    }




    public VerticalLayout activeOrderHolder(List<OrderProducts> productsData){



        VerticalLayout v = new VerticalLayout();
        v.setPadding(false);
        v.setSpacing(false);

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

            v.add(activeOrderPreview(mainImageUrl,product.getProduct().getProductName(),product.getProduct().getSku(),product.getAmountOfProduct(),totalSteps,totalStepsCompleted,product.getOrderSteps(), product.getProduct().getMaterials()));
        }

        setReload(e->{


            System.out.println("reload in side");


            for (var dialog : dialogMemory.values()) {
                dialog.close();
            }

            dialogMemory.clear();

            v.removeAll();

            Orders order = ordersService.getSelectedOrder(currentOrderId);

            for(var product : order.getProductsData()){

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

                v.add(activeOrderPreview(mainImageUrl,product.getProduct().getProductName(),product.getProduct().getSku(),product.getAmountOfProduct(),totalSteps,totalStepsCompleted,product.getOrderSteps(), product.getProduct().getMaterials()));



            }




        });


        return v;

    }






    public VerticalLayout activeOrderPreview(String mainImage, String productName, String productSKU, Long howMany, Long totalSteps, Long totalStepsCompleted, List<OrderStepsToComplete> stepsList, List<ProductMaterials> productMaterials){







        // REM
        HorizontalLayout stepsRequired = new HorizontalLayout();
        stepsRequired.setWidthFull();
        stepsRequired.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);











        HorizontalLayout h = new HorizontalLayout();
        h.addClassName("island");
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



        h.add(
                allHolder

        );

        VerticalLayout v = new VerticalLayout();


        v.add(

                expandSteps( mainImage,  productName,  productSKU,  howMany,  totalSteps,  totalStepsCompleted, stepsList, productMaterials)
        );

        Dialog dialog = new Dialog();
        dialog.setWidth("1000px");



        VerticalLayout stepHolderCompleted = new VerticalLayout();
        stepHolderCompleted.setPadding(false);


        stepHolderCompleted.add(
                h,
                commonComponents.spanCrafter("Manufacturing steps","activityFeed-name"),
                stepHolder(stepsList,productSKU)
        );

        dialog.add(stepHolderCompleted);

        dialogMemory.put(productSKU,dialog);






        v.addClickListener(e->{
            dialog.open();

        });





        return v;
    }


    // ///////////////////////////


    public VerticalLayout stepHolder(List<OrderStepsToComplete> stepsList, String sku){

        VerticalLayout v = new VerticalLayout();
        v.setPadding(false);




        for(var s : stepsList){




            v.add(stepCrafter(s.getId(),s.getStepId(),s.getStepName(), s.getStepDescription(), s.getStepsNeeded(), s.getProductFinishStepStatus(), s.getEmployee(), s.getCreated(), s.getStepsCompleted(),sku));


        }

        ordersService.setSuccess(e->{

        });






        return v;

    }


    public VerticalLayout stepCrafter(Long id,Long stepId, String stepName, String stepDesc, Long totalSteps, ProductFinishStepStatus status, User emp, LocalDateTime create, Long totalStepsCompleted,String productSKU){


        VerticalLayout competedItemsUI = new VerticalLayout();
        competedItemsUI.setVisible(false);


        VerticalLayout v = new VerticalLayout();
        v.addClassName("island-hover");

        if(status.equals(ProductFinishStepStatus.NOT_STARTED)){
            v.getStyle().set("cursor", "default");
            Tooltip.forComponent(competedItemsUI)
                    .withText("Click to access the quantity change ability");
        }
        else{
            v.getStyle().set("cursor", "pointer");

        }

        IntegerField integerField = new IntegerField();
        integerField.setValue(Math.toIntExact(totalStepsCompleted));
        integerField.setStep(1);
        integerField.setMax(Math.toIntExact(totalSteps));
        integerField.setMin(0);
        integerField.setStepButtonsVisible(true);

        integerField.addValueChangeListener(e->{

            ordersService.updateStep(id, Long.valueOf(e.getValue()));

            reload.accept(true);
            reloadOutSide.accept(true);

            dialogMemory.get(productSKU).close();
            dialogMemory.get(productSKU).open();

        });


        HorizontalLayout compltedFieldHolder = new HorizontalLayout(integerField, commonComponents.spanCrafter(String.format("/%d",totalSteps),"stat-description"));

        compltedFieldHolder.setAlignItems(FlexComponent.Alignment.CENTER);

        competedItemsUI.add(
                commonComponents.doubleValueRow(commonComponents.spanCrafter("Update progress","stat-example"),commonComponents.spanCrafter("(Completed quantity)","stat-description")),
                compltedFieldHolder
        );

        v.addClickListener(e->{

            if(!status.equals(ProductFinishStepStatus.NOT_STARTED)) {
                if (competedItemsUI.isVisible()) {
                    competedItemsUI.setVisible(false);
                } else {
                    competedItemsUI.setVisible(true);
                }
            }
        });


        HorizontalLayout h = new HorizontalLayout();
        h.setAlignItems(FlexComponent.Alignment.CENTER);
        h.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        h.setWidthFull();


        Button finishStep = new Button("Finish step");
        finishStep.addThemeVariants(ButtonVariant.PRIMARY);
        finishStep.setVisible(false);

        finishStep.addClickListener(e->{

            ordersService.completeStep(id);
            reload.accept(true);


            dialogMemory.get(productSKU).close();
            dialogMemory.get(productSKU).open();

        });

        Button startStep = new Button("Start step");
        startStep.addClickListener(e->{
            ordersService.acceptStep(id);
            reload.accept(true);

            dialogMemory.get(productSKU).close();
            dialogMemory.get(productSKU).open();

        });
        startStep.setVisible(false);


        Span statusDisplay = commonComponents.spanCrafter(status.getDisplayName(),"stat-example");
        statusDisplay.getStyle().set("width", "fit-content");
        statusDisplay.addClassName("stock-badge");

        Span startedPerson = new Span();

        switch (status){
            case FINISHED -> {
                startedPerson = commonComponents.spanCrafter(emp == null ? "Started by No one" : "Completed by " + emp.getFullName(),"stat-example");
                statusDisplay.addClassName("stock-in");
            }



            case IN_PROGRESS -> {
                statusDisplay.addClassName("status-in-progress");
                startedPerson = commonComponents.spanCrafter(emp == null ? "Started by No one" : "Started by " + emp.getFullName(),"stat-example");
            }
            case NOT_STARTED -> {
                statusDisplay.addClassName("status-none");
            }


        }





        Span startedDate = commonComponents.spanCrafter(create == null ? "Not started" : create.toString(),"stat-example");

        if(status.equals(ProductFinishStepStatus.FINISHED)){
            finishStep.setVisible(false);
            startStep.setVisible(false);
            startedPerson.setVisible(true);
            startedDate.setVisible(true);
        }
        else if (status.equals(ProductFinishStepStatus.IN_PROGRESS)){
            finishStep.setVisible(true);
            startStep.setVisible(false);
            startedPerson.setVisible(true);
            startedDate.setVisible(true);
        }
        else{
            finishStep.setVisible(false);
            startStep.setVisible(true);
            startedPerson.setVisible(false);
            startedDate.setVisible(false);
        }

        HorizontalLayout stepIdShell = new HorizontalLayout();
        stepIdShell.setAlignItems(FlexComponent.Alignment.CENTER);
        stepIdShell.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        stepIdShell.getStyle().set("width","25px").set("height","25px").set("border-radius","50%").setBackgroundColor("rgba(34, 117, 243, 0.15)");

        stepIdShell.add(
                commonComponents.spanCrafter(stepId.toString(),"stat-example")
        );

        Span desc = commonComponents.spanCrafterWordNoHide(stepDesc,"stat-example");
        desc.setWidth("200px");

            h.add(
                   new HorizontalLayout( stepIdShell,
                           new VerticalLayout(commonComponents.spanCrafter(stepName,"stat-example"),
                                   desc,
                                   commonComponents.spanCrafter(String.format("%d/%d Completed",totalStepsCompleted,totalSteps),"stat-description"))),

                    new HorizontalLayout(
                    new VerticalLayout(


                            statusDisplay,
                            startedPerson,
                            startedDate

                    )),


                    finishStep,
                    startStep

            );




            v.setPadding(false);

            v.add(
                    h,
                    competedItemsUI
            );




        return v;

    }

    public VerticalLayout expandSteps(String mainImage, String productName, String productSKU, Long howMany, Long totalSteps, Long totalStepsCompleted, List<OrderStepsToComplete> stepsList, List<ProductMaterials> productMaterials){

        HorizontalLayout stepsMaterialsHolder = new HorizontalLayout();
        stepsMaterialsHolder.setWidthFull();
        stepsMaterialsHolder.setVisible(false);
        stepsMaterialsHolder.addClassName("removetop");
        stepsMaterialsHolder.addClassName("layout-flex");

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



        h.add(
                allHolder,
                commonComponents.iconCrafter(VaadinIcon.ANGLE_DOWN,"25","blue")
        );

        VerticalLayout v = new VerticalLayout();
        v.addClassName("island-hover");
        v.getStyle().set("cursor","pointer");


        v.add(
                h,
                stepsMaterialsHolder
        );



        return v;

    }









}
