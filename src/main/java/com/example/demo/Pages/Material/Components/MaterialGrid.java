package com.example.demo.Pages.Material.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Material.MaterialBriefDto;
import com.example.demo.Enums.ActiveInactive;
import com.example.demo.Enums.MaterialType;
import com.example.demo.Enums.Stock;
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

    public VerticalLayout gridHolder(List<MaterialBriefDto> materiaData){

        VerticalLayout vv = new VerticalLayout();
        vv.setPadding(false);
        vv.setSpacing(false);
        vv.setWidthFull();



        Grid<MaterialBriefDto> grid = new Grid<>(MaterialBriefDto.class,false);
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




        // ======================== Material pic and name =====================================
        grid.addComponentColumn(e->{

            HorizontalLayout h = new HorizontalLayout();
            h.setAlignItems(FlexComponent.Alignment.CENTER);
            Image image = commonComponents.imageCrafter(e.getImageUrl() == null ? "No_picture.png" : e.getImageUrl(),"90px","90px","10px");
            Span span = commonComponents.spanCrafter(e.getName() == null ? "Unknown" : e.getName(),"activityFeed-name");
            Span span2 = commonComponents.spanCrafter(e.getUnitPrice() == null ? "Unknown" :   "Unit price: "+ e.getUnitPrice() + "Eur","stat-title");

            VerticalLayout v = new VerticalLayout();
            v.add(
                    span,
                    span2
            );

            h.add(
                    image,
                    v
            );

            return  h;
        }).setHeader("Material").setAutoWidth(true);

        // ======================== Material type =====================================
        grid.addComponentColumn(e->{

            Span span = commonComponents.spanCrafter(e.getMaterialType() == null ? "Unknown" : String.valueOf(e.getMaterialType().getDisplayName()),"stat-example");
            span.getStyle().set("width", "fit-content");
            span.addClassName("stock-badge");

            if(e.getMaterialType() != null) {
                switch (e.getMaterialType()) {

                    case WOOD, BAMBOO, RATTAN, WICKER, CORK -> {
                        span.getStyle()
                                .set("background", "rgba(56, 189, 248, 0.15)")
                                .set("color", "#38bdf8") // sky blue
                                .set("border", "1px solid rgba(56,189,248,0.3)");
                    }

                    case METAL, CARBON_FIBER -> {
                        span.getStyle()
                                .set("background", "rgba(99, 102, 241, 0.15)")
                                .set("color", "#6366f1") // indigo
                                .set("border", "1px solid rgba(99,102,241,0.3)");
                    }

                    case GLASS, CERAMIC, PORCELAIN, ACRYLIC -> {
                        span.getStyle()
                                .set("background", "rgba(168, 85, 247, 0.15)")
                                .set("color", "#a855f7") // purple
                                .set("border", "1px solid rgba(168,85,247,0.3)");
                    }

                    case LEATHER, FAUX_LEATHER, FABRIC, VELVET, LINEN, COTTON -> {
                        span.getStyle()
                                .set("background", "rgba(14, 165, 233, 0.15)")
                                .set("color", "#0ea5e9") // cyan-blue
                                .set("border", "1px solid rgba(14,165,233,0.3)");
                    }

                    case PLASTIC, RUBBER, FOAM -> {
                        span.getStyle()
                                .set("background", "rgba(236, 72, 153, 0.15)")
                                .set("color", "#ec4899") // pink
                                .set("border", "1px solid rgba(236,72,153,0.3)");
                    }

                    case STONE, MARBLE, GRANITE, CONCRETE -> {
                        span.getStyle()
                                .set("background", "rgba(100, 116, 139, 0.15)")
                                .set("color", "#64748b") // slate
                                .set("border", "1px solid rgba(100,116,139,0.3)");
                    }

                    case MDF, PLYWOOD, PARTICLE_BOARD, LAMINATE -> {
                        span.getStyle()
                                .set("background", "rgba(20, 184, 166, 0.15)")
                                .set("color", "#14b8a6") // teal
                                .set("border", "1px solid rgba(20,184,166,0.3)");
                    }
                }
            }


            return  span;
        }).setHeader("Type").setAutoWidth(true);

        // ======================== Material description=====================================
        grid.addComponentColumn(e->{


            Span span = commonComponents.spanCrafterWordNoHide(e.getDescription() == null ? "Unknown" : e.getDescription(),"stat-title");



            return  span;
        }).setHeader("Description").setResizable(true);

        grid.addComponentColumn(e->{

            Span span = commonComponents.spanCrafter(e.getActiveInactive() == null ? "Unknown" : String.valueOf(e.getActiveInactive().getGetDisplayNames()),"activityFeed-name");
            span.addClassName("stock-badge");
            span.getStyle().set("width", "fit-content");

            switch (e.getActiveInactive()){
                case ACTIVE -> span.addClassName("stock-in");
                case INACTIVE -> span.addClassName("stock-out");
            }


            return  span;
        }).setHeader("Status").setAutoWidth(true);


        // ======================== stock =====================================
        grid.addComponentColumn(e->{

            VerticalLayout v = new VerticalLayout();

            Span span = commonComponents.spanCrafter(e.getStock() == null ? "Unknown" : String.valueOf(e.getStock().getDisplayName()),"activityFeed-name");
            span.addClassName("stock-badge");
            span.getStyle().set("width", "fit-content");

            switch (e.getStock()){
                case In_Stock -> span.addClassName("stock-in");
                case No_Stock -> span.addClassName("stock-out");
                case Low_Stock -> span.addClassName("stock-low");
            }


            Span span2 = commonComponents.spanCrafter(e.getAmountLeft() == null ? "Unknown" :  "Current stock: " + e.getAmountLeft(),"stat-title");
            Span span3 = commonComponents.spanCrafter(e.getMinThresh() == null ? "Unknown" : "Min threshold: " + e.getMinThresh(),"stat-title");


            v.add(
                    span,
                    span2,
                    span3
            );

            return  v;
        }).setHeader("Stock").setAutoWidth(true);

        // ======================== Material create date =====================================
        grid.addComponentColumn(e->{

            String date = common.dateFormatter(e.getCreated(),"MMMM d yyyy");

            Span span = commonComponents.spanCrafter(date,"stat-example");


            return  span;
        }).setHeader("Created").setAutoWidth(true);


        // ======================== Material actions =====================================
        grid.addComponentColumn(e->{


            HorizontalLayout h = new HorizontalLayout();
            Button edit = commonComponents.buttonThemeAndIconNoNavigate("", ButtonVariant.LUMO_ICON, VaadinIcon.PENCIL,"Black");
            Button delete = commonComponents.buttonThemeAndIconNoNavigate("", ButtonVariant.LUMO_ICON, VaadinIcon.TRASH,"Red");

            delete.addClickListener(deleteValue->{
                common.deleteConfirmation(e.getName());
               common.setBooleanConsumer(canDelete->{
                   if(canDelete){
                       System.out.println("deleting material");
                   }
               });
            });

            h.add(
                    delete,
                    edit

            );


            return  h;
        }).setHeader("Actions").setAutoWidth(true);


        return vv;
    }

}
