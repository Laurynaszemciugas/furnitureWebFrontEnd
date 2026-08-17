package com.example.demo.Pages.Settings.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class BriefExplanationOfSettings {


    CommonComponents commonComponents;
    Common common;

    public BriefExplanationOfSettings(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
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
