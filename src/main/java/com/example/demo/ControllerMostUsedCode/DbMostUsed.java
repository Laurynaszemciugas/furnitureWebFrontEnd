package com.example.demo.ControllerMostUsedCode;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.springframework.stereotype.Service;

@Service
public class DbMostUsed {


    public Span spanCrafter(String text, String addClassName){
        Span span = new Span(text);
        span.addClassName(addClassName);
        span.getStyle()
                .set("white-space", "nowrap")
                .set("overflow", "hidden")
                .set("text-overflow", "ellipsis")
                .set("display", "block")
                .set("max-width", "100%");
        return span;
    }

    public HorizontalLayout doubleValueRow(Component component1, Component component2){
        HorizontalLayout valueRow = new HorizontalLayout(component1, component2);
        valueRow.setAlignItems(FlexComponent.Alignment.BASELINE);
        valueRow.addClassName("stat-row");

        valueRow.setWidthFull(); // 🔥 important

        return valueRow;
    }








}
