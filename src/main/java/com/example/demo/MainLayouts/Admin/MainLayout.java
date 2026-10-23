package com.example.demo.MainLayouts.Admin;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.Entity.UserSettings;
import com.example.demo.Pages.ActionLog.Main.ActionLogPage;
import com.example.demo.Pages.DashBoard.Page.DashBoardPage;
import com.example.demo.Pages.Employee.EmployeeAddEdit.EmployeeAddPage;
import com.example.demo.Pages.Employee.Page.EmployeesPage;
import com.example.demo.Pages.Material.MaterialAddEdit.MaterialAddPage;
import com.example.demo.Pages.Material.Page.MaterialPage;
import com.example.demo.Pages.Orders.OrderAdd.OrderAdd;
import com.example.demo.Pages.Orders.Page.OrdersPage;
import com.example.demo.Pages.Products.AddNewMultiProducts;
import com.example.demo.Pages.Products.MultipleProductChoices;
import com.example.demo.Pages.Products.Page.ProductsPage;
import com.example.demo.Pages.Products.ProductAdd.Page.ProductsAdd;
import com.example.demo.Pages.Reports.Page.ReportPage;
import com.example.demo.Pages.Reports.ReportsPages.CreatorPage.ReportCreationPage;
import com.example.demo.Pages.Reports.ReportsPages.EmployeeReport.EmloyeeReportPage;
import com.example.demo.Pages.Reports.ReportsPages.MaterialReport.MaterialReportPage;
import com.example.demo.Pages.Reports.ReportsPages.OrderReports.OrderReportPage;
import com.example.demo.Pages.Reports.ReportsPages.ProductReportpPage.ProductReportPage;
import com.example.demo.Pages.Settings.Page.Settings;
import com.example.demo.Services.LoginService.LoginService;
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
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.Lumo;

@CssImport("./MainCSS.css")
public class MainLayout extends AppLayout {


    CommonComponents commonComponents;
    Common common;

    LoginService loginService;


    private VerticalLayout drawerSmall = new VerticalLayout();
    private VerticalLayout drawerLarge = new VerticalLayout();


    SessionCrafter sessionCrafter;




    public MainLayout(CommonComponents commonComponents, Common common,LoginService loginService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.loginService = loginService;


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



        try {
            UserSettings userSettings = sessionCrafter.extractSession("settings", UserSettings.class);


            System.out.println(userSettings.getSidebarSize());
            System.out.println(userSettings.getAccent());

            UI ui = sessionCrafter.extractSession("UI", UI.class);

            ui.getElement().setAttribute("accent", userSettings.getAccent().toLowerCase());

            changeSidebar(userSettings.getSidebarSize());

            // also later make dark light theme

        } catch (Exception e) {
            common.customNavigate("Login");
            UI.getCurrent().refreshCurrentRoute(true);

        }

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


        SideNav nav = new SideNav();

        nav.getStyle().setOverflow(Style.Overflow.HIDDEN);
        nav.setWidthFull();
        nav.addClassName("white-side-nav");

// Dashboard
        SideNavItem dashboard = new SideNavItem(
                "DashBoard",
                DashBoardPage.class,
                commonComponents.iconCrafter(
                        VaadinIcon.DASHBOARD, "25px", "white"
                )
        );

// Products
        SideNavItem products = new SideNavItem(
                "Products",
                ProductsPage.class,
                commonComponents.iconCrafter(
                        VaadinIcon.PACKAGE, "25px", "white"
                )
        );

        products.addItem(new SideNavItem(
                "Add new product",
                ProductsAdd.class,
                commonComponents.iconCrafter(
                        VaadinIcon.PLUS_CIRCLE, "25px", "white"
                )
        ));

        products.addItem(new SideNavItem(
                "Add new multi variant product",
                AddNewMultiProducts.class,
                commonComponents.iconCrafter(
                        VaadinIcon.PLUS_CIRCLE, "25px", "white"
                )
        ));

// Orders
        SideNavItem orders = new SideNavItem(
                "Orders",
                OrdersPage.class,
                commonComponents.iconCrafter(
                        VaadinIcon.CLIPBOARD_CHECK, "25px", "white"
                )
        );

        orders.addItem(new SideNavItem(
                "Add new order",
                OrderAdd.class,
                commonComponents.iconCrafter(
                        VaadinIcon.PLUS_CIRCLE, "25px", "white"
                )
        ));

// Materials
        SideNavItem material = new SideNavItem(
                "Materials",
                MaterialPage.class,
                commonComponents.iconCrafter(
                        VaadinIcon.CUBES, "25px", "white"
                )
        );

        material.addItem(new SideNavItem(
                "Add new material",
                MaterialAddPage.class,
                commonComponents.iconCrafter(
                        VaadinIcon.PLUS_CIRCLE, "25px", "white"
                )
        ));

// Employees
        SideNavItem employee = new SideNavItem(
                "Employees",
                EmployeesPage.class,
                commonComponents.iconCrafter(
                        VaadinIcon.USERS, "25px", "white"
                )
        );

        employee.addItem(new SideNavItem(
                "Add new employee",
                EmployeeAddPage.class,
                commonComponents.iconCrafter(
                        VaadinIcon.PLUS_CIRCLE, "25px", "white"
                )
        ));

// Reports
        SideNavItem reports = new SideNavItem(
                "Reports",
                ReportPage.class,
                commonComponents.iconCrafter(
                        VaadinIcon.BAR_CHART, "25px", "white"
                )
        );

        reports.addItem(new SideNavItem(
                "Order report",
                OrderReportPage.class,
                commonComponents.iconCrafter(
                        VaadinIcon.CLIPBOARD, "25px", "white"
                )
        ));

        reports.addItem(new SideNavItem(
                "Product report",
                ProductReportPage.class,
                commonComponents.iconCrafter(
                        VaadinIcon.PACKAGE, "25px", "white"
                )
        ));

        reports.addItem(new SideNavItem(
                "Material report",
                MaterialReportPage.class,
                commonComponents.iconCrafter(
                        VaadinIcon.CUBES, "25px", "white"
                )
        ));

        reports.addItem(new SideNavItem(
                "Employee report",
                EmloyeeReportPage.class,
                commonComponents.iconCrafter(
                        VaadinIcon.USERS, "25px", "white"
                )
        ));

        reports.addItem(new SideNavItem(
                "Create custom report",
                ReportCreationPage.class,
                commonComponents.iconCrafter(
                        VaadinIcon.FILE_ADD, "25px", "white"
                )
        ));

// Action logs
        SideNavItem actionLogs = new SideNavItem(
                "Action logs",
                ActionLogPage.class,
                commonComponents.iconCrafter(
                        VaadinIcon.LIST, "25px", "white"
                )
        );

// Settings
        SideNavItem settings = new SideNavItem(
                "Settings",
                Settings.class,
                commonComponents.iconCrafter(
                        VaadinIcon.COG, "25px", "white"
                )
        );

// Add all navigation items
        nav.addItem(
                dashboard,
                products,
                orders,
                material,
                employee,
                reports,
                actionLogs,
                settings
        );
        nav.getElement().executeJs("""
    const sideNav = this;

    sideNav.addEventListener('click', (event) => {
        const item = event.composedPath().find(
            element => element?.tagName === 'VAADIN-SIDE-NAV-ITEM'
        );

        if (!item) {
            return;
        }

        // Wait for Vaadin to process the click first.
        setTimeout(() => {
            const items = sideNav.querySelectorAll(
                'vaadin-side-nav-item'
            );

            items.forEach(otherItem => {
                if (otherItem !== item && otherItem.hasAttribute('expanded')) {
                    otherItem.expanded = false;
                }
            });
        }, 0);
    });
""");

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

        Button logOut =  commonComponents.normalButtons("Login","Logout",VaadinIcon.SIGN_OUT);
        logOut.addClickListener(e->{
            VaadinSession.getCurrent().close();
            //UI.getCurrent().getPage().reload();
        });



        leftSideBar.add(
//                commonComponents.normalButtons("DashBoard","DashBoard",VaadinIcon.DASHBOARD),
//                commonComponents.normalButtons("Products/1","Products",VaadinIcon.PACKAGE),
//                commonComponents.normalButtons("Orders","Orders",VaadinIcon.CLIPBOARD),
//                commonComponents.normalButtons("Materials","Materials",VaadinIcon.STOCK),
//                commonComponents.normalButtons("Employees","Employees",VaadinIcon.USERS),
//                commonComponents.normalButtons("Reports","Reports",VaadinIcon.CHART_LINE),
//                commonComponents.normalButtons("Actions","Action logs",VaadinIcon.RECORDS),
//                commonComponents.normalButtons("Settings","Settings",VaadinIcon.COG),
                nav,
                commonComponents.spaceFiller(),
                logOut);



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
                commonComponents.smallIconButtons("Actions",VaadinIcon.RECORDS,"white"),
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
