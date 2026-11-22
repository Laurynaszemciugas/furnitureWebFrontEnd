package com.example.demo.Pages.ClientPages.Components;

import com.example.demo.ClientUIUpdater;
import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.DTOS.Client.Cart;
import com.example.demo.DTOS.Client.ProductsClients;
import com.example.demo.Entity.Product;
import com.example.demo.Enums.Category;
import com.example.demo.Enums.ImageLogic;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.badge.Badge;
import com.vaadin.flow.component.badge.BadgeVariant;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
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
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class ProductUI {


    ProductService productService;
    CommonComponents commonComponents;
    Common common;
    SessionCrafter sessionCrafter;
    ClientUIUpdater clientUIUpdater;

    List<Long> multiVariantIds = new ArrayList<>();
    List<String> multiVariantImages = new ArrayList<>();
    List<Double> multiVariantPrices = new ArrayList<>();
    List<Long> multiVariantDiscounts = new ArrayList<>();
    List<String> multiVariantColor = new ArrayList<>();


    public ProductUI(ProductService productService, CommonComponents commonComponents, Common common,ClientUIUpdater clientUIUpdater) {
        this.productService = productService;
        this.commonComponents = commonComponents;
        this.common = common;
        this.sessionCrafter = new SessionCrafter();
        this.clientUIUpdater =  clientUIUpdater;
    }


    public VerticalLayout similarProduct(Category category){

        VerticalLayout h = new VerticalLayout();
        h.addClassName("island");
        h.setPadding(false);
        h.setWidthFull();

        h.add(
                commonComponents.spanCrafterWordNoHide("You may also like","activityFeed-name"),
                productDisplay(productService.getSimilarItems(category), "500px","500px","250px",true)
        );

        return h;
    }




    public HorizontalLayout productDisplay(List<ProductsClients> productsClients, String first, String second, String third, boolean buttons){


        HorizontalLayout v = new HorizontalLayout();
        v.addClassName("layout-flex");
        v.setWidthFull();









        // UNPACK ALL CONCAT VALUES
        for(var s : productsClients) {

            Div wrapper = new Div();
            wrapper.getStyle().setPosition(Style.Position.RELATIVE);
            wrapper.getStyle().set("flex", "1 1 " + first );
            wrapper.getStyle().set("max-width", second);
            wrapper.getStyle().set("min-width", third);


            if(s.isMultiVariants()){

                // Unpack ids
                String multiVariantMultiIds = (String) s.getMultiVariantIds();

                if(multiVariantMultiIds != null) {
                    multiVariantIds = Arrays.stream(multiVariantMultiIds.split(","))
                            .map(Long::parseLong)
                            .collect(Collectors.toList());;
                }





                // Unpack images
                String imagesUnSplit = (String) s.getMultiVariantImages();

                if(imagesUnSplit != null) {
                    multiVariantImages = Arrays.asList(imagesUnSplit.split(","));
                }

                // Unpack prices

                String pricesUnSplit = (String) s.getMultiVariantPrices();

                if(pricesUnSplit != null){
                    multiVariantPrices =  Arrays.stream(pricesUnSplit.split(","))
                            .map(Double::parseDouble)
                            .collect(Collectors.toList());

                    multiVariantPrices.sort(Comparator.reverseOrder());
                }

                String discountUnSplit = (String) s.getMultiVariantDiscounts();

                if(discountUnSplit != null){

                    multiVariantDiscounts = Arrays.stream(discountUnSplit.split(","))
                            .map(Long::parseLong)
                            .collect(Collectors.toList());

                    multiVariantDiscounts.sort(Comparator.reverseOrder());
                }

                // Unpack color

                String colorUnSplit = (String) s.getMultiVariantColors();

                if(colorUnSplit != null){

                    multiVariantColor = Arrays.asList(colorUnSplit.split(","));

                }

                VerticalLayout multiVariant = new VerticalLayout();

                multiVariant.addClickListener(e->{

                    UI.getCurrent().navigate("ProductPreview/" + s.getId());

                });

                multiVariant.getStyle().setPosition(Style.Position.RELATIVE);
                multiVariant.setSpacing(false);
                multiVariant.addClassName("island-product");



                String url = "";
                if(!multiVariantImages.isEmpty()) {
                     url = multiVariantImages.get(0);
                }
                Image image = commonComponents.imageCrafter(url,"100%","120px","5px");
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

                if(multiVariantImages.isEmpty()){
                    image.setSrc("No_picture.png");
                }

                HorizontalLayout colorHolder = new HorizontalLayout();
                for(var colors : multiVariantColor){
                    colorHolder.add(
                            colorTemplates(colors)
                    );
                }



                Span multiPrice = new Span();

                if(multiVariantPrices.size() > 1){
                    multiPrice = commonComponents.spanCrafterWordNoHide( String.format("from %.2f to %.2f Eur",multiVariantPrices.get(multiVariantPrices.size()-1),multiVariantPrices.get(0)) ,"stat-example");
                }
                else{
                    multiPrice = commonComponents.spanCrafterWordNoHide(String.format("%.2f Eur",multiVariantPrices.get(0)) ,"stat-example");
                }

                multiVariant.add(
                        discountBubble(multiVariantDiscounts.get(0),true, multiVariantDiscounts.size()),
                        image,
                        commonComponents.spanCrafterWordNoHide(s.getProductName(),"activityFeed-name"),
                        commonComponents.spanCrafterWordNoHide(s.getCategory().getDisplayName(),"stat-description"),
                        multiPrice,
                        colorHolder
                );

                if(buttons){
                    multiVariant.add(
                            expander()
                    );
                }


                wrapper.add(

                        addToWhiteList(false,false),
                        multiVariant


                );

                // change the add to the cart button according to the item
                if(multiVariantPrices.size() > 1 && buttons) {
                    wrapper.add(expandMultiVariantOption(s));
                }
                // if multi variant is alone so add it to cart insta
                else{

                    // add to the cart normal product
                    HorizontalLayout h = new HorizontalLayout();
                    h.setWidthFull();
                    h.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

                    Button addToCart = addToCart();
                    addToCart.addClickListener(e->{
                        Cart cart = new Cart();
                        cart.setProductId(s.getId());
                        cart.setMultiVariantId(multiVariantIds.isEmpty() ? null : multiVariantIds.get(0));
                        cart.setProductName(s.getProductName());
                        cart.setImageUrl(multiVariantImages.isEmpty() ? null : multiVariantImages.get(0));
                        cart.setColor(multiVariantColor.get(0));
                        cart.setCategory(s.getCategory());
                        cart.setPrice(multiVariantPrices.get(0));
                        cart.setDiscount(multiVariantDiscounts.get(0));
                        cart.setAmountSelected(1L);

                        addItemToTheCart(cart);
                    });

                    if(buttons) {
                        h.add(
                                addToCart
                        );
                    }

                    wrapper.add(h);

                }

                v.add(
                        wrapper
                );






            }
            else{





                VerticalLayout multiVariant = new VerticalLayout();
                multiVariant.getStyle().setPosition(Style.Position.RELATIVE);

                multiVariant.addClickListener(e->{

                    UI.getCurrent().navigate("ProductPreview/" + s.getId());

                });

                multiVariant.setSpacing(false);
                multiVariant.addClassName("island-product");




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

                        discountBubble(s.getNormalProductDiscount(),false, multiVariantDiscounts.size()),
                        image,
                        commonComponents.spanCrafterWordNoHide(s.getProductName(),"activityFeed-name"),
                        commonComponents.spanCrafterWordNoHide(s.getCategory().getDisplayName(),"stat-description"),
                        commonComponents.spanCrafterWordNoHide( String.format("%.2f  Eur",s.getNormalProductPrice()) ,"stat-example"),
                        colorHolder
                        );

                if(buttons){
                    multiVariant.add(
                            expander()
                    );
                }


                // add to the cart normal product
                HorizontalLayout h = new HorizontalLayout();
                h.setWidthFull();
                h.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

                Button addToCart = addToCart();
                addToCart.addClickListener(e->{
                    Cart cart = new Cart();
                    cart.setProductId(s.getId());
                    cart.setMultiVariantId(null);
                    cart.setProductName(s.getProductName());
                    cart.setImageUrl(s.getNonMultiVariantImage());
                    cart.setColor(s.getProductColor());
                    cart.setCategory(s.getCategory());
                    cart.setPrice(s.getNormalProductPrice());
                    cart.setDiscount(s.getNormalProductDiscount());
                    IntegerField i = new IntegerField();
                    i.setValue(1);
                    cart.setAmountSelected(1L);


                    addItemToTheCart(cart);
                });

                if(buttons) {
                    h.add(
                            addToCart
                    );
                }

                wrapper.add(

                        addToWhiteList(false,false),
                        multiVariant,
                        h
                );

                v.add(
                        wrapper
                );







            }









        }




        return v;


    }

    public void expandMultiVariantProducts(ProductsClients productsClients){

        Product product = productService.getProduct(productsClients.getId());


        Dialog dialog = new Dialog(product.getProductName());
        dialog.setWidth("800px");

        VerticalLayout main = new VerticalLayout();
        main.setPadding(false);

        Button close =  new Button("Close", e-> dialog.close());
        dialog.getFooter().add(close);

        for(var s : product.getProductMultiples()){
            HorizontalLayout layout = new HorizontalLayout();
            layout.setAlignItems(FlexComponent.Alignment.CENTER);
            layout.addClassName("island");
            layout.setWidthFull();

            String mainImage = common.extractMainImage(s.getImages(),null);

            Image image = commonComponents.imageCrafter(mainImage,"100px","100px","5px");

            VerticalLayout circle = new VerticalLayout();
            circle.setWidth("25px");
            circle.setHeight("25px");
            circle.getStyle().setBorderRadius("50%");
            circle.getStyle().setBackgroundColor(s.getColor());

            VerticalLayout nameCategory = new VerticalLayout();

            Span name = commonComponents.spanCrafterWordNoHide(s.getProductName(),"stat-example");
            Span category = commonComponents.spanCrafterWordNoHide(s.getCategory().getDisplayName(),"stat-description");


            // Price
            VerticalLayout price = new VerticalLayout();
            price.setAlignItems(FlexComponent.Alignment.CENTER);
            price.setWidthFull();

            Span productPrice = commonComponents.spanCrafterWordNoHide("","stat-example");
            Span discountPrice = commonComponents.spanCrafterWordNoHide("","stat-example");
            Span discount = commonComponents.spanCrafterWordNoHide("","stat-example");
            discount.addClassNames("stock-badge","stock-out ");

            productPrice.setText(s.getPrice() + "Eur");


            if(s.getDiscount() > 0){

                productPrice.getStyle()
                        .set("text-decoration", "line-through").setColor("Grey");

                discount.setText("Save " + (long)s.getDiscount() + "%" );
                discountPrice.setText(String.format("%.2f Eur",s.getPrice() - (s.getPrice() * s.getDiscount()/100)));


                price.add(
                        discountPrice,
                        productPrice,
                        discount
                );
            }
            else{
                price.add(
                        productPrice
                );
            }



            VerticalLayout priceHolder = new VerticalLayout();
            priceHolder.add(
                    price
            );

            nameCategory.add(
                    name,
                    category
            );

            Button addToCart = addToCartNormal();
            addToCart.addClickListener(e->{

                Cart cart = new Cart();
                cart.setProductId(product.getId());
                cart.setMultiVariantId(s.getId());
                cart.setProductName(s.getProductName());
                cart.setImageUrl(mainImage);
                cart.setColor(s.getColor());
                cart.setCategory(product.getCategory());
                cart.setPrice(s.getPrice());
                cart.setDiscount((long) s.getDiscount());
                cart.setAmountSelected(1L);

                addItemToTheCart(cart);

            });


            layout.add(
                    image,
                    circle,
                    nameCategory,
                    priceHolder,
                    addToCart
            );



            main.add(layout);

        }

        dialog.add(
                main
        );



        dialog.open();
    }


    public Button addToCart(){

        HorizontalLayout h = new HorizontalLayout();
        h.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        Button button = new Button("Add to cart", VaadinIcon.CART.create());
        button.getStyle().setPosition(Style.Position.ABSOLUTE);
        button.getStyle().setBottom("10px");
        button.setWidth("90%");

        button.addThemeVariants(ButtonVariant.PRIMARY);

        h.add(
                button
        );

        return button;

    }

    public Button addToCartNormal(){

        HorizontalLayout h = new HorizontalLayout();
        h.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        Button button = new Button("Add to cart", VaadinIcon.CART.create());
        button.getStyle().setBottom("10px");

        button.addThemeVariants(ButtonVariant.PRIMARY);

        h.add(
                button
        );

        return button;

    }


    public HorizontalLayout expandMultiVariantOption(ProductsClients productsClients){





        HorizontalLayout h = new HorizontalLayout();
        h.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        Button button = new Button("Options", VaadinIcon.LIST.create());
        button.getStyle().setPosition(Style.Position.ABSOLUTE);
        button.getStyle().setBottom("10px");
        button.setWidth("90%");

        button.addThemeVariants(ButtonVariant.PRIMARY);

        button.addClickListener(e->{
            expandMultiVariantProducts(productsClients);
        });

        h.add(
                button
        );

        return h;

    }

    public VerticalLayout addToWhiteList(boolean selected, boolean resetProducts) {

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

        boolean[] isSelected = {selected};
        button.addClickListener(e->{
            isSelected[0] = !isSelected[0];

            icon.setIcon(isSelected[0] ? VaadinIcon.HEART : VaadinIcon.HEART_O);
            icon.setColor(isSelected[0] ? "red" : "royalblue");

            if(resetProducts) {
                clientUIUpdater.updateProducts(true);
            }
        });




        return button;
    }

    public Span discountBubble(Long discount,boolean selected, int size){

        Span span = commonComponents.spanCrafter("","stat-value");


        if(discount > 0) {
            if (selected && size > 1) {
                span = commonComponents.spanCrafterWordNoHide(String.format("Up to %d", discount) + "%", "stat-example");

            } else {
                span = commonComponents.spanCrafterWordNoHide(String.format("%d", discount) + "%", "stat-example");
            }
        }
        else{
            span.setVisible(false);

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

    public Div expander(){
        Div expander = new Div();
        expander.setHeight("50px");
        return expander;
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

    public void addItemToTheCart(Cart cart) {


        System.out.println(cart);



        List<Cart> selectedItems = sessionCrafter.extractSession("Cart", List.class);

        if (selectedItems == null) {
            selectedItems = new ArrayList<>();
            sessionCrafter.createSession("Cart", selectedItems);
        }

        boolean exists = false;
        if(cart.getMultiVariantId() == null){
        for(var s : selectedItems){
            if(s.getProductId().equals(cart.getProductId())){
                s.setAmountSelected(s.getAmountSelected() + cart.getAmountSelected());
                exists = true;
            }
        }
        }
        else{
            for(var s : selectedItems){
                if(s.getMultiVariantId() == null){
                    continue;
                }
                if(s.getMultiVariantId().equals(cart.getMultiVariantId())){
                    s.setAmountSelected(s.getAmountSelected() + cart.getAmountSelected());
                    exists = true;
                }
            }
        }

        if(!exists) {
            selectedItems.add(cart);
        }


        sessionCrafter.createSession("Cart", selectedItems);

        Notification notification = new Notification();
        notification.setPosition(Notification.Position.BOTTOM_END);
        notification.setDuration(7000);



        Image image = commonComponents.imageCrafter(cart.getImageUrl(),"100px", "100px","5px");


        Badge badge = new Badge("");





        Button actionButton;

        if(!exists){

            Button removeFromCart = new Button("Remove from cart", VaadinIcon.TRASH.create());
            removeFromCart.addThemeVariants(ButtonVariant.TERTIARY);
            removeFromCart.addClickListener(event -> {

                List<Cart> items = sessionCrafter.extractSession("Cart", List.class);

                items.remove(cart);

                sessionCrafter.createSession("Cart", items);

                commonComponents.showNotification(String.format("%s is removed from your cart", cart.getProductName()),3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.LUMO_SUCCESS);

            });

            actionButton = removeFromCart;

            badge.setText("Added to the cart");
            badge.setIcon(commonComponents.iconCrafter(VaadinIcon.CHECK,"15px","Green"));
            badge.addThemeVariants(BadgeVariant.SUCCESS);
        }
        else{

            Button viewCart = new Button("View cart", VaadinIcon.CART.create());
            viewCart.addThemeVariants(ButtonVariant.PRIMARY);
            viewCart.addClickListener(event -> {
                clientUIUpdater.updateCart(true);
            });

            actionButton = viewCart;

            badge.setText("Added +1 to the cart");
            badge.setIcon(commonComponents.iconCrafter(VaadinIcon.PLUS,"15px","green"));
            badge.addThemeVariants(BadgeVariant.SUCCESS);
        }

        VerticalLayout actionLayout = new VerticalLayout();
        actionLayout.add(
                badge,
                commonComponents.spanCrafterWordNoHide(cart.getProductName(),"stat-example")
        );


        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.TERTIARY);
        closeButton.setAriaLabel("Close");
        closeButton.addClickListener(event -> {
            notification.close();
        });





        HorizontalLayout layout = new HorizontalLayout();
        layout.addToEnd(closeButton);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        layout.add(
                image,
                actionLayout,
                actionButton,
                closeButton

        );

        notification.add(layout);
        notification.open();


    }



}
