package com.example.demo.Pages.Orders.Components.OrderProductAddRemove;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Orders.OrderAddProducts;
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
import com.vaadin.flow.component.textfield.IntegerField;

import java.util.ArrayList;
import java.util.List;

public class OrderAddProductListAddRemove {


    CommonComponents commonComponents;
    Common common;

    List<OrderAddProducts> selectedProducts = new ArrayList<>();
    Grid<OrderAddProducts> orderItems;

    Span totalValueOfItems = new Span();;
    List<OrderAddProducts> listOfProducts = new ArrayList<>();


    public OrderAddProductListAddRemove(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }



    public VerticalLayout consumerOrderItems(List<OrderAddProducts> items){
        listOfProducts.clear();
        listOfProducts.addAll(items);
        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();
        v.addClassName("island");


        orderItems = new Grid<>(OrderAddProducts.class,false);
        updateGrid();
        calculateTotal();

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
            quantity.setMin(0);
            quantity.setMax(100);
            quantity.setValue(e.getAmountSelected() == null ? 0: e.getAmountSelected().intValue());
            quantity.setStepButtonsVisible(true);

            quantity.addValueChangeListener(ee->{
                e.setAmountSelected(Long.valueOf(String.valueOf(ee.getValue())));
                updateGrid();
                calculateTotal();
            });

            return  quantity;
        }).setAutoWidth(true).setHeader("Quantity");




        orderItems.addComponentColumn(e->{

            Button button = new Button("", VaadinIcon.TRASH.create());

            button.addClickListener(ee->{
                selectedProducts.removeIf(item-> item.equals(e));
                updateGrid();
                calculateTotal();
            });


            return  button;
        }).setAutoWidth(true).setHeader("Actions");




        totalValueOfItems = commonComponents.spanCrafterWordNoHide(String.format("%s: %.2f %s","Total", 0.0,"Eur"),"stat-example");
        Button addProduct = commonComponents.buttonThemeAndIconNoNavigate("Add product", ButtonVariant.LUMO_PRIMARY, VaadinIcon.PLUS,"white");

        addProduct.addClickListener(e->{
            showAddOrderDialog();
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

    public void showAddOrderDialog(){
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
                            Long value = s.getAmountSelected() == null ? 0 : s.getAmountSelected();
                            value++;
                            s.setAmountSelected(value);
                            break;
                        }
                    }
                    updateGrid();
                    calculateTotal();
                }
                else{
                    selectedProducts.add(orderAddProducts);
                }
                dialog.close();
                updateGrid();
                calculateTotal();
            });


            return  button;
        }).setAutoWidth(true).setHeader("Actions");


        Button button = new Button("back",e-> dialog.close());






        dialog.add(
                productList
        );

        dialog.open();
    }


    public void updateGrid(){
        orderItems.setItems(selectedProducts);
    }

    public void calculateTotal(){
        Double total = 0.0;
        for(var s : selectedProducts){
            Long taken = s.getAmountSelected() == null ? 0 : s.getAmountSelected();
            total+= s.getPrice()* taken;
        }
        totalValueOfItems.setText(String.format("%s: %.2f %s","Total", total,"Eur"));
    }


}
