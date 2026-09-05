package com.example.demo.Pages;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.HttpCallLogic;
import com.example.demo.ControllerModels.BreadCrums.BreadCrumsDto;
import com.example.demo.ControllerModels.CommonDtos.Materials;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Services.GmailVerificationService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Route(value = "GmailVerification/:code")
public class gmailVerification extends VerticalLayout implements BeforeEnterObserver {


    String itemChoice = "";

    GmailVerificationService gmailVerificationService;

    HttpCallLogic httpCallLogic;

    Common common;
    CommonComponents commonComponents;

    public gmailVerification( Common common,CommonComponents commonComponents,HttpCallLogic httpCallLogic,GmailVerificationService gmailVerificationService) {

        this.common = common;
        this.commonComponents = commonComponents;
        this.httpCallLogic = new HttpCallLogic(common);

        this.gmailVerificationService = new GmailVerificationService(httpCallLogic);

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);


        addClassName("animation-page");

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
        verticalLayout.setAlignItems(Alignment.CENTER);
        verticalLayout.setJustifyContentMode(JustifyContentMode.CENTER);







        gmailVerificationService.setSuccess(e->{
            if(e){
                System.out.println("success");
                add("Success");
            }
            else{
                System.out.println("failed");
                verticalLayout.add(successScreen());
            }
        });

        gmailVerificationService.sendGmailCode(itemChoice);





        return verticalLayout;
    }

    public VerticalLayout failedScreen(){

        VerticalLayout failed = new VerticalLayout();
        failed.addClassName("island");
        failed.setJustifyContentMode(JustifyContentMode.CENTER);
        failed.setAlignItems(Alignment.CENTER);
        failed.setWidth("350px");

        Span first = commonComponents.spanCrafterWordNoHide("Failed","stat-value");
        Span second = commonComponents.spanCrafterWordNoHide("Please try again later make sure you are using the gmails URL to verify your gmail","stat-description");
        Button button = new Button("Retry");
        button.addThemeVariants(ButtonVariant.PRIMARY);

        button.addClickListener(e->{
            UI.getCurrent().navigate("GmailVerification/"+itemChoice);
        });

        failed.add(
                first,
                second,
                button
        );

        return failed;

    }


    public VerticalLayout successScreen(){

        VerticalLayout failed = new VerticalLayout();
        failed.addClassName("island");
        failed.setJustifyContentMode(JustifyContentMode.CENTER);
        failed.setAlignItems(Alignment.CENTER);
        failed.setWidth("350px");

        Span first = commonComponents.spanCrafterWordNoHide("Success","stat-value");
        Span second = commonComponents.spanCrafterWordNoHide("Success you can now log into your account ","stat-description");
        Button button = new Button("Go to login");
        button.addThemeVariants(ButtonVariant.PRIMARY);


        UI ui = UI.getCurrent();

        Span timer = new Span("Auto leave in: 10");

        // timer
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        AtomicInteger counter = new AtomicInteger(10);

        executor.scheduleAtFixedRate(() -> {

            int value = counter.getAndDecrement();

            ui.access(() -> {
                timer.setText("Auto leave in: " +  value);


            if (value == 0) {
                executor.shutdown();
                ui.navigate("Login");
            }
            });

        }, 0, 1, TimeUnit.SECONDS);

        button.addClickListener(e->{

            ui.navigate("Login");

        });

        failed.add(
                first,
                second,
                timer,
                button
        );

        return failed;

    }


}
