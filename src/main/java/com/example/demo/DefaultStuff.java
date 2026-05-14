package com.example.demo;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;

@Route("Test")
public class DefaultStuff extends VerticalLayout {


    CommonComponents commonComponents;
    Common common;

    public DefaultStuff(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;

        setWidthFull();

        Accordion accordion = new Accordion();
        accordion.open(0);
        Span question = commonComponents.spanCrafterWordNoHide("How to remove image ?","activityFeed-name");
        Span explanation = commonComponents.spanCrafterWordNoHide("If you want to remove an image from products just double click on the smaller picture then the small delete button will appear after pressing that button the image will be deleted from the list","stat-example");
        VerticalLayout verticalLayout = new VerticalLayout(question,explanation);
        verticalLayout.setPadding(false);
        verticalLayout.setSpacing(false);
        accordion.add("Common Questions",verticalLayout);

        Span question2 = commonComponents.spanCrafterWordNoHide("How to remove image ?","activityFeed-name");
        Span explanation2 = commonComponents.spanCrafterWordNoHide("If you want to remove an image from products just double click on the smaller picture then the small delete button will appear after pressing that button the image will be deleted from the list","stat-example");



        accordion.close();

        add(accordion);
    }





    //
//    CommonComponents commonComponents;
//    Common common;
//    ProductService productService;
//
//    public ProductsPage(CommonComponents commonComponents, Common common, ProductService productService) {
//        this.commonComponents = commonComponents;
//        this.common = common;
//        this.productService = productService;
//
//        setPadding(false);
//        setSpacing(false);
//        setSizeFull();
//        setAlignItems(FlexComponent.Alignment.CENTER);
//
//
//
//        add(mainLayout());
//
//    }
//
//
//    @Override
//    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
//
//    }
//
//    public VerticalLayout mainLayout() {
//        VerticalLayout verticalLayout = new VerticalLayout();
//        verticalLayout.setMaxWidth("1650px");
//        verticalLayout.getStyle().set("margin-top", "5px");
//        verticalLayout.addClassName("main-island");
//
//        return verticalLayout;
//    }
//


}
