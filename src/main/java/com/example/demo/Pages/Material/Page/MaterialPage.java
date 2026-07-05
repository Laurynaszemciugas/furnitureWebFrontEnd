package com.example.demo.Pages.Material.Page;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.CurrentFilterDisplay;
import com.example.demo.Common.Paganation;
import com.example.demo.ControllerModels.Filter.Material.MaterialFilterHolder;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Material.Page.Components.MaterialBriefExplanations;
import com.example.demo.Pages.Material.Page.Components.MaterialFilters;
import com.example.demo.Pages.Material.Page.Components.MaterialGrid;
import com.example.demo.Pages.Material.Page.Components.MaterialMiniStats;
import com.example.demo.Services.Material.MaterialService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "Materials", layout = MainLayout.class)
public class MaterialPage extends VerticalLayout implements BeforeEnterObserver {


    VerticalLayout verticalLayout = new VerticalLayout();

    VerticalLayout filterMemory = new VerticalLayout();

    CommonComponents commonComponents;
    Common common;
    Paganation paganation;
    MaterialMiniStats materialMiniStats;
    MaterialFilters materialFilters;
    MaterialGrid materialGrid;
    MaterialBriefExplanations materialBriefExplanations;
    MaterialService materialService;
    CurrentFilterDisplay currentFilterDisplay;

    MaterialFilterHolder filterData = new MaterialFilterHolder();

    public MaterialPage(CommonComponents commonComponents, Common common,MaterialService materialService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.paganation = new Paganation();
        this.materialMiniStats = new MaterialMiniStats(commonComponents,common);
        this.materialFilters = new MaterialFilters(commonComponents,common);
        this.materialGrid = new MaterialGrid(commonComponents,common,materialService);
        this.materialBriefExplanations = new MaterialBriefExplanations(commonComponents,common);
        this.materialService = materialService;
        this.currentFilterDisplay = new CurrentFilterDisplay(commonComponents,common);

        materialFilters.setCurrentFilterDisplay(currentFilterDisplay);


        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);


        filterMemory.setWidthFull();
        filterMemory.setPadding(false);

        addClassName("animation-page");

    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        removeAll();
        add(mainLayout());

    }







    public VerticalLayout mainLayout() {
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");
        //verticalLayout.addClassName("main-island");


        reloadData();




        materialFilters.setMaterialTypeConsumer(e->{
            setNewPage();
            filterData.setMaterialTypeChoice(e);
            filterFeed();
        });
        materialFilters.setActiveInactiveConsumer(e->{
            setNewPage();
            filterData.setActiveInactive(e);
            filterFeed();
        });
        materialFilters.setStockAmountConsumer(e->{
            setNewPage();
            filterData.setStockAmountChoice(e);
            filterFeed();
        });
        materialFilters.setMinThresholdConsumer(e->{
            setNewPage();
            filterData.setMinThresholdChoice(e);
            filterFeed();
        });
        materialFilters.setUnitPriceConsumer(e->{
            setNewPage();
            filterData.setUnitPriceChoice(e);
            filterFeed();
        });
        materialFilters.setFromDateConsumer(e->{
            setNewPage();
            filterData.setFromDateChoice(e);
            filterFeed();
        });
        materialFilters.setToDateConsumer(e->{
            setNewPage();
            filterData.setTodDateChoice(e);
            filterFeed();
        });
        materialFilters.setStockConsumer(e->{
            setNewPage();
            filterData.setStockChoice(e);
            filterFeed();
        });
        materialFilters.setPrompConsumer(e->{
            setNewPage();
            filterData.setPromtChoice(e);
            filterFeed();
        });
        //clear
        materialFilters.setClearFilters(e->{
            filterData =  e;
            reloadData();
        });
        //needed
        currentFilterDisplay.setReloadController(e->{
            setNewPage();
            filterData = (MaterialFilterHolder) e;
            filterFeed();
        });

        materialService.setSuccess(e->{
            reloadData();
        });

        paganation.setOnPageChange(e->{
            e = e-1;
            filterData.setPage(e);
            filterFeed();
        });



        return  verticalLayout;

    }

    public void reloadData(){



        verticalLayout.removeAll();

        filterData = new MaterialFilterHolder();

        filterMemory.removeAll();
        filterMemory.add(
                materialBriefExplanations.briefExplanation(),
                materialMiniStats.miniStatHolder(
                        materialService.getMiniStats(common.dateCrafter(0,0,0,0,true),
                                common.dateCrafter(0,1,1,0,true))),
                materialFilters.filters()

        );

        verticalLayout.add(
                filterMemory,
                gridFilterHolder()

        );
    }

    public void filterFeed(){


        verticalLayout.removeAll();

        filterMemory.removeAll();
        filterMemory.add(
                materialBriefExplanations.briefExplanation(),
                materialMiniStats.miniStatHolder(
                        materialService.getMiniStats(common.dateCrafter(0,0,0,0,true),
                                common.dateCrafter(0,1,1,0,true))),
                materialFilters.filters()

        );

        verticalLayout.add(
                filterMemory,
                gridFilterHolder()

        );

//        verticalLayout.add(
//                filterMemory,
//                gridFilterHolder()
//
//        );
    }











    public VerticalLayout gridFilterHolder(){
        VerticalLayout v = new VerticalLayout();
        v.setPadding(false);
        v.setWidthFull();

        v.add(
                materialGrid.gridHolder(materialService.getUserMaterialsList(filterData)),
                paganation.buttonHolder(Math.toIntExact(materialService.getPageCount(filterData)))

        );

        return v;
    }




    public void setNewPage(){
        filterData.setPage(0);
        paganation.updateUIFromExternal(1);
    }









}
