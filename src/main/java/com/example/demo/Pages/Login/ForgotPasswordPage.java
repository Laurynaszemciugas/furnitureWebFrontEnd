package com.example.demo.Pages.Login;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.DTOS.Auth.PasswordResetWithCode;
import com.example.demo.Services.LoginService.LoginService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.Route;

@Route("ForgotPassword")
public class ForgotPasswordPage extends VerticalLayout {


    CommonComponents commonComponents;
    Common common;

    LoginService loginService;

    VerticalLayout mainLayout = new VerticalLayout();

    public ForgotPasswordPage(CommonComponents commonComponents, Common common, LoginService loginService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.loginService = loginService;



        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        getStyle()
                .set("background-image", "url('Image.jpg')")
                .set("background-size", "cover")
                .set("background-position", "center")
                .set("background-repeat", "no-repeat");


        mainLayout.setAlignItems(Alignment.CENTER);

        add(
                mainLayout
        );

        mainLayout.add(
                forgotPasswordChooseType()
        );

    }


    public VerticalLayout forgotPasswordChooseType(){

        VerticalLayout v = new VerticalLayout();
        v.addClassName("layout-flex");
        v.setAlignItems(Alignment.CENTER);
        v.getStyle().setOpacity("97%");
        v.addClassName("island");
        v.setWidth("650px");

        Button backToLogin = new Button("Back to sign in",e-> UI.getCurrent().navigate(LoginPage.class));

        HorizontalLayout gmailVerification = new HorizontalLayout();
        gmailVerification.setAlignItems(Alignment.CENTER);
        gmailVerification.setJustifyContentMode(JustifyContentMode.CENTER);
        gmailVerification.addClassName("island-hover");
        gmailVerification.getStyle().set("flex", "1 1 252px");
        gmailVerification.getStyle().set("min-width", "252px");
        gmailVerification.add(
                commonComponents. itemInsideTheBox(VaadinIcon.MAILBOX,"Blue","rgba(59, 130, 246, 0.18)"),
                commonComponents.spanCrafterWordNoHide("Gmail verification","activityFeed-name")
        );

        gmailVerification.addClickListener(e->{
           mainLayout.removeAll();
           mainLayout.add(
                   resetViaGmail()
           );
        });

        HorizontalLayout recoveryPin = new HorizontalLayout();
        recoveryPin.setAlignItems(Alignment.CENTER);
        recoveryPin.setJustifyContentMode(JustifyContentMode.CENTER);
        recoveryPin.addClassName("island-hover");
        recoveryPin.getStyle().set("flex", "1 1 252px");
        recoveryPin.getStyle().set("min-width", "252px");

        recoveryPin.add(
                commonComponents.itemInsideTheBox(VaadinIcon.CODE,"Blue","rgba(59, 130, 246, 0.18)"),
                commonComponents.spanCrafterWordNoHide("Recovery pin","activityFeed-name")
        );

        recoveryPin.addClickListener(e->{
           mainLayout.removeAll();
           mainLayout.add(
                   resetViaRecoveryCode()
           );
        });

        HorizontalLayout optionHolder = new HorizontalLayout();
        optionHolder.addClassName("layout-flex");
        optionHolder.setWidthFull();
        optionHolder.add(
                gmailVerification,
                recoveryPin
        );


        v.add(
                commonComponents.spanCrafterWordNoHide("Recovery method","activityFeed-name"),
                optionHolder,
                backToLogin
        );


       return v;
    }


    public VerticalLayout resetViaGmail(){

        VerticalLayout v = new VerticalLayout();
        v.setAlignItems(Alignment.CENTER);
        v.getStyle().setOpacity("97%");
        v.addClassName("island");
        v.setWidth("400px");

        Button getCode = new Button("Get code");
        getCode.addThemeVariants(ButtonVariant.PRIMARY);

        TextField gmailField = new TextField("Enter your gmail");
        gmailField.setWidthFull();



        HorizontalLayout layout = new HorizontalLayout(gmailField, getCode);
        layout.getStyle().setGap("10px");
        layout.setWidthFull();
        layout.setAlignItems(FlexComponent.Alignment.END);
        layout.setJustifyContentMode(JustifyContentMode.CENTER);
        layout.setWidthFull();





        TextField newPassword = new TextField("New password");
        newPassword.setWidthFull();

        TextField reEnterPassword = new TextField("Re enter password");
        reEnterPassword.setWidthFull();

        IntegerField code = new IntegerField("Received code");
        code.setWidthFull();


        Button changePassword = new Button("Change password");
        changePassword.addThemeVariants(ButtonVariant.PRIMARY);

        changePassword.addClickListener(e->{
            PasswordResetWithCode passwordResetWithCode = new PasswordResetWithCode();
            passwordResetWithCode.setPassword(newPassword.getValue());
            passwordResetWithCode.setReEnterPassword(reEnterPassword.getValue());
            passwordResetWithCode.setCode(code.getValue().toString());
            passwordResetWithCode.setGmail(gmailField.getValue());
            loginService.resetPasswordViaGmail(passwordResetWithCode);
        });



        VerticalLayout vv = new VerticalLayout();
        vv.setAlignItems(Alignment.CENTER);
        vv.setVisible(false);
        vv.setPadding(false);
        vv.add(
                commonComponents.spanCrafterWordNoHide("Change password","activityFeed-name"),
                newPassword,
                reEnterPassword,
                code,
                changePassword
        );



        getCode.addClickListener(e->{



            if(!gmailField.getValue().isEmpty()) {

                vv.setVisible(true);
                getCode.setText("Re-get code");
                loginService.createPasswordResetGmailVerificationCode(gmailField.getValue());


            }
            else{
                commonComponents.showNotification("Gmail field is empty",3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.ERROR);
                gmailField.setErrorMessage("Gmail field cannot be empty");

            }
        });

         Button goBack = new Button("Back to sign in", e-> UI.getCurrent().navigate(LoginPage.class));


        v.add(
                commonComponents.spanCrafterWordNoHide("Receive code","activityFeed-name"),
                layout,
                vv,
                goBack
        );


        return v;
    }



    public VerticalLayout resetViaRecoveryCode(){

        VerticalLayout v = new VerticalLayout();
        v.setAlignItems(Alignment.CENTER);
        v.getStyle().setOpacity("97%");
        v.addClassName("island");
        v.setWidth("400px");


        TextField gmailField = new TextField("Enter your gmail");
        gmailField.setWidthFull();


        PasswordField newPassword = new PasswordField("New password");
        newPassword.setWidthFull();

        PasswordField reEnterPassword = new PasswordField("Re enter password");
        reEnterPassword.setWidthFull();

        PasswordField code = new PasswordField("Recovery code");
        code.setWidthFull();


        Button changePassword = new Button("Change password");
        changePassword.addThemeVariants(ButtonVariant.PRIMARY);

        changePassword.addClickListener(e->{

            if(newPassword.isEmpty() || reEnterPassword.isEmpty() || code.isEmpty() || gmailField.isEmpty()){

            PasswordResetWithCode passwordResetWithCode = new PasswordResetWithCode();
            passwordResetWithCode.setPassword(newPassword.getValue());
            passwordResetWithCode.setReEnterPassword(reEnterPassword.getValue());
            passwordResetWithCode.setCode(code.getValue().toString());
            passwordResetWithCode.setGmail(gmailField.getValue());
            loginService.resetPasswordViaRecoveryCode(passwordResetWithCode);
        });



        VerticalLayout vv = new VerticalLayout();
        vv.setAlignItems(Alignment.CENTER);
        vv.setPadding(false);
        vv.add(
                commonComponents.spanCrafterWordNoHide("Change password","activityFeed-name"),
                gmailField,
                newPassword,
                reEnterPassword,
                code,
                changePassword
        );





        Button goBack = new Button("Back to sign in", e-> UI.getCurrent().navigate(LoginPage.class));



        v.add(
                vv,
                goBack
        );


        return v;
    }



}
