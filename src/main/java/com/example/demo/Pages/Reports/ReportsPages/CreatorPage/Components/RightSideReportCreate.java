package com.example.demo.Pages.Reports.ReportsPages.CreatorPage.Components;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class RightSideReportCreate {

    public HorizontalLayout rightSideReportCustom(HorizontalLayout rightSide) {

        rightSide.addClassName("fromRightToLeft");
        rightSide.addClassName("island");

        rightSide.setWidthFull();
        rightSide.setPadding(false);
        rightSide.setSpacing(false);

        rightSide.getStyle()
                .set("display", "flex")
                .set("flex-wrap", "wrap")
                .set("align-content", "flex-start")
                .set("align-items", "flex-start")
                .set("gap", "16px")
                .set("box-sizing", "border-box");

        return rightSide;
    }



}
