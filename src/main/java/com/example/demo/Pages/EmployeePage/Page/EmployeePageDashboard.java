package com.example.demo.Pages.EmployeePage.Page;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.ImageViewer;
import com.example.demo.ControllerModels.CommonDtos.WorkDay;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.EmployeePage.Page.Components.DisplayActiveOrders;
import com.example.demo.Pages.EmployeePage.Page.Components.DisplayAvailableOrders;
import com.example.demo.Common.Logic.PageStuff.DefaultPageExplanation;
import com.example.demo.Services.Orders.OrdersService;
import com.example.demo.Services.WorkDoneService.WorkDoneService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Route(value = "EmployeesDashBoard", layout = MainLayout.class)
public class EmployeePageDashboard extends VerticalLayout implements BeforeEnterObserver {

    CommonComponents commonComponents;
    Common common;

    OrdersService ordersService;

    ImageViewer imageViewer;

    WorkDoneService workDoneService;

    // thigs to make page happen

    DisplayActiveOrders displayActiveOrders;

    DisplayAvailableOrders displayAvailableOrders;

    DefaultPageExplanation employeeDashboardExplanations;

    public EmployeePageDashboard(CommonComponents commonComponents, Common common, OrdersService ordersService,ImageViewer imageViewer,WorkDoneService workDoneService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.ordersService = ordersService;
        this.imageViewer = imageViewer;
        this.workDoneService = workDoneService;

        this.displayActiveOrders = new DisplayActiveOrders(commonComponents,common,ordersService);

        this.displayAvailableOrders = new DisplayAvailableOrders(commonComponents,common,ordersService,imageViewer);

        this.employeeDashboardExplanations = new DefaultPageExplanation(commonComponents,common);




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

        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");


        verticalLayout.add(
                employeeDashboardExplanations.briefExplanation("Dashboard"),
                dataAndWorkingHours(),
                displayAvailableOrders.availableOrders(ordersService.getEmployeeOrderProjection(),true),
                displayActiveOrders.myActiveOrders(ordersService.findEmployeeActiveOrders()));

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
                commonComponents.spanCrafter("Work status","activityFeed-name"),
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
                commonComponents.spanCrafter("Work overview","activityFeed-name")
        );

        v.add(
                hh,
                h
        );




        return  v;
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
