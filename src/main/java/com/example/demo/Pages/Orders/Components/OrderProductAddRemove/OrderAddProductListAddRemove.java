package com.example.demo.Pages.Orders.Components.OrderProductAddRemove;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.ObjectConverter;
import com.example.demo.ControllerModels.CommonDtos.OrderJoin.OrderProducts;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.ControllerModels.CommonDtos.Product;
import com.example.demo.ControllerModels.Orders.OrderAddProducts;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderAddProductListAddRemove {


    CommonComponents commonComponents;
    Common common;
    ProductService productService;

    OrderGridProductRemoveAdd orderGridProductRemoveAdd;

    List<OrderAddProducts> selectedProducts = new ArrayList<>();
    Grid<OrderAddProducts> orderItems;

    Span totalValueOfItems = new Span();;
    List<OrderAddProducts> listOfProducts = new ArrayList<>();


    public OrderAddProductListAddRemove(CommonComponents commonComponents, Common common,ProductService productService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.productService = productService;
        this.orderGridProductRemoveAdd = new OrderGridProductRemoveAdd(commonComponents,common);

        listOfProducts.clear();
        listOfProducts.addAll(productService.getProductsForAddOrder());

        updataTotalValue();
    }



    public VerticalLayout consumerOrderItems(Orders orders){

        // load data its gonna work only on the edit page cuz it checks if its null on new order its always null
        if(orders.getProductsData()!=null) {
            selectedProducts = productService.getExisitingData(orders.getId());
        }

        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();
        v.addClassName("island");


        orderItems = new Grid<>(OrderAddProducts.class,false);
        orderGridProductRemoveAdd.updateGrid(orderItems, selectedProducts);
        orderGridProductRemoveAdd.calculateTotal(selectedProducts);


        orderGridProductRemoveAdd.assembleGrid(orderItems,selectedProducts,orders);



        totalValueOfItems = commonComponents.spanCrafterWordNoHide(String.format("%s: %.2f %s","Total", 0.0,"Eur"),"stat-example");
        Button addProduct = commonComponents.buttonThemeAndIconNoNavigate("Add product", ButtonVariant.LUMO_PRIMARY, VaadinIcon.PLUS,"white");

        addProduct.addClickListener(e->{
            showAddOrderDialog(orders);
        });

        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        h.add(
                addProduct,
                totalValueOfItems
        );

        v.add(
                commonComponents.spanCrafterWordNoHide("Order items","activityFeed-name"),
                orderItems,
                h
        );

        return  v;
    }

    public void showAddOrderDialog(Orders orders){
        Dialog dialog = new Dialog("Products");
        dialog.setWidth("1000px");
        dialog.setHeight("600px");

        Grid<OrderAddProducts> productList = new Grid<>(OrderAddProducts.class,false);
        productList.setItems(listOfProducts);
        productList.setSizeFull();

        productList.addComponentColumn(e->{

            Image image = commonComponents.imageCrafter(e.getMainImage() == null ? "No_picture.png" : e.getMainImage() ,"80px","80px","15px");

            Span productName = commonComponents.spanCrafterWordNoHide(e.getProductName() == null ? "None" : e.getProductName(),"stat-example");
            Span productSku = commonComponents.spanCrafterWordNoHide(e.getSku() == null ? "None" : e.getSku(),"stat-title");

            VerticalLayout nameHolder = new VerticalLayout();
            nameHolder.add(
                    productName,
                    productSku
            );

            HorizontalLayout productHolder = new HorizontalLayout();
            productHolder.setAlignItems(FlexComponent.Alignment.CENTER);
            productHolder.add(
                    image,
                    nameHolder
            );



            return  productHolder;
        }).setAutoWidth(true).setHeader("Product");


        productList.addComponentColumn(e->{



            Span productCategory = commonComponents.spanCrafterWordNoHide(e.getCategory() == null ? "None" : e.getCategory().toString(),"stat-title");

            return  productCategory;
        }).setAutoWidth(true).setHeader("Category");

        productList.addComponentColumn(e->{

            Span stockQuantity = commonComponents.spanCrafterWordNoHide(e.getStockQuantity() == null ? "None" : String.format("%s %d","In stock:",e.getStockQuantity()),"stat-title");
            Span lowStockThreshold = commonComponents.spanCrafterWordNoHide(e.getLowStockThreshold() == null ? "None" : String.format("%s %d","Threshold:",e.getLowStockThreshold()),"stat-title");

            VerticalLayout stockLevel = new VerticalLayout();
            stockLevel.add(
                    stockQuantity,
                    lowStockThreshold
            );

            return  stockLevel;
        }).setAutoWidth(true).setHeader("Stock levels");

        productList.addComponentColumn(e->{

            Span productPrice = commonComponents.spanCrafterWordNoHide(e.getPrice() == null ? "None" : String.format("%.2f %s",e.getPrice(),"Eur"),"stat-example");

            return  productPrice;
        }).setAutoWidth(true).setHeader("Price");


        productList.addComponentColumn(e->{

            Button button = new Button("Select");
            OrderAddProducts orderAddProducts = e;
            button.setVisible(orderAddProducts != null);
            button.addClickListener(ee->{
                if(selectedProducts.stream().anyMatch(item-> item.getId().equals(orderAddProducts.getId()))) {
                    commonComponents.showNotification("Product already exists in the list +1 added",3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.LUMO_WARNING);
                    for(var s : selectedProducts){
                        if(s.getId().equals(orderAddProducts.getId())){
                            Long value = s.getAmountSelected() == null ? 1 : s.getAmountSelected();
                            value++;
                            s.setAmountSelected(value);
                            break;
                        }
                    }
                    orderGridProductRemoveAdd.updateGrid(orderItems, selectedProducts);
                    orderGridProductRemoveAdd.calculateTotal(selectedProducts);
                }
                else{
                    orderAddProducts.setAmountSelected(1L);
                    selectedProducts.add(orderAddProducts);
                }
                dialog.close();
                orderGridProductRemoveAdd.updateGrid(orderItems, selectedProducts);
                orderGridProductRemoveAdd.calculateTotal(selectedProducts);

                if(orders.getProductsData() != null) {
                    orders.getProductsData().clear();
                }
                List<OrderProducts> orderProducts = new ArrayList<>();
                for(var s : selectedProducts){
                    Product product = new Product();
                    product.setId(s.getId());
                    OrderProducts orderProducts1 = new OrderProducts();
                    orderProducts1.setAmountOfProduct(s.getAmountSelected());
                    orderProducts1.setProduct(product);
                    orderProducts.add(orderProducts1);
                }
                orders.setProductsData(orderProducts);


            });


            return  button;
        }).setAutoWidth(true).setHeader("Actions");

        dialog.add(
                productList
        );

        dialog.open();
    }


    public void updataTotalValue(){
        orderGridProductRemoveAdd.setTotalValue(e->{
            totalValueOfItems.setText(String.format("%s: %.2f %s","Total", e,"Eur"));
        });
    }


    public void loadExistingData(){
        orderGridProductRemoveAdd.updateGrid(orderItems, selectedProducts);
        orderGridProductRemoveAdd.calculateTotal(selectedProducts);
    }



}
