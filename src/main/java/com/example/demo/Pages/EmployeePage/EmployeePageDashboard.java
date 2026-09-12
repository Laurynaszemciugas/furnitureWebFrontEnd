package com.example.demo.Pages.EmployeePage;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.ImageViewer;
import com.example.demo.ControllerModels.CommonDtos.EmployeePage.EmployeeOrderProjection;
import com.example.demo.ControllerModels.CommonDtos.OrderJoin.OrderStepsToComplete;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.ControllerModels.CommonDtos.Product;
import com.example.demo.ControllerModels.CommonDtos.WorkDay;
import com.example.demo.Enums.ImageLogic;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.Enums.ProductFinishStepStatus;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Services.Orders.OrdersService;
import com.example.demo.Services.WorkDoneService.WorkDoneService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
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

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Route(value = "EmployeesDashBoard", layout = MainLayout.class)
public class EmployeePageDashboard extends VerticalLayout implements BeforeEnterObserver {

    CommonComponents commonComponents;
    Common common;

    OrdersService ordersService;

    ImageViewer imageViewer;

    WorkDoneService workDoneService;

    public EmployeePageDashboard(CommonComponents commonComponents, Common common, OrdersService ordersService,ImageViewer imageViewer,WorkDoneService workDoneService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.ordersService = ordersService;
        this.imageViewer = imageViewer;
        this.workDoneService = workDoneService;




        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);





        addClassName("animation-page");




    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        removeAll();






        add(mainLayout());

    }

    public VerticalLayout mainLayout() {

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addClassName("island");

        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");


        verticalLayout.add(
                dataAndWorkingHours(),
                availableOrders(),
                myActiveOrders());

        return verticalLayout;
    }


    public HorizontalLayout dataAndWorkingHours(){

        HorizontalLayout h = new HorizontalLayout();
        h.addClassName("layout-flex");
        h.setWidthFull();

        h.add(
                workingStatus(),
                todayOverview()
        );


        return  h;


    }


    public VerticalLayout workingStatus(){


        WorkDay workDay = workDoneService.getWorkDayInfo();


        Long minutes = Duration.between(workDay.getWorkDayCreated() == null ? LocalDateTime.now() : workDay.getWorkDayCreated(), LocalDateTime.now()).toMinutes();

        Long hours = minutes == null ? 0 : minutes / 60;
        Long minutess = minutes == null ? 0 : minutes % 60;


        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");

        v.getStyle().set("flex", "1 1 252px");
        v.getStyle().set("max-width", "820px");
        v.getStyle().set("min-width", "252px");


        HorizontalLayout h = new HorizontalLayout();
        h.setAlignItems(Alignment.CENTER);
        h.setPadding(false);

        VerticalLayout left = new VerticalLayout();
        left.setPadding(false);


        Button startEndWorkDay = new Button("Start work day");
        startEndWorkDay.addThemeVariants(ButtonVariant.PRIMARY);

        if(workDay.getEmployee() !=null){
            startEndWorkDay.addThemeVariants(ButtonVariant.ERROR);
            startEndWorkDay.setText("Stop work day");
        }
        else{
            startEndWorkDay.addThemeVariants(ButtonVariant.PRIMARY);
            startEndWorkDay.setText("Start work day");
        }

        startEndWorkDay.addClickListener(e->{

            if(startEndWorkDay.getThemeNames().contains("error")){
                common.warningConfirmation("Stop your work day at " + LocalDate.now() + " you have worked for " + String.format("%dh %dm",hours,minutess));
            }
            else{
                workDoneService.addWorkDay();
            }


        });

        common.setBooleanConsumer(e->{
            if(e){
                workDoneService.addWorkDay();
            }
        });





        String started = common.dateFormatterLocalDateTime(workDay.getWorkDayCreated() == null ? LocalDateTime.now() : workDay.getWorkDayCreated(), "HH:mm");
        if(workDay.getUser() == null){
            started = "Not started yet";
        }

        left.add(
                commonComponents.spanCrafter("Work status","stat-example"),
                commonComponents.spanCrafter(workDay.getWorkDayCreated() == null ? "Not working" : "Working" ,"activityFeed-name"),
                commonComponents.spanCrafter("Started at " + started ,"stat-description"),
                commonComponents.spanCrafter("You have been working for","stat-description"),
                commonComponents.spanCrafter(String.format("%dh %dm",hours,minutess),"activityFeed-name"),
                startEndWorkDay

        );


        VerticalLayout right = new VerticalLayout();
        right.setPadding(false);



        right.add(
                createProgressCircle(minutes)
        );


        h.add(
                left,
                right
        );

        v.add(
                h
        );

        return  v;

    }


    public VerticalLayout todayOverview(){

        WorkDay workDay = workDoneService.getWorkDayInfo();

        Long minutes = Duration.between(workDay.getWorkDayCreated() == null ? LocalDateTime.now() : workDay.getWorkDayCreated(), LocalDateTime.now()).toMinutes();

        Long hours = minutes == null ? 0 : minutes / 60;
        Long minutess = minutes == null ? 0 : minutes % 60;

        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");
        v.setAlignItems(Alignment.CENTER);

        v.getStyle().set("flex", "1 1 252px");
        v.getStyle().set("max-width", "820px");
        v.getStyle().set("min-width", "252px");

        HorizontalLayout h = new HorizontalLayout();
        h.setPadding(false);

        String started = common.dateFormatterLocalDateTime(workDay.getWorkDayCreated() == null ? LocalDateTime.now() : workDay.getWorkDayCreated(), "HH:mm");
        if(workDay.getUser() == null){
            started = "0h 0m";
        }

        h.add(
                overviewIslands(VaadinIcon.CLOCK,"Started at",started),
                overviewIslands(VaadinIcon.CLOCK,"Working for",String.format("%dh %dm",hours,minutess)),
                overviewIslands(VaadinIcon.BOOK,"Orders available",String.valueOf(ordersService.findHowManyItemsAreAvailable()))

        );

        HorizontalLayout hh = new HorizontalLayout();
        hh.setWidthFull();
        hh.setJustifyContentMode(JustifyContentMode.START);
        hh.setPadding(false);
        hh.add(
                commonComponents.spanCrafter("Today overview","stat-example")
        );

        v.add(
                hh,
                h
        );




        return  v;
    }



    public VerticalLayout availableOrders(){

        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");

        List<EmployeeOrderProjection> list = ordersService.getEmployeeOrderProjection();

        Grid<EmployeeOrderProjection> grid = new Grid<>(EmployeeOrderProjection.class,false);
        grid.setItems(list);
        grid.setHeightFull();
        grid.setHeight("500px");

        grid.setWidthFull();
        grid.setColumnReorderingAllowed(false);

        Span span = commonComponents.spanCrafter( ordersService.findHowManyItemsAreAvailable()+ " available","stat-example");
        span.addClassNames("new-badge","status-pending");


        Button viewAll = new Button("View all");
        viewAll.setSuffixComponent(VaadinIcon.ANGLE_RIGHT.create());

        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.setPadding(false);
        h.setJustifyContentMode(JustifyContentMode.END);
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
                    imageList.get(0),
                    "150px",
                    "150px",
                    "5px"
            );



            image.addClickListener(ee->{
                imageViewer.popOver(imageList,imageList.get(0));
            });
            HorizontalLayout hh = new HorizontalLayout();
            hh.setWidthFull();
            hh.setAlignItems(Alignment.CENTER);



            HorizontalLayout first = new HorizontalLayout();

            first.add(
                    new VerticalLayout(commonComponents.spanCrafter("#" + e.getId(),"activityFeed-name"), commonComponents.spanCrafter("Some name","stat-example"))

            );

            HorizontalLayout second = new HorizontalLayout();

            second.add(
                    miniStats("Quantity",String.valueOf(e.getAmountOfItems())),
                    miniStats("Created",common.dateFormatterLocalDateTime(e.getCreated(),"dd MMM yyyy, HH:mm")),
                    miniStats("Due date",common.dateFormatterLocalDateTime(e.getDueDate(),"dd MMM yyyy, HH:mm")),
                    miniStats("Materials",e.getOrderStatus() == OrderStatus.LACK_OF_SUPPLY ? "Not available" : "Available"),
                    employeeOnTheProject(e.getEmployeeImages().toString(), e.getEmployeeImages().toString())
            );

            VerticalLayout allHolder = new VerticalLayout();
            allHolder.setSpacing(false);
            allHolder.add(first,second);



            hh.add(
                    image,
                    allHolder
            );

            hh.setJustifyContentMode(JustifyContentMode.BETWEEN);

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

                openDetailsOfTheOrder(orders);


            });

            Button acceptOrders = commonComponents.normalThemeButtonNoNavigate("Accept order", ButtonVariant.LUMO_PRIMARY);

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

    public void openDetailsOfTheOrder(Orders orders){

        Dialog dialog = new Dialog();

        dialog.getHeader().add(
                commonComponents.spanCrafter("Order #" + orders.getId() + " products","activityFeed-name")
        );

        Button close = new Button("Close", e-> dialog.close());
        close.addThemeVariants(ButtonVariant.PRIMARY);


        VerticalLayout v = new VerticalLayout();




            for(var productData : orders.getProductsData()){

                v.add(detailCrafter(productData.getProduct(),orders.getOrderSteps(), productData.getAmountOfProduct()));
            }






//        for(var s : orders.getProductsData()) {
//            v.add(detailCrafter(s.getProduct(),s.getAmountOfProduct()));
//        }

        dialog.add(v);

        dialog.open();

    }

    public VerticalLayout detailCrafter(Product product,List<OrderStepsToComplete> steps, Long amountToMake){

        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");

        v.add(
                commonComponents.spanCrafter(String.format("%s x %d", product.getProductName(),amountToMake), "activityFeed-name")
        );

        HorizontalLayout h = new HorizontalLayout();
        h.setPadding(false);

        h.addClassName("animated-card");

        h.setAlignItems(Alignment.CENTER);
        h.setJustifyContentMode(JustifyContentMode.CENTER);

        List<String> images = new ArrayList<>();

        for(var s : product.getImages()) {

            if (s.getImageLogic().equals(ImageLogic.Main)) {
                images.add(s.getImageUrl());

            }

        }





        Image image = commonComponents.imageCrafter(
                images.get(0),
                "150px",
                "150px",
                "5px"
        );

        image.addClickListener(ee->{
            imageViewer.popOver(images,images.get(0));
        });

        Double stepsThatAreAvailable = 0.0;
        Double totalSteps = Double.valueOf(product.getSteps().size());

        Long stepsThatAreAvailableLong = 0L;
        Long totalStepsLong = (long) product.getSteps().size();


        for(var s : product.getSteps()){


            if(s.getProductFinishStepStatus() == null){
                continue;
            }

            if(s.getProductFinishStepStatus().equals(ProductFinishStepStatus.NOT_STARTED)){
                stepsThatAreAvailable++;
                stepsThatAreAvailableLong++;
            }


        }

        Double percentage;

        try {
             percentage = Double.valueOf(Math.abs(totalSteps - stepsThatAreAvailable) / totalSteps);

            String number = String.format("%.2f",percentage);


            percentage = Double.valueOf(number);

        } catch (Exception e) {
            percentage = 1.0;
        }

        if(totalSteps == 0){
            percentage = 1.0;
        }




        ProgressBar progressBar = new ProgressBar();
        progressBar.setWidth("200px");
        progressBar.setHeight("10px");
        progressBar.setVisible(true);
        progressBar.setValue(percentage);

        Span stepsSpan;

        if(totalSteps == 0){
            stepsSpan = commonComponents.spanCrafter("All steps completed","stat-example");
        }
        else{
            System.out.println(stepsThatAreAvailableLong + " " + totalStepsLong);
            stepsSpan = commonComponents.spanCrafter(String.format("%d / %d steps completed", totalStepsLong, Math.abs(stepsThatAreAvailableLong - totalStepsLong)),"stat-example");
        }




        VerticalLayout stepHolder = new VerticalLayout();
        stepHolder.addClassName("island");
        stepHolder.setWidthFull();
        stepHolder.setVisible(false);
        stepHolder.addClassName("smooth-panel");
        stepHolder.add(
                manufacturingSteps(images.get(0),steps, product)
        );


        Button viewDetails = new Button("View details");
        viewDetails.addClickListener(e->{
           if(stepHolder.isVisible()){
               stepHolder.setVisible(false);
           }
           else{
               stepHolder.setVisible(true);
           }
        });

        h.add(
                image,
                progressBar,
                commonComponents.spanCrafter(progressBar.getValue()*100 + "%","stat-example"),
                stepsSpan,
                viewDetails
                );

        v.add(
                h,
                stepHolder
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




    public VerticalLayout myActiveOrders(){

        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();
        v.addClassName("island");
        v.addClassName("layout-flex");

        Grid<String> grid = new Grid<>(String.class,true);
        grid.setHeightFull();
        grid.setHeight("250px");

        Span span = commonComponents.spanCrafter("4 available","stat-example");
        span.addClassNames("new-badge","status-in-progress");


        Button viewAll = new Button("View all");
        viewAll.setSuffixComponent(VaadinIcon.ANGLE_RIGHT.create());

        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.setPadding(false);
        h.setJustifyContentMode(JustifyContentMode.END);
        h.add(
                commonComponents.doubleValueRow(commonComponents.spanCrafter("My active orders","activityFeed-name"),span),
                commonComponents.spaceFiller(),
                viewAll
        );


        v.add(
                h,
                grid
        );


        return v;

    }


    public HorizontalLayout manufacturingSteps(String mainImageUrl, List<OrderStepsToComplete> steps, Product product){

        VerticalLayout allHolder = new VerticalLayout();
        allHolder.setWidthFull();
        allHolder.setPadding(false);

        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();



        Image image = commonComponents.imageCrafter(
                mainImageUrl,
                "100px",
                "100px",
                "5px"
        );

        VerticalLayout v = new VerticalLayout();

        v.add(
                commonComponents.spanCrafter("Manufacturing steps","stat-example")
        );


        for(var s : steps){

            Icon icon = new Icon();

            if(s.getProductFinishStepStatus().equals(ProductFinishStepStatus.FINISHED)){
                icon = commonComponents.iconCrafter(VaadinIcon.CHECK,"15px","green");
                icon.setTooltipText("Step is completed");
            }
            else if(s.getProductFinishStepStatus().equals(ProductFinishStepStatus.IN_PROGRESS)){
                icon = commonComponents.iconCrafter(VaadinIcon.COG,"15px","blue");
                icon.setTooltipText("Step is in progress");
            }
            else{
                icon = commonComponents.iconCrafter(VaadinIcon.CLOCK,"15px","red");
                icon.setTooltipText("Step is waiting to be started");
            }

            // employee can be displayed here btw

            v.add(
                   commonComponents.doubleValueRow(icon,commonComponents.spanCrafter(String.format("%d. %s - %s",s.getProductFinishSteps().getStepId(),s.getProductFinishSteps().getStepName(), s.getProductFinishSteps().getStepDescription()),"stat-example"))
            );
        }

        if(steps.isEmpty()){
            v.add(
                    commonComponents.spanCrafter("No steps found for this order","stat-example")
            );
        }

        VerticalLayout productMaterials = new VerticalLayout();

        productMaterials.add(
                commonComponents.spanCrafter("Materials used","stat-example")
        );

        for(var s : product.getMaterials()){

            productMaterials.add(
                    commonComponents.doubleValueRow(commonComponents.iconCrafter(VaadinIcon.CHECK,"15px","green"),commonComponents.spanCrafter(String.format("%s - %d units ",s.getMaterials().getMaterialName(),s.getAmountUsed()),"stat-example"))
            );
        }

        if(product.getMaterials().isEmpty()){
            productMaterials.add(
                    commonComponents.spanCrafter("No materials found for this order","stat-example")
            );
        }






        h.add(
                image,
                v,
                commonComponents.spaceFiller(),
                productMaterials
        );


        return h;
    }












    public VerticalLayout overviewIslands(VaadinIcon icon, String lilDesc, String value){

        VerticalLayout v = new VerticalLayout();
        v.setAlignItems(Alignment.CENTER);
        v.add(
                commonComponents.iconCrafter(icon,"25px","Black"),
                commonComponents.spanCrafter(lilDesc,"stat-description"),
                commonComponents.spanCrafter(value,"stat-value")
        );


        return v;

    }


































    public Div createProgressCircle(Long completed) {


        double percentage = (double) completed / 480 * 100;



        Div circle = new Div();
        circle.setText(String.format("%.0f",percentage) + " / 100");

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

        return circle;
    }



}
