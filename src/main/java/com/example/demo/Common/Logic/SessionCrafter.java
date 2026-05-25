package com.example.demo.Common.Logic;

import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Service;

public class SessionCrafter {

    // create session
    public <T> void createSession(String sessionName, T value){
        VaadinSession.getCurrent().setAttribute(sessionName,value);
    }

    public <T> T extractSession(String sessionName, Class<T> tClass){
        return tClass.cast(
                VaadinSession.getCurrent().getAttribute(sessionName)
        );
    }

}
