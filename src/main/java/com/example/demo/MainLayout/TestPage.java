package com.example.demo.MainLayout;

import com.example.demo.ControllerModels.DashBoardMaterialUsageInfo;
import com.example.demo.ControllerMostUsedCode.DbMostUsed;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.Route;

@Route(value = "test", layout = MainLayout.class)
public class TestPage extends VerticalLayout {

    DbMostUsed dbMostUsed;

    public TestPage(
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


        VerticalLayout verticalLayout = orderCompletedMiniStats(
                "Active Users",
                "Screenshot 2026-04-27 001745.png",
                1245,
                "users",
                1000,
                "Compared to last week",
                "400px",
                "160px");

        verticalLayout.setMaxWidth("600px");
        verticalLayout.setMinWidth("200px");


        DashBoardMaterialUsageInfo materialData = new DashBoardMaterialUsageInfo();
        materialData.setMostUsedMaterial("wood");
        materialData.setTotalMaterialsUsed(10);
        materialData.setTotalUsedMaterialCost(152.5);
        materialData.setLastMonthTotalUsedMaterialCost(12);


        horizontalLayout.add(
                orderCompletedMiniStats(
                        "Monthly Orders 'Completed'",
                        "Screenshot 2026-04-27 001745.png",
                        1245,
                        "Orders",
                        1000,
                        "Compared to last months 'Completed' orders",
                        "330px",
                        "160px"),
                materialsStockMiniStats(
                        "Material Stock 'Current'",
                        "Screenshot 2026-04-27 001745.png",
                        22,
                        0,
                        "Current material stock",
                        "330px",
                        "160px"),
                materialUsageMiniStat(
                        "Material usage information",
                        "Screenshot 2026-04-27 001745.png",
                        materialData,
                        "Material usage this month compared to last",
                        "330px",
                        "160px"),


                verticalLayout);

        horizontalLayout.setFlexGrow(1, verticalLayout);



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

        // Title
        Span title = new Span(name);
        title.addClassName("stat-title");

        // Value
        Span valueSpan = new Span(String.valueOf(value));
        valueSpan.addClassName("stat-value");

        Span lastMonth = new Span( "Last month " +  previousValue);
        lastMonth.addClassName("stat-example");

        // Units
        Span unitsSpan = new Span(units);
        unitsSpan.addClassName("stat-units");



        Image image1 = new Image(image, "Image error");
        image1.setWidth("200px");
        image1.getStyle().set("position","absolute")
                .set("bottom", "0")
                .set("right", "0")
                .set("z-index","1");

        // Value row
        HorizontalLayout valueRow = new HorizontalLayout(valueSpan, unitsSpan);
        valueRow.setAlignItems(FlexComponent.Alignment.BASELINE);
        valueRow.addClassName("stat-row");

        double changePercent = ((double)(value  - previousValue ) / previousValue) * 100;


        Span trend = new Span((changePercent >= 0 ? "▲ " : "▼ ") + Math.abs(changePercent) + "%" );

        trend.addClassName("stat-trend");

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
        VerticalLayout island = new VerticalLayout(title, valueRow,lastMonth, trend, desc);
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

        // Title
        Span title = new Span(name);
        title.addClassName("stat-title");

        // Values

        Span lowMaterialValue = new Span((lowMaterial > 0 ? String.valueOf(lowMaterial) : "-"));
        lowMaterialValue.addClassName("stat-value");

        Span noMaterialValue = new Span((noStockMaterial > 0 ? String.valueOf(noStockMaterial) : "-"));
        noMaterialValue.addClassName("stat-value");

        Image image1 = new Image(image, "Image error");
        image1.setWidth("200px");
        image1.getStyle().set("position","absolute")
                .set("bottom", "0")
                .set("right", "0")
                .set("z-index","1");


        Span lowStock = new Span("Low materials");

        Span noStock = new Span("No materials");



        HorizontalLayout valueRow2 = new HorizontalLayout(lowMaterialValue, lowStock);
        valueRow2.setAlignItems(FlexComponent.Alignment.BASELINE);
        valueRow2.addClassName("stat-row");

        valueRow2.setPadding(false);

        HorizontalLayout valueRow3 = new HorizontalLayout(noMaterialValue, noStock);
        valueRow3.setAlignItems(FlexComponent.Alignment.BASELINE);
        valueRow3.addClassName("stat-row");

        valueRow3.setPadding(false);


        Span desc = new Span(description);
        desc.addClassName("stat-description");



        // Layout
        VerticalLayout island = new VerticalLayout(title,valueRow2, valueRow3, desc);
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

        // Title
        Span title = new Span(name);
        title.addClassName("stat-title");

        // Value
        Span mostUsedMaterial = new Span(materialData.getMostUsedMaterial());
        mostUsedMaterial.addClassName("stat-example");

        Span timesUsed = new Span("Is most used material");
        timesUsed.addClassName("stat-units");


        Span totalMaterialsUsed = new Span(String.valueOf(materialData.getTotalMaterialsUsed()));
        totalMaterialsUsed.addClassName("stat-example");

        Span totalMaterialCost = new Span(String.valueOf(materialData.getTotalUsedMaterialCost()));
        totalMaterialCost.addClassName("stat-value");

        Span totalMaterialCostCurre = new Span("Eur");
        totalMaterialCostCurre.addClassName("stat-units");



        Span lastMonth = new Span( "Last month " +  materialData.getLastMonthTotalUsedMaterialCost() + " Eur");
        lastMonth.addClassName("stat-example");


        Image image1 = new Image(image, "Image error");
        image1.setWidth("200px");
        image1.getStyle().set("position","absolute")
                .set("bottom", "0")
                .set("right", "0")
                .set("z-index","1");

        // Value row
        HorizontalLayout valueRow = new HorizontalLayout(totalMaterialCost, totalMaterialCostCurre);
        valueRow.setAlignItems(FlexComponent.Alignment.BASELINE);
        valueRow.addClassName("stat-row");


        HorizontalLayout valueRow2 = new HorizontalLayout(mostUsedMaterial, timesUsed);
        valueRow2.setAlignItems(FlexComponent.Alignment.BASELINE);
        valueRow2.addClassName("stat-row");

        double changePercent = ((double)(materialData.getTotalMaterialsUsed()  - materialData.getLastMonthTotalUsedMaterialCost() ) / materialData.getLastMonthTotalUsedMaterialCost()) * 100;


        Span trend = new Span((changePercent >= 0 ? "▲ " : "▼ ") + String.format("%.2f",Math.abs(changePercent)) + "%" );

        trend.addClassName("stat-trend");

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
        VerticalLayout island = new VerticalLayout(title,valueRow,lastMonth, trend, valueRow2);
        island.addClassName("stat-card");



        island.setPadding(true);
        island.setSpacing(false);
        island.setWidth(width);
        island.setHeight(height);

        return island;
    }



















}
