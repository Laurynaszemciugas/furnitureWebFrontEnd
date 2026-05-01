package com.example.demo.MainLayout;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "test", layout = MainLayout.class)
public class TestPage extends VerticalLayout {


    public TestPage(){

        setPadding(false); // Removes the outer gap
        setSpacing(false); // Removes gaps between components inside this layout
        setSizeFull();



        Button button = new Button("1111111111111111");
        button.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        VerticalLayout verticalLayout = new VerticalLayout(button);
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("background-color","green");

        add(verticalLayout);

    }






}
