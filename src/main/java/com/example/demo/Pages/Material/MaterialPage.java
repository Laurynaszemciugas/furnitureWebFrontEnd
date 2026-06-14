package com.example.demo.Pages.Material;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Paganation;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Material.Components.MaterialBriefExplanations;
import com.example.demo.Pages.Material.Components.MaterialFilters;
import com.example.demo.Pages.Material.Components.MaterialGrid;
import com.example.demo.Pages.Material.Components.MaterialMiniStats;
import com.example.demo.Services.Material.MaterialService;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;

@Route(value = "Materials", layout = MainLayout.class)
public class MaterialPage extends VerticalLayout implements BeforeEnterObserver {

    CommonComponents commonComponents;
    Common common;
    Paganation paganation;
    MaterialMiniStats materialMiniStats;
    MaterialFilters materialFilters;
    MaterialGrid materialGrid;
    MaterialBriefExplanations materialBriefExplanations;
    MaterialService materialService;


    public MaterialPage(CommonComponents commonComponents, Common common,MaterialService materialService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.paganation = new Paganation();
        this.materialMiniStats = new MaterialMiniStats(commonComponents,common);
        this.materialFilters = new MaterialFilters(commonComponents,common);
        this.materialGrid = new MaterialGrid(commonComponents,common);
        this.materialBriefExplanations = new MaterialBriefExplanations(commonComponents,common);
        this.materialService = materialService;


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


        verticalLayout.add(
                materialBriefExplanations.briefExplanation(),
                materialMiniStats.miniStatHolder(materialService.getMiniStats(common.dateCrafter(0,0,0,0,true),common.dateCrafter(0,1,1,0,true))),

                gridFilterHolder()

        );



        return  verticalLayout;

    }













    public VerticalLayout gridFilterHolder(){
        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();
        v.addClassName("island");

        v.add(
                materialFilters.filters(),
                materialGrid.gridHolder(materialService.getUserMaterialsList()),
                paganation.buttonHolder(3)

        );

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
