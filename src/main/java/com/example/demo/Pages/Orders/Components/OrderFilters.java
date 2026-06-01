package com.example.demo.Pages.Orders.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Enums.OrderStatus;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;

import java.util.ArrayList;
import java.util.List;

public class OrderFilters {


    CommonComponents commonComponents;
    Common common;

    public OrderFilters(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }

    public void showFilters(){

        Dialog filters = new Dialog("Filters");

        VerticalLayout filterHolder = new VerticalLayout();
        filterHolder.setPadding(false);


        ComboBox<OrderStatus> statusComboBox = new ComboBox<>("Status");
        statusComboBox.setItems(getOrderStatuses());
        statusComboBox.setItemLabelGenerator(OrderStatus::getDisplayName);

        DatePicker from = new DatePicker("From");
        DatePicker to = new DatePicker("to");

        FormLayout dateHolder = new FormLayout();

        dateHolder.add(
                from,
                to
        );

        dateHolder.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 2)
        );

        IntegerField amountOfProducts = new IntegerField("Product count");
        amountOfProducts.setMax(100);
        amountOfProducts.setMin(1);
        amountOfProducts.setStep(1);
        amountOfProducts.setStepButtonsVisible(true);


        IntegerField fromAmount = new IntegerField("From");
        IntegerField toAmount = new IntegerField("to");

        FormLayout amountHolder = new FormLayout();

        amountHolder.add(
                fromAmount,
                toAmount
        );
        amountHolder.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 2)
        );


        filterHolder.add(
                commonComponents.spanCrafterWordNoHide("Order status","stat-title"),
                statusComboBox,
                commonComponents.spanCrafterWordNoHide("Cost from to","stat-title"),
                amountHolder,
                commonComponents.spanCrafterWordNoHide("Date from to","stat-title"),
                dateHolder,
                commonComponents.spanCrafterWordNoHide("Amount of Products in order","stat-title"),
                amountOfProducts
        );

        Button button = new Button("Back", e-> filters.close());
        Button buttonFilter = commonComponents.normalThemeButtonNoNavigate("Filter", ButtonVariant.LUMO_PRIMARY) ;

        HorizontalLayout h = new HorizontalLayout();
        h.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        h.add(
                button,
                buttonFilter
        );


        filters.add(
                filterHolder,
                h
        );

        filters.open();

    }

    public List<OrderStatus> getOrderStatuses(){
        List<OrderStatus> list = new ArrayList<>();

        for (var v : OrderStatus.values()) {
            if (v != OrderStatus.ALL) {
                list.add(v);
            }
        }

        return  list;
    }


}
