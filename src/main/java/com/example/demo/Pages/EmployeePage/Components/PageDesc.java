package com.example.demo.Pages.EmployeePage.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.springframework.stereotype.Service;

public class PageDesc {


    CommonComponents commonComponents;
    Common common;

    public PageDesc(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }

    public HorizontalLayout orderPreviewDesc(){
        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.setAlignItems(FlexComponent.Alignment.CENTER);

        Button back = new Button("Back", e-> UI.getCurrent().getPage().getHistory().back());
        back.setPrefixComponent(commonComponents.iconCrafter(VaadinIcon.ANGLE_LEFT,"25px","white"));
        back.addThemeVariants(ButtonVariant.PRIMARY);

        h.add(
                back
        );

        return h;
    }




}
