package com.example.demo.Pages.EmployeePage;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.CurrentFilterDisplay;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.Common.Paganation;
import com.example.demo.ControllerModels.CommonDtos.EmployeePage.EmployeeOrderProjection;
import com.example.demo.ControllerModels.Filter.Employee.EmployeeFilterHolder;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Employee.Page.Components.EmployeeBriefExplanations;
import com.example.demo.Pages.Employee.Page.Components.EmployeeFilters;
import com.example.demo.Pages.Employee.Page.Components.EmployeeGrid;
import com.example.demo.Pages.Employee.Page.Components.EmployeeMiniStats;
import com.example.demo.Services.EmployeeService.EmployeeService;
import com.example.demo.Services.Orders.OrdersService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.util.Arrays;
import java.util.List;

@Route(value = "EmployeesDashBoard", layout = MainLayout.class)
public class EmployeePageDashboard extends VerticalLayout implements BeforeEnterObserver {

    CommonComponents commonComponents;
    Common common;

    OrdersService ordersService;

    public EmployeePageDashboard(CommonComponents commonComponents, Common common, OrdersService ordersService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.ordersService = ordersService;




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
                todayOverview(),
                todayOverview()
        );


        return  h;


    }


    public VerticalLayout workingStatus(){

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

        left.add(
                commonComponents.spanCrafter("Work status","stat-example"),
                commonComponents.spanCrafter("Working","activityFeed-name"),
                commonComponents.spanCrafter("Started at 08:02","stat-description"),
                commonComponents.spanCrafter("You have been working for","stat-description"),
                commonComponents.spanCrafter("6h 43m","activityFeed-name"),
                startEndWorkDay

        );


        VerticalLayout right = new VerticalLayout();
        right.setPadding(false);

        Div circle = new Div();
        circle.setText("75");

        circle.getStyle()
                .set("width", "100px")
                .set("height", "100px")
                .set("border-radius", "50%")
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "center")
                .set("font-size", "24px")
                .set("font-weight", "bold");


        right.add(
                createProgressCircle(25)
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
        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");
        v.setAlignItems(Alignment.CENTER);

        v.getStyle().set("flex", "1 1 252px");
        v.getStyle().set("max-width", "820px");
        v.getStyle().set("min-width", "252px");

        HorizontalLayout h = new HorizontalLayout();
        h.setPadding(false);

        h.add(
                overviewIslands(VaadinIcon.CLOCK,"Started at","08:02"),
                overviewIslands(VaadinIcon.CLOCK,"Working for","6h 43m"),
                overviewIslands(VaadinIcon.BOOK,"Orders today","4")

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

        Span span = commonComponents.spanCrafter( list.size()+ " available","stat-example");
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

            HorizontalLayout hh = new HorizontalLayout();

            for (String s : imageList) {
                System.out.println(s.substring(0, Math.min(s.length(), 50)));

                hh.add(commonComponents.imageCrafter(
                        s,
                        "100px",
                        "100px",
                        "5px"
                ));
            }

            return hh;
        });


        v.add(
                h,
                grid
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


































    public Div createProgressCircle(int completed) {

        completed = Math.max(0, Math.min(completed, 100));

        Div circle = new Div();
        circle.setText(completed + " / 100");

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
                                + completed + "%, #e0e0e0 0)");

        return circle;
    }



}
