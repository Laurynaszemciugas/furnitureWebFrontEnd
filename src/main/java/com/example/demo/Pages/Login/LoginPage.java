package com.example.demo.Pages.Login;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.Entity.User;
import com.example.demo.Services.LoginService.LoginService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
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

    String googleClientId = System.getenv("GOOGLE_LOG_IN_ID");

    public LoginPage(CommonComponents commonComponents, Common common,LoginService loginService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.loginService = loginService;

        this.sessionCrafter = new SessionCrafter();

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

        add(login());
    }




    public VerticalLayout login(){


        VerticalLayout v = new VerticalLayout();
        v.setJustifyContentMode(JustifyContentMode.CENTER);
        v.setAlignItems(Alignment.CENTER);
        v.getStyle().setOpacity("97%");
        v.setWidth("400px");
        v.getStyle().setGap("10px");

        v.addClassName("island");

        TextField gmailField = new TextField("Name");
        gmailField.setWidthFull();
        gmailField.setValue("John@gmail.com");

        PasswordField passwordField = new PasswordField("Password");
        passwordField.setWidthFull();
        passwordField.setValue("John@gmail.com");

        Button signIn = new Button("Sign in");
        signIn.addThemeVariants(ButtonVariant.PRIMARY);

        Button dontHaveAnAccount = new Button("Sign up", e-> UI.getCurrent().navigate(RegisterPage.class));
        dontHaveAnAccount.addClassName("color-button");

        Button forgotPassword = new Button("Forgot password", e-> UI.getCurrent().navigate(ForgotPasswordPage.class));
        forgotPassword.addClassName("color-button");

        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.setJustifyContentMode(JustifyContentMode.BETWEEN);


        h.add(
                forgotPassword,
                dontHaveAnAccount

        );





        signIn.addClickListener(e->{
            User user = new User();
            user.setGmail(gmailField.getValue());
            user.setPassword(passwordField.getValue());
            try {
                loginService.getJWTToken(user);
                loginService.createSettings();
            } catch (Exception ex) {
                System.out.println("something went wrong");
                System.out.println(ex);
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
                googleClientId
        );

        Image logo = commonComponents.imageCrafter("No_picture.png","80px","80px","5px");


        v.add(
                logo,
                commonComponents.spanCrafterWordNoHide("Sign in","activityFeed-name"),
                gmailField,
                passwordField,
                signIn,
                googleButton,
                h
        );


        return  v;
    }

}
