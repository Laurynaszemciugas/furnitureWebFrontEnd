package com.example.demo.ControllerMostUsedCode;

import com.vaadin.flow.component.html.Span;
import org.springframework.stereotype.Service;

@Service
public class DbMostUsed {


    public Span spanCrafter(String text, String addClassName){
        Span span = new Span(text);
        span.addClassName(addClassName);

        return  span;
    }


}
