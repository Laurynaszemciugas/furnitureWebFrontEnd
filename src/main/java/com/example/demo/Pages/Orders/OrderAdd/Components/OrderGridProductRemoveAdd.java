package com.example.demo.Pages.Orders.OrderAdd.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.CommonDtos.OrderJoin.OrderProducts;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.ControllerModels.CommonDtos.Product;
import com.example.demo.ControllerModels.Orders.OrderAddProducts;
import com.example.demo.Enums.OrderStatus;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Setter
public class OrderGridProductRemoveAdd {


    CommonComponents commonComponents;
    Common common;

    Consumer<Double> totalValue;

    public OrderGridProductRemoveAdd(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }

    public void assembleGrid(Grid<OrderAddProducts> orderItems, List<OrderAddProducts> selectedProducts, Orders orders){


        orderItems.addComponentColumn(e->{

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


        orderItems.addComponentColumn(e->{



            Span productCategory = commonComponents.spanCrafterWordNoHide(e.getCategory() == null ? "None" : e.getCategory().toString(),"stat-title");

            return  productCategory;
        }).setAutoWidth(true).setHeader("Category");

        orderItems.addComponentColumn(e->{

            Span stockQuantity = commonComponents.spanCrafterWordNoHide(e.getStockQuantity() == null ? "None" : String.format("%s %d","In stock:",e.getStockQuantity()),"stat-title");
            Span lowStockThreshold = commonComponents.spanCrafterWordNoHide(e.getLowStockThreshold() == null ? "None" : String.format("%s %d","Threshold:",e.getLowStockThreshold()),"stat-title");

            VerticalLayout stockLevel = new VerticalLayout();
            stockLevel.add(
                    stockQuantity,
                    lowStockThreshold
            );

            return  stockLevel;
        }).setAutoWidth(true).setHeader("Stock levels");

        orderItems.addComponentColumn(e->{

            Span productPrice = commonComponents.spanCrafterWordNoHide(e.getPrice() == null ? "None" : String.format("%.2f %s",e.getPrice(),"Eur"),"stat-example");

            return  productPrice;
        }).setAutoWidth(true).setHeader("Price");

        orderItems.addComponentColumn(e->{

            IntegerField quantity = new IntegerField();
            quantity.setStep(1);
            quantity.setMin(1);
            quantity.setMax(100);
            quantity.setValue(e.getAmountSelected() == null ? 1: e.getAmountSelected().intValue());
            quantity.setStepButtonsVisible(true);

            if(orders.getOrderStatus() != null && orders.getOrderStatus().equals(OrderStatus.Finished) || orders.getOrderStatus().equals(OrderStatus.LACK_OF_SUPPLY)){
                quantity.setEnabled(false);
                quantity.addClassName("no-gray-disabled");
            }

            quantity.addValueChangeListener(ee->{

                if(ee.getValue() == null || ee.getValue() <= 0 || ee.getValue() >= 100){
                    quantity.setInvalid(true);
                    quantity.setErrorMessage("Invalid input");

                    commonComponents.showNotification("Value must be between 1 and 99",3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.ERROR);

                    return;
                }


                if(ee.isFromClient()) {
                    e.setAmountSelected(Long.valueOf(String.valueOf(ee.getValue())));
                    updateGrid(orderItems, selectedProducts);
                    calculateTotal(selectedProducts);

                    if (orders.getProductsData() != null) {
                        orders.getProductsData().clear();
                    }
                    List<OrderProducts> orderProducts = new ArrayList<>();
                    for (var s : selectedProducts) {
                        Product product = new Product();
                        product.setId(s.getId());
                        OrderProducts orderProducts1 = new OrderProducts();
                        orderProducts1.setAmountOfProduct(s.getAmountSelected());
                        orderProducts1.setProduct(product);
                        orderProducts.add(orderProducts1);
                    }
                    orders.setProductsData(orderProducts);
                }
            });

            return  quantity;
        }).setAutoWidth(true).setHeader("Quantity");




        orderItems.addComponentColumn(e->{
            calculateTotal(selectedProducts);
            Button button = new Button("", VaadinIcon.TRASH.create());

            button.addClickListener(ee->{
                System.out.println(e.getId());
                selectedProducts.removeIf(item-> item.getId().equals(e.getId()));
                updateGrid(orderItems, selectedProducts);
                calculateTotal(selectedProducts);

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

            if(orders.getOrderStatus() != null && orders.getOrderStatus().equals(OrderStatus.Finished) || orders.getOrderStatus().equals(OrderStatus.LACK_OF_SUPPLY)){
                button.setEnabled(false);
            }


            return  button;
        }).setAutoWidth(true).setHeader("Actions");






    }




    public void updateGrid(Grid<OrderAddProducts> orderItems, List<OrderAddProducts> selectedProducts){
        orderItems.setItems(selectedProducts);
    }

    public void calculateTotal(List<OrderAddProducts> selectedProducts){
        Double total = 0.0;
        for(var s : selectedProducts){
            Long taken = s.getAmountSelected() == null ? 0 : s.getAmountSelected();
            total+= s.getPrice()* taken;
        }
        totalValue.accept(total);


    }






}
