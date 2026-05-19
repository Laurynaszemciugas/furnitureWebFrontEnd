package com.example.demo.Pages.CommonComponents;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Common.ListExtraDetailsGrid;
import com.example.demo.ControllerModels.Common.ListMaterialGrid;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;


public class Grids {


    CommonComponents commonComponents;
    Common common;




    public Grids(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }


    // grid crafters

    public Grid<ListMaterialGrid> materialGridCrafter(Grid<ListMaterialGrid> productFeedModelGrid, List<ListMaterialGrid> listMaterialGrids){


        productFeedModelGrid.addComponentColumn(row -> {

                    VerticalLayout v = new VerticalLayout();
                    v.setWidthFull();

                    ComboBox<String> material = row.getMaterial();
                    material.setWidthFull();

                    Span span = commonComponents.spanCrafterWordNoHide(String.format("%s %.2f"," Unit cost:",row.getUnitPrice()),"stat-title");

                    v.add(material,span);

                    return v;
                })
                .setHeader("Material")
                .setAutoWidth(true);



        productFeedModelGrid.addComponentColumn(row -> {

                    VerticalLayout v = new VerticalLayout();
                    v.setWidthFull();

                    IntegerField integerField = row.getAmountOfMaterial();
                    integerField.setWidthFull();

                    double price = row.getUnitPrice()*row.getAmountOfMaterial().getValue();
                    Span span = commonComponents.spanCrafterWordNoHide(String.format("%s %.2f"," Total cost:",price),"stat-title");

                    v.add(integerField,span);

                    return v;
                })
                .setHeader("Amount of material")
                .setAutoWidth(true);


        productFeedModelGrid.addComponentColumn(ListMaterialGrid::getUnit).setHeader("Unit").setAutoWidth(true);

        productFeedModelGrid.addComponentColumn(row ->{

            Button remove = commonComponents.buttonThemeAndIconNoNavigate("", ButtonVariant.PRIMARY, VaadinIcon.TRASH,"White");

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

        return productFeedModelGrid;


    }

    public Grid<ListExtraDetailsGrid> extraDetailsGridCrafter(List<ListExtraDetailsGrid> listExtraDetailsGrids,Grid<ListExtraDetailsGrid> extraDetailsGrid){

        extraDetailsGrid.addComponentColumn(ListExtraDetailsGrid::getSpecName)
                .setHeader("Specficiation name").setAutoWidth(true);

        extraDetailsGrid.addComponentColumn(ListExtraDetailsGrid::getSpecDescription)
                .setHeader("Specification description").setAutoWidth(true);

        extraDetailsGrid.addComponentColumn(row ->{

            Button remove = commonComponents.buttonThemeAndIconNoNavigate("",ButtonVariant.PRIMARY,VaadinIcon.TRASH,"White");

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

    public void upgradeMaterialGrid(Grid<ListMaterialGrid> productFeedModelGrid, List<ListMaterialGrid> listMaterialGrids){
        productFeedModelGrid.setItems(listMaterialGrids);
    }

    public void upgradeExtraDetailsGrid(List<ListExtraDetailsGrid> listExtraDetailsGrids,Grid<ListExtraDetailsGrid> extraDetailsGrid){
        extraDetailsGrid.setItems(listExtraDetailsGrids);
    }






}
