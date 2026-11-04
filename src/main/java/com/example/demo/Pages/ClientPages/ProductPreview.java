package com.example.demo.Pages.ClientPages;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.ReviewCrafter;
import com.example.demo.DTOS.BreadCrums.BreadCrumsDto;
import com.example.demo.Entity.Materials;
import com.example.demo.Entity.Orders;
import com.example.demo.Entity.Product;
import com.example.demo.Enums.ImageLogic;
import com.example.demo.Enums.SpecTypes;
import com.example.demo.MainLayouts.Client.ClientMainLayout;
import com.example.demo.Pages.ClientPages.Components.ProductUI;
import com.example.demo.Services.Orders.OrdersService;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route(value = "ProductPreview/:item" , layout = ClientMainLayout.class)
public class ProductPreview extends VerticalLayout implements BeforeEnterObserver {

    CommonComponents commonComponents;
    Common common;
    ProductService productService;

    Product product;

    ReviewCrafter reviewCrafter;

    // left side images
    VerticalLayout productImages = new VerticalLayout();
    HorizontalLayout extraImages = new HorizontalLayout();


    // right side text
    VerticalLayout productInfo = new VerticalLayout();


    // description

    VerticalLayout descriptionHolder = new VerticalLayout();

    Image mainImage = new Image();


    Long productId = 0L;
    Long multiVariantId = 0L;


    public ProductPreview(CommonComponents commonComponents, Common common, ProductService productService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.productService = productService;


        this.reviewCrafter = new ReviewCrafter(commonComponents,common);


        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);


        addClassName("animation-page");



        mainImage.setWidthFull();
        mainImage.setHeight("400px");


        mainImage.getStyle().setBorderRadius("5px");

        descriptionHolder.setWidthFull();
        descriptionHolder.setPadding(false);


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
                firstLayer(),
                descriptionHolder
        );

        specs();



        return verticalLayout;
    }

    public void specs(){

        descriptionHolder.removeAll();

        HorizontalLayout h = new HorizontalLayout();
        h.addClassName("island");
        h.setWidthFull();

        TabSheet tabSheet = new TabSheet();
        tabSheet.add("Description", description());

        tabSheet.add("Specification", specifications());

        tabSheet.add("Reviews", reviewCrafter.commentsHolder(product.getComments()));

        tabSheet.setWidthFull();


        h.add(
                tabSheet
        );


        descriptionHolder.add(
                h
        );

    }


    public VerticalLayout specifications(){

        VerticalLayout v = new VerticalLayout();

        HorizontalLayout holder = new HorizontalLayout();
        holder.setAlignItems(Alignment.START);
        holder.addClassName("layout-flex");
        holder.setWidthFull();


        HorizontalLayout hh = new HorizontalLayout();
        hh.setWidthFull();
        hh.setJustifyContentMode(JustifyContentMode.BETWEEN);
        hh.add(
                commonComponents.spanCrafterWordNoHide("Type","activityFeed-name"),
                commonComponents.spanCrafterWordNoHide(product.getProductName(),"activityFeed-name")
        );

        VerticalLayout first = new VerticalLayout();
        first.addClassName("island");
        first.setWidth("700px");
        first.setJustifyContentMode(JustifyContentMode.BETWEEN);
        first.add(
                hh
        );


        VerticalLayout second = new VerticalLayout();
        second.setWidth("500px");
        second.addClassName("island");
        second.setJustifyContentMode(JustifyContentMode.BETWEEN);

        Image dimensions = commonComponents.imageCrafter("furnitureDimensions.png","100%","100%","0px");

        second.add(
                dimensions
        );


        holder.add(
                first,
                second
        );
        holder.expand(second);


        for(var specs : product.getExtraDetails()){

            HorizontalLayout h = new HorizontalLayout();
            h.setWidthFull();




            if(specs.getSpecTypes().equals(SpecTypes.DEFAULT)) {

                h.setJustifyContentMode(JustifyContentMode.BETWEEN);

                Span name = commonComponents.spanCrafterWordNoHide(specs.getSpecName(), "stat-example");
                Span desc = commonComponents.spanCrafterWordNoHide(specs.getSpecDescription(), "stat-example");

                h.add(
                        name,
                        desc
                );

                first.add(
                        h
                );
            }

            else{

                String credentials = "";
                if(specs.getSpecTypes().equals(SpecTypes.HEIGHT)){
                    credentials = "H";
                }
                if(specs.getSpecTypes().equals(SpecTypes.WIDTH)){
                    credentials = "W";
                }
                if(specs.getSpecTypes().equals(SpecTypes.DEPTH)){
                    credentials = "D";
                }


                Span name = commonComponents.spanCrafterWordNoHide(String.format("(%s) ",credentials)+ specs.getSpecName(), "stat-example");
                Span desc = commonComponents.spanCrafterWordNoHide(specs.getSpecDescription(), "stat-example");

                h.add(
                        name,
                        desc
                );

                second.add(
                        h
                );

            }


        }



        v.add(
                commonComponents.spanCrafterWordNoHide("Product specification","stat-value"),
                holder
        );

        return v;
    }

    public HorizontalLayout description(){

        Span description = commonComponents.spanCrafterWordNoHide("No data was found","stat-description");

        String mainImage = "No_picture.png";

        HorizontalLayout materialHolder = new HorizontalLayout();
        materialHolder.setWidthFull();

        if(product.getMultipleItems()){
            for(var s : product.getProductMultiples()){
                if(s.getId().equals(multiVariantId)){
                description.setText(s.getDescription());

                for(var img : s.getImages()){
                    if(img.getImageLogic().equals(ImageLogic.Main)){
                        mainImage = img.getImageUrl();
                    }
                }

                // show materials
                for(var material : s.getMaterials()){

                    System.out.println(material.getMaterials().getImages().size());

                    for(var imgs : material.getMaterials().getImages()) {

                        if(imgs.getImageLogic().equals(ImageLogic.Main)) {



                            VerticalLayout st = new VerticalLayout();
                            st.setWidth("150px");
                            st.setAlignItems(Alignment.CENTER);
                            st.addClassName("island");
                            st.add(
                                    commonComponents.imageCrafter(imgs.getImageUrl(), "50px", "50px", "50%"),
                                    commonComponents.spanCrafterWordNoHide(material.getMaterials().getMaterialName(), "stat-example")
                            );

                            materialHolder.add(
                                    st
                            );
                        }
                    }



                }


                }
            }
        }

        else{

            description.setText(product.getDescription());

            for(var img : product.getImages()){
                if(img.getImageLogic().equals(ImageLogic.Main)){
                    mainImage = img.getImageUrl();
                }
            }

            // show materials
            for(var material : product.getMaterials()){


                for(var imgs : material.getMaterials().getImages()) {

                    if(imgs.getImageLogic().equals(ImageLogic.Main)) {



                        VerticalLayout st = new VerticalLayout();
                        st.setWidth("150px");
                        st.setAlignItems(Alignment.CENTER);
                        st.addClassName("island");
                        st.add(
                                commonComponents.imageCrafter(imgs.getImageUrl(), "50px", "50px", "50%"),
                                commonComponents.spanCrafterWordNoHide(material.getMaterials().getMaterialName(), "stat-example")
                        );

                        materialHolder.add(
                                st
                        );
                    }
                }



            }


        }



        HorizontalLayout h = new HorizontalLayout();
        h.addClassName("layout-flex");
        h.setJustifyContentMode(JustifyContentMode.EVENLY);
        h.setWidthFull();





        VerticalLayout left = new VerticalLayout();
        left.setWidth("800px");
        left.add(
                commonComponents.spanCrafterWordNoHide("Description","stat-value"),
                description,
                commonComponents.spanCrafterWordNoHide("Materials","stat-value"),
                materialHolder

        );


        VerticalLayout right = new VerticalLayout();
        right.setWidth("500px");
        right.setAlignItems(Alignment.CENTER);


        Image image = new Image(mainImage,"Product image error");
        image.addClassName("island");
        image.getStyle().setBorderRadius("10px");
        image.setMaxWidth("500px");

        right.add(
                image
        );



        h.add(
                left,
                right
        );
        h.expand(left);


        return h;

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


        VerticalLayout right = productInfo;
        right.addClassName("island");
        right.setWidth("800px");

        // load images from the product or the multi first product
        loadImages(null);
        loadInfo(null);

        main.add(
                left,
                right
        );
        main.expand(right);





        return main;


    }





    public void loadInfo(Long id){


        productInfo.removeAll();

        //get all starts of this product
        Double totalStars = 0.0;
        int items = product.getComments().size();
        for(var starts : product.getComments()){

            totalStars += starts.getReview();

        }

        Double avgStarts = totalStars / items;


        Span sku = commonComponents.spanCrafterWordNoHide("","stat-description");

        Span productName = commonComponents.spanCrafterWordNoHide("","stat-value");

        Span productPrice = commonComponents.spanCrafterWordNoHide("","stat-value");

        Span productDescription = commonComponents.spanCrafterWordNoHide("","stat-description");





        if(product.getMultipleItems()){


            if(id == null) {

                boolean mainAttempted = false;


                for (var s : product.getProductMultiples()) {

                    productId = product.getId();
                    multiVariantId = s.getId();

                    mainAttempted = true;

                    sku.setText(String.format("SKU: %s", s.getSku()));
                    productName.setText(s.getProductName());
                    productPrice.setText(s.getPrice() + "Eur");
                    productDescription.setText(s.getDescription());



                    // break out of this list its no longer needed
                    if (mainAttempted) {
                        break;
                    }


                }
            }

            else{

                for (var s : product.getProductMultiples()) {

                    if(id.equals(s.getId())) {
                        sku.setText(String.format("SKU: %s", s.getSku()));
                        productName.setText(s.getProductName());
                        productPrice.setText(s.getPrice() + "Eur");
                        productDescription.setText(s.getDescription());

                        productId = product.getId();
                        multiVariantId = s.getId();

                    }



                }

            }


            productInfo.add(
                    sku,
                    productName,
                    reviewCrafter.starCrafter(avgStarts),
                    productPrice,
                    productDescription,
                    loadVariant(),
                    addToCardWhiteList()

            );


        }

        else{


            productId = product.getId();
            multiVariantId = null;

            sku.setText(String.format("SKU: %s",product .getSku()));
            productName.setText(product.getProductName());
            productPrice.setText(product.getPrice() + "Eur");
            productDescription.setText(product.getDescription());

            productInfo.add(
                    sku,
                    productName,
                    reviewCrafter.starCrafter(totalStars),
                    productPrice,
                    productDescription,
                    addToCardWhiteList()
            );


        }








    }

    public VerticalLayout addToCardWhiteList(){

        VerticalLayout buttonsHolder = new VerticalLayout();
        buttonsHolder.setSpacing(false);
        buttonsHolder.setJustifyContentMode(JustifyContentMode.CENTER);



        IntegerField amount = new IntegerField("Amount");
        amount.setWidth("75%");
        amount.setValue(1);
        amount.setStepButtonsVisible(true);
        amount.setMin(1);
        amount.setMax(99);

        Button addToCart = new Button("Add to cart", VaadinIcon.CART.create());
        addToCart.setWidth("75%");
        addToCart.addThemeVariants(ButtonVariant.PRIMARY);


        Button whiteList = new Button("Whitelist", VaadinIcon.HEART.create());
        whiteList.setWidth("75%");



        buttonsHolder.add(
                amount,
            addToCart,
                whiteList
        );



        return buttonsHolder;

    }

    public VerticalLayout loadVariant(){

        VerticalLayout v = new VerticalLayout();
        v.setPadding(false);

        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();

        for(var s : product.getProductMultiples()){

            VerticalLayout circle = new VerticalLayout();
            circle.setWidth("50px");
            circle.setHeight("50px");
            circle.getStyle().setBorderRadius("50%");

            circle.getStyle().setBackgroundColor(s.getColor());

            HorizontalLayout button = new HorizontalLayout();
            button.addClassName("island-hover");
            button.add(
                    circle
            );


            button.addClickListener(e->{

                Long id = s.getId();

                loadImages(id);
                loadInfo(id);

                specs();




            });

            h.add(
                    button

            );
        }


        v.add(
                commonComponents.spanCrafterWordNoHide("Colors","activityFeed-name"),
                h
        );


        return v;

    }






    public void loadImages(Long id){


        // clear images layouts
        extraImages.removeAll();
        productImages.removeAll();

        String defaultImage = "No_picture.png";


        mainImage.setSrc(defaultImage);


        Scroller scroller = new Scroller(extraImages);
        scroller.setWidthFull();

        productImages.add(
                mainImage,
                scroller
        );

        if(product.getMultipleItems()) {



            if(id == null) {
                // get main image of the first in the list of multi variant products
                if (product.getProductMultiples() != null) {

                    // is main image already tried was
                    boolean mainAttempted = false;

                    for (var s : product.getProductMultiples()) {

                        mainAttempted = true;


                        for (var img : s.getImages()) {

                            if (img.getImageLogic().equals(ImageLogic.Main)) {
                                mainImage.setSrc(img.getImageUrl());


                                addNonMain(img.getImageUrl());

                            } else {
                                addNonMain(img.getImageUrl());
                            }

                        }


                        // break out of this list its no longer needed
                        if (mainAttempted) {

                            break;
                        }
                    }


                }

            }
            else{



                for (var s : product.getProductMultiples()) {


                    if(s.getId().equals(id)) {

                        for (var img : s.getImages()) {

                            if (img.getImageLogic().equals(ImageLogic.Main)) {
                                mainImage.setSrc(img.getImageUrl());


                                addNonMain(img.getImageUrl());

                            } else {
                                addNonMain(img.getImageUrl());
                            }

                        }

                    }
                    else{
                        continue;
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
