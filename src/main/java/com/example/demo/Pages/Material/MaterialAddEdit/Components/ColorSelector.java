package com.example.demo.Pages.Material.MaterialAddEdit.Components;

import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class ColorSelector {

    public HorizontalLayout colorSelector(TextField textField) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthFull();
        horizontalLayout.setSpacing(true);
        horizontalLayout.setPadding(false);
        horizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER); // Aligns color picker with text box input

        Input colorPicker = new Input();
        colorPicker.addClassName("color-button");
        colorPicker.setHeight("60px"); // Match default Vaadin field height
        colorPicker.setMinWidth("50px");
        colorPicker.setType("color");
        colorPicker.setValue("#1e88e5");

        // Sync initial value to TextField
        if (textField.getValue() == null || textField.getValue().isEmpty()) {
            textField.setValue("#1e88e5");
        }

        // Prevent infinite loop by checking isFromClient()
        textField.addValueChangeListener(e -> {
            if (e.isFromClient() && e.getValue() != null) {
                colorPicker.setValue(e.getValue());
            }
        });

        colorPicker.addValueChangeListener(e -> {
            if (e.isFromClient()) {
                textField.setValue(e.getValue());
            }
        });

        textField.setWidthFull();

        horizontalLayout.add(colorPicker, textField);
        horizontalLayout.expand(textField);

        return horizontalLayout;
    }
}