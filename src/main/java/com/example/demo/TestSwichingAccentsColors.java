package com.example.demo;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@CssImport("./COLOR.css")
@Route("TEST_THE_COLOR_SWITCHING")
public class TestSwichingAccentsColors extends VerticalLayout {

    public TestSwichingAccentsColors() {

        setSizeFull();
        addClassName("mainPage");



        Button button = new Button("Test button");
        button.addClassName("accentButton");

        // Red Switch
        Button button1 = new Button("Red");
        button1.getStyle().set("background-color", "red");
        button1.getStyle().set("color", "white");
        button1.addClickListener(e -> {
            System.out.println("red");
            // Pure Java: Set an attribute on the body tag
            UI.getCurrent().getElement().setAttribute("accent", "red");
        });

        // Yellow Switch
        Button button2 = new Button("Yellow");
        button2.getStyle().set("background-color", "yellow");
        button2.getStyle().set("color", "black");
        button2.addClickListener(e -> {
            System.out.println("yellow");
            // Pure Java: Set an attribute on the body tag
            UI.getCurrent().getElement().setAttribute("accent", "yellow");
        });

        // Blue Switch
        Button button3 = new Button("Blue");
        button3.getStyle().set("background-color", "blue");
        button3.getStyle().set("color", "white");
        button3.addClickListener(e -> {
            System.out.println("blue");
            // Pure Java: Set an attribute on the body tag
            UI.getCurrent().getElement().setAttribute("accent", "blue");
        });

        // Theme Switcher
        Button switchColor = new Button(VaadinIcon.MOON.create());
        switchColor.addClickListener(e -> {
            if ("dark".equals(UI.getCurrent().getElement().getAttribute("theme"))) {
                UI.getCurrent().getElement().setAttribute("theme", "light");
            } else {
                UI.getCurrent().getElement().setAttribute("theme", "dark");
            }
        });

        add(
                switchColor,
                button,
                button1,
                button2,
                button3,
                new Button(),
                new Button(),
                new Button(),
                new Button(),
                new Button(),
                new Button(),
                new Button(),
                new Button(),
                new Button(),
                new Button(),
                new Button(),
                new Button(),
                new Button(),
                new Button(),
                new Button(),
                new Button()
        );
    }
}