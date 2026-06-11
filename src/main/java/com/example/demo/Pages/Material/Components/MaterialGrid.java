package com.example.demo.Pages.Material.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Material.MaterialBriefDto;
import com.example.demo.Enums.ActiveInactive;
import com.example.demo.Enums.MaterialType;
import com.example.demo.Enums.Stock;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MaterialGrid {

    CommonComponents commonComponents;
    Common common;


    public MaterialGrid(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }

    public Grid gridHolder(){

        Grid<MaterialBriefDto> grid = new Grid<>(MaterialBriefDto.class,false);
        List<MaterialBriefDto> info = new ArrayList<>();
        info.add(new MaterialBriefDto(
                "https://picsum.photos/200?1",
                "Oak Wood",
                "Premium solid oak wood for furniture manufacturing.",
                ActiveInactive.ACTIVE,
                MaterialType.BAMBOO,
                Stock.In_Stock,
                LocalDateTime.now().minusDays(15)
        ));

        info.add(new MaterialBriefDto(
                "https://picsum.photos/200?2",
                "Leather",
                "High-quality genuine leather suitable for sofas and chairsHigh-quality genuine leather suitable for sofas and chairsHigh-quality genuine leather suitable for sofas and chairsHigh-quality genuine leather suitable for sofas and chairs.",
                ActiveInactive.INACTIVE,
                MaterialType.BAMBOO,
                Stock.Low_Stock,
                LocalDateTime.now().minusDays(8)
        ));
        grid.setItems(info);




        grid.addComponentColumn(e->{

            HorizontalLayout h = new HorizontalLayout();
            h.setAlignItems(FlexComponent.Alignment.CENTER);
            Image image = commonComponents.imageCrafter(e.getImageUrl() == null ? "No_picture.png" : e.getImageUrl(),"90px","90px","10px");
            Span span = commonComponents.spanCrafter(e.getName() == null ? "Unknown" : e.getName(),"activityFeed-name");

            h.add(
                    image,
                    span
            );

            return  h;
        }).setHeader("Image");


        grid.addComponentColumn(e->{

            Span span = commonComponents.spanCrafter(e.getMaterialType() == null ? "Unknown" : String.valueOf(e.getMaterialType()),"stat-example");



            return  span;
        }).setHeader("Type");


        grid.addComponentColumn(e->{


            Span span = commonComponents.spanCrafterWordNoHide(e.getDescription() == null ? "Unknown" : e.getDescription(),"stat-title");



            return  span;
        }).setHeader("Description");

        grid.addComponentColumn(e->{

            Span span = commonComponents.spanCrafter(e.getActiveInactive() == null ? "Unknown" : String.valueOf(e.getActiveInactive()),"activityFeed-name");
            span.addClassName("stock-badge");

            switch (e.getActiveInactive()){
                case ACTIVE -> span.addClassName("stock-in");
                case INACTIVE -> span.addClassName("stock-out");
            }


            return  span;
        }).setHeader("Status");


        return grid;
    }

}
