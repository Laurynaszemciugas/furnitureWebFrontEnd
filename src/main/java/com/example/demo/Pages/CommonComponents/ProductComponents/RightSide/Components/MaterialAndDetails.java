package com.example.demo.Pages.CommonComponents.ProductComponents.RightSide.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Common.ListMaterialGrid;
import com.example.demo.ControllerModels.CommonDtos.Materials;
import com.example.demo.ControllerModels.CommonDtos.ProductJoin.ProductMaterials;
import com.example.demo.DTOS.ComboBoxMaterial;
import com.example.demo.ServerDBCall.CommonCalls.CommonCalls;
import com.example.demo.Services.CommonService.CommonService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MaterialAndDetails {


    CommonComponents commonComponents;
    Common common;
    CommonService commonService;
    Grids grids;

    List<ComboBoxMaterial> materialNames = new ArrayList<>();


    @SneakyThrows
    public MaterialAndDetails(CommonComponents commonComponents, Common common,CommonService commonService,Grids grids) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.commonService = commonService;
        this.grids = grids;
        materialNames.addAll(commonService.getMaterialNames());
    }




    @SneakyThrows
    public ComboBox<ComboBoxMaterial> comboBoxMaterial(String chosenValue,
                                                       List<ListMaterialGrid> listMaterialGrids,
                                                       Grid<ListMaterialGrid> productFeedModelGrid,
                                                       Button addNewMaterial) {



        ComboBox<ComboBoxMaterial> materials = new ComboBox<>();
        materials.addClassName("no-gray-disabled");
        materials.setItems(materialNames);
        materials.setItemLabelGenerator(ComboBoxMaterial::getMaterialName);

        if(chosenValue!=null && !chosenValue.equalsIgnoreCase("")) {
            for(var s : materialNames){
                if(s.getMaterialName().equalsIgnoreCase(chosenValue)){
                    materials.setValue(s);
                }
            }

            materials.setEnabled(false);
        }

        materials.addValueChangeListener(e->{

            ComboBoxMaterial selected = e.getValue();

            if (selected == null) {
                return;
            }

            boolean exits = listMaterialGrids.stream().anyMatch(item -> item.getNameForCompare().equalsIgnoreCase(e.getValue().getMaterialName()));
            boolean fromClient = e.isFromClient();


            if(exits && fromClient){
                commonComponents.showNotification(String.format("%s - '%s' %s","Material",e.getValue().getMaterialName(),"exists dublicates are not allowed"),
                        3000,
                        Notification.Position.BOTTOM_CENTER,
                        NotificationVariant.LUMO_WARNING);

                materials.setValue(null);
                return;
            }
            if(!exits && fromClient) {

                commonComponents.showNotification(String.format("%s - '%s' %s", "Material", e.getValue().getMaterialName(), "added succesfully"),
                        3000,
                        Notification.Position.BOTTOM_CENTER,
                        NotificationVariant.LUMO_SUCCESS);
                materials.setValue(e.getValue());

                for(var s : listMaterialGrids) {
                    if(s.getNameForCompare().equalsIgnoreCase("")) {
                        s.setNameForCompare(e.getValue().getMaterialName());
                        s.setId(e.getValue().getId());
                    }

                }


                materials.setEnabled(false);
                addNewMaterial.setEnabled(true);

                System.out.println(e.getValue());





            }

            grids.upgradeMaterialGrid(productFeedModelGrid,listMaterialGrids);

            System.out.println("hopefully new values >????????");
            System.out.println(listMaterialGrids);


            extendDataOfTheDataMaterial(e.getValue().getId(),listMaterialGrids);




        });
        return materials;
    }


    public void extendDataOfTheDataMaterial(Long id,List<ListMaterialGrid> listMaterialGrids){
        for(var s : listMaterialGrids) {
            if(s.getId().equals(id)) {
                    Materials materialData = commonService.getMaterialDataAccordingToId(id);
                    s.getUnit().setValue(materialData.getUnit());
                    s.setUnitPrice(materialData.getUnitPrice());
                    s.setStockLevel(materialData.getInStock());
                    System.out.println(s.getUnitPrice());



            }
        }
    }

    public IntegerField quantityField(Long chosenValue,
                                      List<ListMaterialGrid> listMaterialGrids,
                                      Grid<ListMaterialGrid> productFeedModelGrid,
                                      NumberField materialCost) {
        IntegerField quantity = new IntegerField();

        quantity.setStepButtonsVisible(true);
        quantity.setValue(1);
        quantity.setMin(0);
        quantity.setMax(100);
        quantity.setStep(1);
        quantity.setValue(chosenValue.intValue());

        quantity.addValueChangeListener(e->{

            List<ProductMaterials> productMaterials = new ArrayList<>();

            Double sumMaterialPrice = 0.0;

            for(var s : listMaterialGrids){
                ProductMaterials materials = new ProductMaterials();
                materials.setId(s.getId());
                materials.setNameForRefrence(s.getNameForCompare());
                materials.setAmountUsed(Long.valueOf(s.getAmountOfMaterial().getValue()));

                productMaterials.add(materials);

                sumMaterialPrice += (s.getUnitPrice() * s.getAmountOfMaterial().getValue());

            }


            materialCost.setValue(Double.valueOf(String.format("%.2f",sumMaterialPrice)));


            grids.upgradeMaterialGrid(productFeedModelGrid,listMaterialGrids);

        });


        return quantity;
    }

    public ComboBox<String> unitField(String chosenValue) {
        ComboBox<String> unit = new ComboBox<>();
        unit.addClassName("no-gray-disabled");
        unit.setItems("Planks", "Pieces");
        unit.setValue(chosenValue);
        unit.setEnabled(false);



        return unit;
    }


    // components for extra details grid
    public TextField specName(String name){
        TextField textField = new TextField();
        textField.setValue(name);
        textField.setPlaceholder("Material");
        return textField;
    }

    public TextArea specDescription(String name){
        TextArea textArea = new TextArea();
        textArea.setValue(name);
        textArea.setPlaceholder("Wool");
        textArea.setWidthFull();
        return textArea;
    }


}
