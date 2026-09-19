package com.example.demo.Common.Logic.PageStuff;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

@CssImport("./MainCSS.css")
public class DefaultPageExplanation {


    CommonComponents commonComponents;
    Common common;

    boolean firstLoad = true;


    public DefaultPageExplanation(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }

    public HorizontalLayout briefExplanation(String pageName){

        HorizontalLayout v = new HorizontalLayout();


        if(firstLoad){
            v.addClassName("smooth-panel");
            firstLoad = false;
        }
        else{
            v.removeClassName("smooth-panel");
        }
        v.setWidthFull();
        v.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);



        v.add(
                commonComponents.biefPageExplanation(pageName));


        return v;
    }

}
