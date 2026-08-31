package com.example.demo.Pages.ActionLog.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.CurrentFilterDisplay;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.ControllerModels.Filter.ActionLog.ActionLogFilterHolder;
import com.example.demo.Enums.ActionDesciptionEnum;
import com.example.demo.Enums.ActionTrackerEnum;
import com.example.demo.Enums.MaterialType;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.function.Consumer;

@Setter
public class ActionLogFilters {


    CommonComponents commonComponents;
    Common common;

    CurrentFilterDisplay currentFilterDisplay;

    Consumer<String> promptConsumer;
    Consumer<ActionTrackerEnum> whoMadeActionConsumer;
    Consumer<ActionDesciptionEnum> whatTypeOfActionConsumer;
    Consumer<LocalDate> fromConsumer;
    Consumer<LocalDate> toConsumer;

    ActionLogFilterHolder filterData = new ActionLogFilterHolder();

    boolean firstLoad = true;

    public ActionLogFilters(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;

        this.currentFilterDisplay = new CurrentFilterDisplay(commonComponents,common);

    }

    public VerticalLayout filters(){



        VerticalLayout v = new VerticalLayout();
        v.setPadding(false);

        if(firstLoad){
            v.addClassName("smooth-panel");
            firstLoad = false;
        }
        else{
            v.removeClassName("smooth-panel");
        }

        v.add(currentFilterDisplay.getFilters());

        HorizontalLayout h = new HorizontalLayout();




        TextField searchActions = new TextField("Search actions");

        searchActions.addValueChangeListener(e->{

            promptConsumer.accept(e.getValue());

        });


        ComboBox<ActionTrackerEnum> whoMadeAction = new ComboBox<>("Who made the action");
        whoMadeAction.setItems(ActionTrackerEnum.values());
        whoMadeAction.setItemLabelGenerator(ActionTrackerEnum::getDisplayName);
        currentFilterDisplay.setComponentValue("whoMadeTheAction",filterData,whoMadeAction);
        whoMadeAction.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(), ActionTrackerEnum.ALL,null,filterData,"whoMadeTheAction",whoMadeActionConsumer);
        });

        ComboBox<ActionDesciptionEnum> actionType = new ComboBox<>("Action type");
        actionType.setItems(ActionDesciptionEnum.values());
        actionType.setItemLabelGenerator(ActionDesciptionEnum::getDisplayName);
        currentFilterDisplay.setComponentValue("actionType",filterData,actionType);
        actionType.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(), ActionDesciptionEnum.ALL,null,filterData,"actionType",whatTypeOfActionConsumer);
        });


        DatePicker from = new DatePicker("Date from");
        currentFilterDisplay.setComponentValue("dateFrom",filterData,from);
        from.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(),LocalDate.of(1000,12,12),null,filterData,"dateFrom",fromConsumer);
        });


        DatePicker to = new DatePicker("Date to");
        currentFilterDisplay.setComponentValue("dateTo",filterData,to);
        to.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(),LocalDate.of(1000,12,12),null,filterData,"dateTo",toConsumer);
        });


        h.add(
                searchActions,
                whoMadeAction,
                actionType,
                from,
                to
        );

        v.add(h);


        return v;

    }


}
