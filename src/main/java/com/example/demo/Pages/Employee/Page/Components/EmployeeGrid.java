package com.example.demo.Pages.Employee.Page.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Paganation;
import com.example.demo.ControllerModels.CommonDtos.WorkDay;
import com.example.demo.ControllerModels.CommonDtos.WorkDone;
import com.example.demo.ControllerModels.Employee.EmployeeBriefDto;
import com.example.demo.DTOS.WorkDay.WorkDayMiniStats;
import com.example.demo.Enums.ActiveInactive;
import com.example.demo.Enums.EmployeeAcIn;
import com.example.demo.Services.EmployeeService.EmployeeService;
import com.example.demo.Services.WorkDoneService.WorkDoneService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
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

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeGrid {

    CommonComponents commonComponents;
    Common common;

    EmployeeService employeeService;
    WorkDoneService workDoneService;

    Map<Long,Dialog> dialogMemory = new HashMap<>();
    Long openTheDialog = 0L;

    VerticalLayout workHoursHolder = new VerticalLayout();

    Paganation paganation;

    int page = 0;

    public EmployeeGrid(CommonComponents commonComponents, Common common, EmployeeService employeeService,WorkDoneService workDoneService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.employeeService = employeeService;
        this.workDoneService = workDoneService;

        this.paganation = new Paganation();


        workHoursHolder.setWidthFull();
        workHoursHolder.setPadding(false);

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

            viewWorkHours.addClickListener(ew->{
                viewWorkHours(e);
            });

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

            HorizontalLayout toggleActiveStatus = toggleBetween(e.getId(),VaadinIcon.POWER_OFF,"Toggle active status","Active or deactivate employee",e.getEmployeeAcIn());
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



            if(openTheDialog.equals(e.getId())){
                dialogMemory.get(e.getId()).open();
            }



            return  h;
        }).setHeader("Actions").setAutoWidth(true);


        return vv;
    }


    public void viewWorkHours(EmployeeBriefDto e){

        Dialog dialog =new Dialog();
        dialog.setWidth("1000px");

         VerticalLayout v = new VerticalLayout();

        HorizontalLayout miniStatHolder = new HorizontalLayout();
        miniStatHolder.addClassName("layout-flex");
        miniStatHolder.setWidthFull();

        WorkDayMiniStats workDayMiniStats = workDoneService.getQuickActionWorkHoursMiniStats(e.getId());

        miniStatHolder.add(
                commonComponents.miniStatOfQuick(VaadinIcon.CLOCK,"Total hours",common.convertMinutesToTimeLong(workDayMiniStats.getTotalMinutes()),""),
                commonComponents.miniStatOfQuick(VaadinIcon.CALENDAR,"Work days",workDayMiniStats.getWorkDay(),""),
                commonComponents.miniStatOfQuick(VaadinIcon.WORKPLACE,"Total tasks",workDayMiniStats.getTotalWorkDone(),""),
                commonComponents.miniStatOfQuick(VaadinIcon.CLOCK,"Avg time",common.convertMinutesToTimeDouble(workDayMiniStats.getAverageDay()),"Expected 8h")

        );


        HorizontalLayout filters = new HorizontalLayout();
        filters.setAlignItems(FlexComponent.Alignment.BASELINE);
        filters.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        filters.setWidthFull();

        DatePicker fromDate = new DatePicker("From");
        fromDate.setValue(LocalDate.now());

        DatePicker toDate = new DatePicker("To");
        toDate.setValue(LocalDate.now().plusMonths(1));

        Button check = new Button("Look for data");

        check.addClickListener(ew->{

            loadData(e.getId(),fromDate.getValue(),toDate.getValue());
            setNewPage();


        });

        paganation.setOnPageChange(ew->{
            page = ew - 1;
            loadData(e.getId(),fromDate.getValue(),toDate.getValue());
        });


        loadData(e.getId(),fromDate.getValue(),toDate.getValue());

        filters.add(
                fromDate,toDate,check
        );







        v.add(
                commonComponents.descriptionCrafter("Work hours","View worked hours and time logs for " + e.getFullName()),
                miniStatHolder,
                filters,
                workHoursHolder
        );


        dialog.add(
                v
        );



        dialog.open();

    }


    public void loadData(Long id, LocalDate from, LocalDate to){

        workHoursHolder.removeAll();

        List<WorkDay> workDones = workDoneService.allInfoAccordingToEmployee(id, from, to,page);
        Grid<WorkDay> grid = new Grid<>(WorkDay.class,false);

        grid.addComponentColumn(e->{

            return commonComponents.spanCrafter(common.dateFormatter(e.getWorkDayCreated()), "stat-example");

        }).setHeader("WorkDay started").setAutoWidth(true);

        grid.addComponentColumn(e->{

            return commonComponents.spanCrafter(common.convertMinutesToTimeLong(e.getWorkedForMinutes()), "stat-example");

        }).setHeader("Time worked").setAutoWidth(true);

        grid.addComponentColumn(e->{

            return commonComponents.spanCrafter(common.dateFormatter(e.getWorkDayEnd()), "stat-example");

        }).setHeader("Work date ended").setAutoWidth(true);


        grid.addComponentColumn(e->{

            Dialog dialog = new Dialog();
            dialog.setWidth("600px");


            Button button = new Button("See work done");
            button.addThemeVariants(ButtonVariant.PRIMARY);



            Grid<WorkDone> grid1 = new Grid<>(WorkDone.class,false);
            grid1.setItems(e.getWorkDone());
            grid1.addComponentColumn(ew->{

                return commonComponents.spanCrafter(common.dateFormatter(ew.getStarted()),"stat-example");

            }).setAutoWidth(true).setHeader("Started");

            grid1.addComponentColumn(ew->{

                return commonComponents.spanCrafter(common.dateFormatter(ew.getStarted()),"stat-example");

            }).setAutoWidth(true).setHeader("Started");

            grid1.addComponentColumn(ew->{

                return commonComponents.spanCrafterWordNoHide(ew.getWhatWasDone(),"stat-example");

            }).setWidth("200px").setHeader("What was done");


            grid1.addComponentColumn(ew->{

                return commonComponents.spanCrafterWordNoHide("#" + ew.getOrder().getId(),"stat-example");

            }).setAutoWidth(true).setHeader("Order");




            button.addClickListener(ew->{
                dialog.add(
                        grid1
                );
                dialog.open();
            });




            return button;

        }).setHeader("Expand work done").setAutoWidth(true);












        grid.setItems(workDones);

        workHoursHolder.add(
                grid,
                paganation.buttonHolder(Math.toIntExact(workDoneService.getTotalPagesInQuickActions(id, from, to)))
        );

    }






    // Emp img name small info
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

    // expanded info island
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

    // expanded info step crafter
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

    // just actions buttons
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

    // Toggle between Active and Inactive quick action
    public HorizontalLayout toggleBetween(Long id,VaadinIcon icon, String name, String desc, EmployeeAcIn activeInactive){
        HorizontalLayout h = new HorizontalLayout();
        h.setAlignItems(FlexComponent.Alignment.CENTER);
        h.setWidthFull();
        h.addClassName("island-hover");

        h.getStyle().set("position","relative");

        Icon pressIndicator = commonComponents.iconCrafter(VaadinIcon.ANGLE_RIGHT,"25px","grey");
        pressIndicator.getStyle().set("position","absolute").set("right","10px").set("top","40%");

        String backgroundColor = "";
        String allColor = "";

        if(activeInactive.equals(EmployeeAcIn.ACTIVE)){
            backgroundColor = "rgba(34, 197, 94, 0.18)";
            allColor = "Green";
        }
        if(activeInactive.equals(EmployeeAcIn.INACTIVE)){
            backgroundColor = "rgba(239, 68, 68, 0.18)";
            allColor = "Red";
        }


        h.addClickListener(e->{

            EmployeeAcIn thing;

            if(activeInactive.equals(EmployeeAcIn.ACTIVE)){
                thing = EmployeeAcIn.INACTIVE;
            }
            else {
                thing = EmployeeAcIn.ACTIVE;
            }

           employeeService.updateEmployeeActiveStatus(id,thing);
            reloadDisplay();
        });


        h.add(
                commonComponents.itemInsideTheBox(icon,allColor,backgroundColor),
                new Div(commonComponents.spanCrafter(name,"stat-example"),
                        commonComponents.spanCrafterWordNoHide(desc,"stat-description")),
                pressIndicator
        );



        return h;
    }


    public void reloadDisplay(){
        Dialog dialog1 = dialogMemory.get(openTheDialog);
        dialog1.add(
                common.loadingOverlay("Reloading data", UI.getCurrent())
        );
    }


    public void setNewPage(){
        page = 0;
        paganation.updateUIFromExternal(1);
    }


}
