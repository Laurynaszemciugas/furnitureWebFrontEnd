package com.example.demo.Pages.Settings.Page;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.SinglePhotoLogic;
import com.example.demo.Enums.Role;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Reports.Page.Components.BriefReportPageExplanation;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;


@Route(value = "Settings", layout = MainLayout.class)
public class Settings extends VerticalLayout implements BeforeEnterObserver {

    CommonComponents commonComponents;
    Common common;

    SinglePhotoLogic singlePhotoLogic;


    public Settings(CommonComponents commonComponents,  Common common) {


        this.commonComponents = commonComponents;
        this.common = common;

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(Alignment.CENTER);


        addClassName("animation-page");


        this.singlePhotoLogic = new SinglePhotoLogic(commonComponents,common);



    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        removeAll();


        add(mainLayout());




    }

    public VerticalLayout mainLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");

        verticalLayout.add(
                commonComponents.biefPageExplanation("Settings"),
                profileAccount(),
                accountOverview(),
                accountOverviewIslands()
        );




        return verticalLayout;
    }

    public VerticalLayout profileAccount(){

        TextField fullName = new TextField("Full name");
        TextField username = new TextField("Username");
        EmailField emailAddress = new EmailField("Email address");
        ComboBox<Role> role = new ComboBox<>("Role");
        TextField phoneNumber = new TextField("Phone number");
        TextArea bio = new TextArea("Bio");

        FormLayout formLayout = new FormLayout();

        formLayout.add(fullName,username,emailAddress,role,phoneNumber,bio);


        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");

        v.setWidthFull();

        Div div = new Div();
        div.setWidth("700px");
        div.add(
                singlePhotoLogic.imageGetterShower()
        );

        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.add(
                div,
                formLayout
        );

        HorizontalLayout options = new HorizontalLayout();
        options.setJustifyContentMode(JustifyContentMode.END);
        options.setWidthFull();
        options.setPadding(false);

        Button button = new Button("Save changes");
        button.addThemeVariants(ButtonVariant.PRIMARY);

        options.add(
                button
        );

        v.add(
                briefExplanationOfTheSettings("Profile information","Update your personal information and how others see you"),
                h,
                options

        );

        return v;
    }

    public VerticalLayout accountOverview(){

        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");

        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.setPadding(false);

        h.add(
                accountOverviewIslands(VaadinIcon.CALENDAR,"Blue","Member since","jan 15 2024","1 year 4 months"),
                accountOverviewIslands(VaadinIcon.ABACUS,"GREEN","Email status","Verified","jonh@gmail.com"),
                accountOverviewIslands(VaadinIcon.SHIELD,"Cyan","Account status","Active","no issues found"),
                accountOverviewIslands(VaadinIcon.CLOCK,"BLUE","Last login","jan 15 2024","IP: 192.162.1.1")
        );

        v.add(
                briefExplanationOfTheSettings("Account overview","View your account details and status"),
                h


        );

        return v;

    }

    public HorizontalLayout accountOverviewIslands(VaadinIcon icon, String iconColor, String first, String second, String third){

        HorizontalLayout h = new HorizontalLayout();
        h.setPadding(false);
        h.setAlignItems(Alignment.CENTER);
        h.addClassName("island");


        h.getStyle().set("flex", "1 1 252px");
        //h.getStyle().set("max-width", "620px");
        h.getStyle().set("min-width", "252px");


        VerticalLayout v = new VerticalLayout();
        v.setPadding(false);
        v.setSpacing(false);

        v.add(
                commonComponents.spanCrafter(first,"stat-description"),
                commonComponents.spanCrafter(second,"stat-example"),
                commonComponents.spanCrafter(third,"stat-description")
        );


        h.add(
                commonComponents.iconCrafter(icon,"45px",iconColor),
                v
        );





        return h;

    }

    public VerticalLayout accountOverviewIslands(){

        VerticalLayout h = new VerticalLayout();
        h.setPadding(false);
        h.setAlignItems(Alignment.CENTER);
        h.addClassName("island");


        ComboBox<String> dateFormat = new ComboBox<>("Date format");
        Checkbox notificationsToGmail = new Checkbox("Notification preferences");

        FormLayout formLayout = new FormLayout();

        formLayout.add(
                dateFormat,
                notificationsToGmail
        );


        h.add(
                briefExplanationOfTheSettings("Personal preferences","Manage your personal preferences and notification settings"),
                formLayout
        );




        return h;

    }


    public VerticalLayout briefExplanationOfTheSettings(String name, String desc){

        VerticalLayout h = new VerticalLayout();
        h.setSpacing(false);
        h.setPadding(false);
        h.setWidthFull();

        h.add(
                commonComponents.spanCrafter(name,"activityFeed-name"),
                commonComponents.spanCrafter(desc,"stat-description")
        );


        return h;

    }
}
