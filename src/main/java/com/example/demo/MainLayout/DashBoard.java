package com.example.demo.MainLayout;

import com.example.demo.ControllerModels.DashBoard.DashBoardEmployeeMiniInfo;
import com.example.demo.ControllerModels.DashBoard.DashBoardMaterialUsageInfo;
import com.example.demo.ControllerMostUsedCode.DbMostUsed;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "DashBoard", layout = MainLayout.class)
public class DashBoard extends VerticalLayout {

    DbMostUsed dbMostUsed;

    public DashBoard(
            DbMostUsed dbMostUsed
    ){
        this.dbMostUsed = dbMostUsed;

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(Alignment.CENTER);



        add(mainLayout());

    }

    public VerticalLayout mainLayout(){
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.setHeightFull();
        verticalLayout.getStyle().set("margin-top","20px");

        verticalLayout.addClassName("island");

        verticalLayout.add(miniStats());

        verticalLayout.setAlignItems(Alignment.CENTER);

        return verticalLayout;
    }

    public HorizontalLayout miniStats(){

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthFull();
        horizontalLayout.getStyle().set("flex-wrap","wrap");
        horizontalLayout.setJustifyContentMode(JustifyContentMode.CENTER);





        DashBoardMaterialUsageInfo materialData = new DashBoardMaterialUsageInfo();
        materialData.setMostUsedMaterial("wood");
        materialData.setTotalMaterialsUsed(10);
        materialData.setTotalUsedMaterialCost(152.5);
        materialData.setLastMonthTotalUsedMaterialCost(12);

        DashBoardEmployeeMiniInfo employeeData = new DashBoardEmployeeMiniInfo();


        HorizontalLayout employeeCard = new HorizontalLayout(employeeMiniStat(
                "Material usage information",
                "Screenshot 2026-04-27 001745.png",
                employeeData,
                "Material usage this month compared to last",
                null,
                "170px"));



        employeeCard.setMaxWidth("700px");




        horizontalLayout.add(

                orderCompletedMiniStats(
                        "Monthly Orders 'Completed'",
                        "Screenshot 2026-04-27 001745.png",
                        1245,
                        "Orders",
                        1000,
                        "Compared to last months 'Completed' orders",
                        "310px",
                        "170px"),
                materialsStockMiniStats(
                        "Material Stock 'Current'",
                        "Screenshot 2026-04-27 001745.png",
                        22,
                        0,
                        "Current material stock",
                        "310px",
                        "170px"),
                materialUsageMiniStat(
                        "Material cost information",
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
            long value,
            String units,
            long previousValue,
            String description,
            String width,
            String height
    ) {

        Image image1 = new Image(image, "Image error");
        image1.setWidth("200px");
        image1.getStyle().set("position","absolute")
                .set("bottom", "0")
                .set("right", "0")
                .set("z-index","1");



        double changePercent = ((double)(value  - previousValue ) / previousValue) * 100;


        Span trend =  dbMostUsed.spanCrafter((changePercent >= 0 ? "▲ " : "▼ ") + String.format("%.2f",Math.abs(changePercent)) + "%" ,"stat-trend");

        if (changePercent > 0) {
            trend.getStyle().set("color", "green");
        } else if (changePercent < 0) {
            trend.getStyle().set("color", "red");
        } else {
            trend.getStyle().set("color", "gray");
        }

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
            long lowMaterial,
            long noStockMaterial,
            String description,
            String width,
            String height
    ) {

        Image image1 = new Image(image, "Image error");
        image1.setWidth("200px");
        image1.getStyle().set("position","absolute")
                .set("bottom", "0")
                .set("right", "0")
                .set("z-index","1");

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

        Image image1 = new Image(image, "Image error");
        image1.setWidth("200px");
        image1.getStyle().set("position","absolute")
                .set("bottom", "0")
                .set("right", "0")
                .set("z-index","1");

        double changePercent = ((double)(materialData.getTotalMaterialsUsed()  - materialData.getLastMonthTotalUsedMaterialCost() ) / materialData.getLastMonthTotalUsedMaterialCost()) * 100;


        Span trend =  dbMostUsed.spanCrafter((changePercent >= 0 ? "▲ " : "▼ ") + String.format("%.2f",Math.abs(changePercent)) + "%" ,"stat-trend");

        if (changePercent > 0) {
            trend.getStyle().set("color", "green");
        } else if (changePercent < 0) {
            trend.getStyle().set("color", "red");
        } else {
            trend.getStyle().set("color", "gray");
        }

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


        Image image1 = new Image(image, "Image error");
        image1.setWidth("200px");
        image1.getStyle().set("position","absolute")
                .set("bottom", "0")
                .set("right", "0")
                .set("z-index","1");



        double changePercentPaid = dbMostUsed.diffrenceCalculator(totalPaidThisMonth,totalPaidLastMonth);

        double changePercentUnPaid =  dbMostUsed.diffrenceCalculator(totalUnpaidThisMonth,totalUnpaidLastMonth);


       Span trend1 =  dbMostUsed.spanCrafter((changePercentPaid >= 0 ? "▲ " : "▼ ") + String.format("%.2f",Math.abs(changePercentPaid)) + "%" ,"stat-trend");

       Span s = new Span("Compared to last month");

        Span s2 = new Span("Compared to last month");

       HorizontalLayout trendHolder1 = dbMostUsed.doubleValueRow(trend1,s);
        trendHolder1.setWidth("300px");





        if (changePercentPaid > 0) {
            trend1.getStyle().set("color", "red");
        } else if (changePercentPaid < 0) {
            trend1.getStyle().set("color", "green");
        } else {
            trend1.getStyle().set("color", "gray");
        }

        Span trend2 =  dbMostUsed.spanCrafter((changePercentUnPaid >= 0 ? "▲ " : "▼ ") + String.format("%.2f",Math.abs(changePercentUnPaid)) + "%" ,"stat-trend");

        HorizontalLayout trendHolder2 = dbMostUsed.doubleValueRow(trend2,s2);
        trendHolder2.setWidth("300px");


        if (changePercentUnPaid > 0) {
            trend2.getStyle().set("color", "red");
        } else if (changePercentUnPaid < 0) {
            trend2.getStyle().set("color", "green");
        } else {
            trend2.getStyle().set("color", "gray");
        }

        System.out.println(topEmployee);

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



















}
