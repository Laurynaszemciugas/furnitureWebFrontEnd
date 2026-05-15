package com.example.demo.Pages.CommonComponents;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Common.ListExtraDetailsGrid;
import com.example.demo.ControllerModels.Common.ListMaterialGrid;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.List;


public class ProductAddEditGrids {


    CommonComponents commonComponents;
    Common common;




    public ProductAddEditGrids(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }


    // grid crafters

    public Grid<ListMaterialGrid> materialGridCrafter(Grid<ListMaterialGrid> productFeedModelGrid, List<ListMaterialGrid> listMaterialGrids){


        productFeedModelGrid.addComponentColumn(ListMaterialGrid::getMaterial).setHeader("Material").setAutoWidth(true);

        productFeedModelGrid.addComponentColumn(ListMaterialGrid::getAmountOfMaterial).setHeader("Amount of material").setAutoWidth(true);

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
