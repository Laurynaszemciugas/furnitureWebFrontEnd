package com.example.demo.Pages.ClientPages;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Paganation;
import com.example.demo.DTOS.BreadCrums.BreadCrumsDto;
import com.example.demo.MainLayouts.Client.ClientMainLayout;
import com.example.demo.Pages.ClientPages.Components.ProductUI;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "ClientProducts" , layout = ClientMainLayout.class)
public class ClientProducts extends VerticalLayout implements BeforeEnterObserver {


    CommonComponents commonComponents;
    Common common;
    ProductUI productUI;

    ProductService productService;

    Paganation paganation;

    public ClientProducts(CommonComponents commonComponents, Common common, ProductUI productUI, ProductService productService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.productUI = productUI;
        this.productService = productService;
        this.paganation = new Paganation();


        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);


        addClassName("animation-page");

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        removeAll();





        add(mainLayout());


    }



    public VerticalLayout mainLayout() {


        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");



        verticalLayout.add(
            leftRightHolder()
        );




        return verticalLayout;
    }



    public HorizontalLayout leftRightHolder(){

        HorizontalLayout holder = new HorizontalLayout();
        holder.addClassName("layout-flex");
        holder.addClassName("island");
        holder.setWidthFull();


        VerticalLayout left = new VerticalLayout();
        left.addClassName("island");
        left.setWidth("250px");


        VerticalLayout right = new VerticalLayout();
        right.setPadding(false);
        right.setWidth("1000px");

        right.add(
                productUI.productDisplay(productService.getProductsClients(10), "250px","250px","250px"),
                paganation.buttonHolder(10)
        );


        holder.add(left, right);
        holder.expand(right);

        return holder;

    }




}
