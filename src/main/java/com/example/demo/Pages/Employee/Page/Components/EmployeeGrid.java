package com.example.demo.Pages.Employee.Page.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Employee.EmployeeBriefDto;
import com.example.demo.Enums.EmployeeAcIn;
import com.example.demo.Services.EmployeeService.EmployeeService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeGrid {

    CommonComponents commonComponents;
    Common common;

    EmployeeService employeeService;


    Map<Long,Dialog> dialogMemory = new HashMap<>();
    Long openTheDialog = 0L;


    public EmployeeGrid(CommonComponents commonComponents, Common common, EmployeeService employeeService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.employeeService = employeeService;
    }

    public VerticalLayout gridHolder(List<EmployeeBriefDto> materiaData){

        VerticalLayout vv = new VerticalLayout();
        vv.addClassName("smooth-panel");
        vv.setPadding(false);
        vv.setSpacing(false);
        vv.setWidthFull();



        Grid<EmployeeBriefDto> grid = new Grid<>(EmployeeBriefDto.class,false);
        grid.setAllRowsVisible(true);
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
            Image image = commonComponents.imageCrafter(e.getProfileImage() == null ? "No_picture.png" : e.getProfileImage(),"90px","90px","10px");
            Span span = commonComponents.spanCrafter(e.getFullName() == null ? "Unknown" : e.getFullName(),"activityFeed-name");
            Span span2 = commonComponents.spanCrafter(e.getGmail() == null ? "Unknown" :   e.getGmail(),"stat-title");

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
        }).setHeader("Employee").setAutoWidth(true);

        // ======================== Material type =====================================
        grid.addComponentColumn(e->{

            Span span = commonComponents.spanCrafter(e.getEmployeeCategory() == null ? "Unknown" : String.valueOf(e.getEmployeeCategory().getDisplayName()),"stat-example");
            span.getStyle().set("width", "fit-content");
            span.addClassName("stock-badge");




            if (e.getEmployeeCategory() != null) {
                switch (e.getEmployeeCategory()) {

                    case WAREHOUSE -> {
                        span.getStyle()
                                .set("background", "rgba(245, 158, 11, 0.15)")
                                .set("color", "#f59e0b")
                                .set("border", "1px solid rgba(245,158,11,0.3)");
                    }

                    case WORKER -> {
                        span.getStyle()
                                .set("background", "rgba(34, 197, 94, 0.15)")
                                .set("color", "#22c55e")
                                .set("border", "1px solid rgba(34,197,94,0.3)");
                    }


                    case ASSEMBLER -> {
                        span.getStyle()
                                .set("background", "rgba(6, 182, 212, 0.15)")
                                .set("color", "#06b6d4")
                                .set("border", "1px solid rgba(6,182,212,0.3)");
                    }

                    case CARPENTER -> {
                        span.getStyle()
                                .set("background", "rgba(180, 83, 9, 0.15)")
                                .set("color", "#b45309")
                                .set("border", "1px solid rgba(180,83,9,0.3)");
                    }





                    case FINISHER -> {
                        span.getStyle()
                                .set("background", "rgba(20, 184, 166, 0.15)")
                                .set("color", "#14b8a6")
                                .set("border", "1px solid rgba(20,184,166,0.3)");
                    }


                    case MANAGER -> {
                        span.getStyle()
                                .set("background", "rgba(239, 68, 68, 0.15)")
                                .set("color", "#ef4444")
                                .set("border", "1px solid rgba(239,68,68,0.3)");
                    }


                }
            }


            return  span;
        }).setHeader("Role").setAutoWidth(true);

        // ======================== Material description=====================================
        grid.addComponentColumn(e->{


            Span span = commonComponents.spanCrafterWordNoHide(e.getEmployeeDepartment() == null ? "Unknown" : e.getEmployeeDepartment().toString(),"stat-title");
            span.addClassName("stock-badge");
            span.getStyle().set("width", "fit-content");

            if(e.getEmployeeDepartment() != null) {
                switch (e.getEmployeeDepartment()) {

                    case ASSEMBLY -> {
                        span.getStyle()
                                .set("background", "rgba(56, 189, 248, 0.15)")
                                .set("color", "#38bdf8")
                                .set("border", "1px solid rgba(56,189,248,0.3)");
                    }

                    case FINISHING -> {
                        span.getStyle()
                                .set("background", "rgba(99, 102, 241, 0.15)")
                                .set("color", "#6366f1") // indigo
                                .set("border", "1px solid rgba(99,102,241,0.3)");
                    }

                    case LOGISTICS -> {
                        span.getStyle()
                                .set("background", "rgba(236, 72, 153, 0.15)")
                                .set("color", "#ec4899") // pink
                                .set("border", "1px solid rgba(236,72,153,0.3)");

                    }
                    case PRODUCTION -> {
                        span.getStyle()
                                .set("background", "rgba(100, 116, 139, 0.15)")
                                .set("color", "#64748b") // slate
                                .set("border", "1px solid rgba(100,116,139,0.3)");
                    }
                }


                }


            return  span;
        }).setHeader("Department").setAutoWidth(true);

        grid.addComponentColumn(e->{

            Span span = commonComponents.spanCrafter(e.getEmployeeAcIn() == null ? "Unknown" : String.valueOf(e.getEmployeeAcIn().getDisplayName()),"activityFeed-name");
            span.addClassName("stock-badge");
            span.getStyle().set("width", "fit-content");

            if(e.getEmployeeAcIn() != null) {
                switch (e.getEmployeeAcIn()) {
                    case ACTIVE -> span.addClassName("stock-in");
                    case INACTIVE -> span.addClassName("stock-low");
                    case ON_LEAVE -> span.addClassName("stock-out");
                }
            }


            return  span;
        }).setHeader("Status").setAutoWidth(true);


        // ======================== stock =====================================
        grid.addComponentColumn(e->{



            Span span = commonComponents.spanCrafter(e.getHourlyRate() == null ? "Unknown" : e.getHourlyRate() + " Eur","activityFeed-name");


            return  span;
        }).setHeader("Hourly rate").setAutoWidth(true);

        // ======================== Material create date =====================================
        grid.addComponentColumn(e->{

            String date = common.dateFormatter(e.getCreated());

            Span span = commonComponents.spanCrafter(date,"stat-example");


            return  span;
        }).setHeader("Joined").setAutoWidth(true);


        // ======================== Material actions =====================================
        grid.addComponentColumn(e->{

            for (var dialog : dialogMemory.values()) {
                dialog.close();
            }

            Dialog dialog = new Dialog();
            dialog.setWidth("1000px");

            dialogMemory.put(e.getId(),dialog);

            Button close = new Button("Close", ee-> dialog.close());

            dialog.getFooter().add(close);

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



            HorizontalLayout h = new HorizontalLayout();
            Button edit = commonComponents.buttonThemeAndIconNoNavigate("", ButtonVariant.LUMO_ICON, VaadinIcon.PENCIL,"Blue");


            edit.addClickListener(editValue->{

                common.customNavigate("EmployeesEdit/" + e.getId());
            });


            Button delete = commonComponents.buttonThemeAndIconNoNavigate("", ButtonVariant.LUMO_ICON, VaadinIcon.TRASH,"Red");

            if(e.getEmployeeAcIn().equals(EmployeeAcIn.INACTIVE)){
                delete.setVisible(false);
            }

            delete.addClickListener(deleteValue->{
                common.deleteConfirmation(e.getFullName());
               common.setBooleanConsumer(canDelete->{
                   if(canDelete){
                       employeeService.deleteEmployee(e.getId());
                   }
               });
            });

            Button open = new Button("Actions");

            open.addClickListener(ew->{
                dialog.open();
                openTheDialog = e.getId();
            });

            h.add(
                    delete,
                    edit,
                    open

            );



            HorizontalLayout editEmployee = actions(VaadinIcon.PENCIL,"Edit employee","Modify employee details","BLUE");
            editEmployee.setWidthFull();

            editEmployee.addClickListener(ee->{
                dialogMemory.get(e.getId()).close();
                common.customNavigate("EmployeesEdit/" + e.getId());
            });

            HorizontalLayout viewWorkHours = actions(VaadinIcon.CLOCK,"View work hours","View mini statistics of employee hours","BLUE");
            viewWorkHours.setWidthFull();

            HorizontalLayout viewAssignedOrders = actions(VaadinIcon.NOTEBOOK,"View assigned orders","See orders this employee worked on","BLUE");
            viewAssignedOrders.setWidthFull();

            HorizontalLayout viewPerformance = actions(VaadinIcon.CHART,"View performance","Work statistics and productivity","BLUE");
            viewPerformance.setWidthFull();




            HorizontalLayout deleteEmployee = actions(VaadinIcon.TRASH,"Delete employee","Remove from system","RED");
            deleteEmployee.setWidthFull();

            if(e.getEmployeeAcIn().equals(EmployeeAcIn.INACTIVE)){
                deleteEmployee.setEnabled(false);
                deleteEmployee.addClassName("island-disabled");
            }

            deleteEmployee.addClickListener(ew ->{
                common.deleteConfirmation(e.getFullName());
                common.setBooleanConsumer(canDelete->{
                    if(canDelete){
                        employeeService.deleteEmployee(e.getId());
                    }
                });
            });






            VerticalLayout actions = new VerticalLayout();
            actions.setPadding(false);

            actions.add(
                    commonComponents.spanCrafter("Actions","activityFeed-name"),
                    editEmployee,
                    viewWorkHours,
                    viewAssignedOrders,
                    viewPerformance,
                    deleteEmployee

                    );


            VerticalLayout quickActions = new VerticalLayout();
            quickActions.setPadding(false);

            HorizontalLayout toggleActiveStatus = actions(VaadinIcon.POWER_OFF,"Toggle active status","Active or deactivate employee","BLUE");
            toggleActiveStatus.setWidthFull();

            HorizontalLayout changeRoleOrDepartment = actions(VaadinIcon.CHART,"Change role or department","Update employee position","BLUE");
            changeRoleOrDepartment.setWidthFull();


            quickActions.add(
                    commonComponents.spanCrafter("Quick actions","activityFeed-name"),
                    toggleActiveStatus,
                    changeRoleOrDepartment



            );



            leftSide.add(
                    leftSideEmp(e),
                    expandedData(e),
                    quickActions
            );

            rightSide.add(
                    actions
            );

            dialog.add(
                    bothSides
            );







            return  h;
        }).setHeader("Actions").setAutoWidth(true);


        return vv;
    }


    public VerticalLayout leftSideEmp(EmployeeBriefDto e){



        VerticalLayout main = new VerticalLayout();
        main.setPadding(false);

        Image image = commonComponents.imageCrafter(e.getProfileImage() == null ? "No_picture.png" : e.getProfileImage(),"100px","100px","50%");

        Span activity = commonComponents.spanCrafter(e.getEmployeeAcIn().getDisplayName(),"activityFeed-name");
        activity.addClassName("stock-badge");
        activity.getStyle().set("width", "fit-content");

        switch (e.getEmployeeAcIn()) {
            case ACTIVE -> activity.addClassName("stock-in");
            case INACTIVE -> activity.addClassName("stock-out");
        }


        HorizontalLayout topHolder = new HorizontalLayout();
        topHolder.setWidthFull();
        topHolder.setAlignItems(FlexComponent.Alignment.CENTER);
        VerticalLayout extraData = new VerticalLayout();
        extraData.setSpacing(false);

        extraData.add(
                commonComponents.spanCrafterWordNoHide(e.getFullName(),"stat-example"),
                activity,
                commonComponents.spanCrafterWordNoHide(e.getEmployeeCategory().getDisplayName() ,"stat-description"),
                commonComponents.spanCrafterWordNoHide(String.format("Employed since %s",common.dateFormatter(e.getCreated())),"stat-description")
        );

        topHolder.add(
                image,
                extraData
        );


        main.add(topHolder);


        return  main;


    }


    public VerticalLayout expandedData(EmployeeBriefDto e){
        VerticalLayout v = new VerticalLayout();
        v.setPadding(false);
        v.setWidthFull();

        v.addClassName("island");

        v.add(
                specCrafter("Full name", e.getFullName()),
                specCrafter("Email address",e.getGmail()),
                specCrafter("Phone number","+254854"),
                specCrafter("Date of birth",common.dateFormatter(e.getCreated())),
                specCrafter("Address","+254854")
        );



        return v;
    }




    public HorizontalLayout specCrafter( String name, String value){

        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();

        h.add(
                commonComponents.spanCrafter(name,"stat-example"),
                commonComponents.spaceFiller(),
                commonComponents.spanCrafter(value,"stat-example")
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


        h.add(
                commonComponents.itemInsideTheBox(icon,allColor,backgroundColor),
                new Div(commonComponents.spanCrafter(name,"stat-example"),
                        commonComponents.spanCrafterWordNoHide(desc,"stat-description")),
                pressIndicator
        );



        return h;
    }

}
