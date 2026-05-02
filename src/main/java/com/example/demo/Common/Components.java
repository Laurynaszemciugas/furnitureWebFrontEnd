package com.example.demo.Common;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import org.springframework.stereotype.Service;


@Service
public class Components {

    public Icon iconCrafter(VaadinIcon chosenIcon, String size, String color){

        Icon icon = chosenIcon.create();
        icon.setSize(size);
        icon.setColor(color);

        return icon;

    }


    public Button mainLayoutButtons(String navigate, String text, VaadinIcon icon){
        Button button = new Button(text);
        button.addClassName("clean-btn");
        button.setIcon(iconCrafter(icon,"30px","white"));



        return button;

    }

    public Button mainLayoutButtonsSmall(String navigate, VaadinIcon icon){
        Button button = new Button();
        button.addClassName("clean-btn");
        button.setIcon(iconCrafter(icon,"40px","white"));



        return button;

    }


}
