package com.example.demo.Pages.ActionLog.Main;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Paganation;
import com.example.demo.ControllerModels.Filter.Employee.EmployeeFilterHolder;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.ActionLog.Components.ActionLogsBriefExplanation;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "Actions", layout = MainLayout.class)
public class ActionLogPage extends VerticalLayout implements BeforeEnterObserver {

    CommonComponents commonComponents;
    Common common;


    Paganation paganation;

    ActionLogsBriefExplanation actionLogsBriefExplanation;

    public ActionLogPage(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;

        this.actionLogsBriefExplanation = new ActionLogsBriefExplanation(commonComponents,common);
        this.paganation = new Paganation();

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



        verticalLayout.add(
                actionLogsBriefExplanation.briefExplanation(),
                filters(),
                grid(),
                paganation.buttonHolder(3)
        );




        return verticalLayout;
    }


    public HorizontalLayout filters(){

        HorizontalLayout h = new HorizontalLayout();

        TextField searchActions = new TextField("Search actions");

        DatePicker from = new DatePicker("Date from");


        DatePicker to = new DatePicker("Date to");



        h.add(
                searchActions,
                from,
                to
        );


        return h;

    }

    public Grid<String> grid(){

        Grid<String> grid = new Grid<>(String.class);
        grid.setWidthFull();
        grid.setHeight("700px");


        return grid;

    }


}
