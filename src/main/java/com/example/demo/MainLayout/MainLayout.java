package com.example.demo.MainLayout;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;


@CssImport("./MainCSS.css")
public class MainLayout extends AppLayout {

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        createDrawer();
        createHeader();

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

        // ============= header =====================

        DrawerToggle toggle = new DrawerToggle();
        toggle.getStyle()
                .set("background-color","transparent")
                .set("width","50px")
                .set("border","0px");

        toggle.setIcon(VaadinIcon.MENU.create());

        toggle.setMaxWidth("70px");

        toggle.addClickListener(e->{
            if(isDrawerOpened()){
                toggle.setIcon(VaadinIcon.MENU.create());
            }
            else{
                toggle.setIcon(VaadinIcon.ARROW_RIGHT.create());
            }

        });













        HorizontalLayout header = new HorizontalLayout(toggle);
        header.setWidthFull();
        header.setMinHeight("100px");
        header.addClassName("header");
        header.setAlignItems(FlexComponent.Alignment.CENTER);

        header.setSpacing(false);
        header.setPadding(false);

        addToNavbar(header);
    }


}
