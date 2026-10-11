package com.example.demo.Pages.Login;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.DTOS.Auth.PasswordResetWithCode;
import com.example.demo.Entity.User;
import com.example.demo.Services.LoginService.LoginService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("Register")
public class RegisterPage extends VerticalLayout {


    CommonComponents commonComponents;
    Common common;

    SessionCrafter sessionCrafter;

    LoginService loginService;

    String googleClientId = System.getenv("GOOGLE_LOG_IN_ID");

    public RegisterPage(CommonComponents commonComponents, Common common,LoginService loginService) {
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




        add(
                createAccount()
        );



    }



    public VerticalLayout createAccount(){

        VerticalLayout v = new VerticalLayout();
        v.setAlignItems(Alignment.CENTER);
        v.getStyle().setOpacity("97%");
        v.addClassName("island");
        v.setWidth("400px");


        TextField nameField = new TextField("Enter your name");
        nameField.setWidthFull();

        TextField lastNameField = new TextField("Enter your lastname");
        lastNameField.setWidthFull();

        TextField gmailField = new TextField("Enter your gmail");
        gmailField.setWidthFull();


        PasswordField newPassword = new PasswordField("Password");
        newPassword.setWidthFull();

        PasswordField reEnterPassword = new PasswordField("Re-enter password");
        reEnterPassword.setWidthFull();




        Button changePassword = new Button("Sign up");
        changePassword.addThemeVariants(ButtonVariant.PRIMARY);



        changePassword.addClickListener(e->{

            if(nameField.isEmpty() || lastNameField.isEmpty() || gmailField.isEmpty() || newPassword.isEmpty() || reEnterPassword.isEmpty()){
                commonComponents.showNotification("Fields cannot be empty",3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.ERROR);
            }
            else if(!newPassword.getValue().equals(reEnterPassword.getValue())){
                commonComponents.showNotification("Passwords do not match",3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.ERROR);
            }
            else{

                User user = new User();

                user.setGmail(gmailField.getValue());
                user.setPassword(newPassword.getValue());
                user.setBio(reEnterPassword.getValue());
                user.setName(nameField.getValue());
                user.setLastName(lastNameField.getValue());

                loginService.register(user);

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



        VerticalLayout vv = new VerticalLayout();
        vv.setAlignItems(Alignment.CENTER);
        vv.setPadding(false);
        vv.add(
                commonComponents.spanCrafterWordNoHide("Sign up","activityFeed-name"),
                nameField,
                lastNameField,
                gmailField,
                newPassword,
                reEnterPassword,
                changePassword
        );





        Button goBack = new Button("Back to sign in", e-> UI.getCurrent().navigate(LoginPage.class));


        HorizontalLayout continueWith = new HorizontalLayout();
        continueWith.setAlignItems(Alignment.START);
        continueWith.setJustifyContentMode(JustifyContentMode.CENTER);
        continueWith.setWidthFull();
        continueWith.add(
                commonComponents.lineCrafter("25%"),
                commonComponents.spanCrafterWordNoHide("or continue with","stat-description"),
                commonComponents.lineCrafter("25%")
        );

        v.add(
                vv,
                continueWith,
                googleButton,
                goBack
        );


        return v;
    }










}
