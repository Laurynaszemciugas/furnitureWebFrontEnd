package com.example.demo.Pages.Reports.Common.OrderReport;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Pages.Reports.Common.ReportsMiniStatCrafter;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class OrderReportMiniStatCrafter {

    CommonComponents commonComponents;
    Common common;
    ReportsMiniStatCrafter miniStatCrafter;

    public OrderReportMiniStatCrafter(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;

        this.miniStatCrafter = new ReportsMiniStatCrafter(commonComponents,common);
    }

    public HorizontalLayout miniStatHolder(String color){
        HorizontalLayout miniStatHolders = new HorizontalLayout();
        miniStatHolders.addClassName("layout-flex");
        miniStatHolders.setWidthFull();

        String backgroundColor = common.hexToRgba(color,0.15);

        miniStatHolders.add(
                miniStatCrafter.miniStats(VaadinIcon.CUBES, "Total orders", "432432asasas", new Span("123w"), color, backgroundColor),
                miniStatCrafter.miniStats(VaadinIcon.CHECK, "Finished orders", 2, new Span("123w"), color, backgroundColor),
                miniStatCrafter.miniStats(VaadinIcon.EYE_SLASH, "In progress orders", 3, new Span("123w"), color, backgroundColor),
                miniStatCrafter.miniStats(VaadinIcon.PAPERCLIP, "Recently added",4, new Span("123sasaasasw"), color, backgroundColor)
        );

        return miniStatHolders;
    }


}
