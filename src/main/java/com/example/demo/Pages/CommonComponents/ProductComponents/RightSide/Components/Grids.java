package com.example.demo.Pages.CommonComponents.ProductComponents.RightSide.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Common.ListExtraDetailsGrid;
import com.example.demo.ControllerModels.Common.ListMaterialGrid;
import com.example.demo.ControllerModels.Material.MaterialInfo;
import com.example.demo.DTOS.ComboBoxMaterial;
import com.example.demo.Services.Material.MaterialService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
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
import com.vaadin.flow.component.textfield.TextField;
import lombok.Setter;

import java.util.List;
import java.util.function.Consumer;


@Setter
public class Grids {


    CommonComponents commonComponents;
    Common common;

    MaterialService materialService;

    Consumer<Long> consumer;

    public Grids(CommonComponents commonComponents, Common common, MaterialService materialService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.materialService = materialService;
    }


// grid crafters

    public Grid<MaterialInfo> materialGridCrafter(Grid<MaterialInfo> productFeedModelGrid, List<MaterialInfo> listMaterialGrids, Button addNewMaterial){

        addNewMaterial.addClickListener(e->{
           callDialog(listMaterialGrids);
        });

        productFeedModelGrid.addComponentColumn(row -> {

                    Span span = new Span();
                    span.setText(row.getId().toString());

                    return span;
                })
                .setHeader("Material")
                .setAutoWidth(true);



        productFeedModelGrid.addComponentColumn(row -> {

                    Span span = new Span();
                    span.setText(row.getMaterialName());

                    return span;
                })
                .setHeader("Amount of material")
                .setAutoWidth(true);



        productFeedModelGrid.addComponentColumn(row -> {

                    Image image = commonComponents.imageCrafter(row.getImageUrl(), "70px","70px","5px");

                    return image;
                })
                .setHeader("Unit")
                .setAutoWidth(true);


        productFeedModelGrid.addComponentColumn(row -> {

                    Span span = new Span();
                    span.setText(row.getInStock().toString());

                    return span;
                })
                .setHeader("Amount of material")
                .setAutoWidth(true);

        productFeedModelGrid.addComponentColumn(row -> {

                    Span span = new Span();
                    span.setText(row.getUnitPrice().toString());

                    return span;
                })
                .setHeader("Amount of material")
                .setAutoWidth(true);

        productFeedModelGrid.addComponentColumn(row -> {

                    IntegerField textField = new IntegerField();
                    textField.setValue(Math.toIntExact(row.getAmountTaken()));

                    textField.addValueChangeListener(e->{
                       for(var s : listMaterialGrids){
                           if(s.getId().equals(row.getId())){
                               s.setAmountTaken(Long.valueOf(textField.getValue()));
                           }
                       }
                        upgradeMaterialGrid(productFeedModelGrid,listMaterialGrids);
                    });

                    return textField;
                })
                .setHeader("Taken")
                .setAutoWidth(true);



        productFeedModelGrid.addComponentColumn(row ->{

            Button remove = commonComponents.buttonThemeAndIconNoNavigate("", ButtonVariant.PRIMARY, VaadinIcon.TRASH,"RED");

            HorizontalLayout h = new HorizontalLayout();
            h.setWidthFull();
            h.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
            h.add(remove);


            remove.addClickListener(e->{
                listMaterialGrids.remove(row);
                upgradeMaterialGrid(productFeedModelGrid,listMaterialGrids);

            });


            return h;

        }).setHeader("Actions").setAutoWidth(true);

        productFeedModelGrid.setHeight("600px");

        return productFeedModelGrid;


    }

    public void callDialog(List<MaterialInfo> materialInfoList){

        Dialog dialog = new Dialog();

        List<MaterialInfo> materialInfos = materialService.getAllAvailableMaterials();

        Grid<MaterialInfo> productFeedModelGrid = new Grid<>(MaterialInfo.class,false);
        productFeedModelGrid.setItems(materialInfos);

        productFeedModelGrid.addComponentColumn(row -> {

                    Span span = new Span();
                    span.setText(row.getId().toString());

                    return span;
                })
                .setHeader("Material")
                .setAutoWidth(true);



        productFeedModelGrid.addComponentColumn(row -> {

                    Image image = commonComponents.imageCrafter(row.getImageUrl(), "70px","70px","5px");

                    return image;
                })
                .setHeader("Amount of material")
                .setAutoWidth(true);



        productFeedModelGrid.addComponentColumn(row -> {

                    Span span = new Span();
                    span.setText(row.getMaterialName().toString());

                    return span;
                })
                .setHeader("Unit")
                .setAutoWidth(true);



        productFeedModelGrid.addComponentColumn(row ->{

            Span span = new Span();
            span.setText(row.getUnitPrice().toString());

            return span;

        }).setHeader("Actions").setAutoWidth(true);

        productFeedModelGrid.addComponentColumn(row ->{

            Span span = new Span();
            span.setText(row.getInStock().toString());

            return span;

        }).setHeader("Actions").setAutoWidth(true);

        productFeedModelGrid.addComponentColumn(row ->{

            Button select = new Button("Select");

            select.addClickListener(e->{

                if(materialInfoList.stream().anyMatch(p-> p.getId().equals(row.getId()))){
                    commonComponents.showNotification("Material already selected",3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.WARNING);

                }
                else {

                    consumer.accept(row.getId());
                }
                dialog.close();
            });

            return select;

        }).setHeader("Actions").setAutoWidth(true);

        productFeedModelGrid.setHeight("600px");

        dialog.setWidth("800px");

        dialog.add(productFeedModelGrid);

        dialog.open();


    }

    public Grid<ListExtraDetailsGrid> extraDetailsGridCrafter(List<ListExtraDetailsGrid> listExtraDetailsGrids,Grid<ListExtraDetailsGrid> extraDetailsGrid){

        extraDetailsGrid.addComponentColumn(ListExtraDetailsGrid::getSpecName)
                .setHeader("Specficiation name").setAutoWidth(true);

        extraDetailsGrid.addComponentColumn(ListExtraDetailsGrid::getSpecDescription)
                .setHeader("Specification description").setAutoWidth(true);

        extraDetailsGrid.addComponentColumn(row ->{

            Button remove = commonComponents.buttonThemeAndIconNoNavigate("",ButtonVariant.PRIMARY,VaadinIcon.TRASH,"RED");

            HorizontalLayout h = new HorizontalLayout();
            h.setWidthFull();
            h.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
            h.add(remove);


            remove.addClickListener(e->{
                listExtraDetailsGrids.remove(row);
                upgradeExtraDetailsGrid(listExtraDetailsGrids,extraDetailsGrid);
            });


            return h;

        }).setHeader("Actions").setAutoWidth(true);

        return extraDetailsGrid;

    }



// update

    public void upgradeMaterialGrid(Grid<MaterialInfo> productFeedModelGrid, List<MaterialInfo> listMaterialGrids){
        productFeedModelGrid.setItems(listMaterialGrids);
    }

    public void upgradeExtraDetailsGrid(List<ListExtraDetailsGrid> listExtraDetailsGrids,Grid<ListExtraDetailsGrid> extraDetailsGrid){
        extraDetailsGrid.setItems(listExtraDetailsGrids);
    }






}
