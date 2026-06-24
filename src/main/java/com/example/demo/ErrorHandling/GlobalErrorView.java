package com.example.demo.ErrorHandling;

import com.example.demo.Exseptions.UnauthorizedException;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.server.VaadinSession;
import jakarta.servlet.http.HttpServletResponse;

@Tag("div")
public class GlobalErrorView extends VerticalLayout implements HasErrorParameter<Exception> {


    public GlobalErrorView() {
        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);

        add(new Span("23131"));

    }

    @Override
    public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<Exception> parameter) {

        if (hasCause(parameter.getException(), UnauthorizedException.class)) {



            //event.getUI().getPage().setLocation("/Login");

            add(new H2("Redirecting to login..."));

            return HttpServletResponse.SC_UNAUTHORIZED;
        }





        return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
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