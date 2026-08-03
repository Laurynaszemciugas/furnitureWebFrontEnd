package com.example.demo.Pages.Reports.Page;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Reports.Page.Components.BriefReportPageExplanation;
import com.example.demo.Pages.Reports.Page.Components.MiniReportCard;
import com.example.demo.Services.CustomReportService;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "Reports", layout = MainLayout.class)
public class Page extends VerticalLayout implements BeforeEnterObserver {

    CommonComponents commonComponents;
    Common common;
    BriefReportPageExplanation briefReportPageExplanation;
    MiniReportCard miniReportCard;

    CustomReportService customReportService;


    public Page(CommonComponents commonComponents, Common common,CustomReportService customReportService) {

        this.commonComponents = commonComponents;
        this.common = common;
        this.customReportService = customReportService;

        this.briefReportPageExplanation = new BriefReportPageExplanation(commonComponents,common);
        this.miniReportCard = new MiniReportCard(commonComponents,common);

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(Alignment.CENTER);


        addClassName("animation-page");

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        removeAll();


        add(mainLayout());

    }

    public VerticalLayout mainLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");

        Span available = commonComponents.spanCrafter("Available reports","activityFeed-name");
        available.addClassName("animated-card");

        Span customCreated = commonComponents.spanCrafter("Custom created reports","activityFeed-name");
        customCreated.addClassName("animated-card");

                verticalLayout.add(
                        briefReportPageExplanation.briefExplanation(),
                        available,
                        miniReportCard.reportHolder(),
                        customCreated,
                        miniReportCard.loadUserData(customReportService.getCustomReportFeed())

                );
            //miniReportCard.loadUserData(customReportService.getCustomReportFeed());


        return verticalLayout;
    }



}
