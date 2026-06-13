package com.example.demo.Pages.Material.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Enums.ActiveInactive;
import com.example.demo.Enums.MaterialType;
import com.example.demo.Enums.Stock;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;

public class MaterialFilters {

    CommonComponents commonComponents;
    Common common;


    public MaterialFilters(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }



    public VerticalLayout filters(){
        VerticalLayout v = new VerticalLayout();
        v.setPadding(false);


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

        Button back = new Button("Back", e-> dialog.close());

        VerticalLayout dialogHolder = new VerticalLayout();
        dialogHolder.setSpacing(false);
        dialogHolder.setPadding(false);


        ComboBox<MaterialType> materialTypeComboBox = new ComboBox<>("Material type");
        materialTypeComboBox.setItems(MaterialType.values());
        materialTypeComboBox.setItemLabelGenerator(MaterialType::getDisplayName);

        ComboBox<ActiveInactive> activeInactiveComboBox = new ComboBox<>("Material status");
        activeInactiveComboBox.setItems(ActiveInactive.values());
        activeInactiveComboBox.setItemLabelGenerator(ActiveInactive::getGetDisplayNames);





        dialogHolder.add(
                materialTypeComboBox,
                activeInactiveComboBox,
                back
        );

        dialog.add(dialogHolder);

        dialog.open();

return dialog;
    }


}
