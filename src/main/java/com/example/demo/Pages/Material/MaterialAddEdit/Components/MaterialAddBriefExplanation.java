package com.example.demo.Pages.Material.MaterialAddEdit.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.springframework.stereotype.Service;

@Service
public class MaterialAddBriefExplanation {

    CommonComponents commonComponents;
    Common common;


    public MaterialAddBriefExplanation(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }

    public HorizontalLayout briefExplanation(){

        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        HorizontalLayout buttonHolder = new HorizontalLayout();

        Button cancel = commonComponents.normalThemeButton("Cancel","Materials", ButtonVariant.LUMO_ICON);
        Button createOrder = commonComponents.normalThemeButtonNoNavigate("Create Material", ButtonVariant.LUMO_PRIMARY);


        buttonHolder.add(
                cancel,
                createOrder
        );


        h.add(
                commonComponents.biefPageExplanation("Create new material"),
                buttonHolder

        );


        return h;
    }

}
