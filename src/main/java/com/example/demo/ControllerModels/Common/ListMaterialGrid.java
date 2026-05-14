package com.example.demo.ControllerModels.Common;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
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
    private Component material;
    private Component amountOfMaterial;
    private Component unit;
    private  double unitPrice;



}
