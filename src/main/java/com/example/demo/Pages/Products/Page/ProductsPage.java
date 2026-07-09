package com.example.demo.Pages.Products.Page;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.CurrentFilterDisplay;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.ControllerModels.Filter.Prodcut.ProductFilterHolder;
import com.example.demo.ControllerModels.Material.MaterialBriefDto;
import com.example.demo.ControllerModels.Products.ProductFeedModel;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Common.Paganation;
import com.example.demo.Pages.Products.Components.ProductPageBriefExplanation;
import com.example.demo.Pages.Products.Components.ProductPageFilters;
import com.example.demo.Pages.Products.Components.ProductPageMiniStats;
import com.example.demo.Pages.Products.Components.ProductPageProductFeed;
import com.example.demo.Services.CommonService.CommonService;
import com.example.demo.Services.Products.ProductService;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import lombok.SneakyThrows;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.IntConsumer;

@Route(value = "Products/:page?", layout = MainLayout.class)
public class ProductsPage extends VerticalLayout implements BeforeEnterObserver {


    // extra stuff classes
    CommonComponents commonComponents;
    Common common;
    ProductService productService;
    Paganation paganation;
    CommonService commonService;


    // all stuff that make this page
    ProductPageBriefExplanation productPageBriefExplanation;
    ProductPageFilters productPageFilters;
    ProductPageProductFeed productPageProductFeed;
    ProductPageMiniStats productPageMiniStat;
    CurrentFilterDisplay currentFilterDisplay;
    SessionCrafter sessionCrafter;


    // call to pagannation
    private IntConsumer longConsumer;

    // main layout
    VerticalLayout connectAll = new VerticalLayout();
    VerticalLayout verticalLayout = new VerticalLayout();
    VerticalLayout filterMemory = new VerticalLayout();
    VerticalLayout gridHolder = new VerticalLayout();

    private int pageChoise = 1;

    private int totalPages = 0;




    ProductFilterHolder filterData = new ProductFilterHolder();


    public ProductsPage(CommonComponents commonComponents,
                        Common common,
                        ProductService productService,
                        CommonService commonService) {

        this.sessionCrafter = new SessionCrafter();



        this.commonComponents = commonComponents;
        this.common = common;
        this.productService = productService;
        this.commonService = commonService;
        this.productPageBriefExplanation = new ProductPageBriefExplanation(commonComponents,common);
        this.productPageFilters = new ProductPageFilters(commonComponents,common,commonService);
        this.productPageProductFeed = new ProductPageProductFeed(commonComponents,common,productService);
        this.productPageMiniStat = new ProductPageMiniStats(commonComponents,common);
        this.currentFilterDisplay = new CurrentFilterDisplay(commonComponents,common);


        this.productPageFilters.setCurrentFilterDisplay(currentFilterDisplay);

        this.paganation = new Paganation();

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(Alignment.CENTER);


        this.longConsumer = paganation::updateUIFromExternal;



        addClassName("animation-page");





    }





    @SneakyThrows
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        removeAll();



        totalPages = Math.toIntExact(productService.loadProductPageCount(filterData));





        filterData = sessionCrafter.extractSession("productPageFilters",ProductFilterHolder.class) == null ? new ProductFilterHolder() : sessionCrafter.extractSession("productPageFilters",ProductFilterHolder.class);

        currentFilterDisplay.preLoadFilters(ProductFilterHolder.class,"productPageFilters");



        add(mainLayout());

    }


    public VerticalLayout mainLayout() {

        loadData();



        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");
        verticalLayout.getStyle().set("position","relative");

        productPageFilters.setFilterConsumer(promt->{
            filterData.setPrompt(promt);
            loadGridValues();
        });

        productPageFilters.setStockConsumer(stock -> {
            filterData.setStockChoice(stock);
            loadGridValues();
        });


        productPageFilters.setCategoryConsumer(e->{
            filterData.setCategory(e);
            loadGridValues();

        });

        productPageFilters.setVisibilityConsumer(e->{
            filterData.setVisibility(e);
            loadGridValues();
        });

        productPageFilters.setFromDateConsumer(e->{
            filterData.setCreatedFrom(e);
            loadGridValues();
        });
        productPageFilters.setToDateConsumer(e->{
            filterData.setCreatedTo(e);
            loadGridValues();
        });
        productPageFilters.setDiscountConsumer(e->{
            filterData.setDiscount(e);
            loadGridValues();
        });
        productPageFilters.setPriceConsumer(e->{
            filterData.setPrice(e);
            loadGridValues();
        });
        productPageFilters.setMaterialId(e->{
            filterData.setMaterialId(e);
            loadGridValues();
        });


        paganation.setOnPageChange(page -> {
            page = page -1;
            filterData.setPage(page);
            loadGridValues();
        });


        productService.setSuccess(e->{
            loadGridValues();
        });



        productPageFilters.setClearFilters(e->{
            loadData();
        });

        currentFilterDisplay.setReloadController(e->{
            filterData = (ProductFilterHolder) e;
            loadGridValues();
        });








        return verticalLayout;
    }

    //get default data
    @SneakyThrows
    public void loadData(){


        verticalLayout.removeAll();

        //filterData = new ProductFilterHolder();

        filterMemory.removeAll();
        filterMemory.add(
                productPageBriefExplanation.briefPageExplanation(),
                productPageMiniStat.miniStatHolder(
                        productService.getMiniStats(
                                common.dateCrafter(0,0,0,0,true),
                                common.dateCrafter(0,1,1,0,true))),
                productPageFilters.filters(filterData)

        );




        loadGridValues();

       verticalLayout.add(
               filterMemory,
               gridHolder
       );








    }

    public void loadGridValues(){

        System.out.println(filterData);

        UI ui = UI.getCurrent();
        String jwt = sessionCrafter.extractSession("JWT", String.class);

        gridHolder.removeAll();
        gridHolder.add(
                commonComponents.shimmer(5)
        );

        CompletableFuture
                .supplyAsync(()->{

                    List<ProductFeedModel> items = productService.loadProductFeedModel(filterData,jwt);
                    common.timer(250);
                    return items;
                })
                .thenAccept(e->{
                    ui.access(() -> {
                        gridHolder.removeAll();
                        gridHolder.add(gridFilterHolder(e));
                    });
                });

        sessionCrafter.createSession("productPageFilters",filterData);

    }


    public VerticalLayout gridFilterHolder(List<ProductFeedModel> filterStuff){
        VerticalLayout v = new VerticalLayout();
        v.setPadding(false);
        v.setWidthFull();

        v.add(
                productPageProductFeed.productsMain(filterStuff),
                paganation.buttonHolder(Math.toIntExact(productService.loadProductPageCount(filterData)))

        );

        return v;
    }








    }
















