package com.example.demo.Pages.Products.ProductAdd.Page;


import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.ObjectConverter;
import com.example.demo.ControllerModels.Common.CommonImagesData;
import com.example.demo.ControllerModels.CommonDtos.Product;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.CommonComponents.ProductComponents.RightSide.Components.ProductEditImage;
import com.example.demo.Pages.CommonComponents.ProductComponents.RightSide.Main.ProductEditRightSideFields;
import com.example.demo.Pages.CommonComponents.ProductComponents.RightSide.Components.ReviewCrafter;
import com.example.demo.ServerDBCall.CommonCalls.CommonCalls;
import com.example.demo.ServerDBCall.ProductAdd.ProductAddCall;
import com.example.demo.Services.CommonService.CommonService;
import com.example.demo.Services.ProductAdd.ProductAddService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Route(value = "ProductsAdd", layout = MainLayout.class)
public class ProductsAdd extends VerticalLayout implements BeforeEnterObserver {



    CommonComponents commonComponents;
    Common common;
    CommonService commonService;
    ProductAddService productAddService;
    ProductAddCall productAddCall;

    ObjectConverter objectConverter;


    ProductEditRightSideFields productEditRightSideFields;
    ProductEditImage productEditImage;
    ReviewCrafter reviewCrafter;




    public ProductsAdd(CommonComponents commonComponents,
                       Common common,
                       CommonService commonService,
                       ProductAddService productAddService,
                       ProductAddCall productAddCall,
                       ObjectConverter objectConverter) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.commonService = commonService;
        this.productAddService = productAddService;
        this.productAddCall = productAddCall;
        this.objectConverter = objectConverter;


        this.productEditRightSideFields = new ProductEditRightSideFields(commonComponents,common,commonService,objectConverter);
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
        verticalLayout.addClassName("main-island");
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

        verticalLayout.add(
                productEditRightSideFields.briefPageExplanation("Add new product"),
                joinImagesInfo());

        return verticalLayout;
    }


    public HorizontalLayout joinImagesInfo(){
        HorizontalLayout h =new HorizontalLayout();
        h.setWidthFull();

        h.getStyle().set("flex-wrap","wrap");

        //make an empty field
        Product sharedProductInstance = new Product();
        List<CommonImagesData> commonImagesData = new ArrayList<>();

        HorizontalLayout images = productEditImage.images(commonImagesData);
        Div holder = new Div(images,
                productEditImage.uploadStuff(),
                reviewCrafter.commentsHolder(sharedProductInstance.getComments()));
        VerticalLayout fields = productEditRightSideFields.rightSide(sharedProductInstance);

        h.add(holder,fields);
        h.expand(fields);


        return h;
    }



















}
