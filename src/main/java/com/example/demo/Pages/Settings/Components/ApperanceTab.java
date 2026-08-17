package com.example.demo.Pages.Settings.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;
import java.util.List;

public class ApperanceTab {

    CommonComponents commonComponents;
    Common common;


    BriefExplanationOfSettings briefExplanationOfTheSettings;


    List<Image> imageMemory = new ArrayList<>();


    public ApperanceTab(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;

        this.briefExplanationOfTheSettings = new BriefExplanationOfSettings(commonComponents,common);

    }

    public VerticalLayout allInOne(){

        VerticalLayout v = new VerticalLayout();

        v.add(
                profileAccount(),
                accentColor()
        );

        return v;

    }



    public VerticalLayout profileAccount(){




        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");
        v.setWidthFull();

        HorizontalLayout h = new HorizontalLayout();
        h.setAlignItems(FlexComponent.Alignment.CENTER);
        h.setWidthFull();
        h.add(
                themeCrafter("Light","Clean and bright","Light"),
                themeCrafter("Dark","Easy on the eyes","Light"),
                themeCrafter("System","Match system settings","Light"   )
        );
        h.addClassName("layout-flex");


        v.add(
                briefExplanationOfTheSettings.briefExplanationOfTheSettings("Theme","Choose between light and dark appearance"),
                h
        );





        return v;
    }

    public VerticalLayout themeCrafter(String name, String desc, String selectedName){

        VerticalLayout v = new VerticalLayout();
        v.setAlignItems(FlexComponent.Alignment.CENTER);
        v.setSpacing(false);


        v.getStyle().set("flex", "1 1 252px");
        v.getStyle().set("max-width", "620px");
        v.getStyle().set("min-width", "252px");


        Image image = new Image("Screenshot 2026-04-27 001745.png", "image error");
        image.setHeight("200px");
        image.setWidthFull();
        image.getStyle().set("border-radius","10px");



        if(name.equals(selectedName)){
            image.addClassName("island-layout-solid");
        }

        imageMemory.add(image);

        image.addClickListener(e->{

            for(var s : imageMemory){
                s.removeClassName("island-layout-solid");
            }

            for(var s : imageMemory){
                if(s.equals(image)){
                    s.addClassName("island-layout-solid");
                }
            }
        });



        v.add(
                image,
                commonComponents.spanCrafter(name,"activityFeed-name"),
                commonComponents.spanCrafter(desc,"stat-description")
        );


        return v;
    }


    public VerticalLayout accentColor(){




        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");
        v.setWidthFull();

        HorizontalLayout h = new HorizontalLayout();
        h.setAlignItems(FlexComponent.Alignment.CENTER);
        h.setWidthFull();
        h.add(
        colorCrafter("red")
        );
        h.addClassName("layout-flex");


        v.add(
                briefExplanationOfTheSettings.briefExplanationOfTheSettings("Accent color","Choose your favorite accent color"),
                h
        );





        return v;
    }


    public VerticalLayout colorCrafter(String color){
        VerticalLayout v = new VerticalLayout();

        v.setWidth("50px");
        v.setHeight("50px");


        v.getStyle().set("border-radius","100px").set("background-color",color);



        return v;


    }




}
