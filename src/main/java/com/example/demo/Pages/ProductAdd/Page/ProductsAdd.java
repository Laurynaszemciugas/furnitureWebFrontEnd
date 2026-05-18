package com.example.demo.Pages.ProductAdd.Page;


import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.CommonDtos.Product;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.CommonComponents.ProductEditImage;
import com.example.demo.Pages.CommonComponents.ProductEditRightSideFields;
import com.example.demo.Pages.CommonComponents.ReviewCrafter;
import com.example.demo.ServerDBCall.CommonCalls.CommonCalls;
import com.example.demo.ServerDBCall.ProductAdd.ProductAddCall;
import com.example.demo.Services.ProductAdd.ProductAddService;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.io.IOException;

@Route(value = "ProductsAdd", layout = MainLayout.class)
public class ProductsAdd extends VerticalLayout implements BeforeEnterObserver {



    CommonComponents commonComponents;
    Common common;
    CommonCalls commonCalls;
    ProductAddService productAddService;
    ProductAddCall productAddCall;

    ProductEditRightSideFields productEditRightSideFields;
    ProductEditImage productEditImage;
    ReviewCrafter reviewCrafter;




    public ProductsAdd(CommonComponents commonComponents,
                       Common common,
                       CommonCalls commonCalls,
                       ProductAddService productAddService,
                       ProductAddCall productAddCall) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.commonCalls = commonCalls;
        this.productAddService = productAddService;
        this.productAddCall = productAddCall;


        this.productEditRightSideFields = new ProductEditRightSideFields(commonComponents,common,commonCalls);
        this.productEditImage = new ProductEditImage(commonComponents,common);
        this.reviewCrafter = new ReviewCrafter(commonComponents,common);

        this.productEditRightSideFields.setProductEditImage(this.productEditImage);

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(Alignment.CENTER);





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

        productEditRightSideFields.setConsumer(e->{
            try {
                productAddCall.addNewOrder(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        verticalLayout.add(briefPageExplanation(), joinImagesInfo());

        return verticalLayout;
    }


    public HorizontalLayout joinImagesInfo(){
        HorizontalLayout h =new HorizontalLayout();
        h.setWidthFull();
        h.addClassName("island");
        h.getStyle().set("flex-wrap","wrap");

        //make an empty field
        Product sharedProductInstance = new Product();

        HorizontalLayout images = productEditImage.images(sharedProductInstance);
        Div holder = new Div(images,
                productEditImage.uploadStuff(),
                reviewCrafter.commentsHolder(sharedProductInstance.getComments()));
        VerticalLayout fields = productEditRightSideFields.rightSide(sharedProductInstance);

        h.add(holder,fields);
        h.expand(fields);


        return h;
    }






    public HorizontalLayout briefPageExplanation(){
        HorizontalLayout left = commonComponents.biefPageExplanation("Add a new product");
        left.setWidthFull();

        return left;
    }












}
