package com.example.demo.Pages.ClientPages;

import com.example.demo.ClientUIUpdater;
import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.ImageViewer;
import com.example.demo.DTOS.Client.CategoryDisplay;
import com.example.demo.DTOS.Client.ProductsClients;
import com.example.demo.FilterDTO.EmployeeActiveOrderFilter.EmployeeActiveOrderFilter;
import com.example.demo.MainLayouts.Client.ClientMainLayout;
import com.example.demo.MainLayouts.Employee.EmployeeMainLayout;
import com.example.demo.Pages.ClientPages.Components.ProductUI;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


@Route(value = "ClientFrontPage" , layout = ClientMainLayout.class)
@Setter
//@Route(value = "EmployeesDashBoard", layout = EmployeeMainLayout.class)
public class Page extends VerticalLayout implements BeforeEnterObserver {


    CommonComponents commonComponents;
    Common common;
    ProductService productService;

    ImageViewer imageViewer;

    ClientUIUpdater uiUpdater;

    ProductUI productUI;


    public Page(CommonComponents commonComponents, Common common, ProductService productService,ClientUIUpdater uiUpdater,ImageViewer imageViewer,ProductUI productUI) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.productService = productService;
        this.uiUpdater = uiUpdater;
        this.imageViewer = imageViewer;
        this.productUI = productUI;


        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);





        addClassName("animation-page");

    }



    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        removeAll();




        Button testButton = new Button("Test if mainLayout gets request");

        testButton.addClickListener(e->{

            uiUpdater.updateCart(true);

        });

        add(mainLayout(),testButton);

    }



    public VerticalLayout mainLayout() {


        VerticalLayout verticalLayout = new VerticalLayout();

        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");


        verticalLayout.add(
                mainImage(),
                categoryList(),
                commonComponents.spanCrafterWordNoHide("Products","activityFeed-name"),
                productUI.productDisplay(productService.getProductsClients(10))

        );


        return verticalLayout;
    }


    public HorizontalLayout mainImage(){

        HorizontalLayout holder = new HorizontalLayout();
        holder.getStyle().setPosition(Style.Position.RELATIVE);
        holder.setWidthFull();

        Image image = commonComponents.imageCrafter("ClientPageMainImage.png","100%","200px","20px");


        Span mainText = commonComponents.spanCrafterWordNoHide("Make your home comfortable place","stat-value");
        mainText.setWidth("350px");
        mainText.getStyle().setPosition(Style.Position.ABSOLUTE);
        mainText.getStyle().setLeft("10px").setTop("20px");

        holder.add(
                image,
                mainText
        );




        return holder;

    }


    public VerticalLayout categoryList(){

        List<CategoryDisplay> categoryDisplays = productService.getClientProductCategorys(10);

        VerticalLayout main = new VerticalLayout();
        main.setPadding(false);

        HorizontalLayout h = new HorizontalLayout();
        h.addClassName("layout-flex");
        h.setWidthFull();

        for(var s : categoryDisplays){

            VerticalLayout v = new VerticalLayout();
            v.addClassName("island-hover");


            v.getStyle().set("flex", "1 1 252px");
            v.getStyle().set("max-width", "320px");
            v.getStyle().set("min-width", "252px");


            Image image = commonComponents.imageCrafter(s.getImageUrl() == null ? "No_picture.png" : s.getImageUrl(), "100%","100px","10px");


            v.add(
                    image,
                    commonComponents.spanCrafterWordNoHide(s.getCategory().getDisplayName(),"stat-example")
            );

            h.add(
                    v
            );


        }


        HorizontalLayout holder = new HorizontalLayout();
        holder.setWidthFull();
        holder.setJustifyContentMode(JustifyContentMode.BETWEEN);
       // Button viewAllCategory = new Button("View all");
        holder.add(
             //   viewAllCategory
        );
        main.add(
                holder,
                h
        );






        return main;
    }





}
