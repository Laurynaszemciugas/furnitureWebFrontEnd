package com.example.demo.pages.DashBoard;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.stereotype.Service;


@Service
public class QuickAction {

    CommonComponents commonComponents;
    Common common;

    public QuickAction(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }


    public VerticalLayout quickActionButtons(){
        VerticalLayout mainLayout3 = new VerticalLayout(commonComponents.spanCrafterWordNoHide("Quick actions","stat-value"));
        mainLayout3.addClassName("island");
        mainLayout3.setWidth("200px");
        mainLayout3.setMaxWidth("600px");

        VerticalLayout ss = new VerticalLayout(commonComponents.normalThemeButtonWithSize("DashBoard", "dashboard", ButtonVariant.ERROR,"90%",""));
        ss.setSizeFull();
        ss.setAlignItems(FlexComponent.Alignment.CENTER);

        mainLayout3.add(ss);

        return mainLayout3;
    }


}
