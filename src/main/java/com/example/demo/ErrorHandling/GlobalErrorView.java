package com.example.demo.ErrorHandling;

import com.example.demo.ErrorHandling.Exseptions.HttpCallException;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import jakarta.servlet.http.HttpServletResponse;

import java.nio.channels.ClosedChannelException;

public class GlobalErrorView extends VerticalLayout
        implements HasErrorParameter<Exception> {

    public GlobalErrorView() {

        setSizeFull();

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
    }

    @Override
    public int setErrorParameter(
            BeforeEnterEvent event,
            ErrorParameter<Exception> parameter
    ) {

        removeAll();

        Throwable exception = parameter.getException();

        /*
         * ==========================================
         * HTTP CALL EXCEPTION
         * ==========================================
         */

        HttpCallException httpException =
                findCause(exception, HttpCallException.class);

        if (httpException != null) {

            Integer code = httpException.getError().getStatus();

            String message =
                    httpException.getError().getMessage();

            String instructions =
                    httpException.getError().getInstructions();

            add(createErrorPage(
                    code,
                    message,
                    instructions,
                    "Go to login"
            ));

            return code;
        }

        /*
         * ==========================================
         * BACKEND / CONNECTION ERROR
         * ==========================================
         */

        ClosedChannelException closedChannel =
                findCause(exception, ClosedChannelException.class);

        if (closedChannel != null) {

            add(createErrorPage(
                    HttpServletResponse.SC_BAD_GATEWAY,
                    "Server isn't responding",
                    "The server is currently unavailable. Please try again later.",
                    "Try again"
            ));

            return HttpServletResponse.SC_BAD_GATEWAY;
        }

        /*
         * ==========================================
         * DEFAULT / UNKNOWN ERROR
         * ==========================================
         */

        add(createErrorPage(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Something went wrong",
                "An unexpected error occurred. Please try again later.",
                "Go back"
        ));

        return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }


    /*
     * ==========================================
     * FIND EXCEPTION IN CAUSE CHAIN
     * ==========================================
     */

    private <T extends Throwable> T findCause(
            Throwable throwable,
            Class<T> wantedClass
    ) {

        while (throwable != null) {

            if (wantedClass.isAssignableFrom(
                    throwable.getClass()
            )) {

                return wantedClass.cast(throwable);
            }

            throwable = throwable.getCause();
        }

        return null;
    }


    /*
     * ==========================================
     * CREATE ERROR PAGE
     * ==========================================
     */

    private VerticalLayout createErrorPage(
            Integer errCode,
            String message,
            String instructions,
            String buttonText
    ) {

        VerticalLayout card = createCard();

        /*
         * ERROR CODE
         */

        H1 code = new H1(
                String.valueOf(errCode)
        );

        code.getStyle()
                .set("margin", "0")
                .set("font-size", "70px")
                .set("color", "#2563eb");


        /*
         * ERROR TITLE
         */

        H2 title = new H2(message);

        title.getStyle()
                .set("margin", "0")
                .set("color", "#111827");


        /*
         * ERROR DESCRIPTION
         */

        Paragraph text = new Paragraph(
                instructions
        );

        text.getStyle()
                .set("color", "#6b7280")
                .set("margin", "0");


        /*
         * BUTTON
         */

        Button actionButton = new Button(
                buttonText
        );

        actionButton.addThemeVariants(
                ButtonVariant.PRIMARY
        );


        /*
         * BUTTON ACTION
         */

        if ("Go to login".equals(buttonText)) {

            actionButton.addClickListener(e ->
                    UI.getCurrent().navigate("Login")
            );

        } else if ("Try again".equals(buttonText)) {

            actionButton.addClickListener(e ->
                    UI.getCurrent().getPage().reload()
            );

        } else {

            actionButton.addClickListener(e ->
                    UI.getCurrent()
                            .getPage()
                            .getHistory()
                            .back()
            );
        }


        /*
         * ADD EVERYTHING
         */

        card.add(
                code,
                title,
                text,
                actionButton
        );

        return card;
    }


    /*
     * ==========================================
     * ERROR CARD
     * ==========================================
     */

    private VerticalLayout createCard() {

        VerticalLayout card =
                new VerticalLayout();

        card.setWidth("420px");

        card.setPadding(false);

        card.setSpacing(true);

        card.getStyle()
                .set("background", "white")
                .set("border-radius", "22px")
                .set(
                        "box-shadow",
                        "0 10px 30px rgba(0,0,0,0.10)"
                )
                .set("padding", "40px")
                .set("text-align", "center")
                .set("align-items", "center");

        return card;
    }
}


// old version

//package com.example.demo.ErrorHandling;
//
//import com.example.demo.Common.Logic.SessionCrafter;
//import com.example.demo.ErrorHandling.Exseptions.HttpCallException;
//import com.vaadin.flow.component.UI;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.button.ButtonVariant;
//import com.vaadin.flow.component.html.H1;
//import com.vaadin.flow.component.html.H2;
//import com.vaadin.flow.component.html.Paragraph;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.router.BeforeEnterEvent;
//import com.vaadin.flow.router.ErrorParameter;
//import com.vaadin.flow.router.HasErrorParameter;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.nio.channels.ClosedChannelException;
//
//public class GlobalErrorView extends VerticalLayout implements HasErrorParameter<Exception> {
//
//    SessionCrafter sessionCrafter;
//
//
//    public GlobalErrorView() {
//
//        setSizeFull();
//
//        setJustifyContentMode(JustifyContentMode.CENTER);
//        setAlignItems(Alignment.CENTER);
//
//
//
//
//
//    }
//
//    @Override
//    public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<Exception> parameter) {
//
//
//
//
//        removeAll();
//
//        HttpCallException ex = getHttpCallException(parameter.getException());
//
//        if(ex != null){
//            Integer code = ex.getError().getStatus();
//            String message = ex.getError().getMessage();
//            String instuctions = ex.getError().getInstructions();
//
//            add(createUnauthorizedPage(code,message,instuctions));
//        }
//
//
//        ClosedChannelException closedChannel = findCause(parameter.getException(), ClosedChannelException.class);
//        if(closedChannel != null){
//            add(createUnauthorizedPage(502,"Server isn't responding","Please try again later"));
//        }
//
//
//
//
//
//
//        return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
//    }
//
//    // find pre set exseption default class btw
//    private <T extends Throwable> T findCause(Throwable throwable, Class<T> wantedClass) {
//        while (throwable != null) {
//            if (wantedClass.isAssignableFrom(throwable.getClass())) {
//                return wantedClass.cast(throwable);
//            }
//
//            throwable = throwable.getCause();
//        }
//
//        return null;
//    }
//
//
//    // find specific created exseption this is working example also basic default method example btw
//    private HttpCallException getHttpCallException(Throwable throwable) {
//        while (throwable != null) {
//            if (throwable instanceof HttpCallException ex) {
//                return ex;
//            }
//
//
//            throwable = throwable.getCause();
//        }
//
//        return null;
//    }
//
//
//
//
//
//
//    // ui creation
//    private VerticalLayout createUnauthorizedPage(Integer errCode, String message, String instructions) {
//        VerticalLayout card = createCard();
//
//        H1 code = new H1(errCode.toString());
//        code.getStyle()
//                .set("margin", "0")
//                .set("font-size", "70px")
//                .set("color", "#2563eb");
//
//        H2 title = new H2(message);
//        title.getStyle()
//                .set("margin", "0")
//                .set("color", "#111827");
//
//        Paragraph text = new Paragraph(instructions);
//        text.getStyle()
//                .set("color", "#6b7280")
//                .set("margin", "0");
//
//
//
//
//
//        Button loginButton = new Button("Go to login", e -> {
//            UI.getCurrent().navigate("Login");
//        });
//
//        loginButton.addThemeVariants(ButtonVariant.PRIMARY);
//
//
//
//        card.add(code, title, text, loginButton);
//
//        return card;
//    }
//
//
//    private VerticalLayout createCard() {
//        VerticalLayout card = new VerticalLayout();
//
//        card.setWidth("420px");
//        card.setPadding(false);
//        card.setSpacing(true);
//
//        card.getStyle()
//                .set("background", "white")
//                .set("border-radius", "22px")
//                .set("box-shadow", "0 10px 30px rgba(0,0,0,0.10)")
//                .set("padding", "40px")
//                .set("text-align", "center")
//                .set("align-items", "center");
//
//        return card;
//    }
//
//
//}
//
