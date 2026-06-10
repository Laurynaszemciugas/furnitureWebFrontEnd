package com.example.demo.Pages.Material;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.CommonComponents.ProductComponents.RightSide.Components.ProductEditImage;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;

@Route(value = "Materials", layout = MainLayout.class)
public class MaterialPage extends VerticalLayout implements BeforeEnterObserver {

    CommonComponents commonComponents;
    Common common;



    public MaterialPage(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;


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

        HorizontalLayout miniStatHolder = new HorizontalLayout();
        miniStatHolder.addClassName("layout-flex");
        miniStatHolder.setWidthFull();
        miniStatHolder.add(
                miniStats(),
                miniStats(),
                miniStats(),
                miniStats()
        );


        verticalLayout.add(
                briefExplanation(),
                miniStatHolder

        );




        return  verticalLayout;

    }

    public HorizontalLayout miniStats(){
        HorizontalLayout h = new HorizontalLayout();
        h.setAlignItems(Alignment.CENTER);
        h.addClassName("island");
        h.getStyle().set("flex", "1 1 302px");
        h.getStyle().set("max-width", "620px");
        h.getStyle().set("min-width", "302px");

        VerticalLayout iconHolder = new VerticalLayout();
        iconHolder.setHeight("80px");
        iconHolder.setWidth("120px");
        iconHolder.setJustifyContentMode(JustifyContentMode.CENTER);
        iconHolder.setAlignItems(Alignment.CENTER);
        iconHolder.addClassName("miniStatOne");
        Icon icon = VaadinIcon.LAYOUT.create();
        icon.setSize("30px");
        icon.setColor("black");

        iconHolder.add(icon);

        VerticalLayout v = new VerticalLayout();
        v.add(
                commonComponents.spanCrafter("Total materials","activityFeed-name"),
                commonComponents.spanCrafter("15","stat-value"),
                commonComponents.spanCrafter("Total materials","stat-title")
        );


        h.add(
                iconHolder,
                v
        );
        return h;
    }


    public HorizontalLayout briefExplanation(){

        HorizontalLayout v = new HorizontalLayout();
        v.setWidthFull();
        v.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);



        v.add(
                commonComponents.biefPageExplanation("Materials"),
                commonComponents.buttonThemeAndIcon("Add material","", ButtonVariant.PRIMARY, VaadinIcon.PLUS,"White"));
        return v;
    }




//     productEditImage.setListConsumer(e->
//            System.out.println("image changed")
//            );
//
//        productEditImage.setMainChange(e->{
//        System.out.println("main image changed");
//    });
//
//        verticalLayout.add(
//                productEditImage.images(new ArrayList<>()),
//                productEditImage.uploadStuff()
//                );
//




}
