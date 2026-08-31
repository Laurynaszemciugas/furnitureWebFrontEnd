package com.example.demo.Pages.ActionLog.Components;


import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.ActionLogs.ActionLogFeed;
import com.example.demo.ControllerModels.Material.MaterialBriefDto;
import com.example.demo.Enums.ActiveInactive;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class ActionLogGrid {

    CommonComponents commonComponents;
    Common common;

    public ActionLogGrid(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }

    public VerticalLayout gridHolder(List<ActionLogFeed> materiaData){

        VerticalLayout vv = new VerticalLayout();
        vv.addClassName("smooth-panel");
        vv.setPadding(false);
        vv.setSpacing(false);
        vv.setWidthFull();



        Grid<ActionLogFeed> grid = new Grid<>(ActionLogFeed.class,true);
        grid.setHeight("700px");
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        grid.setItems(materiaData);


        vv.add(grid);

        if(materiaData == null || materiaData.isEmpty()){
            grid.setVisible(false);
            vv.add(
                    commonComponents.noDataFound()
            );
        }
        else{
            grid.setVisible(true);
        }




        return vv;

}



}
