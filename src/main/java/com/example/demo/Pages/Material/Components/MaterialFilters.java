package com.example.demo.Pages.Material.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Filter.Common.FilterMeta;
import com.example.demo.ControllerModels.Filter.Material.MaterialFilterHolder;
import com.example.demo.Enums.ActiveInactive;
import com.example.demo.Enums.MaterialType;
import com.example.demo.Enums.Status;
import com.example.demo.Enums.Stock;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.slider.Slider;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.cglib.core.Local;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaterialFilters {

    CommonComponents commonComponents;
    Common common;

    HorizontalLayout h = new HorizontalLayout();
    Map<String, FilterMeta> map = new HashMap<>();


    public MaterialFilters(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;

        h.addClassName("island");
        h.setVisible(false);
        h.setWidthFull();

    }

    MaterialFilterHolder filterData = new MaterialFilterHolder();

    public VerticalLayout filters(){
        VerticalLayout v = new VerticalLayout();
        v.setPadding(false);

        v.add(h);


        HorizontalLayout buttonHolder = new HorizontalLayout();

        HorizontalLayout buttonHolderhOLDER = new HorizontalLayout();
        buttonHolderhOLDER.setWidthFull();
        buttonHolderhOLDER.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        Button all = commonComponents.normalButtonNoNavigate(ActiveInactive.ALL.getGetDisplayNames(), "transparent-button");
        all.addClassName("active");
        Button active = commonComponents.normalButtonNoNavigate(Stock.In_Stock.getDisplayName(), "transparent-button");
        Button inactive = commonComponents.normalButtonNoNavigate(Stock.Low_Stock.getDisplayName(), "transparent-button");
        Button NoStock = commonComponents.normalButtonNoNavigate(Stock.No_Stock.getDisplayName(), "transparent-button");

        Button clear = new Button("Clear filters", VaadinIcon.ERASER.create());

        buttonHolder.add(
                all,
                active,
                inactive,
                NoStock
        );

        buttonHolderhOLDER.add(
                buttonHolder,
                clear
        );


        List<Button> buttonList = List.of(all,active,inactive,NoStock);

        for(var s : buttonList){

            s.addClickListener(e->{
                buttonList.forEach(button->
                        button.removeClassName("active"));
                s.addClassName("active");
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

        ComboBox<ActiveInactive> activeInactiveComboBox = new ComboBox<>("Material status");
        activeInactiveComboBox.setWidthFull();
        activeInactiveComboBox.setItems(ActiveInactive.values());
        activeInactiveComboBox.setItemLabelGenerator(ActiveInactive::getGetDisplayNames);

        IntegerField stockAmount  = new IntegerField("Stock amount");
        stockAmount.addValueChangeListener(e->{
            if (e.getValue() == null || e.getValue() < 0 ) {
                stockAmount.setInvalid(true);
                stockAmount.setErrorMessage("Value must be positive");
            }
            else{
                filterData.setStockAmountChoice(Long.valueOf(stockAmount.getValue()));
                if(e.isFromClient()) {
                    addFilter("Stock Amount", stockAmount.getValue().toString(),"stockAmountChoice");
                }
            }
        });
        if(filterData.getStockAmountChoice() != null){
            stockAmount.setValue(Math.toIntExact(filterData.getStockAmountChoice()));
        }

        IntegerField minThreshold  = new IntegerField("Min threshold");
        minThreshold.addValueChangeListener(e->{
            if (e.getValue() == null || e.getValue() < 0 ) {
                minThreshold.setInvalid(true);
                minThreshold.setErrorMessage("Value must be positive");
            }
            else{
                filterData.setMinThresholdChoice(Long.valueOf(minThreshold.getValue()));
                if(e.isFromClient()) {
                    addFilter("Min Threshold", minThreshold.getValue().toString(),"minThresholdChoice");
                }
            }
        });
        if(filterData.getMinThresholdChoice() != null){
            minThreshold.setValue(Math.toIntExact(filterData.getMinThresholdChoice()));
        }


        NumberField unitPrice  = new NumberField("Unit price");
        unitPrice.setStep(0.5);
        unitPrice.setMax(100000);
        unitPrice.setMin(0);
        unitPrice.setStepButtonsVisible(true);
        unitPrice.setWidthFull();



        HorizontalLayout stockLevels = commonComponents.doubleValueRow(
                stockAmount,
                minThreshold
        );


        DatePicker dateFrom = new DatePicker("Date from");
        DatePicker dateTo = new DatePicker("Date to");

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


    public void addFilter(String filterName,String filterValue,String referenceName){


        if(map.isEmpty()){
            map.put(filterName,new FilterMeta(filterValue,referenceName));
        }
        else{
            if(map.containsKey(filterName)){
                map.put(filterName, new FilterMeta(filterValue,referenceName));
            }
            else {
                map.put(filterName, new FilterMeta(filterValue,referenceName));
            }
        }
        h.removeAll();
        for (Map.Entry<String, FilterMeta> entry : map.entrySet()) {
            h.add(filterExisting(entry.getKey(),entry.getValue()));
        }

    }

    public HorizontalLayout filterExisting(String filterName,FilterMeta filterMeta){

        if(map != null|| !map.isEmpty()){
            h.setVisible(true);
        }



        HorizontalLayout v = new HorizontalLayout();
        v.setAlignItems(FlexComponent.Alignment.CENTER);
        v.setWidth("fill-content");
        v.addClassName("tag-badge");

        Button removeButton = new Button(VaadinIcon.CLOSE_SMALL.create());
        removeButton.addClassName("remove-button");
        removeButton.addClickListener(e -> {
            map.remove(filterName);
            v.getParent().ifPresent(parent -> ((HasComponents) parent).remove(v));


            try {
                Field field =  filterData.getClass().getDeclaredField(filterMeta.getFieldName());
                field.setAccessible(true);
                field.set(filterData, null);
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }





            if(map == null|| map.isEmpty()){
                h.setVisible(false);
            }


        });

            Span span = new Span(String.format("%s | %s",filterName,filterMeta.getValue()));
            v.add(span,removeButton);




            return v;


    }


}
