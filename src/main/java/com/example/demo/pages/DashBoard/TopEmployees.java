package com.example.demo.pages.DashBoard;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.stereotype.Service;


@Service
public class TopEmployees {

    CommonComponents commonComponents;
    Common common;

    public TopEmployees(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }

    public VerticalLayout topEmployees(){
        VerticalLayout mainLayout2 = new VerticalLayout(commonComponents.spanCrafterWordNoHide("Top employees","stat-value"));
        mainLayout2.setWidth("600px");
        mainLayout2.addClassName("island");


        // add data here from db
        VerticalLayout material2 = new VerticalLayout(topEmployeeFeed(),topEmployeeFeed(),topEmployeeFeed(),topEmployeeFeed());

        // scroller that takes material as data from materialLowNoStockFeed
        Scroller scroller2 = new Scroller(material2);
        scroller2.setSizeFull();
        scroller2.setHeight("400px");


        // simple button in the middle
        HorizontalLayout buttonAtTheBottom2 = new HorizontalLayout(commonComponents.normalThemeButton("View All Employee", "s", ButtonVariant.LUMO_PRIMARY));
        buttonAtTheBottom2.setWidthFull();
        buttonAtTheBottom2.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        // add remainin stuff to the main layout of the low no material layout
        mainLayout2.add(scroller2,buttonAtTheBottom2);

        return mainLayout2;
    }









    // employee feed crafter
    public HorizontalLayout topEmployeeFeed(){
        HorizontalLayout iconHolder = new HorizontalLayout(commonComponents.iconCrafter(VaadinIcon.CIRCLE,"20px","blue"));
        iconHolder.getStyle().set("margin-top","5px");

        Image image =new Image("Screenshot 2026-04-27 001745.png","user image");
        image.setWidth("60px");
        image.setHeight("60px");
        image.getStyle().set("border-radius","30px");


        VerticalLayout e = new VerticalLayout(
                commonComponents.tripleValueRow(commonComponents.spanCrafterWordNoHide("Josh Smith","activityFeed-name"),commonComponents.spanCrafterWordNoHide(" - ","activityFeed-name"),commonComponents.spanCrafterWordNoHide("5 units produced","stat-example")),
                commonComponents.doubleValueRow(commonComponents.spanCrafterWordNoHide("Hourly salary - 25 Eur","stat-title"),commonComponents.spanCrafterWordNoHide("● Hours worked - 256 ","stat-title"))
        );
        e.setWidthFull();
        e.setPadding(false);
        e.setSpacing(false);

        HorizontalLayout h12 = new HorizontalLayout(image,e);
        h12.addClassName("island");
        h12.setWidthFull();

        return  h12;
    }


}
