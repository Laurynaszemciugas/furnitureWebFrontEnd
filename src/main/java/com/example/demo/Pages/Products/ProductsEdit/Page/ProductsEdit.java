package com.example.demo.Pages.Products.ProductsEdit.Page;


import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.ObjectConverter;
import com.example.demo.ControllerModels.Common.CommonImagesData;
import com.example.demo.ControllerModels.CommonDtos.ImagesData;
import com.example.demo.ControllerModels.CommonDtos.Product;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.CommonComponents.ProductComponents.RightSide.Components.ProductEditImage;
import com.example.demo.Pages.CommonComponents.ProductComponents.RightSide.Main.ProductEditRightSideFields;
import com.example.demo.Pages.CommonComponents.ProductComponents.RightSide.Components.ReviewCrafter;
import com.example.demo.ServerDBCall.CommonCalls.CommonCalls;
import com.example.demo.Services.ProductEditService.ProductEditService;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route(value = "ProductsEdit/:id", layout = MainLayout.class)
public class ProductsEdit extends VerticalLayout implements BeforeEnterObserver {



    CommonComponents commonComponents;
    Common common;
    ProductEditService productEditService;
    CommonCalls commonCalls;

    ObjectConverter objectConverter;

    ProductEditRightSideFields productEditRightSideFields;
    ProductEditImage productEditImage;
    ReviewCrafter reviewCrafter;


    int productId = 0;



    public ProductsEdit(CommonComponents commonComponents,
                        Common common,
                        CommonCalls commonCalls,
                        ProductEditService productEditService,
                        ObjectConverter objectConverter) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.commonCalls = commonCalls;
        this.productEditService = productEditService;
        this.objectConverter = objectConverter;
        this.productEditRightSideFields = new ProductEditRightSideFields(commonComponents,common,commonCalls,objectConverter);
        this.productEditImage = new ProductEditImage(commonComponents,common);
        this.reviewCrafter = new ReviewCrafter(commonComponents,common);

        this.productEditRightSideFields.setProductEditImage(this.productEditImage);

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);





    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        removeAll();

        int id = Integer.parseInt(beforeEnterEvent.getRouteParameters().get("id").orElse(null));

        this.productId = id;



        add(mainLayout());

    }

    public VerticalLayout mainLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");


        productEditRightSideFields.setConsumer(e->{


            System.out.println("Edit time");

            System.out.println("after pressing save");
            for(var s : e.getImages()){
                System.out.println(s.getImageName() + " " + s.getImageLogic());
            }

            productEditService.updateProductEdit(e);


        });

        verticalLayout.add(briefPageExplanation(), joinImagesInfo());

        return verticalLayout;
    }


    public HorizontalLayout joinImagesInfo(){
        HorizontalLayout h =new HorizontalLayout();
        h.setWidthFull();
        h.addClassName("island");
        h.getStyle().set("flex-wrap","wrap");

        Product product = productEditService.productEditDtoLoad(Long.valueOf(productId));
        if(product == null){
            UI.getCurrent().navigate("make a no page found page");
        }

        HorizontalLayout images = productEditImage.images(objectConverter.convert(product.getImages(),CommonImagesData.class));
        Div holder = new Div(images,productEditImage.uploadStuff(),
                reviewCrafter.commentsHolder(product.getComments()));
        VerticalLayout fields = productEditRightSideFields.rightSide(product);

        h.add(holder,fields);
        h.expand(fields);


        return h;
    }




    public HorizontalLayout briefPageExplanation(){
        HorizontalLayout left = commonComponents.biefPageExplanation("Edit product");
        left.setWidthFull();

        return left;
    }













}
