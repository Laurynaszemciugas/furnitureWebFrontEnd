package com.example.demo.Pages.Material.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class MaterialMiniStats {


    CommonComponents commonComponents;
    Common common;

    public MaterialMiniStats(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }

    public HorizontalLayout miniStatHolder(){
        HorizontalLayout miniStatHolder = new HorizontalLayout();
        miniStatHolder.addClassName("layout-flex");
        miniStatHolder.setWidthFull();

        miniStatHolder.add(
                miniStats(),
                miniStats(),
                miniStats(),
                miniStats()
        );

        return miniStatHolder;
    }

    public HorizontalLayout miniStats(){
        HorizontalLayout h = new HorizontalLayout();
        h.setAlignItems(FlexComponent.Alignment.CENTER);
        h.addClassName("island");
        h.getStyle().set("flex", "1 1 302px");
        h.getStyle().set("max-width", "620px");
        h.getStyle().set("min-width", "302px");

        VerticalLayout iconHolder = new VerticalLayout();
        iconHolder.setHeight("90px");
        iconHolder.setWidth("120px");
        iconHolder.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        iconHolder.setAlignItems(FlexComponent.Alignment.CENTER);
        iconHolder.addClassName("miniStatOne");
        Icon icon = VaadinIcon.LAYOUT.create();
        icon.setSize("30px");
        icon.setColor("black");

        iconHolder.add(icon);

        VerticalLayout v = new VerticalLayout();
        v.setSpacing(false);
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


}
