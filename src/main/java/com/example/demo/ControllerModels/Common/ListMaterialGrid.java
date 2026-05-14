package com.example.demo.ControllerModels.Common;


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
    private ComboBox<String> material;
    private IntegerField amountOfMaterial;
    private ComboBox<String> unit;
    private  double unitPrice;



}
