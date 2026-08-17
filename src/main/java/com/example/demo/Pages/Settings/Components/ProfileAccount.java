package com.example.demo.Pages.Settings.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.SinglePhotoLogic;
import com.example.demo.Enums.Role;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class ProfileAccount {

    CommonComponents commonComponents;
    Common common;

    SinglePhotoLogic singlePhotoLogic;

    BriefExplanationOfSettings briefExplanationOfTheSettings;

    public ProfileAccount(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;

        this.singlePhotoLogic = new SinglePhotoLogic(commonComponents,common);
        this.briefExplanationOfTheSettings = new BriefExplanationOfSettings(commonComponents,common);

    }



    public VerticalLayout profileAccount(){

        TextField fullName = new TextField("Full name");
        EmailField emailAddress = new EmailField("Email address");
        ComboBox<Role> role = new ComboBox<>("Role");
        TextField phoneNumber = new TextField("Phone number");
        TextArea bio = new TextArea("Bio");

        bio.setWidthFull();
        bio.setHeight("150px");

        FormLayout formLayout = new FormLayout();

        formLayout.add(fullName,emailAddress,role,phoneNumber);


        VerticalLayout rightSide = new VerticalLayout();
        rightSide.setSpacing(false);
        rightSide.add(
                formLayout,
                bio
        );

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
                rightSide
        );

        HorizontalLayout options = new HorizontalLayout();
        options.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        options.setWidthFull();
        options.setPadding(false);

        Button button = new Button("Save changes");
        button.addThemeVariants(ButtonVariant.PRIMARY);

        options.add(
                button
        );

        v.add(
                briefExplanationOfTheSettings.briefExplanationOfTheSettings("Profile information","Update your personal information and how others see you"),
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
                briefExplanationOfTheSettings.briefExplanationOfTheSettings("Account overview","View your account details and status"),
                h


        );

        return v;

    }

    public HorizontalLayout accountOverviewIslands(VaadinIcon icon, String iconColor, String first, String second, String third){

        HorizontalLayout h = new HorizontalLayout();
        h.setPadding(false);
        h.setAlignItems(FlexComponent.Alignment.CENTER);
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
        h.setAlignItems(FlexComponent.Alignment.CENTER);
        h.addClassName("island");


        ComboBox<String> dateFormat = new ComboBox<>("Date format");
        ComboBox<String> timeZone = new ComboBox<>("Time zone");
        ComboBox<String> language = new ComboBox<>("Language");
        Checkbox notificationsToGmail = new Checkbox("Receive notification to gmail");


        FormLayout formLayout = new FormLayout();

        formLayout.add(
                dateFormat,
                timeZone,
                language,
                notificationsToGmail
        );


        h.add(
                briefExplanationOfTheSettings.briefExplanationOfTheSettings("Personal preferences","Manage your personal preferences and notification settings"),
                formLayout
        );




        return h;

    }

    public VerticalLayout AllInOne(){
        VerticalLayout v = new VerticalLayout();

        v.add(
                profileAccount(),
                accountOverview(),
                accountOverviewIslands()
        );


        return v;
    }



}
