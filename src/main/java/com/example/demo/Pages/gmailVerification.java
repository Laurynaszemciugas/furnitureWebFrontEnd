package com.example.demo.Pages;

import com.example.demo.Common.Common;
import com.example.demo.Common.Logic.HttpCallLogic;
import com.example.demo.ControllerModels.BreadCrums.BreadCrumsDto;
import com.example.demo.ControllerModels.CommonDtos.Materials;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Services.GmailVerificationService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "GmailVerification/:code")
public class gmailVerification extends VerticalLayout implements BeforeEnterObserver {


    String itemChoice = "";

    GmailVerificationService gmailVerificationService;

    HttpCallLogic httpCallLogic;

    Common common;

    public gmailVerification( Common common,HttpCallLogic httpCallLogic,GmailVerificationService gmailVerificationService) {

        this.common = common;
        this.httpCallLogic = new HttpCallLogic(common);

        this.gmailVerificationService = new GmailVerificationService(httpCallLogic);



    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        removeAll();

        String page = beforeEnterEvent.getRouteParameters().get("code").orElse(null);

        this.itemChoice  = page;

        add(mainLayout());


    }


    public VerticalLayout mainLayout() {


        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");


        gmailVerificationService.sendGmailCode(itemChoice);




        return verticalLayout;
    }

}
