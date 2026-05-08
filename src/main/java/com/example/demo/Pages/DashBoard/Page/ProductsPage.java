package com.example.demo.Pages.DashBoard.Page;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route(value = "test2", layout = MainLayout.class)
public class ProductsPage extends VerticalLayout implements BeforeEnterObserver {


    CommonComponents commonComponents;
    Common common;
    ProductService productService;


    public ProductsPage(CommonComponents commonComponents, Common common, ProductService productService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.productService = productService;

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(Alignment.CENTER);



        add(mainLayout());

    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

    }

    public VerticalLayout mainLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");
        verticalLayout.addClassName("main-island");

        verticalLayout.add(briefPageExplanation(),filters(),productsMain());

        return verticalLayout;
    }



    public HorizontalLayout briefPageExplanation(){
        HorizontalLayout left = new HorizontalLayout(
          commonComponents.spanCrafterWordNoHide("Inventory managment","stat-value")

        );

        HorizontalLayout right =new HorizontalLayout(
                commonComponents.textFieldCrafter("Search products...",""),
                commonComponents.buttonThemeAndIcon("Add new product","", ButtonVariant.PRIMARY,VaadinIcon.PLUS,"White")
        );
        right.addClassName("layout-flex");

        HorizontalLayout main = new HorizontalLayout(left,commonComponents.spaceFiller(),right);
        main.setWidthFull();
        main.setAlignItems(FlexComponent.Alignment.CENTER);
        main.addClassName("layout-flex");


        return main;
    }

    public HorizontalLayout filters(){
        HorizontalLayout horizontalLayout = new HorizontalLayout(
                commonComponents.normalButtonNoNavigate("In stock","S"),
                commonComponents.normalButtonNoNavigate("Low stock","S"),
                commonComponents.normalButtonNoNavigate("no Stock","S")

        );
        horizontalLayout.setWidthFull();


        return horizontalLayout;
    }

    public HorizontalLayout productsMain(){

        HorizontalLayout verticalLayout = new HorizontalLayout();
        verticalLayout.setWidthFull();
        verticalLayout.addClassName("island");
        verticalLayout.addClassName("layout-flex");





        verticalLayout.add(productFeed(),productFeed(),productFeed(),productFeed(),productFeed(),productFeed(),productFeed(),productFeed()
        );



        return verticalLayout;
    }

    public VerticalLayout productFeed(){
        VerticalLayout product = new VerticalLayout();
        product.addClassName("island");
        product.getStyle().set("flex", "1 1 302px");
        product.getStyle().set("max-width", "620px");
        product.getStyle().set("min-width", "302px");
        product.getStyle().set("position","relative");
        product.setHeight("380px");

        product.setSpacing(false);

        Span stockLevel = commonComponents.spanCrafter("In Stock","stock-badge");

        long first = 0;
        long second = 20;

        if(first >= second){
            stockLevel.addClassName("stock-in");
        }
        if(first < second){
            stockLevel.addClassName("stock-low");
        }
        if(first == 0){
            stockLevel.addClassName("stock-out");
        }



        Span productPrice = commonComponents.spanCrafterWordNoHide("420.55 Eur","activityFeed-name");
        productPrice.getStyle().set("margin-top","10px");

        Button editShortCut = commonComponents.smallIconButtons("1", VaadinIcon.PENCIL,"black");
        editShortCut.addClickListener(e->{
        });

        HorizontalLayout editData = new HorizontalLayout(editShortCut);
        editData.getStyle().set("position","absolute").set("bottom","2px").set("right","2px").set("z-index","10");

        product.add(
                commonComponents.imageCrafter("Screenshot 2026-04-27 001745.png","100%","150px","10px"),
                commonComponents.spanCrafterWordNoHide("Red sofa with auto recliner","activityFeed-name"),
                commonComponents.spanCrafter("Furniture","stat-title"),
                productPrice,
                commonComponents.doubleValueRow(stockLevel, commonComponents.spanCrafter("5 Units left","stat-title")),
                editData


        );



        return product;
    }
















    }







//horizontalLayout.getStyle().set("background-color","Red");









