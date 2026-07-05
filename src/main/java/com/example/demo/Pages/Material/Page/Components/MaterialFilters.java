package com.example.demo.Pages.Material.Page.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.CurrentFilterDisplay;
import com.example.demo.ControllerModels.Filter.Material.MaterialFilterHolder;
import com.example.demo.Enums.ActiveInactive;
import com.example.demo.Enums.MaterialType;
import com.example.demo.Enums.Stock;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

@Setter
public class MaterialFilters {

    CommonComponents commonComponents;
    Common common;
    CurrentFilterDisplay currentFilterDisplay;


    MaterialFilterHolder filterData = new MaterialFilterHolder();

    // filter consumers
    Consumer<MaterialType> materialTypeConsumer;
    Consumer<ActiveInactive> activeInactiveConsumer;
    Consumer<Stock> stockConsumer;
    Consumer<Long> stockAmountConsumer;
    Consumer<Long> minThresholdConsumer;
    Consumer<Double> unitPriceConsumer;
    Consumer<LocalDate> fromDateConsumer;
    Consumer<LocalDate> toDateConsumer;
    Consumer<String> prompConsumer;
    Consumer<MaterialFilterHolder> clearFilters;


    boolean firstLoad = true;

    public MaterialFilters(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;




    }

    public void setCurrentFilterDisplay(CurrentFilterDisplay currentFilterDisplay) {
        this.currentFilterDisplay = currentFilterDisplay;
    }

    public VerticalLayout filters(){
        VerticalLayout v = new VerticalLayout();

        if(firstLoad){
            v.addClassName("smooth-panel");
            firstLoad = false;
        }
        else{
            v.removeClassName("smooth-panel");
        }

        v.setPadding(false);


        // get current filter display
        v.add(currentFilterDisplay.getFilters());


        HorizontalLayout buttonHolder = new HorizontalLayout();
        buttonHolder.addClassName("layout-flex");

        HorizontalLayout buttonHolderhOLDER = new HorizontalLayout();
        buttonHolderhOLDER.addClassName("layout-flex");
        buttonHolderhOLDER.setWidthFull();
        buttonHolderhOLDER.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        Button all = commonComponents.normalButtonNoNavigate(ActiveInactive.ALL.getGetDisplayNames(), "transparent-button");
        all.addClickListener(e->{
            currentFilterDisplay.filterSetter(
                    Stock.ALL,
                    Stock.ALL,
                    null,
                    filterData,
                    "stockChoice",
                    "Stock",
                    stockConsumer
            );

        });
        all.addClassName("active");
        Button inStock = commonComponents.normalButtonNoNavigate(Stock.In_Stock.getDisplayName(), "transparent-button");
        inStock.addClickListener(e->{
            currentFilterDisplay.filterSetter(
                    Stock.In_Stock,
                    Stock.ALL,
                    null,
                    filterData,
                    "stockChoice",
                    "Stock",
                    stockConsumer
            );
        });
        Button lowStock = commonComponents.normalButtonNoNavigate(Stock.Low_Stock.getDisplayName(), "transparent-button");
        lowStock.addClickListener(e->{
            currentFilterDisplay.filterSetter(
                    Stock.Low_Stock,
                    Stock.ALL,
                    null,
                    filterData,
                    "stockChoice",
                    "Stock",
                    stockConsumer
            );
        });
        Button NoStock = commonComponents.normalButtonNoNavigate(Stock.No_Stock.getDisplayName(), "transparent-button");
        NoStock.addClickListener(e->{
            currentFilterDisplay.filterSetter(
                    Stock.No_Stock,
                    Stock.ALL,
                    null,
                    filterData,
                    "stockChoice",
                    "Stock",
                    stockConsumer
            );
        });

        Button clear = new Button("Clear filters", VaadinIcon.ERASER.create());
        clear.addClickListener(e->{
            filterData = new MaterialFilterHolder();
            clearFilters.accept(filterData);
            currentFilterDisplay.clearAllData();

        });

        buttonHolder.add(
                all,
                inStock,
                lowStock,
                NoStock
        );

        buttonHolderhOLDER.add(
                buttonHolder,
                clear
        );





        List<Button> buttonList = List.of(all,inStock,lowStock,NoStock);

        for(var s : buttonList){




            currentFilterDisplay.setReloadButtons(ee->{

                if(filterData.getStockChoice().equals(Stock.ALL)) {
                    stockConsumer.accept(Stock.ALL);
                    buttonList.forEach(button ->
                            button.removeClassName("active"));
                    all.addClassName("active");
                }
            });

            s.addClickListener(e->{

                buttonList.forEach(button->
                        button.removeClassName("active"));
                s.addClassName("active");

            });




        }

        if(filterData.getStockChoice().equals(Stock.In_Stock)){
            buttonList.forEach(button->
                    button.removeClassName("active"));
            inStock.addClassName("active");
        }
        if(filterData.getStockChoice().equals(Stock.Low_Stock)){
            buttonList.forEach(button->
                    button.removeClassName("active"));
            lowStock.addClassName("active");
        }
        if(filterData.getStockChoice().equals(Stock.No_Stock)){
            buttonList.forEach(button->
                    button.removeClassName("active"));
            NoStock.addClassName("active");
        }

        TextField search = commonComponents.textFieldCrafter("Search products...","",VaadinIcon.SEARCH);
        search.addValueChangeListener(e->{
            String value = e.getValue().isBlank() ? "ALL" : e.getValue();
            prompConsumer.accept(value);
        });
        Button showMoreFilters = new Button(commonComponents.iconCrafter(VaadinIcon.FILTER,"30px","grey"), e-> showMoreFilters());
        showMoreFilters.addClassName("transparent-button");
        HorizontalLayout h3 = new HorizontalLayout();
        h3.setPadding(false);

        h3.add(
                search,
                showMoreFilters
        );



        Span name = commonComponents.spanCrafter("Materials list","stat-value");

        HorizontalLayout h2 = new HorizontalLayout();
        h2.addClassName("layout-flex");
        h2.setWidthFull();
        h2.setPadding(false);
        h2.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        h2.add(name,h3);

        v.add(
                h2,
                buttonHolderhOLDER
        );

        return  v;
    }

    public Dialog showMoreFilters(){

        Dialog dialog = new Dialog("Filters");
        dialog.getHeader().add(VaadinIcon.FILTER.create());
        dialog.setHeaderTitle("Filters");
        dialog.setHeight("auto");
        dialog.setDraggable(true);

        Button back = new Button("Back", e-> dialog.close());

        VerticalLayout dialogHolder = new VerticalLayout();
        dialogHolder.setSpacing(false);
        dialogHolder.setPadding(false);


        ComboBox<MaterialType> materialTypeComboBox = new ComboBox<>("Material type");
        materialTypeComboBox.setWidthFull();
        materialTypeComboBox.setItems(MaterialType.values());
        materialTypeComboBox.setItemLabelGenerator(MaterialType::getDisplayName);
        currentFilterDisplay.setComponentValue("materialTypeChoice",filterData,materialTypeComboBox);
        materialTypeComboBox.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(),MaterialType.ALL,null,filterData,"materialTypeChoice","Material type",materialTypeConsumer);
        });


        ComboBox<ActiveInactive> activeInactiveComboBox = new ComboBox<>("Material status");
        activeInactiveComboBox.setWidthFull();
        activeInactiveComboBox.setItems(ActiveInactive.values());
        activeInactiveComboBox.setItemLabelGenerator(ActiveInactive::getGetDisplayNames);
        currentFilterDisplay.setComponentValue("activeInactive",filterData,activeInactiveComboBox);
        activeInactiveComboBox.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(),ActiveInactive.ALL,null,filterData,"activeInactive","Activity",activeInactiveConsumer);
        });



        // ================= STOCK AMOUNT =================================
        IntegerField stockAmount  = new IntegerField("Stock amount");
        stockAmount.setStepButtonsVisible(true);
        stockAmount.setMin(0);
        currentFilterDisplay.setComponentValue("stockAmountChoice",filterData,stockAmount);
        stockAmount.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue() == null ? null : Long.valueOf(e.getValue()),0L,null,filterData,"stockAmountChoice","Stock amount",stockAmountConsumer);
        });

        // ================= MIN AMOUNT =================================
        IntegerField minThreshold  = new IntegerField("Min threshold");
        minThreshold.setStepButtonsVisible(true);
        stockAmount.setMin(0);
        currentFilterDisplay.setComponentValue("minThresholdChoice",filterData,minThreshold);
        minThreshold.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue() == null ? null : Long.valueOf(e.getValue()),0L,null,filterData,"minThresholdChoice","Min threshold",minThresholdConsumer);
        });



        // ================= UNIT PRICE AMOUNT =================================
        NumberField unitPrice  = new NumberField("Unit price");
        unitPrice.setInvalid(false);
        unitPrice.setStep(0.5);
        unitPrice.setMax(100000);
        unitPrice.setMin(0);
        unitPrice.setStepButtonsVisible(true);
        unitPrice.setWidthFull();
        currentFilterDisplay.setComponentValue("unitPriceChoice",filterData,unitPrice);
        System.out.println(filterData.getUnitPriceChoice()  + " unit price");
        unitPrice.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(),0.0,null,filterData,"unitPriceChoice","Unit price",unitPriceConsumer);
        });




        HorizontalLayout stockLevels = commonComponents.doubleValueRow(
                stockAmount,
                minThreshold
        );


        DatePicker dateFrom = new DatePicker("Date from");
            currentFilterDisplay.setComponentValue("fromDateChoice",filterData,dateFrom);
        dateFrom.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(),LocalDate.of(1000,12,12),null,filterData,"fromDateChoice","From date",fromDateConsumer);
        });


        DatePicker dateTo = new DatePicker("Date to");
        currentFilterDisplay.setComponentValue("todDateChoice",filterData,dateTo);
        dateTo.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(),LocalDate.of(1000,12,12),null,filterData,"todDateChoice","To date",toDateConsumer);
        });

        HorizontalLayout dateFromTo = commonComponents.doubleValueRow(
                dateFrom,
                dateTo
        );
        dialogHolder.add(
                materialTypeComboBox,
                activeInactiveComboBox,
                stockLevels,
                unitPrice,
                dateFromTo
        );

        dialog.add(dialogHolder);
        dialog.getFooter().add(back);

        dialog.open();

    return dialog;
    }










}
