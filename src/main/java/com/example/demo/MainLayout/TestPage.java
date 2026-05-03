package com.example.demo.MainLayout;

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


    public TestPage(){

        setPadding(false); // Removes the outer gap
        setSpacing(false); // Removes gaps between components inside this layout
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


        VerticalLayout verticalLayout = miniStatCrafter(
                "Active Users",
                "Screenshot 2026-04-27 001745.png",
                1245,
                "users",
                1000,
                "Compared to last week",
                "400px",
                "160px");




        horizontalLayout.add(
                miniStatCrafter(
                        "Monthly Orders 'Completed'",
                        "Screenshot 2026-04-27 001745.png",
                        1245,
                        "Orders",
                        1000,
                        "Compared to last months 'Completed' orders",
                        "330px",
                        "160px"),
                miniStatCrafter(
                        "Active Users",
                        "Screenshot 2026-04-27 001745.png",
                        1245,
                        "users",
                        1000,
                        "Compared to last week",
                        "330px",
                        "160px"),
                miniStatCrafter(
                        "Active Users",
                        "Screenshot 2026-04-27 001745.png",
                        1245,
                        "users",
                        1000,
                        "Compared to last week",
                        "330px",
                        "160px"),

                verticalLayout);

        horizontalLayout.setFlexGrow(1,verticalLayout);

        return  horizontalLayout;

    }


    public VerticalLayout miniStatCrafter(
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

        System.out.println(changePercent);


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
        VerticalLayout island = new VerticalLayout(title, valueRow, trend,lastMonth, desc);
        island.addClassName("stat-card");

        island.setPadding(true);
        island.setSpacing(false);
        island.setWidthFull();
        island.setWidth(width);
        island.setHeight(height);

        return island;
    }















}
