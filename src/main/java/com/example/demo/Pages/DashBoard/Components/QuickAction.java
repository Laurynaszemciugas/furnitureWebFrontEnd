package com.example.demo.Pages.DashBoard.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class QuickAction {

    CommonComponents commonComponents;
    Common common;

    public QuickAction(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }


    public VerticalLayout quickActionButtons(List<String> list){

        boolean empty = list.isEmpty();

        VerticalLayout mainLayout3 = new VerticalLayout(commonComponents.spanCrafterWordNoHide("Quick actions","stat-value"));
        mainLayout3.addClassName("island");
        mainLayout3.setWidth("200px");
        mainLayout3.setMaxWidth("600px");

        VerticalLayout ss = new VerticalLayout();
        ss.setSizeFull();
        ss.setAlignItems(FlexComponent.Alignment.CENTER);

        if(empty){
            ss.add(commonComponents.noDataFound());
        }
        else{
            for(var s : list){
                ss.add(commonComponents.normalThemeButtonWithSize(s, "s", ButtonVariant.LUMO_ICON,"90%",""));
            }
        }


        mainLayout3.add(ss);

        return mainLayout3;
    }



}
