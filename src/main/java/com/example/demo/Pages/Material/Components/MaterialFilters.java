package com.example.demo.Pages.Material.Components;

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

import java.util.List;

public class MaterialFilters {

    CommonComponents commonComponents;
    Common common;
    CurrentFilterDisplay currentFilterDisplay;


    MaterialFilterHolder filterData = new MaterialFilterHolder();


    public MaterialFilters(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.currentFilterDisplay = new CurrentFilterDisplay(commonComponents,common);



    }



    public VerticalLayout filters(){
        VerticalLayout v = new VerticalLayout();
        v.setPadding(false);

        // get current filter display
        v.add(currentFilterDisplay.getFilters());


        HorizontalLayout buttonHolder = new HorizontalLayout();

        HorizontalLayout buttonHolderhOLDER = new HorizontalLayout();
        buttonHolderhOLDER.setWidthFull();
        buttonHolderhOLDER.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        Button all = commonComponents.normalButtonNoNavigate(ActiveInactive.ALL.getGetDisplayNames(), "transparent-button");
        all.addClickListener(e->{
            filterData.setStockChoice(Stock.ALL);
        });
        all.addClassName("active");
        Button inStock = commonComponents.normalButtonNoNavigate(Stock.In_Stock.getDisplayName(), "transparent-button");
        inStock.addClickListener(e->{
            filterData.setStockChoice(Stock.In_Stock);
        });
        Button lowStock = commonComponents.normalButtonNoNavigate(Stock.Low_Stock.getDisplayName(), "transparent-button");
        lowStock.addClickListener(e->{
            filterData.setStockChoice(Stock.Low_Stock);
        });
        Button NoStock = commonComponents.normalButtonNoNavigate(Stock.No_Stock.getDisplayName(), "transparent-button");
        NoStock.addClickListener(e->{
            filterData.setStockChoice(Stock.No_Stock);
        });

        Button clear = new Button("Clear filters", VaadinIcon.ERASER.create());

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

            s.addClickListener(e->{

                buttonList.forEach(button->
                        button.removeClassName("active"));
                s.addClassName("active");

                currentFilterDisplay.addFilter("Stock ", s.getText(),"stockChoice",filterData);

                currentFilterDisplay.setReloadInfo(ee->{
                    if(filterData.getStockChoice() == null){
                        buttonList.forEach(button->
                                button.removeClassName("active"));
                        all.addClassName("active");
                    }
                });




            });




        }

        TextField search = commonComponents.textFieldCrafter("Search products...","",VaadinIcon.SEARCH);
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
        materialTypeComboBox.addValueChangeListener(e->{
            if (e.getValue() == null) {
                materialTypeComboBox.setInvalid(true);
                materialTypeComboBox.setErrorMessage("Material type cannot be empty");
            }
            else{
                filterData.setMaterialTypeChoice(materialTypeComboBox.getValue());
                if(e.isFromClient()) {
                    currentFilterDisplay.addFilter("Material type", materialTypeComboBox.getValue().toString(),"materialTypeChoice",filterData);
                }
            }
        });
        if(filterData.getMaterialTypeChoice() != null){
            materialTypeComboBox.setValue(filterData.getMaterialTypeChoice());
        }

        ComboBox<ActiveInactive> activeInactiveComboBox = new ComboBox<>("Material status");
        activeInactiveComboBox.setWidthFull();
        activeInactiveComboBox.setItems(ActiveInactive.values());
        activeInactiveComboBox.setItemLabelGenerator(ActiveInactive::getGetDisplayNames);
        activeInactiveComboBox.addValueChangeListener(e->{
            if (e.getValue() == null) {
                activeInactiveComboBox.setInvalid(true);
                activeInactiveComboBox.setErrorMessage("Material Active/Inactive cannot be empty");
            }
            else{
                filterData.setActiveInactive(activeInactiveComboBox.getValue());
                if(e.isFromClient()) {
                    currentFilterDisplay.addFilter("Material status", activeInactiveComboBox.getValue().toString(),"activeInactive",filterData);
                }
            }
        });
        if(filterData.getActiveInactive() != null){
            activeInactiveComboBox.setValue(filterData.getActiveInactive());
        }


        // ================= STOCK AMOUNT =================================
        IntegerField stockAmount  = new IntegerField("Stock amount");
        stockAmount.setStepButtonsVisible(true);
        stockAmount.setMin(0);
        stockAmount.addValueChangeListener(e->{
            if (e.getValue() == null || e.getValue() < 0 ) {
                stockAmount.setInvalid(true);
                stockAmount.setErrorMessage("Value must be positive");
            }
            else{
                filterData.setStockAmountChoice(Long.valueOf(stockAmount.getValue()));
                if(e.isFromClient()) {
                    currentFilterDisplay.addFilter("Stock Amount", stockAmount.getValue().toString(),"stockAmountChoice",filterData);
                }
            }
        });
        if(filterData.getStockAmountChoice() != null){
            stockAmount.setValue(Math.toIntExact(filterData.getStockAmountChoice()));
        }
        // ================= MIN AMOUNT =================================
        IntegerField minThreshold  = new IntegerField("Min threshold");
        minThreshold.setStepButtonsVisible(true);
        stockAmount.setMin(0);
        minThreshold.addValueChangeListener(e->{
            if (e.getValue() == null || e.getValue() < 0 ) {
                minThreshold.setInvalid(true);
                minThreshold.setErrorMessage("Value must be positive");
            }
            else{
                filterData.setMinThresholdChoice(Long.valueOf(minThreshold.getValue()));
                if(e.isFromClient()) {
                    currentFilterDisplay.addFilter("Min Threshold", minThreshold.getValue().toString(),"minThresholdChoice",filterData);
                }
            }
        });
        if(filterData.getMinThresholdChoice() != null){
            minThreshold.setValue(Math.toIntExact(filterData.getMinThresholdChoice()));
        }


        // ================= UNIT PRICE AMOUNT =================================
        NumberField unitPrice  = new NumberField("Unit price");
        unitPrice.setStep(0.5);
        unitPrice.setMax(100000);
        unitPrice.setMin(0);
        unitPrice.setStepButtonsVisible(true);
        unitPrice.setWidthFull();
        unitPrice.addValueChangeListener(e->{
            if (e.getValue() == null || e.getValue() < 0.0 ) {
                unitPrice.setInvalid(true);
                unitPrice.setErrorMessage("Value must be positive");
            }
            else{
                filterData.setUnitPriceChoice(unitPrice.getValue());
                if(e.isFromClient()) {
                    currentFilterDisplay.addFilter("Unit price", unitPrice.getValue().toString(),"unitPriceChoice",filterData);
                }
            }
        });
        if(filterData.getUnitPriceChoice() != null){
            unitPrice.setValue(filterData.getUnitPriceChoice());
        }



        HorizontalLayout stockLevels = commonComponents.doubleValueRow(
                stockAmount,
                minThreshold
        );


        DatePicker dateFrom = new DatePicker("Date from");
        dateFrom.addValueChangeListener(e->{
            if (e.getValue() == null) {
                dateFrom.setInvalid(true);
                dateFrom.setErrorMessage("From value cannot be empty");
            }
            else{
                filterData.setFromDateChoice(dateFrom.getValue());
                if(e.isFromClient()) {
                    currentFilterDisplay.addFilter("From date", dateFrom.getValue().toString(),"fromDateChoice",filterData);
                }
            }
        });
        if(filterData.getFromDateChoice() != null){
            dateFrom.setValue(filterData.getFromDateChoice());
        }

        DatePicker dateTo = new DatePicker("Date to");
        dateTo.addValueChangeListener(e->{
            if (e.getValue() == null) {
                dateTo.setInvalid(true);
                dateTo.setErrorMessage("To value cannot be empty");
            }
            else{
                filterData.setTodDateChoice(dateTo.getValue());
                if(e.isFromClient()) {
                    currentFilterDisplay.addFilter("To date", dateTo.getValue().toString(),"todDateChoice",filterData);
                }
            }
        });
        if(filterData.getTodDateChoice() != null){
            dateTo.setValue(filterData.getTodDateChoice());
        }

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
