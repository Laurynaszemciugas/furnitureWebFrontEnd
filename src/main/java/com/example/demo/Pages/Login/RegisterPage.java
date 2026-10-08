package com.example.demo.Pages.Login;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("Register")
public class RegisterPage extends VerticalLayout {


    CommonComponents commonComponents;
    Common common;

    public RegisterPage(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;



        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        getStyle()
                .set("background-image", "url('Image.jpg')")
                .set("background-size", "cover")
                .set("background-position", "center")
                .set("background-repeat", "no-repeat");




    }
}
