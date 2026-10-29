package com.example.demo.MainLayouts.Client;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Style;


@CssImport("./MainCSS.css")

public class ClientMainLayout extends AppLayout {



    CommonComponents commonComponents;
    Common common;


    public ClientMainLayout(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;



        createHeader();
    }

    private void createHeader() {




//        DrawerToggle toggle = new DrawerToggle();
//        toggle.getStyle().set("background", "transparent")
//                .set("border", "none")
//                .set("box-shadow", "none")
//                .set("padding", "0")
//                .set("margin", "0")
//                .set("cursor", "pointer");



        // header stuff

        HorizontalLayout header = new HorizontalLayout();


        header.addClassName("header-island-simple");

        header.setSizeFull();

        header.setSpacing(false);
        header.setPadding(false);


        HorizontalLayout buttonHolder = new HorizontalLayout();

        Button home = new Button("Home");
        home.addClassName("color-button");
        Button products = new Button("Products");
        products.addClassName("color-button");
        Button categories = new Button("Categories");
        categories.addClassName("color-button");
        Button about = new Button("About");
        about.addClassName("color-button");

        buttonHolder.add(
                home,
                products,
                categories,
                about
        );


        TextField search = new TextField();
        search.setPrefixComponent(VaadinIcon.SEARCH.create());
        search.setPlaceholder("Search for furniture, material or categories");
        search.setWidth("20%");


        Icon cartIcon = commonComponents.iconCrafter(VaadinIcon.CART_O,"30px","ROYALBLUE");
        Icon heartIcon = commonComponents.iconCrafter(VaadinIcon.HEART,"30px","RED");




        VerticalLayout whiteListed = new VerticalLayout(heartIcon);
        whiteListed.addClassName("island-hover");
        whiteListed.setPadding(false);
        whiteListed.getStyle().setAlignItems(Style.AlignItems.CENTER);
        whiteListed.getStyle().setJustifyContent(Style.JustifyContent.CENTER);
        whiteListed.setWidth("50px");
        whiteListed.setHeight("50px");
        whiteListed.getStyle().setBorderRadius("50%");


        VerticalLayout cart = new VerticalLayout(cartIcon);
        cart.addClassName("island-hover");
        cart.setPadding(false);
        cart.getStyle().setAlignItems(Style.AlignItems.CENTER);
        cart.getStyle().setJustifyContent(Style.JustifyContent.CENTER);
        cart.setWidth("50px");
        cart.setHeight("50px");
        cart.getStyle().setBorderRadius("50%");

        cart.addClickListener(e->{

            openCart();

        });



        HorizontalLayout actionHolder = new HorizontalLayout();
        actionHolder.add(
                whiteListed,
                cart
        );

        header.add(
                buttonHolder,
                search,
                actionHolder

        );

        header.setJustifyContentMode(FlexComponent.JustifyContentMode.EVENLY);


        addToNavbar(header);
    }


    public void openCart(){

        Dialog cart = new Dialog();
        cart.setWidth("1000px");

        Button close = new Button("Close");

        cart.getFooter().add(close);

        close.addClickListener(e->{
           cart.close();
        });

        VerticalLayout main = new VerticalLayout();


        main.add(
                commonComponents.spanCrafterWordNoHide("Cart","stat-value")
        );


        cart.add(
                main
        );

        cart.open();


    }


    public VerticalLayout dotNumber(Long number){

        return new VerticalLayout();

    }


}



