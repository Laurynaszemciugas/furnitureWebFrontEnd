package com.example.demo.Pages.Settings.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.ControllerModels.CommonDtos.UserSettings;
import com.example.demo.ControllerModels.User.Appearance;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Services.UserService.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ApperanceTab {

    CommonComponents commonComponents;
    Common common;

    SessionCrafter sessionCrafter;


    BriefExplanationOfSettings briefExplanationOfTheSettings;


    List<Image> imageMemory = new ArrayList<>();

    List<Icon> colorMemory = new ArrayList<>();

    List<Image> sideBarMemory = new ArrayList<>();

    UserService userService;


    public ApperanceTab(CommonComponents commonComponents, Common common,UserService userService) {
        this.commonComponents = commonComponents;
        this.common = common;

        this.userService = userService;

        this.sessionCrafter = new SessionCrafter();

        this.briefExplanationOfTheSettings = new BriefExplanationOfSettings(commonComponents,common);




    }

    public VerticalLayout allInOne(){

        VerticalLayout v = new VerticalLayout();

        Appearance appearance = userService.getAppearance();

        v.add(
                profileAccount(appearance.getTheme()),
                accentColor(appearance.getAccent()),
                sideBarSize(appearance.getSidebarSize())
        );

        return v;

    }



    public VerticalLayout profileAccount(String value){




        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");
        v.setWidthFull();

        HorizontalLayout h = new HorizontalLayout();
        h.setAlignItems(FlexComponent.Alignment.CENTER);
        h.setWidthFull();
        h.add(
                themeCrafter("Light","Clean and bright",value,"LightTheme.png"),
                themeCrafter("Dark","Easy on the eyes",value,"DarkTheme.png"),
                themeCrafter("System","Match system settings",value,"SystemTheme.png"   )
        );
        h.addClassName("layout-flex");


        v.add(
                briefExplanationOfTheSettings.briefExplanationOfTheSettings("Theme","Choose between light and dark appearance"),
                h
        );





        return v;
    }

    public VerticalLayout themeCrafter(String name, String desc, String selectedName, String img){

        VerticalLayout v = new VerticalLayout();
        v.setAlignItems(FlexComponent.Alignment.CENTER);
        v.setSpacing(false);


        v.getStyle().set("flex", "1 1 252px");
        v.getStyle().set("max-width", "620px");
        v.getStyle().set("min-width", "252px");



        Image image = new Image(img, "image error");
        image.setHeight("200px");
        image.setWidthFull();
        image.getStyle().set("border-radius","10px");

       image.add("island");



        if(name.equals(selectedName)){
            image.addClassName("island-layout-solid");
        }

        imageMemory.add(image);

        image.addClickListener(e->{

            UserSettings userSettings = sessionCrafter.extractSession("settings", UserSettings.class);
            userSettings.setTheme(name);
            sessionCrafter.createSession("settings", userSettings);


            for(var s : imageMemory){
                s.removeClassName("island-layout-solid");
            }

            for(var s : imageMemory){
                if(s.equals(image)){
                    s.addClassName("island-layout-solid");
                }
            }
            userService.saveTheme(name);
        });



        v.add(
                image,
                commonComponents.spanCrafter(name,"activityFeed-name"),
                commonComponents.spanCrafter(desc,"stat-description")
        );


        return v;
    }


    public VerticalLayout accentColor(String value){




        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");
        v.setWidthFull();

        HorizontalLayout h = new HorizontalLayout();
        h.setAlignItems(FlexComponent.Alignment.CENTER);
        h.setJustifyContentMode(FlexComponent.JustifyContentMode.EVENLY);
        h.setWidthFull();
        h.add(
                colorCrafter("Blue",value),
                colorCrafter("Red",value),
                colorCrafter("Orange",value),
                colorCrafter("Purple",value),
                colorCrafter("Green",value),
                colorCrafter("Grey",value),
                colorCrafter("Pink",value),
                colorCrafter("Indigo",value)
        );
        h.addClassName("layout-flex");


        v.add(
                briefExplanationOfTheSettings.briefExplanationOfTheSettings("Accent color","Choose your favorite accent color"),
                h,
                briefExplanationOfTheSettings.briefExplanationOfTheSettings("Accent preview ","See how accent will effect application EXCEPT CUSTOM REPORTS"),
                exampleOfAccents()
        );





        return v;
    }


    public VerticalLayout colorCrafter(String color, String currentColor){
        VerticalLayout v = new VerticalLayout();
        v.setAlignItems(FlexComponent.Alignment.CENTER);
        v.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        v.setWidth("60px");
        v.setHeight("60px");


        v.getStyle().set("border-radius","100px").set("background-color",color).set("position","relative");

        Icon icon = commonComponents.iconCrafter(VaadinIcon.CHECK,"20px","White");
        icon.getStyle().set("position","absolute");
        icon.setVisible(false);

        v.add(
                icon
        );

        if(color.equals(currentColor)){
            icon.setVisible(true);
        }

        v.addClickListener(e->{

             UserSettings userSettings = sessionCrafter.extractSession("settings", UserSettings.class);
             userSettings.setAccent(color);
            sessionCrafter.createSession("settings", userSettings);

            userService.saveAccent(color);

            UI.getCurrent().getElement().setAttribute("accent", color.toLowerCase());


            for(var s : colorMemory ){
                s.setVisible(false);
            }

            for(var s : colorMemory){
                if(s.equals(icon)){
                    icon.setVisible(true);
                }
            }
        });

        colorMemory.add(icon);

        return v;


    }


    public HorizontalLayout exampleOfAccents(){
        HorizontalLayout h = new HorizontalLayout();

        h.addClassName("layout-flex");
        h.setAlignItems(FlexComponent.Alignment.BASELINE);

        h.setWidthFull();


        Button button = new Button("Preview button");
        button.addClassName("accentButtons");

        TextField textField = new TextField("Preview text field");

        Button anotherButton = new Button("Button with bottom line");
        anotherButton.addClassName("color-button");


        h.add(
                button,
                textField,
                anotherButton
        );




        return h;
    }




    public VerticalLayout sideBarSize(String value){

        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");


        HorizontalLayout h = new HorizontalLayout();
        h.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        h.setWidthFull();

        h.add(
                sideBarButtonCrafter("Large","Expanded view more space and details",value,"LightTheme.png"),
                sideBarButtonCrafter("Small","Compact view to show more content",value,"LightTheme.png")

        );

        v.add(
                briefExplanationOfTheSettings.briefExplanationOfTheSettings("Sidebar size","Customize the sidebar size and behavior"),
                h
        );

        return v;



    }


    public VerticalLayout sideBarButtonCrafter(String name, String desc, String selectedName, String img){

        VerticalLayout v = new VerticalLayout();
        v.setAlignItems(FlexComponent.Alignment.CENTER);
        v.setSpacing(false);


        v.getStyle().set("flex", "1 1 252px");
        v.getStyle().set("max-width", "620px");
        v.getStyle().set("min-width", "252px");



        Image image = new Image(img, "image error");
        image.setHeight("200px");
        image.setWidthFull();
        image.getStyle().set("border-radius","10px");

        image.add("island");



        if(name.equals(selectedName)){
            image.addClassName("island-layout-solid");
        }

        sideBarMemory.add(image);

        image.addClickListener(e -> {


            UserSettings userSettings = sessionCrafter.extractSession("settings", UserSettings.class);
            userSettings.setSidebarSize(name);
            sessionCrafter.createSession("settings", userSettings);


            UI.getCurrent()
                    .getChildren()
                    .filter(component -> component instanceof MainLayout)
                    .findFirst()
                    .ifPresent(component ->
                            ((MainLayout) component).changeSidebar(name)
                    );


            for (var s : sideBarMemory) {
                s.removeClassName("island-layout-solid");
            }

            image.addClassName("island-layout-solid");

            userService.saveSidebar(name);

        });



        v.add(
                image,
                commonComponents.spanCrafter(name,"activityFeed-name"),
                commonComponents.spanCrafter(desc,"stat-description")
        );


        return v;
    }



}
