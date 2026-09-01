package com.example.demo.Pages.ActionLog.Components;


import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.ControllerModels.ActionLogs.ActionLogFeed;
import com.example.demo.ControllerModels.CommonDtos.UserSettings;
import com.example.demo.ControllerModels.Material.MaterialBriefDto;
import com.example.demo.Enums.ActionDesciptionEnum;
import com.example.demo.Enums.ActiveInactive;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.http.HttpMethod;

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



        Grid<ActionLogFeed> grid = new Grid<>(ActionLogFeed.class,false);
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

        grid.addComponentColumn(e -> {

            VerticalLayout v = new VerticalLayout();

            v.setWidth("50px");
            v.setHeight("50px");

            v.getStyle()
                    .set("border-radius", "50%")
                    .set("display", "flex")
                    .set("align-items", "center")
                    .set("justify-content", "center")
                    .set("padding", "0");

            ActionDesciptionEnum action = e.getAction();

            Icon icon = switch (action) {
                case Order_Created,
                     Product_Created,
                     Material_Created,
                     Employee_Created ->
                        commonComponents.iconCrafter(VaadinIcon.CART, "25px", "white");

                case Order_Updated,
                     Product_Updated,
                     Material_Updated,
                     Employee_Updated ->
                        commonComponents.iconCrafter(VaadinIcon.OUTBOX, "25px", "white");

                case Order_Deleted,
                     Product_Deleted,
                     Material_Deleted,
                     Employee_Deleted ->
                        commonComponents.iconCrafter(VaadinIcon.CLOSE, "25px", "white");

                case Order_Status_Change,
                     Product_Status_Change,
                     Material_Status_Change,
                     Employee_Status_Change ->
                        commonComponents.iconCrafter(VaadinIcon.CHECK, "25px", "white");

                case Material_Stock_Change ->
                        commonComponents.iconCrafter(VaadinIcon.CUBES, "25px", "white");

                case Employee_Work_Started ->
                        commonComponents.iconCrafter(VaadinIcon.PLAY, "25px", "white");

                case Employee_Work_Ended ->
                        commonComponents.iconCrafter(VaadinIcon.STOP, "25px", "white");

                case System_Check ->
                        commonComponents.iconCrafter(VaadinIcon.COG, "25px", "white");

                case ALL ->
                        commonComponents.iconCrafter(VaadinIcon.LIST, "25px", "white");
            };




            String color = switch (action) {
                case Order_Created,
                     Product_Created,
                     Material_Created,
                     Employee_Created -> "#22c55e"; // Green

                case Order_Updated,
                     Product_Updated,
                     Material_Updated,
                     Employee_Updated -> "#3b82f6"; // Blue

                case Order_Deleted,
                     Product_Deleted,
                     Material_Deleted,
                     Employee_Deleted -> "#ef4444"; // Red

                case Order_Status_Change,
                     Product_Status_Change,
                     Material_Status_Change,
                     Employee_Status_Change -> "#f59e0b"; // Orange

                case Material_Stock_Change -> "#8b5cf6"; // Purple

                case Employee_Work_Started -> "#14b8a6"; // Teal

                case Employee_Work_Ended -> "#6366f1"; // Indigo

                case System_Check -> "#64748b"; // Gray

                case ALL -> "#94a3b8"; // Light gray
            };

            v.getStyle().set("background", color);



            v.add(icon);


            HorizontalLayout h = new HorizontalLayout();
            h.setAlignItems(FlexComponent.Alignment.CENTER);
            h.setPadding(false);


            VerticalLayout name = new VerticalLayout();
            name.add(
                    commonComponents.spanCrafter(e.getTypeOfActionRecorded().getDisplayName(),"Stat-example"),
                    commonComponents.spanCrafter(e.getActionName(),"stat-example")
            );

            h.add(v,name);

            return h;
        }).setAutoWidth(true).setHeader("What happened");

        grid.addComponentColumn(e->{

            return commonComponents.spanCrafter(common.dateFormatter(e.getCreated()),"stat-example");


        }).setAutoWidth(true).setHeader("When");

        grid.addComponentColumn(e->{

           HorizontalLayout h = new HorizontalLayout();
           h.setPadding(false);
           h.setAlignItems(FlexComponent.Alignment.CENTER);

           Image image = new Image(e.getWhoMadeIt().getImageUrl() == null ? "No_picture.png" : e.getWhoMadeIt().getImageUrl(),e.getWhoMadeIt().getFullName() + "Icon");
            image.setWidth("50px");
            image.setHeight("50px");
            image.getStyle().set("border-radius","50%");


            Span name = commonComponents.spanCrafterWordNoHide(e.getWhoMadeIt().getFullName() ,"stat-example");
            Span role = commonComponents.spanCrafterWordNoHide(e.getWhoMadeIt().getRole().toString(),"stat-description");

            VerticalLayout v = new VerticalLayout();
            v.setPadding(false);
            v.add(
                    name,
                    role
            );


            h.add(
                    image,
                    v
            );

            return h;


        }).setAutoWidth(true).setHeader("Who");



        return vv;

}



}
