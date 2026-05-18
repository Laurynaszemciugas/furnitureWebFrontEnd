package com.example.demo.Pages.Products.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Enums.Category;
import com.example.demo.Enums.Stock;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;


public class ProductPageFilters {

    CommonComponents commonComponents;
    Common common;

    Consumer<Stock> stockConsumer;

    Consumer<Category> type;

    Consumer<String> clearFilters;

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






    public HorizontalLayout filters(){

        Button inStock = commonComponents.normalButtonNoNavigate(Stock.In_Stock.getDisplayName(), "stock-in");
        Button lowStock = commonComponents.normalButtonNoNavigate(Stock.Low_Stock.getDisplayName(), "stock-low");
        Button noStock = commonComponents.normalButtonNoNavigate(Stock.No_Stock.getDisplayName(), "stock-out");

        ComboBox<Category> types = new ComboBox<>();
        types.setItems(Category.values());


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




        HorizontalLayout horizontalLayout = new HorizontalLayout(
                inStock,
                lowStock,
                noStock,
                types,
                clear


        );
        horizontalLayout.setWidthFull();


        return horizontalLayout;
    }

}
