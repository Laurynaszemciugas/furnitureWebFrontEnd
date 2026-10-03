package com.example.demo.Pages.Products.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.DTOS.ComboBoxMaterial;
import com.example.demo.Services.CommonService.CommonService;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import lombok.SneakyThrows;

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
