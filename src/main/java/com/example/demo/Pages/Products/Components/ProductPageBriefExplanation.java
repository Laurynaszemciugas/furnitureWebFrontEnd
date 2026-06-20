package com.example.demo.Pages.Products.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

public class ProductPageBriefExplanation {


    CommonComponents commonComponents;
    Common common;



    public ProductPageBriefExplanation(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }





    public HorizontalLayout briefPageExplanation(){
        HorizontalLayout left = commonComponents.biefPageExplanation("Inventory management");



        HorizontalLayout right =new HorizontalLayout(
                commonComponents.buttonThemeAndIcon("Add new product","ProductsAdd", ButtonVariant.PRIMARY, VaadinIcon.PLUS,"White")
        );
        right.addClassName("layout-flex");

        HorizontalLayout main = new HorizontalLayout(left,commonComponents.spaceFiller(),right);
        main.setWidthFull();
        main.setAlignItems(FlexComponent.Alignment.CENTER);
        main.addClassName("layout-flex");


        return main;
    }

}
