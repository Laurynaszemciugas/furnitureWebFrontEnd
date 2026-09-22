package com.example.demo.Pages.Material.Page.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.InternetScraper.Scraper;
import com.example.demo.Common.Logic.InternetScraper.View;
import com.example.demo.ControllerModels.Material.MaterialBriefDto;
import com.example.demo.Enums.ActiveInactive;
import com.example.demo.Enums.MaterialType;
import com.example.demo.Enums.Stock;
import com.example.demo.Services.Material.MaterialService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.popover.Popover;
import com.vaadin.flow.component.popover.PopoverPosition;
import com.vaadin.flow.component.popover.PopoverVariant;
import com.vaadin.flow.dom.Style;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;

@Setter
public class MaterialGrid {

    CommonComponents commonComponents;
    Common common;

    MaterialService materialService;


    View view;
    Scraper scraper;


    Consumer<String> updateStock;
    Consumer<String> changeDeliveryDate;
    Consumer<String> viewMaterialMovement;



    public MaterialGrid(CommonComponents commonComponents, Common common,MaterialService materialService,View view,Scraper scraper) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.materialService = materialService;

        this.view = view;
        this.scraper = scraper;

    }

    public VerticalLayout gridHolder(List<MaterialBriefDto> materiaData){

        VerticalLayout vv = new VerticalLayout();
        vv.addClassName("smooth-panel");
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
            System.out.println(e.getImageUrl());
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
        }).setHeader("Material").setAutoWidth(true).setKey("Material");

        // ======================== Material type =====================================
        grid.addComponentColumn(e->{

            Span span = commonComponents.spanCrafter(e.getMaterialType() == null ? "Unknown" : String.valueOf(e.getMaterialType().getDisplayName()),"stat-example");
            span.getStyle().set("width", "fit-content");
            span.addClassName("stock-badge");

            if(e.getMaterialType() != null) {
                switch (e.getMaterialType()) {

                    case WOOD, RATTAN, CORK -> {
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

                    case LEATHER, FABRIC, VELVET, LINEN, COTTON -> {
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

                    case  PLYWOOD, PARTICLE_BOARD, LAMINATE -> {
                        span.getStyle()
                                .set("background", "rgba(20, 184, 166, 0.15)")
                                .set("color", "#14b8a6") // teal
                                .set("border", "1px solid rgba(20,184,166,0.3)");
                    }
                }
            }


            return  span;
        }).setHeader("Type").setAutoWidth(true).setKey("Type");

        // ======================== Material description=====================================
        grid.addComponentColumn(e->{


            Span span = commonComponents.spanCrafterWordNoHide(e.getDescription() == null ? "Unknown" : e.getDescription(),"stat-title");



            return  span;
        }).setHeader("Description").setResizable(true).setKey("Description");

        grid.addComponentColumn(e->{

            Span span = commonComponents.spanCrafter(e.getActiveInactive() == null ? "Unknown" : String.valueOf(e.getActiveInactive().getGetDisplayNames()),"activityFeed-name");
            span.addClassName("stock-badge");
            span.getStyle().set("width", "fit-content");

            switch (e.getActiveInactive()){
                case ACTIVE -> span.addClassName("stock-in");
                case INACTIVE -> span.addClassName("stock-out");
            }


            return  span;
        }).setHeader("Status").setAutoWidth(true).setKey("Status");


        // ======================== stock =====================================
        grid.addComponentColumn(e->{

            VerticalLayout v = new VerticalLayout();

            Span span = commonComponents.spanCrafter(e.getStock() == null ? "Unknown" : String.valueOf(e.getStock().getDisplayName()),"activityFeed-name");
            span.addClassName("stock-badge");
            span.getStyle().set("width", "fit-content");

            if(e.getStock() != null) {
                switch (e.getStock()) {
                    case In_Stock -> span.addClassName("stock-in");
                    case No_Stock -> span.addClassName("stock-out");
                    case Low_Stock -> span.addClassName("stock-low");
                }
            }


            Span span2 = commonComponents.spanCrafter(e.getAmountLeft() == null ? "Unknown" :  "Current stock: " + e.getAmountLeft(),"stat-title");
            Span span3 = commonComponents.spanCrafter(e.getMinThresh() == null ? "Unknown" : "Min threshold: " + e.getMinThresh(),"stat-title");
            Span span4 = commonComponents.spanCrafter(e.getMinThresh() == null ? "Unknown" : "Reserved: " + e.getReserved(),"stat-title");






            v.add(
                    span,
                    span2,
                    span3,
                    span4
            );

            return  v;
        }).setHeader("Stock").setAutoWidth(true).setKey("Stock");

        // ======================== Material create date =====================================
        grid.addComponentColumn(e->{

            String date = common.dateFormatter(e.getCreated());

            Span span = commonComponents.spanCrafter(date,"stat-example");


            return  span;
        }).setHeader("Created").setAutoWidth(true).setKey("Created");


        // ======================== Material actions =====================================
        grid.addComponentColumn(e-> {







            Dialog dialog = new Dialog();
            dialog.setWidth("1000px");


            Button openDialog = new Button("Ff",ee-> dialog.open());

            Button closeDialog = new Button("Close", close-> dialog.close());
            dialog.getFooter().add(closeDialog);


            HorizontalLayout bothSides = new HorizontalLayout();
            bothSides.addClassName("layout-flex");
            bothSides.setWidthFull();


            VerticalLayout leftSide = new VerticalLayout();
            leftSide.setWidth("400px");

            VerticalLayout rightSide = new VerticalLayout();
            rightSide.setWidth("400px");





            bothSides.add(
                    leftSide,
                    rightSide
            );

            bothSides.expand(leftSide);

            HorizontalLayout imageText = new HorizontalLayout();

            imageText.add(
                    commonComponents.imageCrafter(e.getImageUrl(),"100px","100px","10px")

            );

            Span stock = commonComponents.spanCrafter(e.getStock() == null ? "Unknown" : String.valueOf(e.getStock().getDisplayName()),"activityFeed-name");
            stock.addClassName("stock-badge");
            stock.getStyle().set("width", "fit-content");

            switch (e.getStock()) {
                case In_Stock -> stock.addClassName("stock-in");
                case No_Stock -> stock.addClassName("stock-out");
                case Low_Stock -> stock.addClassName("stock-low");
            }

            VerticalLayout textHolder = new VerticalLayout();
            textHolder.setPadding(false);
            textHolder.getStyle().setGap("10px");
            textHolder.add(
                    commonComponents.spanCrafter(e.getName(),"stat-value"),
                    commonComponents.spanCrafter(String.format("%s ● %s",e.getMaterialType().getDisplayName(),e.getMaterialTexture().getDisplayName()),"stat-description"),
                    stock
             );

            imageText.add(
                    textHolder
            );


            VerticalLayout quickActions = new VerticalLayout();
            quickActions.setPadding(false);
            quickActions.add(
                    commonComponents.spanCrafter("Quick actions","activityFeed-name"),
                    quickActionCrafter(VaadinIcon.CUBE,"Update stock","Add or remove material stock","Update",updateStock),
                    quickActionCrafter(VaadinIcon.CALENDAR,"Change delivery date","set a new estimated delivery date","Set date",changeDeliveryDate),
                    quickActionCrafter(VaadinIcon.CLOCK,"View stock history","See all stock changes and movement","View",viewMaterialMovement)
            );

            leftSide.add(
                    imageText,
                    specsOfTheMaterial(e),
                    quickActions
            );


            HorizontalLayout editMaterial = actions(VaadinIcon.PENCIL,"Edit material","Modify material details","BLUE");
            editMaterial.setWidthFull();

            editMaterial.addClickListener(ee->{
                dialog.close();
                common.customNavigate("MaterialEdit/" +e.getId());
            });

            HorizontalLayout searchOnline = actions(VaadinIcon.GLOBE_WIRE,"Search online","Find suppliers and prices","BLUE");
            searchOnline.setWidthFull();

            searchOnline.addClickListener(ee->{
                view.layout(e.getName());
            });

            HorizontalLayout viewRelatedProducts = actions(VaadinIcon.CALENDAR,"View related products","See products using this material","BLUE");
            viewRelatedProducts.setWidthFull();

            viewRelatedProducts.addClickListener(ee->{

            });


            HorizontalLayout deleteMaterial = actions(VaadinIcon.TRASH,"Delete material","Remove from the system 'If possible'","RED");
            deleteMaterial.setWidthFull();

            deleteMaterial.addClickListener(ee-> {
                common.deleteConfirmation(e.getName());
                common.setBooleanConsumer(canDelete -> {
                    if (canDelete) {
                        dialog.close();
                        materialService.removeProduct(e.getId());
                    }
                });
            });

            if(e.getActiveInactive().equals(ActiveInactive.INACTIVE)){
                deleteMaterial.setEnabled(false);
                deleteMaterial.addClassName("island-disabled");
            }




            VerticalLayout actions = new VerticalLayout();
            actions.setPadding(false);

            actions.add(
                    commonComponents.spanCrafter("Actions","activityFeed-name"),
                    editMaterial,
                    searchOnline,
                    viewRelatedProducts,
                    deleteMaterial

            );

            rightSide.add(
                    actions
            );



            dialog.add(
                    bothSides
            );



            return openDialog;
        }).setHeader("Actions").setAutoWidth(true).setKey("Actions");


        vv.add(
                testForGridRemoval(grid, "Material","Type","Description","Status","Stock","Actions","Created")
        );

        return vv;
    }


    public Button testForGridRemoval(Grid grid, String... columnsList ){


        List<String> columns = List.of(columnsList);

        Button target = new Button("Test");

        Popover popover = new Popover();
        popover.setModal(true);
        popover.setBackdropVisible(true);
        popover.setPosition(PopoverPosition.BOTTOM_END);
        popover.setTarget(target);


        VerticalLayout v = new VerticalLayout();

        Button showAll = new Button("Show all");
        Button reset = new Button("Reset");


        CheckboxGroup<String> group = new CheckboxGroup<>();
        group.setItems(columns);
        group.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        Set<String> defaultColumns = Set.of("Material", "Type", "Description",
                "Status");
        group.setValue(defaultColumns);


        group.addValueChangeListener((e) -> {


            for(var s : columns){
                grid.getColumnByKey(s).setVisible(e.getValue().contains(s));
            }


        });

        reset.addClickListener(e->{
           group.setValue(defaultColumns);
        });

        showAll.addClickListener(e->{
           group.setValue(new HashSet<>(columns));
        });

        v.add(
                group,
                reset,
                showAll
        );

        popover.add(
                v
        );


        return target;
    }



    public VerticalLayout specsOfTheMaterial(MaterialBriefDto mat){

        VerticalLayout island = new VerticalLayout();
        island.addClassName("island");
        island.setWidthFull();

        island.add(
                specCrafter(VaadinIcon.CUBES, "Current stock", String.format("%d %s", mat.getAmountLeft(), mat.getUnit())),

                specCrafter(VaadinIcon.EXCLAMATION_CIRCLE, "Minimum threshold", String.format("%d %s", mat.getMinThresh(), mat.getUnit())),

                specCrafter(VaadinIcon.EURO, "Unit price", String.format("€ %.2f / %s", mat.getUnitPrice(), mat.getUnit())),

                specCrafter(VaadinIcon.SCALE, "Unit", mat.getUnit()),

                specCrafter(VaadinIcon.TAGS, "Type", mat.getType().getDisplayName()),

                specCrafter(VaadinIcon.PICTURE, "Texture", mat.getMaterialTexture().getDisplayName())
        );


        return island;

    }
    public HorizontalLayout specCrafter(VaadinIcon icon, String name, String value){

        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();

        h.add(
                commonComponents.iconCrafter(icon,"25px","grey"),
                commonComponents.spanCrafter(name,"stat-example"),
                commonComponents.spaceFiller(),
                commonComponents.spanCrafter(value,"stat-example")
        );


        return h;

    }




    public HorizontalLayout quickActionCrafter(VaadinIcon icon, String name, String desc, String buttonName, Consumer<String> actionEvent){
        HorizontalLayout h = new HorizontalLayout();
        h.setAlignItems(FlexComponent.Alignment.CENTER);
        h.setWidthFull();
        h.addClassName("island");
        h.addClassName("layout-flex");

        VerticalLayout iconHolder = new VerticalLayout();
        iconHolder.setHeight("60px");
        iconHolder.setWidth("70px");
        iconHolder.getStyle().setBorderRadius("20px");
        iconHolder.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        iconHolder.setAlignItems(FlexComponent.Alignment.CENTER);


        iconHolder.getStyle().set("background-color","rgba(59, 130, 246, 0.18)");
        iconHolder.add(commonComponents.iconCrafter(icon,"30px","Royalblue"));

        Button universalButton = new Button(buttonName);

        universalButton.addClickListener(e->{
           actionEvent.accept("yoo");
        });

        h.add(
                iconHolder,
                new Div(commonComponents.spanCrafter(name,"stat-example"),
                        commonComponents.spanCrafter(desc,"stat-description")),
                commonComponents.spaceFiller(),
                universalButton
        );



        return h;
    }

    public HorizontalLayout actions(VaadinIcon icon, String name, String desc, String color){
        HorizontalLayout h = new HorizontalLayout();
        h.setAlignItems(FlexComponent.Alignment.CENTER);
        h.setWidthFull();
        h.addClassName("island-hover");

        h.getStyle().set("position","relative");

        Icon pressIndicator = commonComponents.iconCrafter(VaadinIcon.ANGLE_RIGHT,"25px","grey");
        pressIndicator.getStyle().set("position","absolute").set("right","10px").set("top","40%");

        String backgroundColor = "";
        String allColor = "";

        if(color.equals("BLUE")){
            backgroundColor = "rgba(59, 130, 246, 0.18)";
            allColor = "RoyalBlue";
        }
        if(color.equals("RED")){
            backgroundColor = "rgba(239, 68, 68, 0.18)";
            allColor = "Red";
        }



        VerticalLayout iconHolder = new VerticalLayout();
        iconHolder.setHeight("60px");
        iconHolder.setWidth("70px");
        iconHolder.getStyle().setBorderRadius("20px");
        iconHolder.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        iconHolder.setAlignItems(FlexComponent.Alignment.CENTER);


        iconHolder.getStyle().set("background-color",backgroundColor);
        iconHolder.add(commonComponents.iconCrafter(icon,"30px",allColor));




        h.add(
                iconHolder,
                new Div(commonComponents.spanCrafter(name,"stat-example"),
                        commonComponents.spanCrafterWordNoHide(desc,"stat-description")),
                pressIndicator
        );



        return h;
    }

}
//Button target = new Button(commonComponents.iconCrafter(VaadinIcon.ELLIPSIS_DOTS_V,"30px","Blue"));
//            target.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
//            target.setAriaLabel("Material actions");
//
//
//Popover popover = new Popover();
//            popover.setTarget(target);
//            popover.setWidth("250px");
//            popover.setModal(true);
//            popover.addThemeVariants(PopoverVariant.ARROW);
//
//
//Span title = new Span("Material actions");
//            title.getStyle()
//                    .set("font-weight", "600")
//                    .set("font-size", "var(--lumo-font-size-m)");
//
//Span materialName = new Span(e.getName());
//            materialName.getStyle()
//                    .set("font-size", "var(--lumo-font-size-s)")
//                    .set("color", "var(--lumo-secondary-text-color)")
//                    .set("margin-top", "2px");
//
//VerticalLayout header = new VerticalLayout(
//        title,
//        materialName
//);
//
//            header.setSpacing(false);
//
//
//HorizontalLayout h = new HorizontalLayout();
//Button edit = commonComponents.buttonThemeAndIconNoNavigate("Edit material", ButtonVariant.LUMO_TERTIARY, VaadinIcon.PENCIL,"BLUE");
//            edit.setWidthFull();
//            edit.getStyle().set("justify-content", "flex-start");
//
//
//            h.add(
//        target
//
//        );
//
//            edit.addClickListener(editValue->{
//
//        common.customNavigate("MaterialEdit/" +e.getId());
//        });
//
//
//Button delete = commonComponents.buttonThemeAndIconNoNavigate("Delete material", ButtonVariant.LUMO_TERTIARY, VaadinIcon.TRASH,"Red");
//            delete.setWidthFull();
//            delete.getStyle().set("justify-content", "flex-start");
//
//
//
//            if(e.getActiveInactive().equals(ActiveInactive.INACTIVE)){
//        delete.setVisible(false);
//            }
//
//                    delete.addClickListener(deleteValue->{
//        common.deleteConfirmation(e.getName());
//        common.setBooleanConsumer(canDelete->{
//        if(canDelete){
//        materialService.removeProduct(e.getId());
//        }
//        });
//        });
//
//Button checkInternet = commonComponents.buttonThemeAndIconNoNavigate("Search online", ButtonVariant.LUMO_TERTIARY, VaadinIcon.GLOBE_WIRE,"BLUE");
//            checkInternet.setWidthFull();
//            checkInternet.getStyle().set("justify-content", "flex-start");
//
//            checkInternet.addClickListener(ew->{
//        view.layout(e.getName());
//        });
//
//
//VerticalLayout v = new VerticalLayout();
//            v.add(
//        commonComponents.spanCrafter("Material actions","stat-example"),
//                    commonComponents.spanCrafter(e.getName(),"stat-description")
//        );
//        v.getStyle().setGap("0px");
//
//VerticalLayout actions = new VerticalLayout(
//        edit,
//        delete,
//        checkInternet
//);
//
//            actions.setSpacing(false);
//            actions.setWidthFull();
//
//            popover.add(
//        header,
//        actions
//        );
//
//
//            return  h;