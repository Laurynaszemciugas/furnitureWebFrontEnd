package com.example.demo.pages.DashBoard;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.chart;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.stereotype.Service;

@Service
public class GraphStatistics {

    CommonComponents commonComponents;
    Common common;

    chart chart;


    public GraphStatistics(CommonComponents commonComponents, Common common,chart chart) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.chart = chart;
    }


    // grapth for smth
    public VerticalLayout graph(){

        VerticalLayout graph = new VerticalLayout(
                commonComponents.doubleValueRow(
                        commonComponents.spanCrafterWordNoHide("Current month revenue graph","stat-value"),
                        commonComponents.spanCrafterWordNoHide(String.format("%s %s %s %s",
                                "From",
                                common.dateCrafter(0,0,0,0,true),
                                "To",
                                common.dateCrafter(0,1,1,0,true)),"stat-description")));

        graph.add(chart.ChartTest());

        graph.addClassName("island");
        graph.getStyle().set("flex-wrap","wrap");


        return graph;



    }

}
