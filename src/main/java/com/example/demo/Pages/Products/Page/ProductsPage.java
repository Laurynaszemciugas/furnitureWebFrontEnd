package com.example.demo.Pages.Products.Page;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.DashBoard.DashBoardPageData;
import com.example.demo.ControllerModels.Products.ProductPageData;
import com.example.demo.Enums.Category;
import com.example.demo.Enums.Stock;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Common.Paganation;
import com.example.demo.Pages.Products.Components.ProductPageBriefExplanation;
import com.example.demo.Pages.Products.Components.ProductPageFilters;
import com.example.demo.Pages.Products.Components.ProductPageProductFeed;
import com.example.demo.Services.Products.ProductService;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

@Route(value = "Products/:page?", layout = MainLayout.class)
public class ProductsPage extends VerticalLayout implements BeforeEnterObserver {


    // extra stuff classes
    CommonComponents commonComponents;
    Common common;
    ProductService productService;
    Paganation paganation;


    // all stuff that make this page
    ProductPageBriefExplanation productPageBriefExplanation;
    ProductPageFilters productPageFilters;
    ProductPageProductFeed productPageProductFeed;


    // call to pagannation
    private IntConsumer longConsumer;

    // main layout
    VerticalLayout verticalLayout = new VerticalLayout();

    private Stock stockChoise = Stock.ALL;
    private Category categoryChoise = Category.ALL;
    private int pageChoise = 1;


    // page data
    ProductPageData data = new ProductPageData();



    public ProductsPage(CommonComponents commonComponents,
                        Common common,
                        ProductService productService,
                        ProductPageBriefExplanation productPageBriefExplanation
                        ) {


        this.commonComponents = commonComponents;
        this.common = common;
        this.productService = productService;
        this.productPageBriefExplanation = productPageBriefExplanation;
        this.productPageFilters = new ProductPageFilters(commonComponents,common);
        this.productPageProductFeed = new ProductPageProductFeed(commonComponents,common);


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




        if(data == null || data.isDataStale()){
            data = productService.loadProductData(Stock.ALL, Category.ALL,1,20);
            System.out.println(data.getProductFeedModelList());
        }

        else{
            System.out.println("data good");
        }


        int page = Integer.parseInt(beforeEnterEvent.getRouteParameters().get("page").orElse(null));


        if(page > 1) {
            this.pageChoise = page;
            updateView(verticalLayout);
            longConsumer.accept(page);
        }


        else if(page == 1){
            loadData(verticalLayout);
        }



        add(mainLayout());


    }

    public VerticalLayout mainLayout() {

        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");
        verticalLayout.addClassName("main-island");



        productPageFilters.consumerStock(stock -> {

            this.stockChoise = stock;

            updateView(verticalLayout);
        });


        productPageFilters.consumerType(e->{

            this.categoryChoise = e;

            updateView(verticalLayout);

        });

        paganation.setOnPageChange(page -> {

            this.pageChoise = page;
            updateView(verticalLayout);



        });

        productPageFilters.consumerClear(e->{
            verticalLayout.removeAll();
                verticalLayout.add(productPageBriefExplanation.briefPageExplanation(),
                        productPageFilters.filters(),
                        productPageProductFeed.productsMain(data.getProductFeedModelList()),
                        paganation.buttonHolder(Math.toIntExact(productService.loadProductPageCount())));

        });








        return verticalLayout;
    }

    public void loadData(VerticalLayout verticalLayout){
        verticalLayout.removeAll();
        verticalLayout.add(productPageBriefExplanation.briefPageExplanation(),
                productPageFilters.filters(),
                productPageProductFeed.productsMain(data.getProductFeedModelList()),
                paganation.buttonHolder(Math.toIntExact(productService.loadProductPageCount())));
    }

    public void updateView(VerticalLayout verticalLayout){






        verticalLayout.removeAll();
        try {
            verticalLayout.add(productPageBriefExplanation.briefPageExplanation(),
                    productPageFilters.filters(),
                    productPageProductFeed.productsMain(productService.loadProductFeedModel(stockChoise,categoryChoise,pageChoise,20)),
                    paganation.buttonHolder(Math.toIntExact(productService.loadProductPageCount())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }




    }
















