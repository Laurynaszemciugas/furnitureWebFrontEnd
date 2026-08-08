package com.example.demo.Pages.DashBoard.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ChartsGraphs.DashBoard.DashBoardCharts;
import com.example.demo.ControllerModels.Common.GraphDataDateValue;
import com.example.demo.Services.Orders.OrdersService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GraphStatistics {

    CommonComponents commonComponents;
    Common common;

    DashBoardCharts chart;

    OrdersService ordersService;


    public GraphStatistics(CommonComponents commonComponents, Common common, DashBoardCharts chart,OrdersService ordersService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.chart = chart;
        this.ordersService = ordersService;
    }


    // grapth for smth
    public VerticalLayout graph(){

        List<GraphDataDateValue> list = ordersService.getGraphDashboard(common.currentMonthStart(),common.nextMonthDate());

        VerticalLayout graph = new VerticalLayout(
                commonComponents.doubleValueRow(
                        commonComponents.spanCrafterWordNoHide("Current month revenue graph","stat-value"),
                        commonComponents.spanCrafterWordNoHide(String.format("%s %s %s %s",
                                "From",
                                common.dateCrafter(0,0,0,0,true),
                                "To",
                                common.dateCrafter(0,1,1,0,true)),"stat-description")));

        graph.add(chart.ChartTest(list));

        graph.addClassName("island");
        graph.getStyle().set("flex-wrap","wrap");


        return graph;



    }

}
