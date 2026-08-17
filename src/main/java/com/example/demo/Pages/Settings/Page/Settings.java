package com.example.demo.Pages.Settings.Page;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.SinglePhotoLogic;
import com.example.demo.Enums.Role;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Reports.Page.Components.BriefReportPageExplanation;
import com.example.demo.Pages.Settings.Components.ProfileAccount;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
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



    Tab profileAccount;
    Tab payment;
    Tab shipping;
    VerticalLayout content = new VerticalLayout();

    ProfileAccount profileAndAccount;


    public Settings(CommonComponents commonComponents,  Common common) {


        this.commonComponents = commonComponents;
        this.common = common;

        this.profileAndAccount = new ProfileAccount(commonComponents,common);

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(Alignment.CENTER);


        addClassName("animation-page");





    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        removeAll();


        add(mainLayout());




    }

    public VerticalLayout mainLayout() {

        profileAccount = new Tab("Profile & Account");
        payment = new Tab("Payment");
        shipping = new Tab("Shipping");


        Tabs tabs = new Tabs(profileAccount, payment, shipping);
        tabs.addSelectedChangeListener(
                event -> setContent(event.getSelectedTab()));

        setContent(profileAccount);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");



        verticalLayout.add(
                commonComponents.biefPageExplanation("Settings"),
                tabs,
                content
        );




        return verticalLayout;
    }



    public VerticalLayout profileAccountTab(){

        VerticalLayout div = new VerticalLayout();

        div.add(
                profileAndAccount.AllInOne()
        );


        return div;

    }









    private void setContent(Tab tab) {
        content.removeAll();

        if (tab.equals(profileAccount)) {
            content.add(profileAccountTab());
        } else if (tab.equals(payment)) {
            content.add(new Paragraph("This is the Payment tab"));
        } else {
            content.add(new Paragraph("This is the Shipping tab"));
        }
    }

}
