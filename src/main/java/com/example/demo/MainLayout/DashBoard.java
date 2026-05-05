package com.example.demo.MainLayout;

import com.example.demo.Common.Common;
import com.example.demo.ControllerModels.DashBoard.DashBoardEmployeeMiniInfo;
import com.example.demo.ControllerModels.DashBoard.DashBoardMaterialStock;
import com.example.demo.ControllerModels.DashBoard.DashBoardMaterialUsageInfo;
import com.example.demo.ControllerModels.DashBoard.DashBoardMonthlyOrdersCompleted;
import com.example.demo.ControllerMostUsedCode.DbMostUsed;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.signals.Signal;

import java.time.LocalDate;


@Route(value = "DashBoard", layout = MainLayout.class)
public class DashBoard extends VerticalLayout {

    DbMostUsed dbMostUsed;
    Common common;

    public DashBoard(
            DbMostUsed dbMostUsed,
            Common common
    ){
        this.dbMostUsed = dbMostUsed;
        this.common = common;

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(Alignment.CENTER);



        add(mainLayout());

    }

    // main layout all stuff go here

    public VerticalLayout mainLayout(){
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top","5px");
        verticalLayout.addClassName("main-island");




        verticalLayout.add(miniStats(),activityGraphHolder(),smth());

        verticalLayout.setAlignItems(Alignment.CENTER);

        return verticalLayout;
    }







    // ===================================== mini stats =====================================

    public HorizontalLayout miniStats(){

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthFull();
        horizontalLayout.getStyle().set("flex-wrap","wrap");
        horizontalLayout.setJustifyContentMode(JustifyContentMode.CENTER);


        // ------------------------ Monthly Orders 'Completed' first mini stat ---------------------------------
        DashBoardMonthlyOrdersCompleted ordersCompletedCompleted = new DashBoardMonthlyOrdersCompleted();
        ordersCompletedCompleted.setThisMonthOrders(100L);
        ordersCompletedCompleted.setPreviousMonthOrders(200L);

        // ------------------------ Material Stock 'Current' second mini stat ---------------------------------


        DashBoardMaterialStock dashBoardMaterialStock = new DashBoardMaterialStock();
        dashBoardMaterialStock.setLowMaterial(20);


        // ------------------------ Material mini information third mini stat ---------------------------------
        DashBoardMaterialUsageInfo materialData = new DashBoardMaterialUsageInfo();
        materialData.setMostUsedMaterial("wood");
        materialData.setTotalMaterialsUsed(10);
        materialData.setTotalUsedMaterialCost(152.5);
        materialData.setLastMonthTotalUsedMaterialCost(12);

        // ------------------------ Employee mini information fourth mini stat ---------------------------------
        DashBoardEmployeeMiniInfo employeeData = new DashBoardEmployeeMiniInfo();




        // fourth mini stat large auto resize
        HorizontalLayout employeeCard = new HorizontalLayout(employeeMiniStat(
                "Employee mini information",
                "Screenshot 2026-04-27 001745.png",
                employeeData,
                "Material usage this month compared to last",
                null,
                "170px"));
        employeeCard.setMaxWidth("700px");


        // all the remaining mini stats
        horizontalLayout.add(

                orderCompletedMiniStats(
                        "Monthly Orders 'Completed'",
                        "Screenshot 2026-04-27 001745.png",
                        "Orders",
                        ordersCompletedCompleted,
                        "Compared to last months 'Completed' orders",
                        "310px",
                        "170px"),
                materialsStockMiniStats(
                        "Material Stock 'Current'",
                        "Screenshot 2026-04-27 001745.png",
                        dashBoardMaterialStock,
                        "Current material stock",
                        "310px",
                        "170px"),
                materialUsageMiniStat(
                        "Material mini information",
                        "Screenshot 2026-04-27 001745.png",
                        materialData,
                        "Material usage this month compared to last",
                        "310px",
                        "170px"),

                employeeCard

        );

        horizontalLayout.setFlexGrow(1, employeeCard);



        return  horizontalLayout;

    }



    public VerticalLayout orderCompletedMiniStats(
            String name,
            String image,
            String units,
            DashBoardMonthlyOrdersCompleted ordersData,
            String description,
            String width,
            String height
    ) {

        long value = 0;
        long previousValue = 0;

        if(ordersData != null) {
            value = ordersData.getThisMonthOrders();
            previousValue = ordersData.getPreviousMonthOrders();
        }

        double changePercent = dbMostUsed.diffrenceCalculator(value, previousValue);


        Span trend =  dbMostUsed.spanCrafter((changePercent >= 0 ? "▲ " : "▼ ") + String.format("%.2f",Math.abs(changePercent)) + "%" ,"stat-trend");

        dbMostUsed.trendColoring("Green","Red",changePercent, trend);

        // Layout
        VerticalLayout island = new VerticalLayout(
                dbMostUsed.spanCrafter(name,"stat-title"),
                dbMostUsed.doubleValueRow(dbMostUsed.spanCrafter(String.valueOf(value),"stat-value"), dbMostUsed.spanCrafter(units,"stat-units")),
                dbMostUsed.doubleValueRow(dbMostUsed.spanCrafter("Last Month " + previousValue,"stat-example"), dbMostUsed.spanCrafter(units,"stat-units")),
                trend,
                dbMostUsed.spanCrafter(description,"stat-description"));
        island.addClassName("stat-card");

        island.setPadding(true);
        island.setSpacing(false);
        island.setWidth(width);
        island.setHeight(height);

        return island;
    }


    public VerticalLayout materialsStockMiniStats(
            String name,
            String image,
            DashBoardMaterialStock dashBoardMaterialStock,
            String description,
            String width,
            String height
    ) {


        long lowMaterial = 0;
        long noStockMaterial = 0;

        if(dashBoardMaterialStock != null){
            lowMaterial = dashBoardMaterialStock.getLowMaterial();
            noStockMaterial = dashBoardMaterialStock.getNoStockMaterial();
        }


        // Layout
        VerticalLayout island = new VerticalLayout(
                dbMostUsed.spanCrafter(name,"stat-title"),
                dbMostUsed.doubleValueRow(dbMostUsed.spanCrafter(String.valueOf(lowMaterial),"stat-value"), dbMostUsed.spanCrafter("Low material stock","stat-unit")),
                dbMostUsed.doubleValueRow(dbMostUsed.spanCrafter(String.valueOf(noStockMaterial),"stat-value"), dbMostUsed.spanCrafter("No material stock","stat-unit")),
                dbMostUsed.spanCrafter(description,"stat-description"));
        island.addClassName("stat-card");

        island.setPadding(true);
        island.setSpacing(false);
        island.setWidth(width);
        island.setHeight(height);

        return island;
    }


    public VerticalLayout materialUsageMiniStat(
            String name,
            String image,
            DashBoardMaterialUsageInfo materialData,
            String description,
            String width,
            String height
    ) {
        String mostUsedMaterial = "-";
        long totalMaterialUsageCount = 0;
        double totalCostOfMaterialsUsedThisMonth = 0;
        double totalCostOfMaterialUsedLastMonth = 0.0;

        if(materialData !=null) {
            mostUsedMaterial = materialData.getMostUsedMaterial();
            totalMaterialUsageCount = materialData.getTotalMaterialsUsed();
            totalCostOfMaterialsUsedThisMonth = materialData.getTotalUsedMaterialCost();
            totalCostOfMaterialUsedLastMonth = materialData.getLastMonthTotalUsedMaterialCost();
        }


        double changePercent = dbMostUsed.diffrenceCalculator(totalCostOfMaterialsUsedThisMonth,totalCostOfMaterialUsedLastMonth);


        Span trend =  dbMostUsed.spanCrafter((changePercent >= 0 ? "▲ " : "▼ ") + String.format("%.2f",Math.abs(changePercent)) + "%" ,"stat-trend");

       dbMostUsed.trendColoring("Green","Red",changePercent, trend);

        Span desc = new Span(description);
        desc.addClassName("stat-description");



        // Layout
        VerticalLayout island = new VerticalLayout(
                dbMostUsed.spanCrafter(name,"stat-title"),
                dbMostUsed.doubleValueRow(dbMostUsed.spanCrafter(String.valueOf(totalCostOfMaterialsUsedThisMonth),"stat-value"), dbMostUsed.spanCrafter("Eur","stat-unit")),
                dbMostUsed.doubleValueRow(dbMostUsed.spanCrafter("Last month " + totalCostOfMaterialUsedLastMonth,"stat-example"), dbMostUsed.spanCrafter("Eur","stat-unit") ),
                trend,
                dbMostUsed.doubleValueRow(dbMostUsed.spanCrafter(mostUsedMaterial,"stat-example"), dbMostUsed.spanCrafter("Compared to all usage -  " + totalMaterialUsageCount,"stat-description")));


        island.addClassName("stat-card");

        island.setPadding(true);
        island.setSpacing(false);
        island.setWidth(width);
        island.setHeight(height);

        return island;
    }


    public VerticalLayout employeeMiniStat(
            String name,
            String image,
            DashBoardEmployeeMiniInfo employeeData,
            String description,
            String width,
            String height
    ) {
        String topEmployee = "None";
        long topEmployeeProduced = 0;
        double totalPaidThisMonth = 0.0;
        double totalUnpaidThisMonth = 0.0;
        double totalPaidLastMonth = 0.0;
        double totalUnpaidLastMonth = 0.0;



        if(employeeData.getTopEmployee() != null) {
            topEmployee = employeeData.getTopEmployee();
            topEmployeeProduced = employeeData.getTopEmployeeProduced();
            totalPaidThisMonth = employeeData.getTotalPaidSalary();
            totalUnpaidThisMonth = employeeData.getTotalUnpaidSalary();
            totalPaidLastMonth = employeeData.getTotalPaidLastMonth();
            totalUnpaidLastMonth = employeeData.getTotalUnpaidLastMonth();
        }

        // find change according to this month and previous

        double changePercentPaid = dbMostUsed.diffrenceCalculator(totalPaidThisMonth,totalPaidLastMonth);

        double changePercentUnPaid =  dbMostUsed.diffrenceCalculator(totalUnpaidThisMonth,totalUnpaidLastMonth);

        // show trent according to change

       Span trend1 =  dbMostUsed.spanCrafter((changePercentPaid >= 0 ? "▲ " : "▼ ") + String.format("%.2f",Math.abs(changePercentPaid)) + "%" ,"stat-trend");

       Span trend2 =  dbMostUsed.spanCrafter((changePercentUnPaid >= 0 ? "▲ " : "▼ ") + String.format("%.2f",Math.abs(changePercentUnPaid)) + "%" ,"stat-trend");


       // change color according to change
        dbMostUsed.trendColoring("red","green",changePercentPaid, trend1);
        dbMostUsed.trendColoring("red","green",changePercentUnPaid, trend2);


        HorizontalLayout trendHolder2 = dbMostUsed.doubleValueRow(trend2, dbMostUsed.spanCrafter("Compared to last month","stat-description"));
        trendHolder2.setWidth("300px");

        HorizontalLayout trendHolder1 = dbMostUsed.doubleValueRow(trend1,dbMostUsed.spanCrafter("Compared to last month","stat-description"));
        trendHolder1.setWidth("300px");



        // Layout
        VerticalLayout island = new VerticalLayout(
                dbMostUsed.spanCrafter(name,"stat-title"),
                dbMostUsed.doubleValueRow(dbMostUsed.spanCrafter(topEmployee,"stat-value"), dbMostUsed.spanCrafter(topEmployeeProduced + " units produced","stat-unit")),
                dbMostUsed.tripleValueRow(dbMostUsed.spanCrafter(String.valueOf(totalPaidThisMonth),"stat-example"), dbMostUsed.spanCrafter("This month paid salary Eur  ","stat-description"),trendHolder1),
                dbMostUsed.tripleValueRow(dbMostUsed.spanCrafter(String.valueOf(totalUnpaidThisMonth),"stat-example"), dbMostUsed.spanCrafter("This month unpaid salary Eur  ","stat-description"),trendHolder2),
                dbMostUsed.spanCrafter(description,"stat-description")

                );






        island.addClassName("stat-card");

        island.setPadding(true);
        island.setSpacing(false);
        island.setWidthFull();
        island.setHeight(height);

        return island;
    }




    // ===================================== Activity feed and the graph =====================================

    // do list

    public VerticalLayout activityFeedCrafter(){


        VerticalLayout activityFeedHolder = new VerticalLayout(dbMostUsed.spanCrafter("Activity feed","stat-value"));
        activityFeedHolder.addClassName("island");
        activityFeedHolder.setMaxWidth("500px");
        activityFeedHolder.setMaxHeight("600px");


        HorizontalLayout buttonAtTheBottom = new HorizontalLayout(common.normalThemeButton("View All Logs", DashBoard.class, ButtonVariant.LUMO_PRIMARY));
        buttonAtTheBottom.setWidthFull();
        buttonAtTheBottom.setJustifyContentMode(JustifyContentMode.CENTER);


        //fix add the action logic to add the info

        VerticalLayout actionsOfTheSystems = new VerticalLayout();
        actionsOfTheSystems.add(activityLogCrafter("Ultra 4k 178545hz asus gigabyte monitor","bob smith",5),activityLogCrafter("Ultra 4k 178545hz asus gigabyte monitor","bob smith",5),activityLogCrafter("Ultra 4k 178545hz asus gigabyte monitor","bob smith",5),activityLogCrafter("Ultra 4k 178545hz asus gigabyte monitor","bob smith",5));


        Scroller scroller = new Scroller(actionsOfTheSystems);
        scroller.setSizeFull();


        activityFeedHolder.add(scroller,buttonAtTheBottom);

        return  activityFeedHolder;
    }

    // log crafter for "activityFeedCrafter"
    public HorizontalLayout activityLogCrafter(String nameOfTheUpdatedCreatedItem, String whoMadeAction, long howLongAgo){
        HorizontalLayout iconHolder = new HorizontalLayout(common.iconCrafter(VaadinIcon.CIRCLE,"20px","green"));
        iconHolder.getStyle().set("margin-top","5px");

        // how  long ago is in minutes
        long timePassed = howLongAgo;
        String timeName = timePassed == 1 ? "Minute ago" : "Minutes ago";
        if(howLongAgo >= 60){
            timePassed = howLongAgo / 60;
            timeName =  timePassed == 1 ? "Hour ago" : "Hours ago";
        }
        if(timePassed >= 24){
            timePassed = timePassed /24;
            timeName = timePassed == 1 ? "Day ago" : "Days ago";
        }
        if(timePassed >=30){
            timePassed = timePassed / 30;
            timeName = timePassed == 1 ? "Month ago" : "Months ago";
        }
        if(timePassed >= 48){
            timePassed = timePassed / 48;
            timeName = timePassed == 1 ? "Year ago" : "Years ago";;
        }



        VerticalLayout verticalLayout = new VerticalLayout(dbMostUsed.spanCrafterWordNoHide(nameOfTheUpdatedCreatedItem,"activityFeed-name"), dbMostUsed.spanCrafter(String.format("%s %s %d %s",whoMadeAction, "●", timePassed,timeName ),"stat-description"));
        verticalLayout.setPadding(false);
        verticalLayout.setSpacing(false);

        HorizontalLayout activityFeedHolder = new HorizontalLayout(iconHolder,verticalLayout);
        activityFeedHolder.addClassName("island");
        activityFeedHolder.setWidthFull();


        return  activityFeedHolder;

    }

    // grapth for smth
    public VerticalLayout graph(){

        VerticalLayout graph = new VerticalLayout(
                dbMostUsed.doubleValueRow(
                        dbMostUsed.spanCrafterWordNoHide("Current month revenue graph","stat-value"),
                        dbMostUsed.spanCrafterWordNoHide(String.format("%s %s %s %s",
                                "From",
                                common.dateCrafter(0,0,0,0,true),
                                "To",
                                common.dateCrafter(0,1,1,0,true)),"stat-description")));

        graph.addClassName("island");
        graph.getStyle().set("flex-wrap","wrap");


        return graph;



    }

    // graph and the activitylog holder
    public HorizontalLayout activityGraphHolder(){
        VerticalLayout activityFeed = activityFeedCrafter();
        VerticalLayout graphHolder = graph();
        graphHolder.setWidth("400px");
        HorizontalLayout h = new HorizontalLayout(graphHolder, activityFeed);
        h.setWidthFull();
        h.setFlexGrow(1, graphHolder);

        h.getStyle().set("flex-wrap","wrap");

        return h;
    }



    // =============================== Low/No MATERIAL top employees quickActions ======================================

    public HorizontalLayout smth(){

        VerticalLayout mainLayout = new VerticalLayout(dbMostUsed.spanCrafterWordNoHide("Material Low/No stock","stat-value"));
        mainLayout.setWidth("600px");
        mainLayout.addClassName("island");


        // add data here from db
        VerticalLayout material = new VerticalLayout(materialLowNoStockFeed(),materialLowNoStockFeed(),materialLowNoStockFeed(),materialLowNoStockFeed());

        // scroller that takes material as data from materialLowNoStockFeed
        Scroller scroller = new Scroller(material);
        scroller.setSizeFull();
        scroller.setHeight("400px");

        // simple button in the middle
        HorizontalLayout buttonAtTheBottom = new HorizontalLayout(common.normalThemeButton("View All Material", DashBoard.class, ButtonVariant.LUMO_PRIMARY));
        buttonAtTheBottom.setWidthFull();
        buttonAtTheBottom.setJustifyContentMode(JustifyContentMode.CENTER);

        // add remainin stuff to the main layout of the low no material layout
        mainLayout.add(scroller,buttonAtTheBottom);



        VerticalLayout mainLayout2 = new VerticalLayout(dbMostUsed.spanCrafterWordNoHide("Top employees","stat-value"));
        mainLayout2.setWidth("600px");
        mainLayout2.addClassName("island");


        // add data here from db
        VerticalLayout material2 = new VerticalLayout(topEmployeeFeed(),topEmployeeFeed(),topEmployeeFeed(),topEmployeeFeed());

        // scroller that takes material as data from materialLowNoStockFeed
        Scroller scroller2 = new Scroller(material2);
        scroller2.setSizeFull();
        scroller2.setHeight("400px");

        // simple button in the middle
        HorizontalLayout buttonAtTheBottom2 = new HorizontalLayout(common.normalThemeButton("View All Employee", DashBoard.class, ButtonVariant.LUMO_PRIMARY));
        buttonAtTheBottom2.setWidthFull();
        buttonAtTheBottom2.setJustifyContentMode(JustifyContentMode.CENTER);

        // add remainin stuff to the main layout of the low no material layout
        mainLayout2.add(scroller2,buttonAtTheBottom2);








        VerticalLayout activityFeed = new VerticalLayout(dbMostUsed.spanCrafterWordNoHide("Top employees","stat-value"));
        activityFeed.addClassName("island");
        activityFeed.setWidth("600px");

        VerticalLayout s = new VerticalLayout(dbMostUsed.spanCrafterWordNoHide("Quick actions","stat-value"));
        s.add(new Button("132"),new Button("132"),new Button("132"),new Button("132"));
        s.addClassName("island");
        s.setWidth("200px");
        s.setMaxWidth("600px");



        HorizontalLayout h = new HorizontalLayout(mainLayout, mainLayout2,s);
        h.setWidthFull();
        h.setFlexGrow(1, s);

        h.getStyle().set("flex-wrap","wrap");

        return  h;

    }




    // material feed crafter
    public HorizontalLayout materialLowNoStockFeed(){
        HorizontalLayout iconHolder = new HorizontalLayout(common.iconCrafter(VaadinIcon.CIRCLE,"20px","blue"));
        iconHolder.getStyle().set("margin-top","5px");

        VerticalLayout e = new VerticalLayout(
                dbMostUsed.tripleValueRow(dbMostUsed.spanCrafterWordNoHide("Wood","activityFeed-name"),dbMostUsed.spanCrafterWordNoHide(" - ","activityFeed-name"),dbMostUsed.spanCrafterWordNoHide("5 units left","stat-example")),
                dbMostUsed.tripleValueRow(dbMostUsed.spanCrafterWordNoHide("Current stock - 10","stat-title"),dbMostUsed.spanCrafterWordNoHide("● Min treshold - 25 ","stat-title"),dbMostUsed.spanCrafterWordNoHide("● Peace cost - 10 eur ","stat-title"))
        );
        e.setWidthFull();
        e.setPadding(false);
        e.setSpacing(false);

        HorizontalLayout h12 = new HorizontalLayout(iconHolder,e);
        h12.addClassName("island");
        h12.setWidthFull();

        return  h12;
    }





    // employee feed crafter
    public HorizontalLayout topEmployeeFeed(){
        HorizontalLayout iconHolder = new HorizontalLayout(common.iconCrafter(VaadinIcon.CIRCLE,"20px","blue"));
        iconHolder.getStyle().set("margin-top","5px");

        Image image =new Image("Screenshot 2026-04-27 001745.png","user image");
        image.setWidth("60px");
        image.setHeight("60px");
        image.getStyle().set("border-radius","30px");


        VerticalLayout e = new VerticalLayout(
                dbMostUsed.tripleValueRow(dbMostUsed.spanCrafterWordNoHide("Josh Smith","activityFeed-name"),dbMostUsed.spanCrafterWordNoHide(" - ","activityFeed-name"),dbMostUsed.spanCrafterWordNoHide("5 units produced","stat-example")),
                dbMostUsed.doubleValueRow(dbMostUsed.spanCrafterWordNoHide("Hourly salary - 25 Eur","stat-title"),dbMostUsed.spanCrafterWordNoHide("● Hours worked - 256 ","stat-title"))
        );
        e.setWidthFull();
        e.setPadding(false);
        e.setSpacing(false);

        HorizontalLayout h12 = new HorizontalLayout(image,e);
        h12.addClassName("island");
        h12.setWidthFull();

        return  h12;
    }
















}
