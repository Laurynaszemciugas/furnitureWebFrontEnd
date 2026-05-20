package com.example.demo.Pages.Orders.Page;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.MainLayout.MainLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "Orders", layout = MainLayout.class)
public class Orders extends VerticalLayout implements BeforeEnterObserver {

    CommonComponents commonComponents;
    Common common;

    public Orders(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);

        add(mainLayout());
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

        verticalLayout.add(briefExplanation());

        return verticalLayout;
    }

    public VerticalLayout briefExplanation(){
        VerticalLayout v = new VerticalLayout();
        v.add(commonComponents.biefPageExplanation("Orders"));
        return v;
    }



}











