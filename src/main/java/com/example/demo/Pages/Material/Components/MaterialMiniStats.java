package com.example.demo.Pages.Material.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.MiniStatCrafter;
import com.example.demo.ControllerModels.Material.MaterialMiniStat;
import com.example.demo.Enums.ActiveInactive;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class MaterialMiniStats {


    CommonComponents commonComponents;
    Common common;
    MiniStatCrafter miniStatCrafter;

    public MaterialMiniStats(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.miniStatCrafter = new MiniStatCrafter(commonComponents,common);
    }

    public HorizontalLayout miniStatHolder(MaterialMiniStat materialMiniStat){
        HorizontalLayout miniStatHolder = new HorizontalLayout();
        miniStatHolder.addClassName("layout-flex");
        miniStatHolder.setWidthFull();

        miniStatHolder.add(
                miniStatCrafter.miniStats(VaadinIcon.CUBES, "Total material", materialMiniStat.getTotalMaterials(), "All materials", "Black", "rgba(239, 68, 68, 0.18)"),
                miniStatCrafter.miniStats(VaadinIcon.CHECK, ActiveInactive.ACTIVE.getGetDisplayNames(), materialMiniStat.getActiveMaterials(), "Usable materials", "Black", "rgba(59, 130, 246, 0.18)"),
                miniStatCrafter.miniStats(VaadinIcon.EYE_SLASH, ActiveInactive.INACTIVE.getGetDisplayNames(), materialMiniStat.getInactiveMaterials(), "Non Usable materials", "Black", "rgba(234, 179, 8, 0.18)"),
                miniStatCrafter.miniStats(VaadinIcon.PAPERCLIP, "Recently added", materialMiniStat.getRecentlyAdded(), "This month", "Black", "rgba(249, 115, 22, 0.18)")
        );

        return miniStatHolder;
    }




}
