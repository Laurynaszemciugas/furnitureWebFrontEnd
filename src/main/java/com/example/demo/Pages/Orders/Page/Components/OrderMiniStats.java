package com.example.demo.Pages.Orders.Page.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.MiniStatCrafter;
import com.example.demo.ControllerModels.Common.MiniStatHolder;
import com.example.demo.Enums.ActiveInactive;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class OrderMiniStats {


    CommonComponents commonComponents;
    Common common;
    MiniStatCrafter miniStatCrafter;

    public OrderMiniStats(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.miniStatCrafter = new MiniStatCrafter(commonComponents,common);
    }

    public HorizontalLayout miniStatHolder(MiniStatHolder miniStatHolder){
        HorizontalLayout miniStatHolders = new HorizontalLayout();
        miniStatHolders.addClassName("layout-flex");
        miniStatHolders.setWidthFull();

        miniStatHolders.add(
                miniStatCrafter.miniStats(VaadinIcon.CUBES, "Total orders", miniStatHolder.getFirst(), "All orders", "Black", "rgba(239, 68, 68, 0.18)"),
                miniStatCrafter.miniStats(VaadinIcon.CHECK, "Finished orders", miniStatHolder.getSecond(), "Finished orders", "Black", "rgba(59, 130, 246, 0.18)"),
                miniStatCrafter.miniStats(VaadinIcon.EYE_SLASH, "In progress orders", miniStatHolder.getThird(), "Pending orders", "Black", "rgba(234, 179, 8, 0.18)"),
                miniStatCrafter.miniStats(VaadinIcon.PAPERCLIP, "Recently added", miniStatHolder.getFourth(), "This month", "Black", "rgba(249, 115, 22, 0.18)")
        );

        return miniStatHolders;
    }




}
