package com.example.demo.Pages.ClientPages.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.DTOS.Client.ProductsClients;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class ProductUI {


    ProductService productService;
    CommonComponents commonComponents;
    Common common;


    List<String> multiVariantImages = new ArrayList<>();
    List<Double> multiVariantPrices = new ArrayList<>();
    List<Long> multiVariantDiscounts = new ArrayList<>();
    List<String> multiVariantColor = new ArrayList<>();



    public ProductUI(ProductService productService, CommonComponents commonComponents, Common common) {
        this.productService = productService;
        this.commonComponents = commonComponents;
        this.common = common;
    }

    public HorizontalLayout productDisplay(){


        HorizontalLayout v = new HorizontalLayout();
        v.setWidthFull();



        List<ProductsClients> productsClients = productService.getProductsClients(10);



        // UNPACK ALL CONCAT VALUES
        for(var s : productsClients) {

            Div wrapper = new Div();
            wrapper.getStyle().setPosition(Style.Position.RELATIVE);
            wrapper.getStyle().set("flex", "1 1 300px");
            wrapper.getStyle().set("max-width", "320px");
            wrapper.getStyle().set("min-width", "252px");


            if(s.isMultiVariants()){

                // Unpack images
                String imagesUnSplit = (String) s.getMultiVariantImages();

                if(imagesUnSplit != null) {
                    multiVariantImages = Arrays.asList(imagesUnSplit.split(", "));
                }

                // Unpack prices

                String pricesUnSplit = (String) s.getMultiVariantPrices();

                if(pricesUnSplit != null){
                    multiVariantPrices =  Arrays.stream(pricesUnSplit.split(", "))
                            .map(Double::parseDouble)
                            .collect(Collectors.toList());

                    multiVariantPrices.sort(Comparator.reverseOrder());
                }

                String discountUnSplit = (String) s.getMultiVariantDiscounts();

                if(discountUnSplit != null){

                    multiVariantDiscounts = Arrays.stream(discountUnSplit.split(", "))
                            .map(Long::parseLong)
                            .collect(Collectors.toList());

                    multiVariantDiscounts.sort(Comparator.reverseOrder());
                }

                // Unpack color

                String colorUnSplit = (String) s.getMultiVariantColors();

                if(colorUnSplit != null){

                    multiVariantColor = Arrays.asList(colorUnSplit.split(", "));

                }

                VerticalLayout multiVariant = new VerticalLayout();

                multiVariant.getStyle().setPosition(Style.Position.RELATIVE);
                multiVariant.setSpacing(false);
                multiVariant.addClassName("island-hover");

                multiVariant.getStyle().set("flex", "1 1 300px");
                multiVariant.getStyle().set("max-width", "320px");
                multiVariant.getStyle().set("min-width", "252px");



                Image image = commonComponents.imageCrafter(multiVariantImages.get(0),"100%","120px","5px");
                image.getStyle().setPaddingBottom("10px");



                ScheduledExecutorService scheduler =
                        Executors.newSingleThreadScheduledExecutor();

                AtomicInteger show = new AtomicInteger(0);
                AtomicReference<ScheduledFuture<?>> task = new AtomicReference<>();

                multiVariant.getElement().addEventListener("mouseenter", event -> {

                    if (multiVariantImages.isEmpty()) {
                        return;
                    }

                    // Prevent multiple scheduled tasks
                    if (task.get() != null && !task.get().isDone()) {
                        return;
                    }

                    UI ui = UI.getCurrent();

                    ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(() -> {

                        if (ui.isAttached()) {
                            ui.access(() -> {

                                if (!multiVariant.isAttached() || multiVariantImages.isEmpty()) {
                                    return;
                                }

                                int index = show.getAndUpdate(i ->
                                        (i + 1) % multiVariantImages.size()
                                );

                                image.setSrc(multiVariantImages.get(index));

                                System.out.println(index);
                            });
                        }

                    }, 0, 1, TimeUnit.SECONDS);

                    task.set(future);
                });

                multiVariant.getElement().addEventListener("mouseleave", event -> {

                    ScheduledFuture<?> future = task.getAndSet(null);

                    if (future != null) {
                        future.cancel(false);
                    }

                    show.set(0);

                    if (!multiVariantImages.isEmpty()) {
                        image.setSrc(multiVariantImages.get(0));
                    }
                });

                HorizontalLayout colorHolder = new HorizontalLayout();
                for(var colors : multiVariantColor){
                    colorHolder.add(
                            colorTemplates(colors)
                    );
                }




                multiVariant.add(
                        discountBubble(multiVariantDiscounts.get(0),true),
                        image,
                        commonComponents.spanCrafterWordNoHide(s.getProductName(),"activityFeed-name"),
                        commonComponents.spanCrafterWordNoHide(s.getCategory().getDisplayName(),"stat-description"),
                        commonComponents.spanCrafterWordNoHide( String.format("from %.2f to %.2f Eur",multiVariantPrices.get(multiVariantPrices.size()-1),multiVariantPrices.get(0)) ,"stat-example"),
                        colorHolder);



                wrapper.add(

                        addToWhiteList(false),
                        multiVariant
                );

                v.add(
                        wrapper
                );






            }
            else{





                VerticalLayout multiVariant = new VerticalLayout();
                multiVariant.getStyle().setPosition(Style.Position.RELATIVE);

                multiVariant.addClickListener(e->{

                    System.out.println("CLICK");

                });

                multiVariant.setSpacing(false);
                multiVariant.addClassName("island-hover");

                multiVariant.getStyle().set("flex", "1 1 300px");
                multiVariant.getStyle().set("max-width", "320px");
                multiVariant.getStyle().set("min-width", "252px");


                Image image = commonComponents.imageCrafter(s.getNonMultiVariantImage() == null ? "No_picture.png" : s.getNonMultiVariantImage(),"100%","120px","5px");
                image.getStyle().setPaddingBottom("10px");



                ScheduledExecutorService scheduler =
                        Executors.newSingleThreadScheduledExecutor();

                AtomicInteger show = new AtomicInteger(0);
                AtomicReference<ScheduledFuture<?>> task = new AtomicReference<>();




                HorizontalLayout colorHolder = new HorizontalLayout();

                    colorHolder.add(
                            colorTemplates(s.getProductColor())
                    );





                multiVariant.add(

                        discountBubble(s.getNormalProductDiscount(),false),
                        image,
                        commonComponents.spanCrafterWordNoHide(s.getProductName(),"activityFeed-name"),
                        commonComponents.spanCrafterWordNoHide(s.getCategory().getDisplayName(),"stat-description"),
                        commonComponents.spanCrafterWordNoHide( String.format("%.2f  Eur",s.getNormalProductPrice()) ,"stat-example"),
                        colorHolder
                );


                wrapper.add(

                        addToWhiteList(false),
                        multiVariant
                );

                v.add(
                        wrapper
                );







            }









        }




        return v;


    }


    public Button addToCart(){

        Button button = new Button("Add to cart", VaadinIcon.CART.create());
        button.setWidth("100%");

        button.addThemeVariants(ButtonVariant.PRIMARY);


        return button;

    }

    public VerticalLayout addToWhiteList(boolean selected) {

        Icon icon = selected
                ? commonComponents.iconCrafter(VaadinIcon.HEART,"35px","white")
                : commonComponents.iconCrafter(VaadinIcon.HEART_O,"35px","white");

        icon.setColor(selected ? "red" : "royalblue");
        icon.setSize("24px");

        VerticalLayout button = new VerticalLayout(icon);
        button.addClassName("island-hover");
        button.getStyle().set("cursor", "pointer");
        button.getStyle().setPosition(Style.Position.ABSOLUTE);
        button.getStyle().setTop("10px");
        button.getStyle().setRight("10px");
        button.getStyle().set("z-index", "100");
        button.getStyle().set("width", "44px");
        button.getStyle().set("height", "44px");
        button.getStyle().set("padding", "0");
        button.getStyle().set("background", "white");
        button.getStyle().set("border-radius", "50%");
        button.getStyle().set("align-items", "center");
        button.getStyle().set("justify-content", "center");

        button.getElement()
                .addEventListener("click", event ->
                        System.out.println("Heart clicked"))
                .stopPropagation();

        return button;
    }

    public Span discountBubble(Long discount,boolean selected){

        Span span = null;


        if(selected){
            span = commonComponents.spanCrafterWordNoHide(String.format("Up to %d",discount) + "%","stat-example");

        }
        else{
            span = commonComponents.spanCrafterWordNoHide(String.format("%d",discount) + "%","stat-example");
        }

        span.addClassName("discount-badge");
        span.getStyle().setPosition(Style.Position.ABSOLUTE);

        span.getStyle().setTop("10px");
        span.getStyle().setLeft("10px");


       if(discount < 0){
           span.setVisible(false);
       }



        return span;
    }

    public VerticalLayout colorTemplates(String color){

        VerticalLayout v = new VerticalLayout();
        v.getStyle().setWidth("25px");
        v.getStyle().setHeight("25px");
        v.getStyle().setBorderRadius("50%");


        if(color != null) {
            v.getStyle().setBackgroundColor(color);
        }

        return v;

    }



}
