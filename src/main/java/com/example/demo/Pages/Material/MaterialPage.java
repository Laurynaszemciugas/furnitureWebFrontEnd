package com.example.demo.Pages.Material;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.CommonComponents.ProductComponents.RightSide.Components.ProductEditImage;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;

@Route(value = "Materials", layout = MainLayout.class)
public class MaterialPage extends VerticalLayout implements BeforeEnterObserver {

    CommonComponents commonComponents;
    Common common;

    ProductEditImage productEditImage;


    public MaterialPage(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.productEditImage = new ProductEditImage(commonComponents,common);


        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);


    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        removeAll();




        add(mainLayout());

    }


    public VerticalLayout mainLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");
        verticalLayout.addClassName("main-island");


        productEditImage.setListConsumer(e->
                System.out.println("image changed")
        );

        productEditImage.setMainChange(e->{
            System.out.println("main image changed");
        });

        verticalLayout.add(
                productEditImage.images(new ArrayList<>()),
                productEditImage.uploadStuff()
        );

        return  verticalLayout;

    }





}
