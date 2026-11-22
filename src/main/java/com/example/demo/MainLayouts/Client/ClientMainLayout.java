package com.example.demo.MainLayouts.Client;

import com.example.demo.ClientUIUpdater;
import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.DTOS.Client.Cart;
import com.example.demo.Pages.ClientPages.Components.ProductUI;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Style;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


@CssImport("./MainCSS.css")
@Setter
public class ClientMainLayout extends AppLayout {



    CommonComponents commonComponents;
    Common common;


    ClientUIUpdater clientUIUpdater;
    SessionCrafter sessionCrafter;
    ProductUI productUI;


    List<Cart> selectedItems = new ArrayList<>();
    Span totalPrice = new Span();



    public ClientMainLayout(CommonComponents commonComponents, Common common,ClientUIUpdater clientUIUpdater,ProductUI productUI) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.clientUIUpdater = clientUIUpdater;
        this.sessionCrafter = new SessionCrafter();
        this.productUI = productUI;


        totalPrice = commonComponents.spanCrafterWordNoHide("","activityFeed-name");

        clientUIUpdater.setUpdateCart(e->{

            openCart();

        });


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
        home.addClickListener(e->{
            UI.getCurrent().navigate("ClientFrontPage");
        });

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


        VerticalLayout cartMemory = new VerticalLayout();
        cartMemory.setPadding(false);

        Dialog cart = new Dialog("Cart");
        cart.setWidth("1000px");


        Button close = new Button("Close");

        cart.getFooter().add(close);

        close.addClickListener(e->{
           cart.close();
        });




        selectedItems = sessionCrafter.extractSession("Cart", List.class);

        if(selectedItems == null || selectedItems.isEmpty()) {

            cartMemory.removeAll();
            cartMemory.add(commonComponents.noDataFoundImproved("Cart is currently empty",null,null));
        }
        else{

            cartMemory.removeAll();
            for (var s : selectedItems) {
                cartMemory.add(CraftCartItem(s,cart));
            }
        }


        totalPrice.setText(calculateTotalPrice());
        HorizontalLayout priceHolder = new HorizontalLayout();
        priceHolder.setWidthFull();
        priceHolder.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        priceHolder.add(
                totalPrice
        );

        cart.add(
                cartMemory,
                priceHolder

        );

        cart.open();


    }

    public HorizontalLayout CraftCartItem(Cart cart, Dialog dialog){


        HorizontalLayout h = new HorizontalLayout();
        h.setMinHeight("200px");
        h.getStyle().setPosition(Style.Position.RELATIVE);
        h.setAlignItems(FlexComponent.Alignment.CENTER);
        h.addClassName("island");
        h.setWidthFull();


        VerticalLayout nameCategory = new VerticalLayout();

        nameCategory.setSpacing(false);
        nameCategory.setPadding(false);
        nameCategory.add(
                commonComponents.spanCrafter(cart.getProductName(),"activityFeed-name"),
                commonComponents.spanCrafter(cart.getCategory().getDisplayName(),"stat-description")
        );

        HorizontalLayout imageHolder = new HorizontalLayout();
        imageHolder.setAlignItems(FlexComponent.Alignment.CENTER);
        imageHolder.add(
                commonComponents.imageCrafter(cart.getImageUrl() == null ? "No_picture.png" : cart.getImageUrl(), "100px","100px","10px"),
                nameCategory
        );

        VerticalLayout colorCircle = new VerticalLayout();
        colorCircle.setWidth("25px");
        colorCircle.setHeight("25px");
        colorCircle.getStyle().setBorderRadius("50%");
        colorCircle.getStyle().setBackgroundColor(cart.getColor());

        Tooltip.forComponent(colorCircle)
                .withText("Color hex: " + cart.getColor());


        VerticalLayout price = new VerticalLayout();
        price.setAlignItems(FlexComponent.Alignment.CENTER);
        price.setWidthFull();

        Span productPrice = commonComponents.spanCrafterWordNoHide("","activityFeed-name");
        Span discountPrice = commonComponents.spanCrafterWordNoHide("","activityFeed-name");
        Span discount = commonComponents.spanCrafterWordNoHide("","stat-example");
        discount.addClassNames("stock-badge","stock-out ");

        productPrice.setText(cart.getPrice() + "Eur");

        if(cart.getDiscount() > 0){

            productPrice.getStyle()
                    .set("text-decoration", "line-through").setColor("Grey");

            discount.setText("Discount " + (long)cart.getDiscount() + "%" );
            discountPrice.setText(String.format("%.2f Eur",cart.getPrice() - (cart.getPrice() * cart.getDiscount()/100)));


            price.add(
                    discount,
                    discountPrice,
                    productPrice

            );
        }
        else{
            price.add(
                    productPrice
            );
        }


        IntegerField amountOfItems = new IntegerField();
        amountOfItems.setValue(Math.toIntExact(cart.getAmountSelected()));
        amountOfItems.setStepButtonsVisible(true);
        amountOfItems.setMin(1);
        amountOfItems.setMax(99);



        Span totalPriceOfTheProduct = commonComponents.spanCrafterWordNoHide(calculatePrice(amountOfItems.getValue().longValue(),cart.getPrice(),cart.getDiscount()),"stat-example");
        totalPriceOfTheProduct.getStyle().setPosition(Style.Position.ABSOLUTE).setBottom("10px").setRight("10px");


        amountOfItems.addValueChangeListener(e->{



            for(var s : selectedItems){
                if(s.getProductId().equals(cart.getProductId())){
                    s.setAmountSelected(e.getValue().longValue());
                }
            }

            sessionCrafter.createSession("Cart", selectedItems);

            selectedItems = sessionCrafter.extractSession("Cart",List.class);

            totalPriceOfTheProduct.setText(calculatePrice(e.getValue().longValue(),cart.getPrice(),cart.getDiscount()));

            totalPrice.setText(calculateTotalPrice());


        });


        Button removeItem = new Button(commonComponents.iconCrafter(VaadinIcon.TRASH,"25px","red"));
        removeItem.getStyle().setPosition(Style.Position.ABSOLUTE).setLeft("10px").setBottom("10px");
        removeItem.addClickListener(e->{
           selectedItems.remove(cart);
           h.setVisible(false);
           h.removeAll();

            totalPrice.setText(calculateTotalPrice());

            commonComponents.showNotification(String.format("%s is removed from your cart", cart.getProductName()),3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.LUMO_SUCCESS);
            if(selectedItems.isEmpty()){
                dialog.close();
            }
        });


        h.add(
                removeItem,
                totalPriceOfTheProduct,
                productUI.addToWhiteList(false,true),
                imageHolder,
                colorCircle,
                price,
                amountOfItems
        );

        return h;
    }


    public VerticalLayout dotNumber(Long number){

        return new VerticalLayout();

    }

    public String calculatePrice(Long amountSelected, Double unitCost, Long discount){

        String price = "";


        Double totalCost = amountSelected * unitCost;


        if(discount > 0) {
            Double totalCostOfThisProduct = totalCost - (totalCost * discount / 100);
            price = String.format("Cost - %.2f Eur",totalCostOfThisProduct);
        }
        else{
            price = String.format("Cost - %.2f Eur",totalCost);
        }

        return price;

    }


    public String calculateTotalPrice(){

        String price = "";

        Double totalCost = 0.0;

        if(selectedItems != null ) {


            for (var s : selectedItems) {
                Double priceAccordingToSelectedItems = s.getPrice() * s.getAmountSelected();
                if (s.getDiscount() > 1) {
                    totalCost += priceAccordingToSelectedItems - (priceAccordingToSelectedItems * s.getDiscount() / 100);
                } else {
                    totalCost += s.getPrice() * s.getAmountSelected();
                }
            }


        }
        price = String.format("Total cost - %.2f Eur", totalCost);
        return price;

    }




}



