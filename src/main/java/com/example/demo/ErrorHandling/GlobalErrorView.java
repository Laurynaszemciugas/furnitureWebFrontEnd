package com.example.demo.ErrorHandling;

import com.example.demo.Common.CommonComponents;
import com.example.demo.Exseptions.UnauthorizedException;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import jakarta.servlet.http.HttpServletResponse;

@Tag("div")
public class GlobalErrorView extends Div implements HasErrorParameter<Exception> {




    public GlobalErrorView() {
        setSizeFull();

        getStyle()
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "center")
                .set("background", "#f5f7fb");



    }

    @Override
    public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<Exception> parameter) {

        removeAll();

        if (hasCause(parameter.getException(), UnauthorizedException.class)) {
            add(createUnauthorizedPage());
            return HttpServletResponse.SC_UNAUTHORIZED;
        }

        add(createUnauthorizedPage());
        return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }

    private VerticalLayout createUnauthorizedPage() {
        VerticalLayout card = createCard();

        H1 code = new H1("401");
        code.getStyle()
                .set("margin", "0")
                .set("font-size", "70px")
                .set("color", "#2563eb");

        H2 title = new H2("Unauthorized");
        title.getStyle()
                .set("margin", "0")
                .set("color", "#111827");

        Paragraph text = new Paragraph("You need to login before accessing this page.");
        text.getStyle()
                .set("color", "#6b7280")
                .set("margin", "0");

        Button loginButton = new Button("Go to login", e -> {
            UI.getCurrent().navigate("Login");
        });

        loginButton.addThemeVariants(ButtonVariant.PRIMARY);



        card.add(code, title, text, loginButton);

        return card;
    }


    private VerticalLayout createCard() {
        VerticalLayout card = new VerticalLayout();

        card.setWidth("420px");
        card.setPadding(false);
        card.setSpacing(true);

        card.getStyle()
                .set("background", "white")
                .set("border-radius", "22px")
                .set("box-shadow", "0 10px 30px rgba(0,0,0,0.10)")
                .set("padding", "40px")
                .set("text-align", "center")
                .set("align-items", "center");

        return card;
    }

    private boolean hasCause(Throwable throwable, Class<?> wantedClass) {
        Throwable current = throwable;

        while (current != null) {
            if (wantedClass.isAssignableFrom(current.getClass())) {
                return true;
            }

            current = current.getCause();
        }

        return false;
    }
}