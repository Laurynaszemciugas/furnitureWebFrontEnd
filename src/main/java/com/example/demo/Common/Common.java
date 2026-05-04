package com.example.demo.Common;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import org.springframework.stereotype.Service;

@Service
public class Common {

    public Icon iconCrafter(VaadinIcon chosenIcon, String size, String color){

        Icon icon = chosenIcon.create();
        icon.setSize(size);
        icon.setColor(color);

        return icon;

    }

}
