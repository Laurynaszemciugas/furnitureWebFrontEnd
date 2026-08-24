package com.example.demo.Pages.DashBoard.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.DashBoard.DashBoardEmployeeMiniInfo;
import com.example.demo.ControllerModels.DashBoard.DashBoardMaterialStock;
import com.example.demo.ControllerModels.DashBoard.DashBoardMaterialUsageInfo;
import com.example.demo.ControllerModels.DashBoard.DashBoardMonthlyOrdersCompleted;
import com.example.demo.Services.EmployeeService.EmployeeService;
import com.example.demo.Services.Material.MaterialService;
import com.example.demo.Services.Orders.OrdersService;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MiniStatistics {


    CommonComponents commonComponents;
    Common common;

    OrdersService ordersService;
    MaterialService materialService;
    EmployeeService employeeService;



    String formattedVs = "";

    public MiniStatistics(CommonComponents commonComponents, Common common, OrdersService ordersService,MaterialService materialService,EmployeeService employeeService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.ordersService = ordersService;
        this.materialService = materialService;
        this.employeeService = employeeService;

        formattedVs = String.format(
                "vs %s - %s",
                common.dateFormatterWithFormat(
                        LocalDate.now().withDayOfMonth(1).minusMonths(1).atStartOfDay(),
                        "MMMM d"
                ),
                common.dateFormatterWithFormat(
                        LocalDate.now().withDayOfMonth(1).minusDays(1).atStartOfDay(),
                        "MMMM d, yyyy"
                )
        );
    }

    // ===================================== mini stats =====================================

    public HorizontalLayout miniStats(){

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthFull();
        horizontalLayout.getStyle().set("flex-wrap","wrap");
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);







        // fourth mini stat large auto resize
        HorizontalLayout employeeCard = new HorizontalLayout(employeeMiniStat(
                "Employee mini information",
                "Screenshot 2026-04-27 001745.png",
                "This months top employee",
                null,
                "170px"));
        employeeCard.setMaxWidth("720px");

        // all the remaining mini stats
        horizontalLayout.add(

                orderCompletedMiniStats(
                        "Monthly Orders 'Completed'",
                        "Screenshot 2026-04-27 001745.png",
                        "Orders",
                        "Compared to last months 'Completed' orders",
                        "280px",
                        "170px"),
                materialsStockMiniStats(
                        "Material Stock 'Current'",
                        "Screenshot 2026-04-27 001745.png",
                        "Current material stock",
                        "280px",
                        "170px"),
                materialUsageMiniStat(
                        "Material mini information",
                        "Screenshot 2026-04-27 001745.png",
                        "Top employee units produced",
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
            String description,
            String width,
            String height
    ) {

        DashBoardMonthlyOrdersCompleted ordersData = ordersService.getDashboardOrderMini(common.currentMonthStart(),common.nextMonthDate());

        Long value = ordersData.getThisMonthOrders() == null
                ? 0L
                : ordersData.getThisMonthOrders();
        Long previousValue = ordersData.getPreviousMonthOrders() == null ? 0L : ordersData.getPreviousMonthOrders();

        boolean empty = ordersData.isEmpty();

        double changePercent = common.diffrenceCalculator(value, previousValue);


        Span trend = commonComponents.spanCrafter(empty ? "▲ 0.00%" :  (value >= previousValue  ? "▲ " : "▼ ") + String.format("%.2f",changePercent) + "%","stat-trend");

        if(!empty) {
            common.trendColoring("green", "red",value,previousValue, changePercent, trend);
        }

        // Layout
        VerticalLayout island = new VerticalLayout(
                commonComponents.spanCrafter(name,"stat-title"),
                commonComponents.doubleValueRow(commonComponents.spanCrafter(empty ? "No data" : String.valueOf(value),"stat-value"), commonComponents.spanCrafter(units,"stat-units")),
                trend,
                commonComponents.spanCrafter(empty ? "No data": formattedVs ,"stat-description"));
//                commonComponents.spanCrafter(description,"stat-description"));
        island.addClassName("island");

        island.setPadding(true);
        island.setSpacing(false);
        island.setWidth(width);
        island.setHeight(height);

        return island;
    }


    public VerticalLayout materialsStockMiniStats(
            String name,
            String image,
            String description,
            String width,
            String height
    ) {

        DashBoardMaterialStock dashBoardMaterialStock = materialService.getDashBoardMiniStatas(common.currentMonthStart(),common.nextMonthDate());

        boolean empty = (dashBoardMaterialStock != null && dashBoardMaterialStock.isEmpty());

        Long lowMaterial = dashBoardMaterialStock.getLowMaterial() == null ? 0L : dashBoardMaterialStock.getLowMaterial();
        Long noStockMaterial = dashBoardMaterialStock.getNoStockMaterial() == null ? 0L : dashBoardMaterialStock.getNoStockMaterial();


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
            String description,
            String width,
            String height
    ) {

        DashBoardMaterialUsageInfo materialData = materialService.getMiniDashboardTwoMoreIndepth(common.currentMonthStart(),common.nextMonthDate());

        boolean empty = (materialData == null || materialData.isEmpty());

        String mostUsedMaterial = materialData.getMostUsedMaterial() == null ? "No data" : materialData.getMostUsedMaterial();
        Long totalMaterialUsageCount = materialData.getTotalMaterialsUsed() == null ? 0L : materialData.getTotalMaterialsUsed();
        Double totalCostOfMaterialsUsedThisMonth = materialData.getTotalUsedMaterialCost() == null ? 0L : materialData.getTotalUsedMaterialCost();
        Double totalCostOfMaterialUsedLastMonth = materialData.getLastMonthTotalUsedMaterialCost() == null ? 0L : materialData.getLastMonthTotalUsedMaterialCost();



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
                trend,
                commonComponents.spanCrafter(formattedVs,"stat-description"));



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

        DashBoardEmployeeMiniInfo employeeData = employeeService.getEmployeeMiniStats(common.currentMonthStart(),common.nextMonthDate());

        boolean empty = (employeeData == null);

            topEmployee =  employeeData.getTopEmployee() == null ? "No data" :employeeData.getTopEmployee();
            topEmployeeProduced = employeeData.getTopEmployeeProduced() == null ? 0 :employeeData.getTopEmployeeProduced();


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
                commonComponents.spanCrafter(topEmployee,"stat-value"),
                commonComponents.spanCrafter(topEmployeeProduced + " units produced","stat-unit"),
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
