package com.example.demo.Pages.ProductsEdit.Page;


import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Products.ProductFeedModel;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.ProductsEdit.Components.ProductEditImage;
import com.example.demo.Pages.ProductsEdit.Components.ProductEditRightSideFields;
import com.example.demo.Services.ProductEditService.ProductEditService;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "ProductsEdit", layout = MainLayout.class)
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


        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);





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

        productEditImage.setListConsumer(e->{
            //System.out.println(e);
        });

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
