package com.example.demo.Pages.ClientPages;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.DTOS.BreadCrums.BreadCrumsDto;
import com.example.demo.Entity.Materials;
import com.example.demo.Entity.Orders;
import com.example.demo.Entity.Product;
import com.example.demo.Enums.ImageLogic;
import com.example.demo.MainLayouts.Client.ClientMainLayout;
import com.example.demo.Services.Orders.OrdersService;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "ProductPreview/:item" , layout = ClientMainLayout.class)
public class ProductPreview extends VerticalLayout implements BeforeEnterObserver {

    CommonComponents commonComponents;
    Common common;
    ProductService productService;

    Product product;

    VerticalLayout productImages = new VerticalLayout();
    HorizontalLayout extraImages = new HorizontalLayout();

    Image mainImage = new Image();



    public ProductPreview(CommonComponents commonComponents, Common common, ProductService productService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.productService = productService;


        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);


        addClassName("animation-page");



        mainImage.setWidthFull();
        mainImage.setHeight("400px");


        mainImage.getStyle().setBorderRadius("5px");



    }

    int itemChoice = 0;

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        removeAll();

        int page = Math.toIntExact( Integer.parseInt(beforeEnterEvent.getRouteParameters().get("item").orElse(null)));

        this.itemChoice  = page;


        product = productService.getProduct((long) itemChoice);

        add(mainLayout());


    }









    public VerticalLayout mainLayout() {


        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");



        verticalLayout.add(
                firstLayer()
        );

        return verticalLayout;
    }

    public HorizontalLayout firstLayer(){

        HorizontalLayout main = new HorizontalLayout();
        main.addClassName("layout-flex");

        main.setWidthFull();


        VerticalLayout left = productImages;
        left.addClassName("island");

        left.setWidth("100%");
        left.setMaxWidth("680px");


        left.getStyle().set("min-width", "0");


        VerticalLayout right = new VerticalLayout();
        right.addClassName("island");
        right.setWidth("800px");

        // load images from the product or the multi first product
        loadImages();

        main.add(
                left,
                right
        );
        main.expand(right);





        return main;


    }

    public void loadImages(){

        String defaultImage = "No_picture.png";


        mainImage.setSrc(defaultImage);


        Scroller scroller = new Scroller(extraImages);
        scroller.setWidthFull();

        productImages.add(
                mainImage,
                scroller
        );

        if(product.getMultipleItems()) {



            // get main image of the first in the list of multi variant products
            if(product.getProductMultiples() != null) {

                // is main image already tried was
                boolean mainAttempted = false;

                for (var s : product.getProductMultiples()) {

                    mainAttempted = true;


                    for(var img : s.getImages()){

                        if(img.getImageLogic().equals(ImageLogic.Main)){
                            mainImage.setSrc(img.getImageUrl());


                            addNonMain(img.getImageUrl());

                        }

                        else{
                            addNonMain(img.getImageUrl());
                        }

                    }


                    // break out of this list its no longer needed
                    if(mainAttempted){

                        break;
                    }
                }



            }









        }

        else{
            // get single product images non multi

            System.out.println("Single");

            if(product.getImages() != null){

                for(var img : product.getImages()){
                    if(img.getImageLogic().equals(ImageLogic.Main)){
                        mainImage.setSrc(img.getImageUrl());


                        addNonMain(img.getImageUrl());
                    }

                    else{
                        addNonMain(img.getImageUrl());
                    }

                }
            }

        }


    }

    public void addNonMain(String url){

        if(url == null){
            url = "No_picture.png";
        }

        Image nonMainImage = commonComponents.imageCrafter(url, "200px", "75px", "5px");
        nonMainImage.addClassName("island");

        String finalUrl = url;
        nonMainImage.addClickListener(e->{

            mainImage.setSrc(finalUrl);


        });

        extraImages.add(
                nonMainImage
        );

    }


}
