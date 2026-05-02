package com.example.demo.MainLayout;

import com.example.demo.Common.Components;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@CssImport("./MainCSS.css")
public class MainLayout extends AppLayout {

    Components components;



    public MainLayout(Components components) {

        this.components = components;

        setPrimarySection(Section.DRAWER);
        createDrawer();
        createHeader();

        getStyle().set("gap","20px");

    }

    private void createDrawer() {



        Div Logo = new Div(new Span("Furniture CO"));
        Logo.getStyle()
                .set("background-color", "#1F2A44")
                .set("color", "white")
                .set("font-weight", "bold")
                .set("font-size", "24px")
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "center")
                .set("width", "100%")
                .set("height", "100px")
                .set("margin-bottom", "20px")
                .set("border-bottom", "2px solid #2A3B6F");

        Logo.addClickListener(e->{
            UI.getCurrent().navigate("dashboard");
        });

        // Left Sidebar
        VerticalLayout leftSideBar = new VerticalLayout(Logo);
        leftSideBar.setPadding(false);
        leftSideBar.setSpacing(false);
        leftSideBar.setHeightFull();
        leftSideBar.setWidth("250px");
        leftSideBar.getStyle()
                .set("background-color", "#374a75")
                .set("box-shadow", "2px 0px 10px rgba(0,0,0,0.2)")
                .set("border-right", "1px solid #2A3B6F");

        Button logOut = new Button();
        logOut.addClickListener(e->{
            VaadinSession.getCurrent().close();
            UI.getCurrent().getPage().reload();
        });

        leftSideBar.add(
                new Button(),
                new Button(),
                new Button(),
                new Button(),
                new Button(),
                new Button(),
                logOut);

        leftSideBar.getStyle().set("gap","10px");

        leftSideBar.getStyle().set("z-index","10");

        addToDrawer(leftSideBar);
    }



    private void createHeader() {

        // toggle button

        Icon drawerStepOne = components.iconCrafter(VaadinIcon.MENU,"40px","black");
        Icon drawerStepTwo = components.iconCrafter(VaadinIcon.ARROW_RIGHT,"40px","black");

        DrawerToggle toggle = new DrawerToggle();
        toggle.getStyle().set("background", "transparent")
                .set("border", "none")
                .set("box-shadow", "none")
                .set("padding", "0")
                .set("margin", "0")
                .set("cursor", "pointer");

        toggle.setIcon(drawerStepOne);



        toggle.addClickListener(e->{
            if(isDrawerOpened()){
                toggle.setIcon(drawerStepOne);
            }
            else{
                toggle.setIcon(drawerStepTwo);
            }

        });


        // header stuff

        HorizontalLayout header = new HorizontalLayout(toggle);
        header.setAlignItems(FlexComponent.Alignment.CENTER);

        header.addClassName("header-island");

        header.setSizeFull();

        header.setSpacing(false);
        header.setPadding(false);

        addToNavbar(header);
    }


}
