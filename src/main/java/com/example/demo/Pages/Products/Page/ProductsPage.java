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

@Route(value = "test2", layout = MainLayout.class)
public class ProductsPage extends VerticalLayout implements BeforeEnterObserver {


    CommonComponents commonComponents;
    Common common;
    ProductService productService;
    Paganation paganation;


    ProductPageBriefExplanation productPageBriefExplanation;
    ProductPageFilters productPageFilters;
    ProductPageProductFeed productPageProductFeed;


    private IntConsumer longConsumer;

    private Stock stockChoise = Stock.ALL;
    private Category categoryChoise = Category.ALL;
    private int pageChoise = 1;

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
            data = productService.loadDashboardData(Stock.ALL, Category.ALL,1,20);
            System.out.println(data.getProductFeedModelList());
        }

        else{
            System.out.println("data good");
        }


//        long page = 15;
//
//        longConsumer.accept(15);



        add(mainLayout());


    }

    public VerticalLayout mainLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");
        verticalLayout.addClassName("main-island");



        productPageFilters.consumerStock(stock -> {

            this.stockChoise = stock;

//            verticalLayout.removeAll();
//            verticalLayout.add(productPageBriefExplanation.briefPageExplanation(),
//                    productPageFilters.filters(),
//                    productPageProductFeed.productsMain(data.getProductFeedModelList()),
//                    paganation.buttonHolder(5));
        });


        productPageFilters.consumerType(e->{

            this.categoryChoise = e;

            verticalLayout.removeAll();
            try {
                verticalLayout.add(productPageBriefExplanation.briefPageExplanation(),
                        productPageFilters.filters(),
                        productPageProductFeed.productsMain(productService.loadProductFeedModel(Stock.ALL,categoryChoise,pageChoise,20)),
                        paganation.buttonHolder(5));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }

        });

        paganation.setOnPageChange(page -> {

            this.pageChoise = page;

//            verticalLayout.removeAll();
//            verticalLayout.add(productPageBriefExplanation.briefPageExplanation(),
//                    productPageFilters.filters(),
//                    paganation.buttonHolder(5));


        });

        productPageFilters.consumerClear(e->{
            verticalLayout.removeAll();
            verticalLayout.add(productPageBriefExplanation.briefPageExplanation(),
                    productPageFilters.filters(),
                    productPageProductFeed.productsMain(data.getProductFeedModelList()),
                    paganation.buttonHolder(5));
        });

        verticalLayout.add(productPageBriefExplanation.briefPageExplanation(),
                productPageFilters.filters(),
                productPageProductFeed.productsMain(data.getProductFeedModelList()),
                paganation.buttonHolder(5));

        return verticalLayout;
    }




    }
















