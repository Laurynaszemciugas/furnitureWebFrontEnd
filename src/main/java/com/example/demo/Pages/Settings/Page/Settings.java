package com.example.demo.Pages.Settings.Page;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Settings.Components.ApperanceTab;
import com.example.demo.Pages.Settings.Components.ProfileAccount;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;


@Route(value = "Settings", layout = MainLayout.class)
public class Settings extends VerticalLayout implements BeforeEnterObserver {

    CommonComponents commonComponents;
    Common common;



    Tab profileAccount;
    Tab appearance;
    Tab shipping;
    VerticalLayout content = new VerticalLayout();

    ProfileAccount profileAndAccount;
    ApperanceTab apearnce;



    public Settings(CommonComponents commonComponents,  Common common) {


        this.commonComponents = commonComponents;
        this.common = common;

        this.profileAndAccount = new ProfileAccount(commonComponents,common);
        this.apearnce = new ApperanceTab(commonComponents,common);



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
        appearance = new Tab("Appearance");
        shipping = new Tab("Shipping");

        profileAccount.addClassName("accentTabs");
        appearance.addClassName("accentTabs");
        shipping.addClassName("accentTabs");

        Tabs tabs = new Tabs(profileAccount, appearance, shipping);
        tabs.addClassName("accentTabs");
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














    private void setContent(Tab tab) {
        content.removeAll();

        if (tab.equals(profileAccount)) {
            content.add(profileAndAccount.AllInOne());
        } else if (tab.equals(appearance)) {
            content.add(apearnce.allInOne());
        } else {
            content.add(new Paragraph("This is the Shipping tab"));
        }
    }

}
