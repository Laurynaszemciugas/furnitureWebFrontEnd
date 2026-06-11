package com.example.demo.Pages.Material.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Enums.ActiveInactive;
import com.vaadin.flow.component.button.Button;
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
        Button active = commonComponents.normalButtonNoNavigate(ActiveInactive.ACTIVE.getGetDisplayNames(), "transparent-button");
        Button inactive = commonComponents.normalButtonNoNavigate(ActiveInactive.INACTIVE.getGetDisplayNames(), "transparent-button");

        Button clear = new Button("Clear filters", VaadinIcon.ERASER.create());

        buttonHolder.add(
                all,
                active,
                inactive
        );

        buttonHolderhOLDER.add(
                buttonHolder,
                clear
        );


        List<Button> buttonList = List.of(all,active,inactive);

        for(var s : buttonList){

            s.addClickListener(e->{
                buttonList.forEach(button->
                        button.removeClassName("active"));
                s.addClassName("active");
            });
        }

        TextField search = commonComponents.textFieldCrafter("Search products...","",VaadinIcon.SEARCH);
        Button showMoreFilters = new Button(commonComponents.iconCrafter(VaadinIcon.FILTER,"30px","grey"));
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


}
