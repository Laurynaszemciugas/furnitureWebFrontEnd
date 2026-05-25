package com.example.demo.Pages.Products.Page;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.DashBoard.DashBoardPageData;
import com.example.demo.ControllerModels.Products.ProductPageData;
import com.example.demo.Enums.Category;
import com.example.demo.Enums.Stock;
import com.example.demo.Enums.Visibility;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Common.Paganation;
import com.example.demo.Pages.Products.Components.ProductPageBriefExplanation;
import com.example.demo.Pages.Products.Components.ProductPageFilters;
import com.example.demo.Pages.Products.Components.ProductPageProductFeed;
import com.example.demo.Services.Products.ProductService;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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
    VerticalLayout filterHolder = new VerticalLayout();

    private Stock stockChoise = Stock.ALL;
    private Category categoryChoise = Category.ALL;
    private int pageChoise = 1;
    private String promtChoise = "ALL";
    private Visibility visibilityChoise = Visibility.Visible;

    private int totalPages = 0;


    // page data
    ProductPageData data = new ProductPageData();



    public ProductsPage(CommonComponents commonComponents,
                        Common common,
                        ProductService productService) {


        this.commonComponents = commonComponents;
        this.common = common;
        this.productService = productService;
        this.productPageBriefExplanation = new ProductPageBriefExplanation(commonComponents,common);
        this.productPageFilters = new ProductPageFilters(commonComponents,common);
        this.productPageProductFeed = new ProductPageProductFeed(commonComponents,common,productService);


        this.paganation = new Paganation();

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(Alignment.CENTER);


        this.longConsumer = paganation::updateUIFromExternal;

        filterHolder.add(
                productPageBriefExplanation.briefPageExplanation(),
                productPageFilters.filters());




        totalPages = Math.toIntExact(productService.loadProductPageCount(stockChoise,categoryChoise,promtChoise,visibilityChoise));

    }





    @SneakyThrows
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        removeAll();




        if(data == null || data.isDataStale()){
            data = productService.loadProductData(Stock.ALL, Category.ALL,"ALL",Visibility.Visible,1,20);
            System.out.println(data.getProductFeedModelList());
        }

        else{
            System.out.println("data good");
        }


        int page = Integer.parseInt(beforeEnterEvent.getRouteParameters().get("page").orElse(null));

        if(page <= 0){
            this.pageChoise = 1;
            UI.getCurrent().navigate("Products/1");
            updateView(verticalLayout);
            longConsumer.accept(1);

        }

        if(page > 1) {
            if(totalPages < page){
                this.pageChoise = 1;
                UI.getCurrent().navigate("Products/1");
                updateView(verticalLayout);
                longConsumer.accept(1);
            }
            else {
                this.pageChoise = page;
                updateView(verticalLayout);
                longConsumer.accept(page);
            }


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
        verticalLayout.getStyle().set("position","relative");

        productPageBriefExplanation.setFilterConsumer(promt->{
            this.promtChoise = promt;
            if(promt.equalsIgnoreCase("")){
                this.promtChoise = "ALL";
            }
            updateView(verticalLayout);
        });

        productPageFilters.consumerStock(stock -> {

            this.stockChoise = stock;

            updateView(verticalLayout);
        });


        productPageFilters.consumerType(e->{

            this.categoryChoise = e;

            updateView(verticalLayout);

        });

        productPageFilters.consumerAllStock(e->{
            this.stockChoise = Stock.ALL;
            updateView(verticalLayout);
        });

        paganation.setOnPageChange(page -> {


            if(totalPages!=1) {
                this.pageChoise = page;
                updateView(verticalLayout);
            }


        });

        productPageFilters.setVisibilityConsumer(e->{
            this.visibilityChoise = e;
            updateView(verticalLayout);
        });


        productPageFilters.consumerClear(e->{

            stockChoise = Stock.ALL;
            categoryChoise = Category.ALL;
            promtChoise = "ALL";
            visibilityChoise = Visibility.Visible;


            filterHolder.removeAll();
            filterHolder.add(
                    productPageBriefExplanation.briefPageExplanation(),
                    productPageFilters.filters());

            loadData(verticalLayout);


        });








        return verticalLayout;
    }

    //get default data
    public void loadData(VerticalLayout verticalLayout){
        verticalLayout.removeAll();
        verticalLayout.add(
                filterHolder,
                productPageProductFeed.productsMain(data.getProductFeedModelList()),
                paganation.buttonHolder(totalPages));


        verticalLayout.add(commonComponents.pageIndicator(pageChoise,totalPages));

    }


    // updata data according to filters
    public void updateView(VerticalLayout verticalLayout){
        totalPages = Math.toIntExact(productService.loadProductPageCount(stockChoise,categoryChoise,promtChoise,visibilityChoise));

        commonComponents.showNotification(String.format("Filters - Stock: '%s' Category: '%s' Prompt: '%s' Page: '%d'",
                stockChoise.getDisplayName(),
                categoryChoise.getDisplayName(),
                promtChoise, pageChoise),
                6000, Notification.Position.BOTTOM_CENTER, NotificationVariant.LUMO_SUCCESS);

        verticalLayout.removeAll();
        try {
            verticalLayout.add(
                    filterHolder,
                    productPageProductFeed.productsMain(productService.loadProductFeedModel(stockChoise,categoryChoise,promtChoise,visibilityChoise,pageChoise,20)),
                    paganation.buttonHolder(totalPages));

            verticalLayout.add(commonComponents.pageIndicator(pageChoise,totalPages));

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }




    }
















