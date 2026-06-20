package com.example.demo.Pages.Login;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.CommonDtos.User;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Services.LoginService.LoginService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


@Route("Login")
public class LoginPage extends VerticalLayout {

    CommonComponents commonComponents;
    Common common;
    LoginService loginService;

    public LoginPage(CommonComponents commonComponents, Common common,LoginService loginService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.loginService = loginService;

        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setAlignItems(FlexComponent.Alignment.CENTER);

        add(login());
    }


    public VerticalLayout login(){
        VerticalLayout v = new VerticalLayout();

        TextField textField1 = new TextField("Name");
        textField1.setValue("maxx@gmail.com");

        TextField textField2 = new TextField("Password");
        textField2.setValue("maxx@gmail.com");

        Button button = new Button("Login");





        button.addClickListener(e->{
            User user = new User();
            user.setGmail(textField1.getValue());
            user.setPassword(textField2.getValue());
            System.out.println(user.getGmail());
            System.out.println(user.getPassword());
           loginService.getJWTToken(user);
        });

        v.add(textField1,textField2,button);

        return  v;
    }

}
