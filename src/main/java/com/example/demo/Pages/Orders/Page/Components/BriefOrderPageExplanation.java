package com.example.demo.Pages.Orders.Page.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;


public class BriefOrderPageExplanation {

    CommonComponents commonComponents;
    Common common;


    boolean firstLoad = true;

    public BriefOrderPageExplanation(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }

    public HorizontalLayout briefExplanation(){

        HorizontalLayout v = new HorizontalLayout();

        if(firstLoad){
            v.addClassName("smooth-panel");
            firstLoad = false;
        }
        else{
            v.removeClassName("smooth-panel");
        }

        v.setWidthFull();
        v.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);




        v.add(
                commonComponents.biefPageExplanation("Orders"),
                commonComponents.buttonThemeAndIcon("Add new Order","OrdersAdd", ButtonVariant.PRIMARY, VaadinIcon.PLUS,"White"));
        return v;
    }

}
