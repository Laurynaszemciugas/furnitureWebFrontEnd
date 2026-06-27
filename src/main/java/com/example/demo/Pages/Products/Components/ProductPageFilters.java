package com.example.demo.Pages.Products.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.CurrentFilterDisplay;
import com.example.demo.ControllerModels.Filter.Prodcut.ProductFilterHolder;
import com.example.demo.DTOS.ComboBoxEmployees;
import com.example.demo.DTOS.ComboBoxMaterial;
import com.example.demo.Enums.Category;
import com.example.demo.Enums.MaterialType;
import com.example.demo.Enums.Stock;
import com.example.demo.Enums.Visibility;
import com.example.demo.Services.CommonService.CommonService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


@Setter
public class ProductPageFilters {

    CommonComponents commonComponents;
    Common common;

    Consumer<Stock> stockConsumer;

    Consumer<Category> categoryConsumer;

    Consumer<Visibility> visibilityConsumer;

    Consumer<String> filterConsumer;

    Consumer<LocalDate> fromDateConsumer;

    Consumer<LocalDate> toDateConsumer;

    Consumer<Long> discountConsumer;

    Consumer<Double> priceConsumer;

    Consumer<Long> materialId;

    CurrentFilterDisplay currentFilterDisplay;

    CommonService commonService;

    Consumer<String> clearFilters;

    ProductFilterHolder filterData = new ProductFilterHolder();


    List<ComboBoxMaterial> materialList = new ArrayList<>();

    @SneakyThrows
    public ProductPageFilters(CommonComponents commonComponents, Common common, CommonService commonService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.commonService = commonService;

        materialList.addAll(commonService.getMaterialNames());

    }









    public VerticalLayout filters(){


        VerticalLayout allHolder = new VerticalLayout();
        allHolder.setPadding(false);
        allHolder.setWidthFull();
        // get current filter display
        allHolder.add(currentFilterDisplay.getFilters());

        Button allStock = commonComponents.normalButtonNoNavigate(Stock.ALL.getDisplayName(), "transparent-button");
        allStock.addClassName("active");
        Button inStock = commonComponents.normalButtonNoNavigate(Stock.In_Stock.getDisplayName(), "transparent-button");
        Button lowStock = commonComponents.normalButtonNoNavigate(Stock.Low_Stock.getDisplayName(), "transparent-button");
        Button noStock = commonComponents.normalButtonNoNavigate(Stock.No_Stock.getDisplayName(), "transparent-button");

        Button showMoreFilters = new Button(commonComponents.iconCrafter(VaadinIcon.FILTER,"30px","grey"));
        showMoreFilters.addClassName("transparent-button");

        List<Button> buttons = List.of(allStock,inStock,lowStock,noStock);


        for(var s : buttons){

            currentFilterDisplay.setReloadButtons(ee->{

                if(filterData.getStockChoice().equals(Stock.ALL)) {
                    stockConsumer.accept(Stock.ALL);
                    buttons.forEach(button ->
                            button.removeClassName("active"));
                    allStock.addClassName("active");
                }
            });

            s.addClickListener(e->{
                buttons.forEach(button -> button.removeClassName("active"));
               s.addClassName("active");
            });
        }

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

        noStock.addClickListener(e->{
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

        allStock.addClickListener(e->{
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


        showMoreFilters.addClickListener(e-> showMoreFilters());


        TextField search = commonComponents.textFieldCrafter("Search products...","",VaadinIcon.SEARCH);

        search.addValueChangeListener(e->{
            String value = e.getValue().isBlank() ? "ALL" : e.getValue();
            filterConsumer.accept(value);
        });

        Button clear = new Button(VaadinIcon.ERASER.create());
        clear.setText("Clear Filters");

        clear.addClickListener(e->{
            clearFilters.accept("");
        });

        HorizontalLayout stuff = new HorizontalLayout();
        stuff.setPadding(false);
        stuff.add(
                search,
                showMoreFilters
        );

        HorizontalLayout lilExplanation = new HorizontalLayout();
        lilExplanation.setPadding(false);
        lilExplanation.setWidthFull();
        lilExplanation.add(
                commonComponents.spanCrafterWordNoHide("Product feed","activityFeed-name"),
                stuff
        );
        lilExplanation.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);



        HorizontalLayout buttonsHolder = new HorizontalLayout(
                allStock,
                inStock,
                lowStock,
                noStock,

                commonComponents.spaceFiller(),
                clear



        );



        allHolder.add(
                lilExplanation,
                buttonsHolder
        );

        buttonsHolder.setWidthFull();
        buttonsHolder.addClassName("layout-flex");


        return allHolder;
    }

    public void showMoreFilters(){


        Button dialogClose = new Button("Back");

        Dialog dialog = new Dialog();
        dialogClose.addClickListener(e-> dialog.close());

        dialog.getFooter().add(dialogClose);


        ComboBox<Category> types = new ComboBox<>("Categorys");
        types.setItems(Category.values());
        types.setWidthFull();

        currentFilterDisplay.setComponentValue("category",filterData,types);
        types.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(), Category.ALL,null,filterData,"category","Products category",categoryConsumer);
        });


        ComboBox<Visibility> visibilityComboBox = new ComboBox<>("Visibility");
        visibilityComboBox.setItems(Visibility.values());
        visibilityComboBox.setWidthFull();

        currentFilterDisplay.setComponentValue("visibility",filterData,visibilityComboBox);
        visibilityComboBox.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(), Visibility.ALL,null,filterData,"visibility","Products visibility",visibilityConsumer);
        });

        DatePicker fromDate = new DatePicker("Created from date");

        currentFilterDisplay.setComponentValue("createdFrom",filterData,fromDate);
        fromDate.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(), LocalDate.of(1000,12,12),null,filterData,"createdFrom","Created from date",fromDateConsumer);
        });


        DatePicker toDate = new DatePicker("Created to date");

        currentFilterDisplay.setComponentValue("createdTo",filterData,toDate);
        toDate.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(), LocalDate.of(1000,12,12),null,filterData,"createdTo","Created to date",toDateConsumer);
        });

        HorizontalLayout dateHolder = commonComponents.doubleValueRow(
                fromDate,
                toDate
        );


        IntegerField discount = new IntegerField("Discount");

        currentFilterDisplay.setComponentValue("discount",filterData,discount);
        discount.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue() == null ? 0L : e.getValue(), 0L,null,filterData,"discount","Discount",discountConsumer);
        });
        discount.setStepButtonsVisible(true);
        discount.setStep(1);
        discount.setWidthFull();

        NumberField price = new NumberField("Price");
        currentFilterDisplay.setComponentValue("price",filterData,price);
        price.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(), 0.0,null,filterData,"price","Product price",priceConsumer);
        });
        price.setStepButtonsVisible(true);
        price.setStep(10);
        price.setWidthFull();

        ComboBox<ComboBoxMaterial> materialComboBox = new ComboBox<>("Material in products");
        materialComboBox.setItems(materialList);
        materialComboBox.setValue(materialList.stream().filter(e->e.getId().equals(filterData.getMaterialId()))
                .findFirst().orElse(null));

        materialComboBox.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue() == null ? null : Long.valueOf(e.getValue().getId()), 0L,e.getValue().getMaterialName(),filterData,"materialId","Material",materialId);
        });
        materialComboBox.setWidthFull();
        materialComboBox.setItemLabelGenerator(ComboBoxMaterial::getMaterialName);


        VerticalLayout dialogStuff = new VerticalLayout(
                commonComponents.biefPageExplanation("Filters"),
                types,
                visibilityComboBox,
                dateHolder,
                discount,
                price,
                materialComboBox);
        dialog.add(dialogStuff);

        dialog.open();

    }

}
