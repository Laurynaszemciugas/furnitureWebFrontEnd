package com.example.demo.Pages.Products.Components;


import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.MiniStatCrafter;
import com.example.demo.ControllerModels.Common.MiniStatHolder;
import com.example.demo.ControllerModels.Material.MaterialMiniStat;
import com.example.demo.ControllerModels.Products.ProductPageMiniStat;
import com.example.demo.Enums.ActiveInactive;
import com.example.demo.Enums.Visibility;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class ProductPageMiniStats {

    CommonComponents commonComponents;
    Common common;
    MiniStatCrafter miniStatCrafter;

    public ProductPageMiniStats(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.miniStatCrafter = new MiniStatCrafter(commonComponents,common);
    }

    public HorizontalLayout miniStatHolder(MiniStatHolder miniStatHolder){
        HorizontalLayout miniStatHolders = new HorizontalLayout();
        miniStatHolders.addClassName("layout-flex");
        miniStatHolders.setWidthFull();


        miniStatHolders.add(
                miniStatCrafter.miniStats(VaadinIcon.CUBES, "Total products", miniStatHolder.getFirst(), "All materials", "Black", "rgba(239, 68, 68, 0.18)"),
                miniStatCrafter.miniStats(VaadinIcon.CHECK, Visibility.Visible.name(), miniStatHolder.getSecond(), "Usable materials", "Black", "rgba(59, 130, 246, 0.18)"),
                miniStatCrafter.miniStats(VaadinIcon.EYE_SLASH, Visibility.NonVisible.name(), miniStatHolder.getThird(), "Non Usable materials", "Black", "rgba(234, 179, 8, 0.18)"),
                miniStatCrafter.miniStats(VaadinIcon.PAPERCLIP, "Recently added", miniStatHolder.getFourth(), "This month", "Black", "rgba(249, 115, 22, 0.18)")
        );

        return miniStatHolders;
    }


}
