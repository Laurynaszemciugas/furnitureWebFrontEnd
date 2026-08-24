package com.example.demo.Pages.Settings.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.SinglePhotoLogic;
import com.example.demo.ControllerModels.CommonDtos.User;
import com.example.demo.ControllerModels.CommonDtos.UserSettings;
import com.example.demo.ControllerModels.User.AccountOverview;
import com.example.demo.ControllerModels.User.PersonalPrefrences;
import com.example.demo.ControllerModels.User.ProfileInformation;
import com.example.demo.Enums.*;
import com.example.demo.Services.LoginService.LoginService;
import com.example.demo.Services.UserService.UserService;
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

import java.lang.reflect.Member;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ProfileAccount {

    CommonComponents commonComponents;
    Common common;

    SinglePhotoLogic singlePhotoLogic;

    UserService userService;

    BriefExplanationOfSettings briefExplanationOfTheSettings;

    LoginService loginService;

    public ProfileAccount(CommonComponents commonComponents, Common common,UserService userService,LoginService loginService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.userService = userService;

        this.loginService = loginService;

        this.singlePhotoLogic = new SinglePhotoLogic(commonComponents,common);
        this.briefExplanationOfTheSettings = new BriefExplanationOfSettings(commonComponents,common);

    }



    public VerticalLayout profileAccount(){

        TextField fullName = new TextField("Full name");
        fullName.setReadOnly(true);
        EmailField emailAddress = new EmailField("Email address");
        emailAddress.setReadOnly(true);
        TextField role = new TextField("Role");
        role.setReadOnly(true);
        TextField phoneNumber = new TextField("Phone number");
        TextArea bio = new TextArea("Bio");




        ProfileInformation profileInformation = userService.getProfileInfo();


        fullName.setValue(profileInformation.getFullName() != null
                ? profileInformation.getFullName()
                : "");


        emailAddress.setValue(profileInformation.getEmailAddress() != null
                ? profileInformation.getEmailAddress()
                : "");


        if (profileInformation.getRole() != null) {
            role.setValue(profileInformation.getRole().toString());
        }


        phoneNumber.setValue(profileInformation.getPhoneNumber() != null
                ? profileInformation.getPhoneNumber()
                : "");


        bio.setValue(profileInformation.getBio() != null
                ? profileInformation.getBio()
                : "");




        bio.setWidthFull();
        bio.setHeight("60px");

        FormLayout formLayout = new FormLayout();
        formLayout.setWidth("auto");
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

        singlePhotoLogic.setImageData(profileInformation.getImageUrl());

        div.add(
                singlePhotoLogic.imageGetterShower()
        );

        div.getStyle().set("flex", "1 1 252px");
        div.getStyle().set("min-width", "252px");

        rightSide.getStyle().set("flex", "1 1 252px");
        rightSide.getStyle().set("min-width", "252px");

        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.add(
                div,
                rightSide
        );
        h.expand(rightSide);
        h.addClassName("layout-flex");


        HorizontalLayout options = new HorizontalLayout();
        options.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        options.setWidthFull();
        options.setPadding(false);

        Button button = new Button("Save changes");
        button.setPrefixComponent(commonComponents.iconCrafter(VaadinIcon.CHECK,"20px","White"));
        button.addClassName("accentButtons");
        button.addThemeVariants(ButtonVariant.PRIMARY);

        button.addClickListener(e->{

            User user = new User();
            user.setPhoneNumber(phoneNumber.getValue());
            user.setBio(bio.getValue());
            user.setImageUrl(singlePhotoLogic.getImageData());

            userService.saveProfileInfo(user);



        });


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

        h.addClassName("layout-flex");


        AccountOverview accountOverview = userService.getAccountOverview();

        System.out.println("Account overview: " + accountOverview);

        String memberSince = common.dateFormatter(
                accountOverview.getCreated()
        );

        long daysPassed = ChronoUnit.DAYS.between(
                accountOverview.getCreated().toLocalDate(),
                LocalDate.now()
        );

        String verification = accountOverview.getVerification() != null
                ? accountOverview.getVerification().toString()
                : "Unverified";

        String gmail = accountOverview.getGmail();

        String active;

        if (accountOverview.getBannedTill() == null) {
            active = "Active";
        } else {
            active = "Inactive";
        }

        String lastLogin = accountOverview.getLastLogin() != null
                ? common.dateFormatter(accountOverview.getLastLogin())
                : "Never";

        String ip = accountOverview.getIp() != null
                ? accountOverview.getIp()
                : "Unknown";



        h.add(
                accountOverviewIslands(VaadinIcon.CALENDAR,"Blue","Member since",memberSince,daysPassed + " Days"),
                accountOverviewIslands(VaadinIcon.ABACUS,"GREEN","Email status",verification,gmail),
                accountOverviewIslands(VaadinIcon.SHIELD,"Cyan","Account status",active,""),
                accountOverviewIslands(VaadinIcon.CLOCK,"BLUE","Last login",lastLogin,"IP: " + ip)
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


        PersonalPrefrences personalPrefrences = userService.getPersonalPrefrences();


        DateFormat dateFormatGot = personalPrefrences.getDateFormat() != null
                ? personalPrefrences.getDateFormat()
                : DateFormat.DD_MM_YYYY;

        TimeZone timeZoneGot = personalPrefrences.getTimeZone() != null
                ? personalPrefrences.getTimeZone()
                : TimeZone.UTC;

        Language languageGot = personalPrefrences.getLanguage() != null
                ? personalPrefrences.getLanguage()
                : Language.EN;

        boolean activeNotificationGot = personalPrefrences.isActiveNotification();


        ComboBox<DateFormat> dateFormat = new ComboBox<>("Date format");
        dateFormat.setItems(DateFormat.values());
        dateFormat.setValue(dateFormatGot);

        ComboBox<TimeZone> timeZone = new ComboBox<>("Time zone");
        timeZone.setItems(TimeZone.values());
        timeZone.setValue(timeZoneGot);

        ComboBox<Language> language = new ComboBox<>("Language");
        language.setItems(Language.values());
        language.setValue(languageGot);

        Checkbox notificationsToGmail = new Checkbox("Receive notification to gmail");
        notificationsToGmail.setValue(activeNotificationGot);

        FormLayout formLayout = new FormLayout();

        formLayout.add(
                dateFormat,
                timeZone,
                language,
                notificationsToGmail
        );

        Button button = new Button("Save changes");
        button.setPrefixComponent(commonComponents.iconCrafter(VaadinIcon.CHECK,"20px","White"));
        button.addClassName("accentButtons");
        button.addThemeVariants(ButtonVariant.PRIMARY);

        HorizontalLayout options = new HorizontalLayout();
        options.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        options.setWidthFull();
        options.setPadding(false);

        options.add(
                button
        );

        button.addClickListener(e->{

            User user = new User();
            UserSettings userSettings = new UserSettings();

            userSettings.setLanguage(language.getValue());
            userSettings.setDateFormat(dateFormat.getValue());
            userSettings.setReceiveGmail(notificationsToGmail.getValue());
            userSettings.setTimeZone(timeZone.getValue());

            user.setUserSettingsList(userSettings);



            userService.savePersonalPrefrences(user);

            loginService.createSettings();
            common.reloadPage();

        });


        h.add(
                briefExplanationOfTheSettings.briefExplanationOfTheSettings("Personal preferences","Manage your personal preferences and notification settings"),
                formLayout,
                options
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
