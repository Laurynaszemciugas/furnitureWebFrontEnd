package com.example.demo.Pages.Products.Page;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.CurrentFilterDisplay;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.ControllerModels.Error.FrontEndError;
import com.example.demo.ControllerModels.Filter.Prodcut.ProductFilterHolder;
import com.example.demo.Exseptions.HttpCallException;
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
    VerticalLayout filterHolder = new VerticalLayout();
    VerticalLayout briefExplanation = new VerticalLayout();

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







    }





    @SneakyThrows
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        removeAll();



        totalPages = Math.toIntExact(productService.loadProductPageCount(filterData));

        briefExplanation.add(
                productPageBriefExplanation.briefPageExplanation()
        );


        filterHolder.add(
                productPageFilters.filters()
        );



        int page = Integer.parseInt(beforeEnterEvent.getRouteParameters().get("page").orElse(null));


        if (page <= 0) {
            this.pageChoise = 1;
            UI.getCurrent().navigate("Products/1");

            updateView();
            longConsumer.accept(1);
            return;
        }

        if (page > 1) {
            if (totalPages < page) {
                this.pageChoise = 1;
                UI.getCurrent().navigate("Products/1");
                updateView();
                longConsumer.accept(1);
                return;
            } else {
                this.pageChoise = page;
                updateView();
                longConsumer.accept(page);

            }


        } else if (page == 1) {
            loadData();
        }


        add(mainLayout());

    }


    public VerticalLayout mainLayout() {

        filterHolder.setPadding(false);


        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");
        verticalLayout.addClassName("main-island");
        verticalLayout.getStyle().set("position","relative");

        productPageFilters.setFilterConsumer(promt->{
            filterData.setPrompt(promt);
            updateView();
        });

        productPageFilters.setStockConsumer(stock -> {
            filterData.setStockChoice(stock);
            updateView();
        });


        productPageFilters.setCategoryConsumer(e->{
            filterData.setCategory(e);
            updateView();

        });

        productPageFilters.setVisibilityConsumer(e->{
            filterData.setVisibility(e);
            updateView();
        });

        productPageFilters.setFromDateConsumer(e->{
            filterData.setCreatedFrom(e);
            updateView();
        });
        productPageFilters.setToDateConsumer(e->{
            filterData.setCreatedTo(e);
            updateView();
        });
        productPageFilters.setDiscountConsumer(e->{
            filterData.setDiscount(e);
            updateView();
        });
        productPageFilters.setPriceConsumer(e->{
            filterData.setPrice(e);
            updateView();
        });
        productPageFilters.setMaterialId(e->{
            filterData.setMaterialId(e);
            updateView();
        });


        paganation.setOnPageChange(page -> {
            page = page -1;
            filterData.setPage(page);
            updateView();
        });




        productPageFilters.setClearFilters(e->{
            loadData();
        });

        currentFilterDisplay.setReloadController(e->{
            filterData = (ProductFilterHolder) e;
            updateView();
        });








        return verticalLayout;
    }

    //get default data
    @SneakyThrows
    public void loadData(){

        filterData = new ProductFilterHolder();

        connectAll.removeAll();
        connectAll.addClassName("island");

        connectAll.add(
                filterHolder,
                productPageProductFeed.productsMain(productService.loadProductFeedModel(filterData)),
                paganation.buttonHolder(totalPages),
                commonComponents.pageIndicator(pageChoise,totalPages)
        );

        verticalLayout.removeAll();
        verticalLayout.add(
                briefExplanation,
                productPageMiniStat.miniStatHolder(
                        productService.getMiniStats(
                                common.dateCrafter(0,0,0,0,true),
                                common.dateCrafter(0,1,1,0,true))),
                connectAll
        );









    }


    // updata data according to filters
    @SneakyThrows
    public void updateView(){
        totalPages = Math.toIntExact(productService.loadProductPageCount(filterData));


        connectAll.removeAll();
        connectAll.addClassName("island");
        connectAll.add(
                filterHolder,
                productPageProductFeed.productsMain(productService.loadProductFeedModel(filterData)),
                paganation.buttonHolder(totalPages),
                commonComponents.pageIndicator(pageChoise,totalPages)
        );

        verticalLayout.removeAll();
        verticalLayout.add(
                briefExplanation,
                productPageMiniStat.miniStatHolder(
                        productService.getMiniStats(
                                common.dateCrafter(0,0,0,0,true),
                                common.dateCrafter(0,1,1,0,true))),
                connectAll
        );


    }




    }
















