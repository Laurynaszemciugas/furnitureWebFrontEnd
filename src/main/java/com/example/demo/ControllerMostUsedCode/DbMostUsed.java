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

    public Span spanCrafterWordNoHide(String text, String addClassName){
        Span span = new Span(text);
        span.addClassName(addClassName);

        span.getStyle()
                .set("white-space", "normal")
                .set("word-break", "break-word")
                .set("display", "block")
                .set("max-width", "100%");

        return span;
    }


    public HorizontalLayout doubleValueRow(Component component1, Component component2){
        HorizontalLayout valueRow = new HorizontalLayout(component1, component2);
        valueRow.setAlignItems(FlexComponent.Alignment.BASELINE);
        valueRow.addClassName("stat-row");

        valueRow.setWidthFull();

        return valueRow;
    }

    public HorizontalLayout tripleValueRow(Component component1, Component component2, Component component3){
        HorizontalLayout valueRow = new HorizontalLayout(component1, component2, component3);
        valueRow.setAlignItems(FlexComponent.Alignment.BASELINE);
        valueRow.addClassName("stat-row");

        valueRow.setWidthFull();

        return valueRow;
    }


    public double diffrenceCalculator(double currentValue, double oldValue){

        double value = ((currentValue - oldValue) / oldValue) * 100;

        if(currentValue == 0.0 && oldValue == 0.0){
            value = 0.0;
        }


        return  Math.abs(value);
    }


    public void trendColoring(String moreThanZeroColor, String lessThanZeroColor, double changePercent, Span trend){

        if (changePercent > 0) {
            trend.getStyle().set("color", moreThanZeroColor);
        } else if (changePercent < 0) {
            trend.getStyle().set("color", lessThanZeroColor);
        } else {
            trend.getStyle().set("color", "gray");
        }

    }








}
