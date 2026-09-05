package com.example.demo.Pages.Login;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.ControllerModels.CommonDtos.User;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Services.LoginService.LoginService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.dependency.JsModule;


@Route("Login")
@JsModule("./google-signin.js")
public class LoginPage extends VerticalLayout {

    CommonComponents commonComponents;
    Common common;
    LoginService loginService;

    SessionCrafter sessionCrafter;

    public LoginPage(CommonComponents commonComponents, Common common,LoginService loginService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.loginService = loginService;

        this.sessionCrafter = new SessionCrafter();

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
            try {
                loginService.getJWTToken(user);
                loginService.createSettings();
            } catch (Exception ex) {
                System.out.println("something went wrong");
                //throw new RuntimeException(ex);
            }

        });


        Div googleButton = new Div();
        googleButton.setId("googleButton");

        googleButton.getElement()
                .addEventListener("google-login", event -> {

                    String googleToken = event
                            .getEventData()
                            .get("event.detail")
                            .asText();;

                    System.out.println("Google token received:");
                    System.out.println(googleToken);


                    try {
                        loginService.googleLogin(googleToken);

                        if(!sessionCrafter.extractSession("JWT",String.class).isEmpty()) {
                            loginService.createSettings();
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                })
                .addEventData("event.detail");

        getElement().executeJs(
                "window.initGoogleButton($0, $1)",
                googleButton.getElement(),
                "216793106747-g66oc54mlg3mh38pehhp5d40606b5p8e.apps.googleusercontent.com"
        );

        v.add(textField1, textField2, button, googleButton);

        v.add(textField1,textField2,button,googleButton, new Button("zaza"));

        return  v;
    }

}
