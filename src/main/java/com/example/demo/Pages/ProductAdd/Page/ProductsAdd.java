package com.example.demo.Pages.ProductAdd.Page;


import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.CommonDtos.Product;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.ProductsEdit.Components.ProductEditImage;
import com.example.demo.Pages.ProductsEdit.Components.ProductEditRightSideFields;
import com.example.demo.ServerCall.ProductAdd.ProductAddCall;
import com.example.demo.Services.ProductAdd.ProductAddService;
import com.example.demo.Services.ProductEditService.ProductEditService;
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
    ProductAddService productAddService;
    ProductAddCall productAddCall;

    ProductEditRightSideFields productEditRightSideFields;
    ProductEditImage productEditImage;




    public ProductsAdd(CommonComponents commonComponents,
                       Common common,
                       ProductAddService productAddService,
                       ProductAddCall productAddCall) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.productAddService = productAddService;
        this.productAddCall = productAddCall;


        this.productEditRightSideFields = new ProductEditRightSideFields(commonComponents,common);
        this.productEditImage = new ProductEditImage(commonComponents,common);

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
        //verticalLayout.addClassName("main-island");

//        productEditImage.setListConsumer(e->{
//            System.out.println("ąęčė");
//        });

        productEditRightSideFields.setConsumer(e->{
            System.out.println("heeeeeeeeeeeeeeeeeeeeee");
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

        Product sharedProductInstance = productAddService.productAddDtoLoad();

        HorizontalLayout images = productEditImage.images(sharedProductInstance);
        Div holder = new Div(images,productEditImage.uploadStuff());
        VerticalLayout fields = productEditRightSideFields.rightSide(sharedProductInstance);

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
