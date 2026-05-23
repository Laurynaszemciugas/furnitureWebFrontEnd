package com.example.demo.ControllerModels.Common;


import com.example.demo.DTOS.ComboBoxMaterial;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ListMaterialGrid {


    private Long id;
    private String nameForCompare;
    private ComboBox<ComboBoxMaterial> material;
    private IntegerField amountOfMaterial;
    private ComboBox<String> unit;
    private double unitPrice;
    private Long stockLevel;



}
