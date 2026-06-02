package com.example.demo.Pages.Orders.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Enums.OrderStatus;
import com.sun.jdi.LongValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Setter
public class OrderFilters {


    CommonComponents commonComponents;
    Common common;

    Consumer<OrderStatus> orderStatusConsumer;
    Consumer<Double> fromCostConsumer;
    Consumer<Double> toCostConsumer;
    Consumer<LocalDate> fromDateConsumer;
    Consumer<LocalDate> toDateConsumer;
    Consumer<Long> amountOfProductsConsumer;
    Consumer<String> clearFilters;


    OrderStatus orderStatusChoice = null;
    Double priceFromChoice = null;
    Double priceToChoice = null;
    LocalDate dateFromChoice= null;
    LocalDate dateToChoice= null;
    Long amountOfProductsChoice= null;


    public OrderFilters(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;



    }



    public void showFilters(){

        Dialog filters = new Dialog("Filters");

        VerticalLayout filterHolder = new VerticalLayout();
        filterHolder.setPadding(false);



        // =========== FROM DATE =====================
        DatePicker from = new DatePicker("From");

        from.addValueChangeListener(e->{
            LocalDate value = e.getValue() == null ? LocalDate.of(1000,12,12) : e.getValue();
            fromDateConsumer.accept(value);
            dateFromChoice = value;
        });
        if(dateFromChoice!=null && !dateFromChoice.equals(LocalDate.of(1000,12,12))){
            from.setValue(dateFromChoice);
        }

        // =========== TO DATE =====================
        DatePicker to = new DatePicker("to");
        to.addValueChangeListener(e->{
            LocalDate value = e.getValue() == null ? LocalDate.of(1000,12,12) : e.getValue();
            toDateConsumer.accept(value);
            dateToChoice = value;
        });
        if(dateToChoice!=null && !dateToChoice.equals(LocalDate.of(1000,12,12))){
            to.setValue(dateToChoice);
        }

        FormLayout dateHolder = new FormLayout();

        dateHolder.add(
                from,
                to
        );

        dateHolder.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 2)
        );

        // =========== PRODUCT COUNT =====================
        IntegerField amountOfProducts = new IntegerField("Product count");
        amountOfProducts.setMax(100);
        amountOfProducts.setMin(0);
        amountOfProducts.setStep(1);
        amountOfProducts.setStepButtonsVisible(true);

        amountOfProducts.addValueChangeListener(e->{
            Long value = e.getValue() == null ? 0l : e.getValue();
            amountOfProductsConsumer.accept(value);
            amountOfProductsChoice = value;
        });
        if(amountOfProductsChoice!=null && amountOfProductsChoice != 0){
            amountOfProducts.setValue(Math.toIntExact(amountOfProductsChoice));
        }


        // =========== FROM AMOUNT =====================
        IntegerField fromAmount = new IntegerField("From");

        fromAmount.addValueChangeListener(e->{
            Double value = e.getValue() == null ? 0.0 : e.getValue().doubleValue();
            fromCostConsumer.accept(value);
            priceFromChoice = value;
        });
        if(priceFromChoice!=null && priceFromChoice != 0){
            fromAmount.setValue(priceFromChoice.intValue());
        }

        // =========== TO AMOUNT =====================
        IntegerField toAmount = new IntegerField("to");
        toAmount.setMin(0);

        toAmount.addValueChangeListener(e->{
            Double value = e.getValue() == null ? 0.0 : e.getValue().doubleValue();
            toCostConsumer.accept(value);
            priceToChoice = value;
        });
        if(priceToChoice!=null && priceToChoice != 0){
            toAmount.setValue(priceToChoice.intValue());
        }


        FormLayout amountHolder = new FormLayout();

        amountHolder.add(
                fromAmount,
                toAmount
        );
        amountHolder.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 2)
        );


        filterHolder.add(
                commonComponents.spanCrafterWordNoHide("Cost from to","stat-title"),
                amountHolder,
                commonComponents.spanCrafterWordNoHide("Date from to","stat-title"),
                dateHolder,
                commonComponents.spanCrafterWordNoHide("Amount of Products in order","stat-title"),
                amountOfProducts
        );

        Button button = new Button("Back", e-> filters.close());

        HorizontalLayout h = new HorizontalLayout();
        h.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        h.add(
                button
        );


        filters.add(
                filterHolder,
                h
        );

        filters.open();


    }


    public HorizontalLayout Buttons(){
        Button allButton = commonComponents.normalButtonNoNavigate("All", "transparent-button");
        allButton.addClassName("active");
        allButton.addClickListener(e->{
           orderStatusConsumer.accept(OrderStatus.ALL);
        });

        Button pendingButton = commonComponents.normalButtonNoNavigate("Pending", "transparent-button");
        pendingButton.addClickListener(e->{
            orderStatusConsumer.accept(OrderStatus.Pending);
        });

        Button inProgressButton = commonComponents.normalButtonNoNavigate("In Progress", "transparent-button");
        inProgressButton.addClickListener(e->{
            orderStatusConsumer.accept(OrderStatus.In_Progress);
        });

        Button finishedButton = commonComponents.normalButtonNoNavigate("Finished", "transparent-button");
        finishedButton.addClickListener(e->{
            orderStatusConsumer.accept(OrderStatus.Finished);
        });

        List<Button> buttons = List.of(allButton,pendingButton,inProgressButton,finishedButton);

        for(var s : buttons){

            s.addClickListener(e->{
                buttons.forEach(b-> b.removeClassName("active"));
                s.addClassName("active");
            });

        }




        HorizontalLayout buttonsHolder = new HorizontalLayout();
        buttonsHolder.add(
                allButton,
                pendingButton,
                inProgressButton,
                finishedButton

        );

        Button clear = new Button("Clear filters",VaadinIcon.ERASER.create(), e-> clearFilters.accept("yep"));

        HorizontalLayout allHolder = new HorizontalLayout();
        allHolder.setWidthFull();
        allHolder.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        allHolder.add(
                buttonsHolder,
                clear
        );

        return allHolder;
    }


    public HorizontalLayout moreFilters(){
        HorizontalLayout nameOfGrids = new HorizontalLayout();
        nameOfGrids.addClassName("layout-flex");
        nameOfGrids.setWidthFull();

        Button showMoreFilters = new Button(commonComponents.iconCrafter(VaadinIcon.FILTER,"30px","grey"), e-> showFilters());
        showMoreFilters.addClassName("transparent-button");

        nameOfGrids.add(
                commonComponents.spanCrafterWordNoHide("Orders List","activityFeed-name"),
                commonComponents.spaceFiller(),
                showMoreFilters
        );
        return nameOfGrids;
    }





}
