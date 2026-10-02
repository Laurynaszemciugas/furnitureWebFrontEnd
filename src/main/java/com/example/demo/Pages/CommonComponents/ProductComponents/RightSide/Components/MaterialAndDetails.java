package com.example.demo.Pages.CommonComponents.ProductComponents.RightSide.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Common.ListMaterialGrid;
import com.example.demo.ControllerModels.CommonDtos.Materials;
import com.example.demo.ControllerModels.CommonDtos.ProductJoin.ProductMaterials;
import com.example.demo.DTOS.ComboBoxMaterial;
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


    // components for extra details grid
    public TextField specName(String name){
        TextField textField = new TextField();
        textField.setValue(name);
        return textField;
    }

    public TextArea specDescription(String name){
        TextArea textArea = new TextArea();
        textArea.setValue(name);
        textArea.setWidthFull();
        return textArea;
    }


}
