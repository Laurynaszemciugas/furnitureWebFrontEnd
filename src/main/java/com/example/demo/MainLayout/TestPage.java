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
        setAlignItems(Alignment.CENTER);



        add(mainLayout());

    }

    public VerticalLayout mainLayout(){
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.setHeightFull();
        verticalLayout.getStyle().set("margin-top","20px");

        verticalLayout.addClassName("island");

        return verticalLayout;
    }






}
