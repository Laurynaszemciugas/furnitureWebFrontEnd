package com.example.demo.Pages.Products.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.DTOS.Products.ProductFeedModel;
import com.example.demo.Enums.Category;
import com.example.demo.Enums.Stock;
import com.example.demo.Enums.Visibility;
import com.example.demo.Pages.ClientPages.Components.ProductUI;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;


public class ProductPageProductFeed {


    HorizontalLayout verticalLayout = new HorizontalLayout();

    CommonComponents commonComponents;
    Common common;
    ProductService productService;

    Consumer<String> updateRequired;

    ProductUI productUI;

    public ProductPageProductFeed(CommonComponents commonComponents,
                                  Common common,
                                  ProductService productService,
                                  ProductUI productUI) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.productService = productService;
        this.productUI = productUI;
    }

    public void setUpdateRequired(Consumer<String> updateRequired){
        this.updateRequired = updateRequired;
    }


    public HorizontalLayout productsMain(List<ProductFeedModel> productFeedModelList){

        verticalLayout.removeAll();

        boolean empty = productFeedModelList.isEmpty();

        verticalLayout = new HorizontalLayout();
        verticalLayout.setWidthFull();
        verticalLayout.addClassName("layout-flex");

        verticalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);


        if(empty){
            verticalLayout.add(commonComponents.noDataFound());
        }
        else{
            for(var s : productFeedModelList){
                verticalLayout.add(productFeed(s.getId(),s.getImageUrl(),s.getProductName(),s.getCategory(),s.getPrice(),s.getStockQuantity(),s.getLowStockThreshold(),s.getDiscount(),s.getDiscountedPrice(),s.getVisibility(),s.getMultipleProducts(),s.getPrices(),s.getDiscounts(),s.getStocks()));
            }
        }







        return verticalLayout;
    }





    public VerticalLayout productFeed(long id, String mainImageUrl, String productName, Category productCategory, double price, long unitsLeft, long minTreshold, double discount, double discountedPrice, Visibility visibility, Boolean with,Object prices,Object discounts,Object stocks){

        VerticalLayout product = new VerticalLayout();
        product.addClassName("island");

        product.getStyle().set("flex", "1 1 302px");
        product.getStyle().set("max-width", "620px");
        product.getStyle().set("min-width", "302px");
        product.getStyle().set("position", "relative");
        product.setHeight("400px");


        product.getStyle().setGap("5px");


        if(with){

            List<Double> multiVariantPrices = new ArrayList<>();
            List<Long> multiVariantDiscounts = new ArrayList<>();
            ArrayList<Stock> multiVariantStocks = new ArrayList<>();

            String pricesUnSplit = (String) prices;

            if(pricesUnSplit != null){
                multiVariantPrices =  Arrays.stream(pricesUnSplit.split(","))
                        .map(Double::parseDouble)
                        .collect(Collectors.toList());

                multiVariantPrices.sort(Comparator.reverseOrder());
            }


            String discountUnSplit = (String) discounts;

            if(discountUnSplit != null){

                multiVariantDiscounts = Arrays.stream(discountUnSplit.split(","))
                        .map(Long::parseLong)
                        .collect(Collectors.toList());

                multiVariantDiscounts.sort(Comparator.reverseOrder());
            }


            String stocksUnSplit = (String) stocks;

            System.out.println(stocksUnSplit);

            if (stocksUnSplit != null && !stocksUnSplit.isBlank()) {
                multiVariantStocks = Arrays.stream(stocksUnSplit.split(","))
                        .map(String::trim)
                        .map(Stock::valueOf)
                        .collect(Collectors.toCollection(ArrayList::new));
            }


            Span multiPrice = new Span();

            if(multiVariantPrices.size() > 1){
                multiPrice = commonComponents.spanCrafterWordNoHide( String.format("from %.2f to %.2f Eur",multiVariantPrices.get(multiVariantPrices.size()-1),multiVariantPrices.get(0)) ,"activityFeed-name");
            }
            else{
                multiPrice = commonComponents.spanCrafterWordNoHide(String.format("%.2f Eur",multiVariantPrices.get(0)) ,"activityFeed-name");
            }


            Button editShortCut = commonComponents.smallIconButtonsNoNavigate(VaadinIcon.EDIT, "black");
            editShortCut.addClickListener(e -> {
                UI.getCurrent().navigate("ProductsEdit/" + id);
            });


            HorizontalLayout actionHolder = new HorizontalLayout(editShortCut);


            VerticalLayout div = new VerticalLayout(
                    commonComponents.spanCrafterWordNoHide(productName, "stat-example"),
                    commonComponents.spanCrafterWordNoHide("Actions", "stat-example"),
                    actionHolder);

            div.setAlignItems(FlexComponent.Alignment.CENTER);
            div.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
            div.setWidth("80%");

            div.getStyle()
                    .set("position", "absolute")
                    .set("top", "50%")
                    .set("left", "50%")
                    .set("z-index", "10");
            div.addClassName("overlay-layout");


            product.getElement().addEventListener("mouseenter", e -> {
                div.addClassName("show");
            });


            product.getElement().addEventListener("mouseleave", e -> {
                div.removeClassName("show");

            });

            HorizontalLayout stocksHolder = new HorizontalLayout();
            stocksHolder.addClassName("layout-flex");
            stocksHolder.setWidthFull();

            for(var s : multiVariantStocks){
                stocksHolder.add(
                        colorStock(s)
                );
            }

            stocksHolder.add(
                    commonComponents.spanCrafter(String.format("%d %s", unitsLeft, "Units"), "stat-title")
            );

            product.add(
                    div,
                    commonComponents.imageCrafter(mainImageUrl, "100%", "220px", "10px"),
                    commonComponents.spanCrafterWordNoHide(productName, "activityFeed-name"),
                    commonComponents.spanCrafter(productCategory.getDisplayName(), "stat-title"),
                    multiPrice,
                    productUI.discountBubble(multiVariantDiscounts.get(0),true, multiVariantDiscounts.size()),
                    stocksHolder

            );



        }

        else {


            Span stockLevel = commonComponents.spanCrafter("In Stock", "stock-badge");

            colorSpan(unitsLeft, minTreshold, stockLevel);


            Span productPrice = commonComponents.spanCrafterWordNoHide(String.format("%.2f %s", price, "Eur"), "activityFeed-name");
            productPrice.getStyle().set("margin-top", "10px");

            HorizontalLayout priceHolder = new HorizontalLayout(productPrice);

            if (discount > 0) {

                priceHolder.add(productUI.discountBubble((long) discount,false,1));
                productPrice.getStyle()
                        .set("text-decoration", "line-through");

                Span span = commonComponents.spanCrafterWordNoHide(String.format("%.2f %s", discountedPrice, "Eur"), "activityFeed-name");
                priceHolder.setAlignItems(FlexComponent.Alignment.CENTER);
                priceHolder.add(span);


            }


            // ========================================== buttons ===============================================================
            Button editShortCut = commonComponents.smallIconButtonsNoNavigate(VaadinIcon.EDIT, "black");
            editShortCut.addClickListener(e -> {
                UI.getCurrent().navigate("ProductsEdit/" + id);
            });

            Button removeProduct = commonComponents.smallIconButtonsNoNavigate(VaadinIcon.TRASH, "red");
            removeProduct.addClickListener(e -> {

                common.deleteConfirmation(productName);

                common.setBooleanConsumer(answer -> {
                    productService.removeProductById(id);

                });


            });


            HorizontalLayout actionHolder = new HorizontalLayout(removeProduct, editShortCut);


            VerticalLayout div = new VerticalLayout(
                    commonComponents.spanCrafterWordNoHide(productName, "stat-example"),
                    commonComponents.spanCrafterWordNoHide("Actions", "stat-example"),
                    actionHolder);

            div.setAlignItems(FlexComponent.Alignment.CENTER);
            div.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
            div.setWidth("80%");

            div.getStyle()
                    .set("position", "absolute")
                    .set("top", "50%")
                    .set("left", "50%")
                    .set("z-index", "10");
            div.addClassName("overlay-layout");


            product.getElement().addEventListener("mouseenter", e -> {
                div.addClassName("show");
            });


            product.getElement().addEventListener("mouseleave", e -> {
                div.removeClassName("show");

            });


            product.add(
                    commonComponents.imageCrafter(mainImageUrl, "100%", "220px", "10px"),
                    commonComponents.spanCrafterWordNoHide(productName, "activityFeed-name"),
                    commonComponents.spanCrafter(productCategory.getDisplayName(), "stat-title"),
                    priceHolder,
                    commonComponents.doubleValueRow(stockLevel, commonComponents.spanCrafter(String.format("%d %s", unitsLeft, "Units"), "stat-title")),
                    div


            );

            if (visibility.equals(Visibility.NonVisible)) {
                removeProduct.setVisible(false);
                removeProduct.setEnabled(false);
            }
        }



        return product;
    }






    public void colorSpan(long unitsLeft, long minTreshold, Span stockLevel){

        long first = unitsLeft;
        long second = minTreshold;

        if(first >= second){
            stockLevel.addClassName("stock-in");
            stockLevel.setText(Stock.In_Stock.getDisplayName());
        }
        if(first < second){
            stockLevel.addClassName("stock-low");
            stockLevel.setText(Stock.Low_Stock.getDisplayName());
        }
        if(first == 0){
            stockLevel.addClassName("stock-out");
            stockLevel.setText(Stock.No_Stock.getDisplayName());
        }


    }

    public Span colorStock(Stock stock){

        Span stockLevel = commonComponents.spanCrafter("In Stock", "stock-badge");


        switch (stock){
            case In_Stock -> {
                stockLevel.addClassName("stock-in");
                stockLevel.setText(Stock.In_Stock.getDisplayName());
            }
            case No_Stock -> {
                stockLevel.addClassName("stock-out");
                stockLevel.setText(Stock.No_Stock.getDisplayName());
            }
            case Low_Stock -> {
                stockLevel.addClassName("stock-low");
                stockLevel.setText(Stock.Low_Stock.getDisplayName());
            }
        }


        return stockLevel;

    }


}
