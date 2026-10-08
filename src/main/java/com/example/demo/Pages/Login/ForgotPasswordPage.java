package com.example.demo.Pages.Login;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("ForgotPassword")
public class ForgotPasswordPage extends VerticalLayout {


    CommonComponents commonComponents;
    Common common;

    public ForgotPasswordPage(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;



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



        add(
                forgotPasswordChooseType()
        );

    }


    public VerticalLayout forgotPasswordChooseType(){

        VerticalLayout v = new VerticalLayout();
        v.setAlignItems(Alignment.CENTER);
        v.getStyle().setOpacity("97%");
        v.addClassName("island");
        v.setWidth("650px");

        Button backToLogin = new Button("Back to sign in",e-> UI.getCurrent().navigate(LoginPage.class));
        backToLogin.addThemeVariants(ButtonVariant.PRIMARY);

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

        HorizontalLayout optionHolder = new HorizontalLayout();
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



}
