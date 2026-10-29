package com.example.demo.Pages.ClientPages;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.DTOS.Client.CategoryDisplay;
import com.example.demo.DTOS.Client.ProductsClients;
import com.example.demo.FilterDTO.EmployeeActiveOrderFilter.EmployeeActiveOrderFilter;
import com.example.demo.MainLayouts.Client.ClientMainLayout;
import com.example.demo.MainLayouts.Employee.EmployeeMainLayout;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Route(value = "ClientFrontPage" , layout = ClientMainLayout.class)
//@Route(value = "EmployeesDashBoard", layout = EmployeeMainLayout.class)
public class Page extends VerticalLayout implements BeforeEnterObserver {


    CommonComponents commonComponents;
    Common common;
    ProductService productService;


    public Page(CommonComponents commonComponents, Common common, ProductService productService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.productService = productService;


        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);





        addClassName("animation-page");

    }



    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        removeAll();






        add(mainLayout());

    }



    public VerticalLayout mainLayout() {


        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addClassName("island");

        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");


        productDisplay();

        verticalLayout.add(
                categoryList()
        );


        return verticalLayout;
    }

    public VerticalLayout categoryList(){

        List<CategoryDisplay> categoryDisplays = productService.getClientProductCategorys(10);

        VerticalLayout main = new VerticalLayout();
        main.setPadding(false);

        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();

        for(var s : categoryDisplays){

            VerticalLayout v = new VerticalLayout();
            v.addClassName("island-hover");


            v.getStyle().set("flex", "1 1 252px");
            v.getStyle().set("max-width", "320px");
            v.getStyle().set("min-width", "252px");

            Image image = commonComponents.imageCrafter(s.getImageUrl() == null ? "No_picture.png" : s.getImageUrl(), "100%","50px","10px");


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
        Button viewAllCategory = new Button("View all");
        holder.add(
                commonComponents.spanCrafterWordNoHide("Category's","activityFeed-name"),
                viewAllCategory
        );
        main.add(
                holder,
                h
        );






        return main;
    }


    public void productDisplay(){

        List<ProductsClients> productsClients = productService.getProductsClients(10);


        System.out.println("UNPACKING");
        for(var s : productsClients) {
            String imagesUnSplit = (String) s.getMultiVariantImages();

            if(imagesUnSplit != null) {
                List<String> images = Arrays.asList(imagesUnSplit.split(", "));


                for(var img : images){
                    System.out.println(img);
                }

            }






        }




    }


}
