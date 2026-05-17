package com.example.demo.Pages.ProductsEdit.Page;


import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.ProductsEdit.Components.ProductEditImage;
import com.example.demo.Pages.ProductsEdit.Components.ProductEditRightSideFields;
import com.example.demo.Services.ProductEditService.ProductEditService;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "ProductsEdit/:id", layout = MainLayout.class)
public class ProductsEdit extends VerticalLayout implements BeforeEnterObserver {



    CommonComponents commonComponents;
    Common common;
    ProductEditService productEditService;

    ProductEditRightSideFields productEditRightSideFields;
    ProductEditImage productEditImage;




    public ProductsEdit(CommonComponents commonComponents,
                        Common common,
                        ProductEditService productEditService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.productEditService = productEditService;
        this.productEditRightSideFields = new ProductEditRightSideFields(commonComponents,common);
        this.productEditImage = new ProductEditImage(commonComponents,common);

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

        System.out.println(id);



        add(mainLayout());

    }

    public VerticalLayout mainLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");


        productEditRightSideFields.setConsumer(e->{
            System.out.println("yes");
        });

        verticalLayout.add(briefPageExplanation(), joinImagesInfo());

        return verticalLayout;
    }


    public HorizontalLayout joinImagesInfo(){
        HorizontalLayout h =new HorizontalLayout();
        h.setWidthFull();
        h.addClassName("island");
        h.getStyle().set("flex-wrap","wrap");

        HorizontalLayout images = productEditImage.images(productEditService.productEditDtoLoad());
        Div holder = new Div(images,productEditImage.uploadStuff());
        VerticalLayout fields = productEditRightSideFields.rightSide(productEditService.productEditDtoLoad());

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
