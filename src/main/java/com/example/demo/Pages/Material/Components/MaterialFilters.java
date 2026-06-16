package com.example.demo.Pages.Material.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.CurrentFilterDisplay;
import com.example.demo.ControllerModels.Filter.Material.MaterialFilterHolder;
import com.example.demo.Enums.ActiveInactive;
import com.example.demo.Enums.MaterialType;
import com.example.demo.Enums.Stock;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
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
import lombok.SneakyThrows;

import java.lang.reflect.Field;
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
        setComponentValue("materialTypeChoice",filterData,materialTypeComboBox);
        materialTypeComboBox.addValueChangeListener(e->{

            filterSetter(e.getValue(),MaterialType.ALL,filterData,"materialTypeChoice","Material type",materialTypeConsumer,materialTypeComboBox);


//            MaterialType value = e.getValue() == null ? MaterialType.ALL : e.getValue();
//
//            if (e.getValue() == null) {
//                filterData.setMaterialTypeChoice(null);
//                currentFilterDisplay.addFilter("Material type", materialTypeComboBox.getValue(),"materialTypeChoice",filterData);
//                materialTypeConsumer.accept(value);
//            }
//            else{
//                filterData.setMaterialTypeChoice(materialTypeComboBox.getValue());
//                if(e.isFromClient()) {
//                    currentFilterDisplay.addFilter("Material type", materialTypeComboBox.getValue(),"materialTypeChoice",filterData);
//                }
//                materialTypeConsumer.accept(value);
//            }
        });
//        if(filterData.getMaterialTypeChoice() != null){
//            materialTypeComboBox.setValue(filterData.getMaterialTypeChoice());
//
//        }

        ComboBox<ActiveInactive> activeInactiveComboBox = new ComboBox<>("Material status");
        activeInactiveComboBox.setWidthFull();
        activeInactiveComboBox.setItems(ActiveInactive.values());
        activeInactiveComboBox.setItemLabelGenerator(ActiveInactive::getGetDisplayNames);
        activeInactiveComboBox.addValueChangeListener(e->{

            ActiveInactive value = e.getValue() == null ? ActiveInactive.ALL : e.getValue();

            if (e.getValue() == null) {
                activeInactiveConsumer.accept(value);
            }
            else{
                filterData.setActiveInactive(activeInactiveComboBox.getValue());
                if(e.isFromClient()) {
                    currentFilterDisplay.addFilter("Material status", activeInactiveComboBox.getValue(),"activeInactive",filterData);
                }
                activeInactiveConsumer.accept(value);
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

            Long value = e.getValue() == null ? 0L : e.getValue();

            if (e.getValue() == null || e.getValue() < 0 ) {
                stockAmountConsumer.accept(value);
            }
            else{
                filterData.setStockAmountChoice(Long.valueOf(stockAmount.getValue()));
                if(e.isFromClient()) {
                    currentFilterDisplay.addFilter("Stock Amount", stockAmount.getValue(),"stockAmountChoice",filterData);
                }
                stockAmountConsumer.accept(value);
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

            Long value = e.getValue() == null ? 0L : e.getValue();

            if (e.getValue() == null || e.getValue() < 0 ) {
                minThresholdConsumer.accept(value);
            }
            else{
                filterData.setMinThresholdChoice(Long.valueOf(minThreshold.getValue()));
                if(e.isFromClient()) {
                    currentFilterDisplay.addFilter("Min Threshold", minThreshold.getValue(),"minThresholdChoice",filterData);
                }
                minThresholdConsumer.accept(value);
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

            Double value = e.getValue() == null ? 0L : e.getValue();

            if (e.getValue() == null || e.getValue() < 0.0 ) {
                unitPriceConsumer.accept(value);
            }
            else{
                filterData.setUnitPriceChoice(unitPrice.getValue());
                if(e.isFromClient()) {
                    currentFilterDisplay.addFilter("Unit price", unitPrice.getValue().toString(),"unitPriceChoice",filterData);
                }
                unitPriceConsumer.accept(value);
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

            LocalDate value = e.getValue() == null ? LocalDate.of(1000,12,12) : e.getValue();


            if (e.getValue() == null) {
                fromDateConsumer.accept(value);
            }
            else{
                filterData.setFromDateChoice(dateFrom.getValue());
                if(e.isFromClient()) {
                    currentFilterDisplay.addFilter("From date", dateFrom.getValue().toString(),"fromDateChoice",filterData);
                }
                fromDateConsumer.accept(value);
            }
        });
        if(filterData.getFromDateChoice() != null){
            dateFrom.setValue(filterData.getFromDateChoice());
        }

        DatePicker dateTo = new DatePicker("Date to");
        dateTo.addValueChangeListener(e->{

            LocalDate value = e.getValue() == null ? LocalDate.of(1000,12,12) : e.getValue();

            if (e.getValue() == null) {
                toDateConsumer.accept(value);
            }
            else{
                filterData.setTodDateChoice(dateTo.getValue());
                if(e.isFromClient()) {
                    currentFilterDisplay.addFilter("To date", dateTo.getValue().toString(),"todDateChoice",filterData);
                }
                toDateConsumer.accept(value);
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


    @SneakyThrows
    public <T,S> void filterSetter(T getValue, T ifNotNull, S filterDTO, String referenceName, String filterName, Consumer<T> consumer, Component component){

            T valueItem = getValue == null ? ifNotNull : getValue;

            if (getValue == null) {

                Field field = filterDTO.getClass().getDeclaredField(referenceName);
                field.setAccessible(true);
                field.set(filterDTO, null);

                currentFilterDisplay.addFilter(filterName, getValue,referenceName,filterDTO);
                consumer.accept(valueItem);
            }
            else{
                Field field = filterDTO.getClass().getDeclaredField(referenceName);
                field.setAccessible(true);
                field.set(filterDTO, getValue);

                currentFilterDisplay.addFilter(filterName, getValue,referenceName,filterDTO);

                consumer.accept(valueItem);
            }


    }
    @SneakyThrows
    public <T,S> void setComponentValue(String referenceName, S filterDTO, Component component) {
        if (component instanceof HasValue<?, ?> c) {


                Field field = filterDTO.getClass().getDeclaredField(referenceName);
                field.setAccessible(true);
                Object value = field.get(filterDTO);

            if (value != null) {
                ((HasValue) c).setValue(value);


            }
        }


//     materialTypeComboBox.addValueChangeListener(e->{
//
//        MaterialType value = e.getValue() == null ? MaterialType.ALL : e.getValue();
//
//        if (e.getValue() == null) {
//            filterData.setMaterialTypeChoice(null);
//            currentFilterDisplay.addFilter("Material type", materialTypeComboBox.getValue(),"materialTypeChoice",filterData);
//            materialTypeConsumer.accept(value);
//        }
//        else{
//            filterData.setMaterialTypeChoice(materialTypeComboBox.getValue());
//            if(e.isFromClient()) {
//                currentFilterDisplay.addFilter("Material type", materialTypeComboBox.getValue(),"materialTypeChoice",filterData);
//            }
//            materialTypeConsumer.accept(value);
//        }
//    });
//        if(filterData.getMaterialTypeChoice() != null){
//        materialTypeComboBox.setValue(filterData.getMaterialTypeChoice());
//
//    }


    }
}
