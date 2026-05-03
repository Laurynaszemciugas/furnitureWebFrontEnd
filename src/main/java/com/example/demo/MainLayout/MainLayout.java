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

    private VerticalLayout drawerSmall = new VerticalLayout();
    private VerticalLayout drawerLarge = new VerticalLayout();






    public MainLayout(Components components) {
        this.components = components;

        // change the drawer size to large
        addClassName("custom-layout-large");

        setPrimarySection(Section.DRAWER);

        createHeader();
        drawerSmall = createDrawerSmall();
        drawerLarge = createDrawerLarge();

        drawerSmall.setVisible(false);


        addToDrawer(drawerSmall, drawerLarge);


    }



    private VerticalLayout createDrawerLarge() {

        Button toggle = new Button("Switch scenes", e -> switchDrawer());

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
        leftSideBar.setWidthFull();
        leftSideBar.getStyle()
                .set("background-color", "#374a75")
                .set("box-shadow", "2px 0px 10px rgba(0,0,0,0.2)")
                .set("border-right", "1px solid #2A3B6F");
        leftSideBar.setAlignItems(FlexComponent.Alignment.START);

        Button logOut = new Button();
        logOut.addClickListener(e->{
            VaadinSession.getCurrent().close();
            UI.getCurrent().getPage().reload();
        });

        Button button = new Button("32423");
        button.getStyle().set("background-color","green").set("cursor", "pointer");;
        leftSideBar.add(
                components.mainLayoutButtons("bob","DashBoard",VaadinIcon.MENU),
                components.mainLayoutButtons("bob","Products",VaadinIcon.MENU),
                components.mainLayoutButtons("bob","Orders",VaadinIcon.MENU),
                components.mainLayoutButtons("bob","Materials",VaadinIcon.MENU),
                components.mainLayoutButtons("bob","Employees",VaadinIcon.MENU),
                components.mainLayoutButtons("bob","Reports",VaadinIcon.MENU),
                components.mainLayoutButtons("bob","Quick actions",VaadinIcon.MENU),
                components.mainLayoutButtons("bob","Settings",VaadinIcon.MENU),
                logOut);


        leftSideBar.add(toggle);

        leftSideBar.getStyle().set("gap","10px");

        leftSideBar.getStyle().set("z-index","10");

        return leftSideBar;
    }


    private VerticalLayout createDrawerSmall() {

        Button toggle = new Button("Switch scenes", e -> switchDrawer());

        Div Logo = new Div(new Span("Furniture CO"));
        Logo.getStyle()
                .set("background-color", "#1F2A44")
                .set("color", "white")
                .set("font-weight", "bold")
                .set("font-size", "12px")
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
        leftSideBar.setWidthFull();
        leftSideBar.getStyle()
                .set("background-color", "#374a75")
                .set("box-shadow", "2px 0px 10px rgba(0,0,0,0.2)")
                .set("border-right", "1px solid #2A3B6F");
        leftSideBar.setAlignItems(FlexComponent.Alignment.CENTER);

        Button logOut = new Button();
        logOut.addClickListener(e->{
            VaadinSession.getCurrent().close();
            UI.getCurrent().getPage().reload();
        });

        Button button = new Button("32423");
        button.getStyle().set("background-color","green").set("cursor", "pointer");;
        leftSideBar.add(
                components.mainLayoutButtonsSmall("123",VaadinIcon.MENU),
                components.mainLayoutButtonsSmall("123",VaadinIcon.MENU),
                components.mainLayoutButtonsSmall("123",VaadinIcon.MENU),
                components.mainLayoutButtonsSmall("123",VaadinIcon.MENU),
                components.mainLayoutButtonsSmall("123",VaadinIcon.MENU),
                components.mainLayoutButtonsSmall("123",VaadinIcon.MENU),
                components.mainLayoutButtonsSmall("123",VaadinIcon.MENU),
                logOut);

        leftSideBar.add(toggle);

        leftSideBar.getStyle().set("gap","10px");

        leftSideBar.getStyle().set("z-index","10");

        return leftSideBar;
    }




    private void createHeader() {

        // toggle button

        Icon drawerStepOne = components.iconCrafter(VaadinIcon.MENU,"40px","black");


        DrawerToggle toggle = new DrawerToggle();
        toggle.getStyle().set("background", "transparent")
                .set("border", "none")
                .set("box-shadow", "none")
                .set("padding", "0")
                .set("margin", "0")
                .set("cursor", "pointer");

        toggle.setIcon(drawerStepOne);


        // header stuff

        HorizontalLayout header = new HorizontalLayout(toggle);
        header.setAlignItems(FlexComponent.Alignment.CENTER);

        header.addClassName("header-island");

        header.setSizeFull();

        header.setSpacing(false);
        header.setPadding(false);

        addToNavbar(header);
    }

    private void switchDrawer() {
            boolean isDrawer1Visible = drawerSmall.isVisible();

            if(!isDrawerOpened()){
                setDrawerOpened(true);
            }

            // Toggle visibility
            drawerSmall.setVisible(!isDrawer1Visible);
            drawerLarge.setVisible(isDrawer1Visible);

            // Remove old classes FIRST
            this.removeClassName("custom-layout-large");
            this.removeClassName("custom-layout-small");

            // Apply new size
            if (drawerSmall.isVisible()) {
                this.addClassName("custom-layout-small");
            } else {
                this.addClassName("custom-layout-large");
            }
    }

}
