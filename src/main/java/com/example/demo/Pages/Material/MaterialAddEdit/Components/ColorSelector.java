package com.example.demo.Pages.Material.MaterialAddEdit.Components;

import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Setter;

import java.util.function.Consumer;

@Setter
public class ColorSelector {

    Consumer<Boolean> colorChanged;

    Input colorPicker = new Input();

        public TextField colorSelector(TextField textField) {

            textField.setTooltipText("Select color the format must be HEX");

        colorPicker.addClassName("color-button");
        colorPicker.setHeight("35px");
        colorPicker.setMaxWidth("50px");
        colorPicker.setType("color");
        colorPicker.setValue("#1e88e5");

        if (textField.getValue() == null || textField.getValue().isEmpty()) {
            textField.setValue("#1e88e5");
        }

        textField.addValueChangeListener(e -> {
            if (e.isFromClient() && e.getValue() != null) {
                colorPicker.setValue(e.getValue());
                colorChanged.accept(true);
            }
        });

        colorPicker.addValueChangeListener(e -> {
            if (e.isFromClient()) {
                textField.setValue(e.getValue());
                try {
                    colorChanged.accept(true);
                } catch (NullPointerException ex) {
                    System.out.println("color picker was used but not its consummer this is not serious");
                }
            }
        });

        textField.setWidthFull();

        textField.setPrefixComponent(colorPicker);



        return textField;
    }

    public void loadColor(String color){

        colorPicker.setValue(color);

    }


}