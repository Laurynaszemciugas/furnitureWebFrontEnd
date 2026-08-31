package com.example.demo.Pages.ActionLog.Main;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.Common.Paganation;
import com.example.demo.ControllerModels.ActionLogs.ActionLogFeed;
import com.example.demo.ControllerModels.Filter.ActionLog.ActionLogFilterHolder;
import com.example.demo.ControllerModels.Filter.Employee.EmployeeFilterHolder;
import com.example.demo.ControllerModels.Material.MaterialBriefDto;
import com.example.demo.Enums.ActionDesciptionEnum;
import com.example.demo.Enums.ActionTrackerEnum;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.ActionLog.Components.ActionLogFilters;
import com.example.demo.Pages.ActionLog.Components.ActionLogGrid;
import com.example.demo.Pages.ActionLog.Components.ActionLogsBriefExplanation;
import com.example.demo.Services.ActionTrackerService.ActionService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Route(value = "Actions", layout = MainLayout.class)
public class ActionLogPage extends VerticalLayout implements BeforeEnterObserver {


    // main layout
    VerticalLayout verticalLayout = new VerticalLayout();
    VerticalLayout filterMemory = new VerticalLayout();
    Div gridHolder = new Div();

    ActionLogFilterHolder filterData = new ActionLogFilterHolder();

    ActionService actionService;

    SessionCrafter sessionCrafter;

    CommonComponents commonComponents;
    Common common;


    Paganation paganation;

    ActionLogsBriefExplanation actionLogsBriefExplanation;


    ActionLogFilters actionLogFilters;
    ActionLogGrid actionLogGrid;

    public ActionLogPage(CommonComponents commonComponents, Common common, ActionService actionService) {
        this.commonComponents = commonComponents;
        this.common = common;

        this.actionLogsBriefExplanation = new ActionLogsBriefExplanation(commonComponents,common);
        this.paganation = new Paganation();

        this.actionLogFilters = new ActionLogFilters(commonComponents,common);

        this.sessionCrafter = new SessionCrafter();

        this.actionService = actionService;

        this.actionLogFilters = new ActionLogFilters(commonComponents,common);

        this.actionLogGrid = new ActionLogGrid(commonComponents,common);


        gridHolder.setWidthFull();
        filterMemory.setWidthFull();
        filterMemory.setPadding(false);

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


        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");


        reloadData();







        return verticalLayout;
    }


    public void reloadData(){



        verticalLayout.removeAll();

        //filterData = new MaterialFilterHolder();

        filterMemory.removeAll();
        filterMemory.add(
                actionLogsBriefExplanation.briefExplanation(),
                actionLogFilters.filters()

        );




        loadGridValues();


        verticalLayout.add(
                filterMemory,
                gridHolder

        );
    }


    public void loadGridValues(){

        UI ui = UI.getCurrent();
        String jwt = sessionCrafter.extractSession("JWT", String.class);

        gridHolder.removeAll();
        gridHolder.add(
                commonComponents.shimmer(5)
        );

        CompletableFuture
                .supplyAsync(()->{

                    List<ActionLogFeed> items = actionService.getActionLogFeed(filterData,jwt);
                    common.timer(250);
                    return items;
                })
                .thenAccept(e->{
                    ui.access(() -> {
                        gridHolder.removeAll();
                        gridHolder.add(gridFilterHolder(e));
                    });
                });


        sessionCrafter.createSession("actionLogsPageFilters",filterData);

        paganation.updateUIFromExternal(filterData.getPage()+1);

    }

    public VerticalLayout gridFilterHolder(List<ActionLogFeed> filterStuff){
        VerticalLayout v = new VerticalLayout();
        v.setPadding(false);
        v.setWidthFull();

        v.add(
                actionLogGrid.gridHolder(filterStuff),
                paganation.buttonHolder(Math.toIntExact(actionService.getAmountOfPages(filterData)))

        );

        return v;
    }





    public void setNewPage(){
        filterData.setPage(0);
        paganation.updateUIFromExternal(1);
    }


}
