package com.example.demo.Common.Logic;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.stereotype.Service;

@Service
public class miniStatCrafter {

    CommonComponents commonComponents;
    Common common;

    public miniStatCrafter(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }

    public <T> HorizontalLayout miniStats(VaadinIcon selectedIcon, String title, T value, String miniTittle){
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
        Icon icon = commonComponents.iconCrafter(selectedIcon,"30px","black");
        iconHolder.add(icon);

        VerticalLayout v = new VerticalLayout();
        v.setSpacing(false);
        v.add(
                commonComponents.spanCrafter(title,"activityFeed-name"),
                commonComponents.spanCrafter(value.toString(),"stat-value"),
                commonComponents.spanCrafter(miniTittle,"stat-title")
        );


        h.add(
                iconHolder,
                v
        );
        return h;
    }

}
