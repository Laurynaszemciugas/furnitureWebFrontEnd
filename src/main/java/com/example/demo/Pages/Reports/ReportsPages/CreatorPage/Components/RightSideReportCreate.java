package com.example.demo.Pages.Reports.ReportsPages.CreatorPage.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Pages.Reports.Common.CommonBriefPageExplanation;
import com.example.demo.Pages.Reports.Page.Page;
import com.example.demo.Pages.Reports.ReportsPages.CreatorPage.ReportCreationPage;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

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
        rightSide.setPadding(true);
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
            rightSide.addClassName("island");

            rightSide.add(
                    rightSideTop()
            );


        }



        return rightSide;
    }

    public VerticalLayout rightSideTop() {

        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();
        v.setPadding(false);
        v.setSpacing(false);

        HorizontalLayout h = new HorizontalLayout();
        h.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        h.setAlignItems(FlexComponent.Alignment.CENTER);
        h.setPadding(false);

        RouterLink link = new RouterLink(
                "already have one",
                Page.class
        );

        link.getStyle()
                .set("font-size", "12px");

        h.add(
                commonComponents.spanCrafter("Filtering will be available after report is created","stat-description"),
                link
        );

        v.add(
                commonComponents.spanCrafter("Live preview","activityFeed-name"),
                h,
                commonComponents.lineCrafter("100%")
        );

        return v;
    }



}
