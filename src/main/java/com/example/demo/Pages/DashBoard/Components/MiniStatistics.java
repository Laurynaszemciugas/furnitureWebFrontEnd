package com.example.demo.Pages.DashBoard.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.DashBoard.DashBoardEmployeeMiniInfo;
import com.example.demo.ControllerModels.DashBoard.DashBoardMaterialStock;
import com.example.demo.ControllerModels.DashBoard.DashBoardMaterialUsageInfo;
import com.example.demo.ControllerModels.DashBoard.DashBoardMonthlyOrdersCompleted;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.stereotype.Service;

@Service
public class MiniStatistics {


    CommonComponents commonComponents;
    Common common;

    public MiniStatistics(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }

    // ===================================== mini stats =====================================

    public HorizontalLayout miniStats(DashBoardMonthlyOrdersCompleted ordersCompletedCompleted,DashBoardMaterialStock dashBoardMaterialStock,DashBoardMaterialUsageInfo materialData,DashBoardEmployeeMiniInfo employeeData){

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthFull();
        horizontalLayout.getStyle().set("flex-wrap","wrap");
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);







        // fourth mini stat large auto resize
        HorizontalLayout employeeCard = new HorizontalLayout(employeeMiniStat(
                "Employee mini information",
                "Screenshot 2026-04-27 001745.png",
                employeeData,
                "Material usage this month compared to last",
                null,
                "170px"));
        employeeCard.setMaxWidth("720px");

        // all the remaining mini stats
        horizontalLayout.add(

                orderCompletedMiniStats(
                        "Monthly Orders 'Completed'",
                        "Screenshot 2026-04-27 001745.png",
                        "Orders",
                        ordersCompletedCompleted,
                        "Compared to last months 'Completed' orders",
                        "280px",
                        "170px"),
                materialsStockMiniStats(
                        "Material Stock 'Current'",
                        "Screenshot 2026-04-27 001745.png",
                        dashBoardMaterialStock,
                        "Current material stock",
                        "280px",
                        "170px"),
                materialUsageMiniStat(
                        "Material mini information",
                        "Screenshot 2026-04-27 001745.png",
                        materialData,
                        "Material usage this month compared to last",
                        "280px",
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

        boolean empty = (ordersData == null || ordersData.isEmpty());

        long value = empty ? 0 : ordersData.getThisMonthOrders();
        long previousValue = empty ? 0 : ordersData.getPreviousMonthOrders();


        double changePercent = empty ? 0 : common.diffrenceCalculator(value, previousValue);


        Span trend = commonComponents.spanCrafter(empty ? "▲ 0.00%" :  (value >= previousValue  ? "▲ " : "▼ ") + String.format("%.2f",changePercent) + "%","stat-trend");

        if(!empty) {
            common.trendColoring("green", "red",value,previousValue, changePercent, trend);
        }

        // Layout
        VerticalLayout island = new VerticalLayout(
                commonComponents.spanCrafter(name,"stat-title"),
                commonComponents.doubleValueRow(commonComponents.spanCrafter(empty ? "No data" : String.valueOf(value),"stat-value"), commonComponents.spanCrafter(units,"stat-units")),
                commonComponents.doubleValueRow(commonComponents.spanCrafter(empty ? "No data": "Last Month " + previousValue,"stat-example"), commonComponents.spanCrafter(units,"stat-units")),
                trend,
                commonComponents.spanCrafter(description,"stat-description"));
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

        boolean empty = (dashBoardMaterialStock != null && dashBoardMaterialStock.isEmpty());

        long lowMaterial = empty ? 0 : dashBoardMaterialStock.getLowMaterial();
        long noStockMaterial = empty ? 0 : dashBoardMaterialStock.getNoStockMaterial();


        // Layout
        VerticalLayout island = new VerticalLayout(
                commonComponents.spanCrafter(name,"stat-title"),
                commonComponents.doubleValueRow(commonComponents.spanCrafter(String.valueOf(lowMaterial),"stat-value"), commonComponents.spanCrafter("Low material stock","stat-unit")),
                commonComponents.doubleValueRow(commonComponents.spanCrafter(String.valueOf(noStockMaterial),"stat-value"), commonComponents.spanCrafter("No material stock","stat-unit")),
                commonComponents.spanCrafter(description,"stat-description"));
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

        boolean empty = (materialData == null || materialData.isEmpty());

        String mostUsedMaterial = empty ? "No data" : materialData.getMostUsedMaterial();
        long totalMaterialUsageCount = empty ? 0 : materialData.getTotalMaterialsUsed();
        double totalCostOfMaterialsUsedThisMonth = empty ? 0 : materialData.getTotalUsedMaterialCost();
        double totalCostOfMaterialUsedLastMonth = empty ? 0 : materialData.getLastMonthTotalUsedMaterialCost();



        double changePercent = empty ? 0 : common.diffrenceCalculator(totalCostOfMaterialsUsedThisMonth,totalCostOfMaterialUsedLastMonth);


        Span trend =  commonComponents.spanCrafter(empty ? "▲ 0.00%" :(changePercent >= 0 ? "▲ " : "▼ ") + String.format("%.2f",changePercent) + "%" ,"stat-trend");

        if(!empty) {
            common.trendColoring("Green", "Red",totalCostOfMaterialsUsedThisMonth,totalCostOfMaterialUsedLastMonth, changePercent, trend);
        }

        Span desc = new Span(description);
        desc.addClassName("stat-description");



        // Layout
        VerticalLayout island = new VerticalLayout(
                commonComponents.spanCrafter(name,"stat-title"),
                commonComponents.doubleValueRow(commonComponents.spanCrafter(String.valueOf(totalCostOfMaterialsUsedThisMonth),"stat-value"), commonComponents.spanCrafter("Eur","stat-unit")),
                commonComponents.doubleValueRow(commonComponents.spanCrafter("Last month " + totalCostOfMaterialUsedLastMonth,"stat-example"), commonComponents.spanCrafter("Eur","stat-unit") ),
                trend,
                commonComponents.doubleValueRow(commonComponents.spanCrafter(mostUsedMaterial,"stat-example"), commonComponents.spanCrafter("Compared to all usage -  " + totalMaterialUsageCount,"stat-description")));


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
        String topEmployee = "-";
        long topEmployeeProduced = 0;
        double totalPaidThisMonth = 0.0;
        double totalUnpaidThisMonth = 0.0;
        double totalPaidLastMonth = 0.0;
        double totalUnpaidLastMonth = 0.0;


        boolean empty = (employeeData == null);

            topEmployee =  empty ? "No data" :employeeData.getTopEmployee();
            topEmployeeProduced = empty ? 0 :employeeData.getTopEmployeeProduced();
            totalPaidThisMonth = empty ? 0 : employeeData.getTotalPaidSalary();
            totalUnpaidThisMonth = empty ? 0 : employeeData.getTotalUnpaidSalary();
            totalPaidLastMonth = empty ? 0 : employeeData.getTotalPaidLastMonth();
            totalUnpaidLastMonth = empty ? 0 : employeeData.getTotalUnpaidLastMonth();

        // find change according to this month and previous

            double changePercentPaid = empty ? 0 : common.diffrenceCalculator(totalPaidThisMonth, totalPaidLastMonth);

            double changePercentUnPaid = empty ? 0 : common.diffrenceCalculator(totalUnpaidThisMonth, totalUnpaidLastMonth);

        // show trent according to change

        Span trend1 =  commonComponents.spanCrafter( empty ? "▲ 0.00%" : (changePercentPaid >= 0 ? "▲ " : "▼ ") + String.format("%.2f",changePercentPaid) + "%" ,"stat-trend");

        Span trend2 =  commonComponents.spanCrafter(empty ? "▲ 0.00%" : (changePercentUnPaid >= 0 ? "▲ " : "▼ ") + String.format("%.2f",changePercentUnPaid) + "%" ,"stat-trend");


        // change color according to change
        if(!empty) {
            common.trendColoring("red", "green", totalPaidThisMonth, totalPaidLastMonth, changePercentPaid, trend1);
            common.trendColoring("red", "green", totalUnpaidThisMonth, totalUnpaidLastMonth, changePercentUnPaid, trend2);
        }

        HorizontalLayout trendHolder2 = commonComponents.doubleValueRow(trend2, commonComponents.spanCrafter("Compared to last month","stat-description"));
        trendHolder2.setWidth("300px");

        HorizontalLayout trendHolder1 = commonComponents.doubleValueRow(trend1,commonComponents.spanCrafter("Compared to last month","stat-description"));
        trendHolder1.setWidth("300px");



        // Layout
        VerticalLayout island = new VerticalLayout(
                commonComponents.spanCrafter(name,"stat-title"),
                commonComponents.doubleValueRow(commonComponents.spanCrafter(topEmployee,"stat-value"), commonComponents.spanCrafter(topEmployeeProduced + " units produced","stat-unit")),
                commonComponents.tripleValueRow(commonComponents.spanCrafter(String.valueOf(totalPaidThisMonth),"stat-example"), commonComponents.spanCrafter("This month paid salary Eur  ","stat-description"),trendHolder1),
                commonComponents.tripleValueRow(commonComponents.spanCrafter(String.valueOf(totalUnpaidThisMonth),"stat-example"), commonComponents.spanCrafter("This month unpaid salary Eur  ","stat-description"),trendHolder2),
                commonComponents.spanCrafter(description,"stat-description")

        );






        island.addClassName("stat-card");

        island.setPadding(true);
        island.setSpacing(false);
        island.setWidthFull();
        island.setHeight(height);

        return island;
    }


}
