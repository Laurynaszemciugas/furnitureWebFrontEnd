package com.example.demo.Pages.Products.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Enums.Category;
import com.example.demo.Enums.Stock;
import com.example.demo.Enums.Visibility;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;


public class ProductPageFilters {

    CommonComponents commonComponents;
    Common common;

    Consumer<Stock> stockConsumer;

    Consumer<Category> type;

    Consumer<String> clearFilters;

    Consumer<String> allStockConsumer;

    public ProductPageFilters(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }


    public void consumerStock(Consumer<Stock> stockConsumer){
        this.stockConsumer = stockConsumer;
    }

    public void consumerType(Consumer<Category> type){
        this.type = type;
    }

    public void consumerClear(Consumer<String> clearFilters){
        this.clearFilters = clearFilters;
    }

    public void consumerAllStock(Consumer<String> allStockConsumer){
        this.allStockConsumer = allStockConsumer;
    }




    public HorizontalLayout filters(){

        Button allStock = commonComponents.normalButtonNoNavigate(Stock.ALL.getDisplayName(), "transparent-button");
        allStock.addClassName("active");
        Button inStock = commonComponents.normalButtonNoNavigate(Stock.In_Stock.getDisplayName(), "transparent-button");
        Button lowStock = commonComponents.normalButtonNoNavigate(Stock.Low_Stock.getDisplayName(), "transparent-button");
        Button noStock = commonComponents.normalButtonNoNavigate(Stock.No_Stock.getDisplayName(), "transparent-button");

        Button showMoreFilters = commonComponents.smallIconButtonsNoNavigate(VaadinIcon.FILTER,"grey");

        List<Button> buttons = List.of(allStock,inStock,lowStock,noStock);


        for(var s : buttons){
            s.addClickListener(e->{
                buttons.forEach(button -> button.removeClassName("active"));
               s.addClassName("active");
            });
        }

        ComboBox<Category> types = new ComboBox<>("Categorys");
        types.setItems(Category.values());

        ComboBox<Visibility> visibilityComboBox = new ComboBox<>("Visibility");
        visibilityComboBox.setItems(Visibility.values());


        // dialog test

        Dialog dialog = new Dialog();
        dialog.add(types,visibilityComboBox);

        showMoreFilters.addClickListener(e-> dialog.open());


        Button clear = commonComponents.normalButtonNoNavigate("Clear filters","clear-button");

        inStock.addClickListener(e->{
            stockConsumer.accept(Stock.In_Stock);
        });

        lowStock.addClickListener(e->{
            stockConsumer.accept(Stock.Low_Stock);

        });

        noStock.addClickListener(e->{
            stockConsumer.accept(Stock.No_Stock);
        });

        types.addValueChangeListener(e->{
            type.accept(e.getValue());
        });

        clear.addClickListener(e->{
            clearFilters.accept("");
        });

        allStock.addClickListener(e->{
            allStockConsumer.accept("");
        });




        HorizontalLayout horizontalLayout = new HorizontalLayout(
                allStock,
                inStock,
                lowStock,
                noStock,
                clear,
                showMoreFilters


        );
        horizontalLayout.setWidthFull();
        //horizontalLayout.getStyle().set("background-color","Red");
        horizontalLayout.addClassName("layout-flex");


        return horizontalLayout;
    }

}
