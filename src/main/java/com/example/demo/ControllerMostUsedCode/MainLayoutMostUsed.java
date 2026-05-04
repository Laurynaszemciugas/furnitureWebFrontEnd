package com.example.demo.ControllerMostUsedCode;

import com.example.demo.Common.Common;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import org.springframework.stereotype.Service;


@Service
public class MainLayoutMostUsed {

    Common common;

    public MainLayoutMostUsed(Common common) {
        this.common = common;
    }

    public Button mainLayoutButtons(String navigate, String text, VaadinIcon icon){
        Button button = new Button(text);
        button.addClassName("clean-btn");
        button.setIcon(common.iconCrafter(icon,"30px","white"));



        return button;

    }

    public Button mainLayoutButtonsSmall(String navigate, VaadinIcon icon){
        Button button = new Button();
        button.addClassName("clean-btn");
        button.setIcon(common.iconCrafter(icon,"40px","white"));
        return button;

    }


}
