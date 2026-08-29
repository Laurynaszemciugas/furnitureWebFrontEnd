package com.example.demo.Services.AI;

import com.example.demo.Common.*;
import com.example.demo.Common.Logic.HttpCallLogic;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.ControllerModels.CommonDtos.ExtraDetails;
import com.example.demo.ControllerModels.CommonDtos.Materials;
import com.example.demo.ControllerModels.CommonDtos.ProductJoin.ProductFinishSteps;
import com.example.demo.ControllerModels.Error.ErrorResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vaadin.copilot.shaded.checkerframework.checker.units.qual.C;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Service
@Setter
public class AIService {

    HttpCallLogic httpCallLogic;

    CommonComponents commonComponents;
    Common common;
    SessionCrafter sessionCrafter;
    AiCalls aiCalls;


    int selectedItems = 0;

    Consumer<List<ProductFinishSteps>> productFinishStepsConsumer;
    Consumer<List<ExtraDetails>> productExtraDetailsConsumer;


    public AIService(CommonComponents commonComponents, Common common, HttpCallLogic httpCallLogic) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.httpCallLogic = httpCallLogic;
        this.aiCalls = new AiCalls();

        this.sessionCrafter = new SessionCrafter();
    }

    @SneakyThrows
    public<T> T getMaterialDataAccordingToId(AiQuestion aiQuestion, String jwt, Class<T> tClass, UI ui) {


        T t = (T) httpCallLogic.HttpCallWithJwt("Ai/getAiFillText", HttpMethod.POST,aiQuestion, tClass,false,jwt);


        if (t instanceof ErrorResponse response) {
            ui.access(()->{

            common.customActionsForNotification(response.getMessage(),response.getWarning(),null,false);
            });

        }

        if (t instanceof ProductAiDto productAiDto) {
            productFinishStepsConsumer.accept(productAiDto.getProductFinishStepsList());
            productExtraDetailsConsumer.accept(productAiDto.getExtraDetails());
        }

        return t;

    }

    @SneakyThrows
    public<T,S> T dialogTest(T dto, Class<T> tClass, HasComponents layout, Component layoutComponents, S refrenceToTheForm, String refrenceToTheClassForBackend){

        selectedItems = 0;

        Dialog dialog = new Dialog();
        dialog.setWidth("1000px");


        HorizontalLayout container = new HorizontalLayout();
        container.setWidthFull();
        container.addClassName("layout-flex");

        TextArea aiPrompt = new TextArea("Ai prompt");
        aiPrompt.setWidthFull();
        aiPrompt.setHeight("200px");

        Span warining = commonComponents.spanCrafter("Ai can make mistakes and it can overwrite text please check your form before saving","stat-description");
        HorizontalLayout warningLayout = new HorizontalLayout();
        warningLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        warningLayout.setPadding(false);

        warningLayout.add(
                commonComponents.iconCrafter(VaadinIcon.EXCLAMATION,"20px","RED"),
                warining
        );

        dialog.add(
                commonComponents.spanCrafter("Ai prompt 'Tell Ai what to do'","activityFeed-name"),
                warningLayout,
                aiPrompt,
                commonComponents.spanCrafter("Select fields which Ai need to fill'","activityFeed-name"),
                container);

        Button cancel = new Button("Cancel", e-> dialog.close());
        Button generate = new Button("Generate");
        generate.setEnabled(false);
        generate.addThemeVariants(ButtonVariant.PRIMARY);
        Button selectAll = new Button("Select All");
        selectAll.addThemeVariants(ButtonVariant.SUCCESS);



        UI ui = UI.getCurrent();

        layout.removeAll();
        layout.add(
                layoutComponents
        );

        T defaultValues = tClass.getDeclaredConstructor().newInstance();


        for(var s : dto.getClass().getDeclaredFields()){
            s.setAccessible(true);
            Span selected = new Span("Select");
            Checkbox checkbox = new Checkbox();


            HorizontalLayout row = commonComponents.tripleValueRow(
                    checkbox,
                    commonComponents.spanCrafter(
                            common.textConverter(s.getName()),
                            "stat-example"
                    ),
                    selected

            );

            row.getStyle().set("flex", "1 1 252px");
            row.getStyle().set("min-width", "252px");
            row.addClassName("island_hv");
            row.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
            row.setAlignItems(FlexComponent.Alignment.CENTER);

            row.addClickListener(e->{


                if(row.hasClassName("selected")){
                    row.removeClassName("selected");
                    checkbox.setValue(false);

                    selected.setText("Select");

                    selectedItems--;

                    if(selectedItems  >= 1){
                        generate.setEnabled(true);
                    }
                    else{
                        generate.setEnabled(false);
                    }

                    try {
                        s.set(dto,s.get(defaultValues));
                    } catch (IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    }

                }
                else{

                    selectedItems++;

                    if(selectedItems >= 0){
                        generate.setEnabled(true);
                    }
                    else{
                        generate.setEnabled(false);
                    }

                    selected.setText("Selected");


                    row.addClassName("selected");
                    checkbox.setValue(true);

                    try {
                        s.set(dto,null);
                        System.out.println(dto);
                    } catch (IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    }



                }
            });

            container.add(row);
        }


        // select all basically reset the UI and select all and remake ui
        selectAll.addClickListener(e->{

            container.removeAll();
            for(var s : dto.getClass().getDeclaredFields()) {

                selectedItems++;
                generate.setEnabled(true);


                Span selected = new Span("Select");
                s.setAccessible(true);
                Checkbox checkbox = new Checkbox();
                checkbox.setValue(true);

                selected.setText("Selected");

                try {
                    s.set(dto,null);
                    System.out.println(dto);
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }

                HorizontalLayout row = commonComponents.tripleValueRow(
                        checkbox,
                        commonComponents.spanCrafter(
                                common.textConverter(s.getName()),
                                "stat-example"
                        ),

                        selected
                );

                row.addClickListener(ee->{


                    if(row.hasClassName("selected")){
                        row.removeClassName("selected");
                        checkbox.setValue(false);

                        selected.setText("Select");

                        selectedItems--;

                        if(selectedItems  >= 1){
                            generate.setEnabled(true);
                        }
                        else{
                            generate.setEnabled(false);
                        }


                        try {
                            s.set(dto,s.get(defaultValues));
                        } catch (IllegalAccessException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                    else{

                        selectedItems++;

                        if(selectedItems  >= 1){
                            generate.setEnabled(true);
                        }
                        else{
                            generate.setEnabled(false);
                        }

                        selected.setText("Selected");


                        row.addClassName("selected");
                        checkbox.setValue(true);

                        try {
                            s.set(dto,null);
                            System.out.println(dto);
                        } catch (IllegalAccessException ex) {
                            throw new RuntimeException(ex);
                        }



                    }
                });



                row.addClassName("selected");
                row.getStyle().set("flex", "1 1 252px");
                //h.getStyle().set("max-width", "620px");
                row.getStyle().set("min-width", "252px");
                row.addClassName("island_hv");
                row.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
                row.setAlignItems(FlexComponent.Alignment.CENTER);

                container.add(row);

            }
        });


        generate.addClickListener(e->{
            String jwt = sessionCrafter.extractSession("JWT", String.class);
            ui.access(() -> {
                dialog.close();
            });

            layout.removeAll();
            layout.add(loadingOverlay("Loading AI response please wait",ui));

            CompletableFuture.runAsync(() -> {

                try {

                    AiQuestion aiQuestion = new AiQuestion();

                    aiQuestion.setPrompt(
                            aiCalls.classToStringConverter(
                                    dto,
                                    tClass,
                                    aiPrompt.getValue()
                            ));

                    aiQuestion.setReferenceToDataNeeded(refrenceToTheClassForBackend);



                    T aiDto =  getMaterialDataAccordingToId(aiQuestion,jwt,tClass,ui);

                    ui.access(() -> {



                        // Fill the existing fields
                        aiCalls.bind(refrenceToTheForm, aiDto);

                        // Rebuild the layout
                        layout.removeAll();

                        layout.add(
                                layoutComponents
                        );

                    });


                } catch (Exception ex) {


                    System.out.println(ex);
                    // if fails build it up

                    ui.access(() -> {
                        layout.removeAll();

                        layout.add(
                                layoutComponents
                        );
                    });

                }

            });



        });




        dialog.getFooter().add(
                selectAll,
                cancel,
                generate);

        dialog.open();


        return dto;

    }


    private Component loadingOverlay(String loadingText, UI ui) {
        Div overlay = new Div();
        overlay.addClassName("loading-overlay");

        Div loader = new Div();
        loader.addClassName("modern-loader");

        Span text = new Span(loadingText+ "...");
        text.addClassName("loading-text");

        Button stuckWaiting = new Button("Stuck waiting ? ", e-> common.reloadPage());
        stuckWaiting.setVisible(false);
        stuckWaiting.addThemeVariants(ButtonVariant.PRIMARY);

        overlay.add(loader, text,stuckWaiting);

        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();

        scheduler.schedule(() -> {
            ui.access(() -> {
                stuckWaiting.setVisible(true);
            });

            scheduler.shutdown();

        }, 10, TimeUnit.SECONDS);

        return overlay;
    }


}
