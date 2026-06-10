package com.example.demo.Pages.Material;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Paganation;
import com.example.demo.DTOS.ComboBoxEmployees;
import com.example.demo.Enums.Enabled;
import com.example.demo.Enums.Stock;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.CommonComponents.ProductComponents.RightSide.Components.ProductEditImage;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route(value = "Materials", layout = MainLayout.class)
public class MaterialPage extends VerticalLayout implements BeforeEnterObserver {

    CommonComponents commonComponents;
    Common common;
    Paganation paganation;



    public MaterialPage(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.paganation = new Paganation();


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
                miniStatHolder,
                gridFilterHolder()

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
        iconHolder.setHeight("90px");
        iconHolder.setWidth("120px");
        iconHolder.setJustifyContentMode(JustifyContentMode.CENTER);
        iconHolder.setAlignItems(Alignment.CENTER);
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

    public VerticalLayout gridFilterHolder(){
        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();
        v.addClassName("island");

        v.add(
                filters(),
                gridHolder(),
                paganation.buttonHolder(3)

        );

        return v;
    }

    public VerticalLayout filters(){
        VerticalLayout v = new VerticalLayout();
        v.setPadding(false);


        HorizontalLayout buttonHolder = new HorizontalLayout();

        HorizontalLayout buttonHolderhOLDER = new HorizontalLayout();
        buttonHolderhOLDER.setWidthFull();
        buttonHolderhOLDER.setJustifyContentMode(JustifyContentMode.BETWEEN);

        Button all = commonComponents.normalButtonNoNavigate(Enabled.ALL.getGetDisplayNames(), "transparent-button");
        all.addClassName("active");
        Button active = commonComponents.normalButtonNoNavigate(Enabled.ACTIVE.getGetDisplayNames(), "transparent-button");
        Button inactive = commonComponents.normalButtonNoNavigate(Enabled.INACTIVE.getGetDisplayNames(), "transparent-button");

        Button clear = new Button("Clear filters",VaadinIcon.ERASER.create());

        buttonHolder.add(
                all,
                active,
                inactive
        );

        buttonHolderhOLDER.add(
                buttonHolder,
                clear
        );


        List<Button> buttonList = List.of(all,active,inactive);

        for(var s : buttonList){

            s.addClickListener(e->{
                buttonList.forEach(button->
                        button.removeClassName("active"));
                s.addClassName("active");
            });
        }

        TextField search = commonComponents.textFieldCrafter("Search products...","",VaadinIcon.SEARCH);
        Button showMoreFilters = new Button(commonComponents.iconCrafter(VaadinIcon.FILTER,"30px","grey"));
        showMoreFilters.addClassName("transparent-button");
        HorizontalLayout h3 = new HorizontalLayout();
        h3.setPadding(false);

        h3.add(
                search,
                showMoreFilters
        );



        Span name = commonComponents.spanCrafter("Materials list","stat-value");

        HorizontalLayout h2 = new HorizontalLayout();
        h2.setWidthFull();
        h2.setPadding(false);
        h2.setJustifyContentMode(JustifyContentMode.BETWEEN);
        h2.add(name,h3);

        v.add(
                h2,
                buttonHolderhOLDER
                );

        return  v;
    }


    public Grid gridHolder(){

        Grid<ComboBoxEmployees> grid = new Grid<>(ComboBoxEmployees.class);


        return grid;
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
