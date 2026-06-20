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
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;


@Setter
public class ProductPageFilters {

    CommonComponents commonComponents;
    Common common;

    Consumer<Stock> stockConsumer;

    Consumer<Category> type;

    Consumer<String> clearFilters;

    Consumer<String> allStockConsumer;

    Consumer<Visibility> visibilityConsumer;

    Consumer<String> filterConsumer;

    public ProductPageFilters(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }







    public VerticalLayout filters(){

        Button allStock = commonComponents.normalButtonNoNavigate(Stock.ALL.getDisplayName(), "transparent-button");
        allStock.addClassName("active");
        Button inStock = commonComponents.normalButtonNoNavigate(Stock.In_Stock.getDisplayName(), "transparent-button");
        Button lowStock = commonComponents.normalButtonNoNavigate(Stock.Low_Stock.getDisplayName(), "transparent-button");
        Button noStock = commonComponents.normalButtonNoNavigate(Stock.No_Stock.getDisplayName(), "transparent-button");

        Button showMoreFilters = new Button(commonComponents.iconCrafter(VaadinIcon.FILTER,"30px","grey"));
        showMoreFilters.addClassName("transparent-button");

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

        Button dialogClose = new Button("Back");

        Dialog dialog = new Dialog();
        VerticalLayout dialogStuff = new VerticalLayout(
                commonComponents.biefPageExplanation("Filters"),
                types,
                visibilityComboBox,
                dialogClose);
        dialog.add(dialogStuff);



        showMoreFilters.addClickListener(e-> dialog.open());
        dialogClose.addClickListener(e-> dialog.close());

        Button clear = new Button(VaadinIcon.ERASER.create());
        clear.setText("Clear Filters");


        TextField search = commonComponents.textFieldCrafter("Search products...","",VaadinIcon.SEARCH);

        search.addValueChangeListener(e->{
            filterConsumer.accept(e.getValue());
        });


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

        visibilityComboBox.addValueChangeListener(e->{
           visibilityConsumer.accept(e.getValue());
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



        HorizontalLayout horizontalLayout = new HorizontalLayout(
                allStock,
                inStock,
                lowStock,
                noStock,

                commonComponents.spaceFiller(),
                clear



        );

        VerticalLayout allHolder = new VerticalLayout();
        allHolder.setPadding(false);
        allHolder.setWidthFull();

        allHolder.add(
                lilExplanation,
                horizontalLayout
        );

        horizontalLayout.setWidthFull();
        horizontalLayout.addClassName("layout-flex");


        return allHolder;
    }

}
