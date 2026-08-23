package com.example.demo.MainLayout;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.ControllerModels.CommonDtos.UserSettings;
import com.example.demo.Pages.Settings.Components.ApperanceTab;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;

@CssImport("./MainCSS.css")
public class MainLayout extends AppLayout {


    CommonComponents commonComponents;
    Common common;


    private VerticalLayout drawerSmall = new VerticalLayout();
    private VerticalLayout drawerLarge = new VerticalLayout();


    SessionCrafter sessionCrafter;




    public MainLayout(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;


        this.sessionCrafter = new SessionCrafter();
        // change the drawer size to large
        addClassName("custom-layout-large");

        setPrimarySection(Section.DRAWER);

        createHeader();
        drawerSmall = createDrawerSmall();
        drawerLarge = createDrawerLarge();

        drawerSmall.setVisible(false);

        addClassName("vaadin-app-layout");

        addToDrawer(drawerSmall, drawerLarge);


        sessionCrafter.createSession("UI",UI.getCurrent());

        loadSettings();

    }

    public void loadSettings(){

        System.out.println("settings loading");

        UserSettings userSettings = sessionCrafter.extractSession("settings", UserSettings.class);

        System.out.println(userSettings.getSidebarSize());
        System.out.println(userSettings.getAccent());

        UI ui = sessionCrafter.extractSession("UI", UI.class);

        ui.getElement().setAttribute("accent", userSettings.getAccent().toLowerCase());

        changeSidebar(userSettings.getSidebarSize());

        // also later make dark light theme

    }

    public void changeSidebar(String size) {

        boolean small = size.equals("Small");

        drawerSmall.setVisible(small);
        drawerLarge.setVisible(!small);

        removeClassName("custom-layout-large");
        removeClassName("custom-layout-small");

        if (small) {
            addClassName("custom-layout-small");
        } else {
            addClassName("custom-layout-large");
        }
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


        leftSideBar.add(
                commonComponents.normalButtons("DashBoard","DashBoard",VaadinIcon.DASHBOARD),
                commonComponents.normalButtons("Products/1","Products",VaadinIcon.PACKAGE),
                commonComponents.normalButtons("Orders","Orders",VaadinIcon.CLIPBOARD),
                commonComponents.normalButtons("Materials","Materials",VaadinIcon.STOCK),
                commonComponents.normalButtons("Employees","Employees",VaadinIcon.USERS),
                commonComponents.normalButtons("Reports","Reports",VaadinIcon.CHART_LINE),
                commonComponents.normalButtons("bob","Quick actions",VaadinIcon.MENU),
                commonComponents.normalButtons("Settings","Settings",VaadinIcon.COG),
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
                commonComponents.smallIconButtons("DashBoard",VaadinIcon.DASHBOARD,"white"),
                commonComponents.smallIconButtons("Products/1",VaadinIcon.PACKAGE,"white"),
                commonComponents.smallIconButtons("Orders",VaadinIcon.CLIPBOARD,"white"),
                commonComponents.smallIconButtons("Materials",VaadinIcon.USERS,"white"),
                commonComponents.smallIconButtons("Employees",VaadinIcon.CHART_LINE,"white"),
                commonComponents.smallIconButtons("Reports",VaadinIcon.MENU,"white"),
                commonComponents.smallIconButtons("Settings",VaadinIcon.COG,"white"),
                logOut);

        leftSideBar.add(toggle);

        leftSideBar.getStyle().set("gap","10px");

        leftSideBar.getStyle().set("z-index","10");

        return leftSideBar;
    }




    private void createHeader() {

        // toggle button

        Icon drawerStepOne = commonComponents.iconCrafter(VaadinIcon.MENU,"40px","black");


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
