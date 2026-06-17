package com.example.demo.Pages.Material;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Paganation;
import com.example.demo.ControllerModels.Material.MaterialFilterHolder;
import com.example.demo.Enums.ActiveInactive;
import com.example.demo.Enums.MaterialType;
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


    VerticalLayout verticalLayout = new VerticalLayout();

    CommonComponents commonComponents;
    Common common;
    Paganation paganation;
    MaterialMiniStats materialMiniStats;
    MaterialFilters materialFilters;
    MaterialGrid materialGrid;
    MaterialBriefExplanations materialBriefExplanations;
    MaterialService materialService;

    MaterialFilterHolder filterData = new MaterialFilterHolder();

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
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");
        verticalLayout.addClassName("main-island");


        reloadData();




        materialFilters.setMaterialTypeConsumer(e->{
            filterData.setMaterialTypeChoice(e);
            filterFeed();
        });
        materialFilters.setActiveInactiveConsumer(e->{
            filterData.setActiveInactive(e);
            filterFeed();
        });
        materialFilters.setStockAmountConsumer(e->{
            filterData.setStockAmountChoice(e);
            filterFeed();
        });
        materialFilters.setMinThresholdConsumer(e->{
            filterData.setMinThresholdChoice(e);
            filterFeed();
        });
        materialFilters.setUnitPriceConsumer(e->{
            filterData.setUnitPriceChoice(e);
            filterFeed();
        });
        materialFilters.setFromDateConsumer(e->{
            filterData.setFromDateChoice(e);
            System.out.println(e);
            filterFeed();
        });
        materialFilters.setToDateConsumer(e->{
            filterData.setTodDateChoice(e);
            filterFeed();
        });
        materialFilters.setStockConsumer(e->{
            filterData.setStockChoice(e);
            filterFeed();
        });
        materialFilters.setPrompConsumer(e->{
            filterData.setPromtChoice(e);
            filterFeed();
        });



        return  verticalLayout;

    }

    public void reloadData(){
        verticalLayout.removeAll();

        filterData = new MaterialFilterHolder();

        verticalLayout.add(
                materialBriefExplanations.briefExplanation(),
                materialMiniStats.miniStatHolder(
                        materialService.getMiniStats(common.dateCrafter(0,0,0,0,true),
                                common.dateCrafter(0,1,1,0,true))),
                gridFilterHolder()

        );
    }

    public void filterFeed(){
        verticalLayout.removeAll();

        verticalLayout.add(
                materialBriefExplanations.briefExplanation(),
                materialMiniStats.miniStatHolder(
                        materialService.getMiniStats(common.dateCrafter(0,0,0,0,true),
                                common.dateCrafter(0,1,1,0,true))),
                gridFilterHolder()

        );
    }











    public VerticalLayout gridFilterHolder(){
        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();
        v.addClassName("island");

        v.add(
                materialFilters.filters(),
                materialGrid.gridHolder(materialService.getUserMaterialsList(filterData)),
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
