package com.example.demo.Pages.Products.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Products.ProductFeedModel;
import com.example.demo.Enums.ProductCategory;
import com.example.demo.Enums.Stock;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Service;

import java.util.List;



public class ProductPageProductFeed {


    CommonComponents commonComponents;
    Common common;

    public ProductPageProductFeed(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }


    public HorizontalLayout productsMain(List<ProductFeedModel> productFeedModelList){

        boolean empty = productFeedModelList.isEmpty();

        HorizontalLayout verticalLayout = new HorizontalLayout();
        verticalLayout.setWidthFull();
        verticalLayout.addClassName("layout-flex");
        verticalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        System.out.println(productFeedModelList);

        if(empty){
            verticalLayout.add(commonComponents.noDataFound());
        }
        else{
            for(var s : productFeedModelList){
                verticalLayout.add(productFeed(s.getId(),s.getMainImageUrl(),s.getProductName(),s.getProductCategory(),s.getPrice(),s.getUnitsLeft(),s.getMinTreshold()));
            }
        }







        return verticalLayout;
    }





    public VerticalLayout productFeed(long id, String mainImageUrl, String productName, ProductCategory productCategory, double price, long unitsLeft, long minTreshold){
        VerticalLayout product = new VerticalLayout();
        product.addClassName("island");
        product.getStyle().set("flex", "1 1 302px");
        product.getStyle().set("max-width", "620px");
        product.getStyle().set("min-width", "302px");
        product.getStyle().set("position","relative");
        product.setHeight("380px");

        product.setSpacing(false);

        Span stockLevel = commonComponents.spanCrafter("In Stock","stock-badge");

        colorSpan(unitsLeft,minTreshold,stockLevel);

        Span productPrice = commonComponents.spanCrafterWordNoHide(String.format("%.2f %s",price,"Eur"),"activityFeed-name");
        productPrice.getStyle().set("margin-top","10px");

        Button editShortCut = commonComponents.smallIconButtons("1", VaadinIcon.PENCIL,"black");
        editShortCut.addClickListener(e->{
        });

        HorizontalLayout editData = new HorizontalLayout(editShortCut);
        editData.getStyle().set("position","absolute").set("bottom","2px").set("right","2px").set("z-index","10");

        product.add(
                commonComponents.imageCrafter(mainImageUrl,"100%","150px","10px"),
                commonComponents.spanCrafterWordNoHide(productName,"activityFeed-name"),
                commonComponents.spanCrafter(productCategory.getDisplayName(),"stat-title"),
                productPrice,
                commonComponents.doubleValueRow(stockLevel, commonComponents.spanCrafter(String.format("%d %s",unitsLeft, "Units"),"stat-title")),
                editData


        );



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


}
