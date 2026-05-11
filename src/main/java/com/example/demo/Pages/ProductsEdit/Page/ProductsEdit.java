package com.example.demo.Pages.ProductsEdit.Page;


import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Services.ProductEditService.ProductEditService;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "ProductsEdit", layout = MainLayout.class)
public class ProductsEdit extends VerticalLayout implements BeforeEnterObserver {



    CommonComponents commonComponents;
    Common common;
    ProductEditService productEditService;

    public ProductsEdit(CommonComponents commonComponents,
                        Common common,
                        ProductEditService productEditService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.productEditService = productEditService;

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);



        add(mainLayout());

    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

    }

    public VerticalLayout mainLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");
        verticalLayout.addClassName("main-island");

        verticalLayout.add(briefPageExplanation());

        return verticalLayout;
    }




    public HorizontalLayout briefPageExplanation(){
        HorizontalLayout left = commonComponents.biefPageExplanation("Edit product");
        left.setWidthFull();

        return left;
    }



}
