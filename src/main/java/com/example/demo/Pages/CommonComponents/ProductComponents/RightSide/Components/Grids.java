package com.example.demo.Pages.CommonComponents.ProductComponents.RightSide.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Common.ListExtraDetailsGrid;
import com.example.demo.ControllerModels.Common.ListMaterialGrid;
import com.example.demo.ControllerModels.Common.ListStepsToPrepare;
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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


@Setter
public class Grids {


    CommonComponents commonComponents;
    Common common;

    MaterialService materialService;

    Consumer<Long> consumer;
    Consumer<Double> price;

    List<MaterialInfo> materialInfos = new ArrayList<>();

    public Grids(CommonComponents commonComponents, Common common, MaterialService materialService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.materialService = materialService;
    }


// grid crafters

    public Grid<MaterialInfo> materialGridCrafter(Grid<MaterialInfo> productFeedModelGrid, List<MaterialInfo> listMaterialGrids, Button addNewMaterial){

        productFeedModelGrid.removeAllColumns();

        materialInfos = listMaterialGrids;

        addNewMaterial.addClickListener(e->{
           callDialog(listMaterialGrids);
        });


        productFeedModelGrid.addComponentColumn(row -> {

                    HorizontalLayout h = new HorizontalLayout();
                    h.setAlignItems(FlexComponent.Alignment.CENTER);
                    Span span = commonComponents.spanCrafter(row.getMaterialName(),"stat-example");
                    Image image = commonComponents.imageCrafter(row.getImageUrl(), "70px","70px","5px");

                    h.add(
                            image,
                            span
                    );

                    return h;
                })
                .setHeader("Material")
                .setAutoWidth(true);



        productFeedModelGrid.addComponentColumn(row -> {

                    Span span = commonComponents.spanCrafter(row.getInStock().toString(),"stat-example");

                    return span;

                })
                .setHeader("Material stock")
                .setAutoWidth(true);


        productFeedModelGrid.addComponentColumn(row -> {

                    Span span = commonComponents.spanCrafter(row.getUnitPrice() + " Eur", "stat-example");

                    return span;
                })
                .setHeader("Material unit cost")
                .setAutoWidth(true);


        productFeedModelGrid.addComponentColumn(row -> {

                    IntegerField textField = new IntegerField();
                    textField.setStepButtonsVisible(true);
                    textField.setMin(0);
                    textField.setValue(Math.toIntExact(row.getAmountTaken()));

                    textField.addValueChangeListener(e->{
                       for(var s : listMaterialGrids){
                           if(s.getId().equals(row.getId())){
                               s.setAmountTaken(Long.valueOf(textField.getValue()));
                           }
                       }

                        calculateTotal();
                        upgradeMaterialGrid(productFeedModelGrid,listMaterialGrids);
                    });

                    return textField;
                })
                .setHeader("Amount of material")
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

        Button back = new Button("Back");
        back.addThemeVariants(ButtonVariant.PRIMARY);

        List<MaterialInfo> materialInfos = materialService.getAllAvailableMaterials();

        Grid<MaterialInfo> productFeedModelGrid = new Grid<>(MaterialInfo.class,false);
        productFeedModelGrid.setItems(materialInfos);


        productFeedModelGrid.addComponentColumn(row -> {


                    HorizontalLayout h = new HorizontalLayout();
                    h.setAlignItems(FlexComponent.Alignment.CENTER);
                    Span span = commonComponents.spanCrafter(row.getMaterialName(),"stat-example");
                    Image image = commonComponents.imageCrafter(row.getImageUrl(), "70px","70px","5px");

                    h.add(
                            image,
                            span
                    );

                    return h;
                })
                .setHeader("Material")
                .setAutoWidth(true);



        productFeedModelGrid.addComponentColumn(row -> {

                    Span span = commonComponents.spanCrafter(row.getUnitPrice().toString(),"stat-example");

                    return span;
                })
                .setHeader("Stock of the material")
                .setAutoWidth(true);



        productFeedModelGrid.addComponentColumn(row ->{

            Span span = commonComponents.spanCrafter(row.getUnitPrice().toString(),"stat-example");

            return span;

        }).setHeader("Cost per unit").setAutoWidth(true);


        productFeedModelGrid.addComponentColumn(row ->{

            Button select = new Button("Select");

            if(materialInfoList.stream().anyMatch(p-> p.getId().equals(row.getId()))){
                select.setText("Selected");
            }

            select.addClickListener(e->{

                    if (materialInfoList.stream().anyMatch(p -> p.getId().equals(row.getId()))) {
                        commonComponents.showNotification("Material already selected", 3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.WARNING);

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

        dialog.getFooter().add(back);

        dialog.open();



        back.addClickListener(e->{
           dialog.close();
        });


    }

    public void calculateTotal(){
        Double sum = 0.0;
        for(var s : materialInfos){
            sum+= s.getUnitPrice()*s.getAmountTaken();
        }
        price.accept(sum);
    }

    public Grid<ListExtraDetailsGrid> extraDetailsGridCrafter(List<ListExtraDetailsGrid> listExtraDetailsGrids,Grid<ListExtraDetailsGrid> extraDetailsGrid){

        extraDetailsGrid.removeAllColumns();

        extraDetailsGrid.addComponentColumn(ListExtraDetailsGrid::getSpecName)
                .setHeader("Specification name").setAutoWidth(true);

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

    public Grid<ListStepsToPrepare> preparationSteps(List<ListStepsToPrepare> listStepsToPrepares, Grid<ListStepsToPrepare> listStepsToPrepareGrid){

        listStepsToPrepareGrid.removeAllColumns();

        listStepsToPrepareGrid.addComponentColumn(e->{


                    int i = 1;
                    for(var s : listStepsToPrepares){
                        s.setStep(Long.valueOf(i++));
                    }

                return commonComponents.spanCrafter(e.getStep().toString(), "stat-example");


                })
                .setHeader("Step").setAutoWidth(true);

        listStepsToPrepareGrid.addComponentColumn(ListStepsToPrepare::getStepName)
                .setHeader("Step name").setAutoWidth(true);

        listStepsToPrepareGrid.addComponentColumn(ListStepsToPrepare::getStepDescription)
                .setHeader("Step description").setAutoWidth(true);

        listStepsToPrepareGrid.addComponentColumn(row ->{

            Button remove = commonComponents.buttonThemeAndIconNoNavigate("",ButtonVariant.PRIMARY,VaadinIcon.TRASH,"RED");

            HorizontalLayout h = new HorizontalLayout();
            h.setWidthFull();
            h.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
            h.add(remove);


            remove.addClickListener(e->{
                listStepsToPrepares.remove(row);
                int i = 1;
                for(var s : listStepsToPrepares){
                    s.setStep(Long.valueOf(i++));
                }

                updateStepGrid(listStepsToPrepares,listStepsToPrepareGrid);
            });


            return h;

        }).setHeader("Actions").setAutoWidth(true);

        return listStepsToPrepareGrid;

    }




// update

    public void upgradeMaterialGrid(Grid<MaterialInfo> productFeedModelGrid, List<MaterialInfo> listMaterialGrids){
        productFeedModelGrid.setItems(listMaterialGrids);
    }

    public void upgradeExtraDetailsGrid(List<ListExtraDetailsGrid> listExtraDetailsGrids,Grid<ListExtraDetailsGrid> extraDetailsGrid){
        extraDetailsGrid.setItems(listExtraDetailsGrids);
    }

    public void updateStepGrid(List<ListStepsToPrepare> listExtraDetailsGrids,Grid<ListStepsToPrepare> extraDetailsGrid){

        extraDetailsGrid.setItems(listExtraDetailsGrids);
    }






}
