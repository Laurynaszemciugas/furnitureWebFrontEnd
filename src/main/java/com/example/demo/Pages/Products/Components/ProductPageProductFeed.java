package com.example.demo.Pages.Products.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.CommonDtos.Product;
import com.example.demo.ControllerModels.Products.ProductFeedModel;
import com.example.demo.Enums.Category;
import com.example.demo.Enums.ProductCategory;
import com.example.demo.Enums.Stock;
import com.example.demo.ServerDBCall.ProductPage.ProductsCall;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;


public class ProductPageProductFeed {


    HorizontalLayout verticalLayout = new HorizontalLayout();

    CommonComponents commonComponents;
    Common common;
    ProductService productService;

    Consumer<String> updateRequired;

    public ProductPageProductFeed(CommonComponents commonComponents,
                                  Common common,
                                  ProductService productService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.productService = productService;
    }

    public void setUpdateRequired(Consumer<String> updateRequired){
        this.updateRequired = updateRequired;
    }


    public HorizontalLayout productsMain(List<ProductFeedModel> productFeedModelList){

        verticalLayout.removeAll();

        boolean empty = productFeedModelList.isEmpty();

        verticalLayout = new HorizontalLayout();
        verticalLayout.setWidthFull();
        verticalLayout.addClassName("layout-flex");
        verticalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        System.out.println(productFeedModelList);

        if(empty){
            verticalLayout.add(commonComponents.noDataFound());
        }
        else{
            for(var s : productFeedModelList){
                verticalLayout.add(productFeed(s.getId(),s.getImageUrl(),s.getProductName(),s.getCategory(),s.getPrice(),s.getStockQuantity(),s.getLowStockThreshold()));
            }
        }







        return verticalLayout;
    }





    public VerticalLayout productFeed(long id, String mainImageUrl, String productName, Category productCategory, double price, long unitsLeft, long minTreshold){
        VerticalLayout product = new VerticalLayout();
        product.addClassName("island");
        product.getStyle().set("flex", "1 1 302px");
        product.getStyle().set("max-width", "620px");
        product.getStyle().set("min-width", "302px");
        product.getStyle().set("position","relative");
        product.setHeight("400px");

        product.setSpacing(false);

        Span stockLevel = commonComponents.spanCrafter("In Stock","stock-badge");

        colorSpan(unitsLeft,minTreshold,stockLevel);

        Span productPrice = commonComponents.spanCrafterWordNoHide(String.format("%.2f %s",price,"Eur"),"activityFeed-name");
        productPrice.getStyle().set("margin-top","10px");



        // ========================================== buttons ===============================================================
        Button editShortCut = commonComponents.smallIconButtonsNoNavigate(VaadinIcon.EDIT,"black");
        editShortCut.addClickListener(e->{
            UI.getCurrent().navigate("ProductsEdit/"+ id);
        });

        Button removeProduct = commonComponents.smallIconButtonsNoNavigate(VaadinIcon.TRASH,"red");
        removeProduct.addClickListener(e->{
            deleteConfirmation(productName,id);


        });



        HorizontalLayout actionHolder = new HorizontalLayout(removeProduct,editShortCut);


        VerticalLayout div = new VerticalLayout(
                commonComponents.spanCrafterWordNoHide(productName,"stat-example"),
                commonComponents.spanCrafterWordNoHide("Actions","stat-example"),
                actionHolder);

        div.setAlignItems(FlexComponent.Alignment.CENTER);
        div.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        div.setWidth("80%");

        div.getStyle()
                .set("position", "absolute")
                .set("top", "50%")
                .set("left", "50%")
                .set("z-index", "10");
        div.addClassName("overlay-layout");


        product.getElement().addEventListener("mouseenter", e -> {
            div.addClassName("show");
        });


        product.getElement().addEventListener("mouseleave", e -> {
            div.removeClassName("show");

        });


        product.add(
                commonComponents.imageCrafter(mainImageUrl,"100%","220px","10px"),
                commonComponents.spanCrafterWordNoHide(productName,"activityFeed-name"),
                commonComponents.spanCrafter(productCategory.getDisplayName(),"stat-title"),
                productPrice,
                commonComponents.doubleValueRow(stockLevel, commonComponents.spanCrafter(String.format("%d %s",unitsLeft, "Units"),"stat-title")),
                div


        );



        return product;
    }


    public void deleteConfirmation(String productName, Long id){
        ConfirmDialog dialog = new ConfirmDialog();

        dialog.setHeader("Warning");

        VerticalLayout content = new VerticalLayout();
        content.setSpacing(false);
        content.setPadding(false);

        Span line = new Span(String.format("%s '%s' %s", "Please enter ", productName.toUpperCase(), "to remove a product"));
        line.getStyle().set("color", "red");

        TextField confirmName = new TextField("Enter product name");

        content.add(line,confirmName);



        dialog.setCancelable(true);
        dialog.setConfirmText("Remove");
        dialog.setCancelText("Go back");


        dialog.addConfirmListener(event -> {
            if(confirmName.getValue().equals(productName.toUpperCase())) {
                productService.removeProductById(id);
                commonComponents.showNotification("Product" + productName + " removed", 3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.LUMO_SUCCESS);
                updateRequired.accept("need update");
                UI.getCurrent().getPage().reload();
            }
            else{
                commonComponents.showNotification("Product was not removed verification failed ", 3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.ERROR);
            }
        });

        dialog.addCancelListener(event -> {
        });

        dialog.add(content);

        dialog.open();
    }

    public void colorSpan(long unitsLeft, long minTreshold, Span stockLevel){

        long first = unitsLeft;
        long second = minTreshold;

        if(first >= second){
            stockLevel.addClassName("stock-in");
            stockLevel.setText(Stock.In_Stock.getDisplayName());
        }
        if(first < second){
            stockLevel.addClassName("stock-low");
            stockLevel.setText(Stock.Low_Stock.getDisplayName());
        }
        if(first == 0){
            stockLevel.addClassName("stock-out");
            stockLevel.setText(Stock.No_Stock.getDisplayName());
        }


    }


}
