package com.example.demo.Pages.Material.MaterialAdd.Components;

import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class ColorSelector {

    public HorizontalLayout colorSelector(TextField textField) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthFull();
        horizontalLayout.setSpacing(false);
        horizontalLayout.setPadding(false);
        horizontalLayout.setAlignItems(FlexComponent.Alignment.BASELINE);


        Input colorPicker = new Input();
        colorPicker.addClassName("color-button");
        colorPicker.setHeight("50px");
        colorPicker.setType("color");
        colorPicker.setValue("#1e88e5");




        textField.addValueChangeListener(e->{
            colorPicker.setValue(e.getValue());
        });

        colorPicker.addValueChangeListener(e->{
            textField.setValue(e.getValue());
        });


        horizontalLayout.add(colorPicker,textField);

        return horizontalLayout;
    }

}
