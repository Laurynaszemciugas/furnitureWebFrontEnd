package com.example.demo.Pages.Reports.ReportsPages.CreatorPage.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Pages.Reports.Common.CommonBriefPageExplanation;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class RightSideReportCreate {

    CommonComponents commonComponents;
    Common common;


    public RightSideReportCreate(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;



    }

    public HorizontalLayout rightSideReportCustom(HorizontalLayout rightSide, boolean includeFiltering) {

       // rightSide.addClassName("fromRightToLeft");

        rightSide.setWidthFull();
        rightSide.setPadding(false);
        rightSide.setSpacing(false);

        rightSide.addClassName("layout-flex");

        rightSide.getStyle()
                .set("display", "flex")
                .set("flex-wrap", "wrap")
                .set("align-content", "flex-start")
                .set("align-items", "flex-start")
                .set("gap", "16px")
                .set("box-sizing", "border-box");

        if(includeFiltering){
            rightSide.add(
                    rightSideTop()
            );
            rightSide.addClassName("island");

        }



        return rightSide;
    }

    public VerticalLayout rightSideTop(){

        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();
        v.setPadding(true);

        v.add(
                commonComponents.spanCrafter("Live preview","stat-example"),
                commonComponents.lineCrafter("100%")
        );

        return v;
    }



}
