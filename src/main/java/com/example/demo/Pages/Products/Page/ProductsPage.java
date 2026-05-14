package com.example.demo.Pages.Products.Page;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.DashBoard.DashBoardPageData;
import com.example.demo.ControllerModels.Products.ProductPageData;
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


    private LongConsumer longConsumer;



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


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        removeAll();

        if(data == null || data.isDataStale()){
            data = productService.loadDashboardData();
            System.out.println(data.getProductFeedModelList());
        }

        else{
            System.out.println("data good");
        }


//        long page = 10;
//
//        longConsumer.accept(page);



        add(mainLayout());


    }

    public VerticalLayout mainLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");
        verticalLayout.addClassName("main-island");

        paganation.setOnPageChange(page -> {
            System.out.println("Page changed to: " + page);

        });

        productPageFilters.consumerStock(stock -> {
            System.out.println(stock);
        });

        productPageFilters.consumerType(e->{
            System.out.println(e);
        });

        productPageFilters.consumerClear(e->{
            System.out.println("clearing time");
        });

        verticalLayout.add(productPageBriefExplanation.briefPageExplanation(),
                productPageFilters.filters(),
                productPageProductFeed.productsMain(data.getProductFeedModelList()),
                paganation.buttonHolder());

        return verticalLayout;
    }




    }
















