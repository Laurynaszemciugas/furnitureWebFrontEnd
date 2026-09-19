package com.example.demo.Pages.EmployeePage.AvailableOrderPage.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.CurrentFilterDisplay;
import com.example.demo.ControllerModels.Filter.ActionLog.ActionLogFilterHolder;
import com.example.demo.ControllerModels.Filter.EmployeeAvailableOrderFilter.EmployeeAvailableOrderFilter;
import com.example.demo.Enums.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Setter;

import java.time.LocalDate;
import java.util.function.Consumer;

@Setter
public class AvailableOrderFilter {

    CommonComponents commonComponents;
    Common common;

    CurrentFilterDisplay currentFilterDisplay;

    Consumer<String> promptConsumer;
    Consumer<OrderStatus> orderStatusConsumer;
    Consumer<Priority> PrioritynConsumer;
    Consumer<SortOrder> sortOrderConsumer;
    Consumer<String> clearConsumer;

    EmployeeAvailableOrderFilter filterData = new EmployeeAvailableOrderFilter();


    boolean firstLoad = true;

    public AvailableOrderFilter(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
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
        h.setWidthFull();




        TextField searchActions = new TextField("Search orders");

        searchActions.addValueChangeListener(e->{
            String value = e.getValue().isBlank() ? "ALL" : e.getValue();
            promptConsumer.accept(value);

        });

        //d
        ComboBox<OrderStatus> orderStatus = new ComboBox<>("Order status");
        orderStatus.setItems(OrderStatus.values());
        orderStatus.setItemLabelGenerator(OrderStatus::getDisplayName);
        currentFilterDisplay.setComponentValue("orderStatus",filterData,orderStatus);
        orderStatus.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(), OrderStatus.ALL,null,filterData,"orderStatus",orderStatusConsumer);
        });

        ComboBox<Priority> priority = new ComboBox<>("Order priority");
        priority.setItems(Priority.values());
        priority.setItemLabelGenerator(Priority::getDisplayName);
        currentFilterDisplay.setComponentValue("priority",filterData,priority);
        priority.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(), Priority.ALL,null,filterData,"priority",PrioritynConsumer);
        });

        ComboBox<SortOrder> sortOrder = new ComboBox<>("Sort");
        sortOrder.setItems(SortOrder.values());
        sortOrder.setItemLabelGenerator(SortOrder::getDisplayName);
        currentFilterDisplay.setComponentValue("sortOrder",filterData,sortOrder);
        sortOrder.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(), SortOrder.ALL,null,filterData,"sortOrder",sortOrderConsumer);
        });



        HorizontalLayout fields = new HorizontalLayout();
        fields.setPadding(false);

        fields.add(
                searchActions,
                orderStatus,
                priority,
                sortOrder
        );

        Button clear = new Button("Clear filters");
        clear.setPrefixComponent(VaadinIcon.ERASER.create());

        clear.addClickListener(e->{
            clearConsumer.accept("ba");
        });

        h.add(
                fields,
                clear
        );
        h.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        h.setAlignItems(FlexComponent.Alignment.BASELINE);


        v.add(h);


        return v;

    }


}
