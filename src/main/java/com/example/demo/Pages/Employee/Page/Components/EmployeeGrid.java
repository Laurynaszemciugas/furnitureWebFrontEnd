package com.example.demo.Pages.Employee.Page.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Employee.EmployeeBriefDto;
import com.example.demo.Services.EmployeeService.EmployeeService;
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

public class EmployeeGrid {

    CommonComponents commonComponents;
    Common common;

    EmployeeService employeeService;


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

                    case INTERN -> {
                        span.getStyle()
                                .set("background", "rgba(245, 158, 11, 0.15)")
                                .set("color", "#f59e0b")
                                .set("border", "1px solid rgba(245,158,11,0.3)");
                    }

                    case JUNIOR_WORKER -> {
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



                    case PAINTER -> {
                        span.getStyle()
                                .set("background", "rgba(168, 85, 247, 0.15)")
                                .set("color", "#a855f7")
                                .set("border", "1px solid rgba(168,85,247,0.3)");
                    }

                    case FINISHER -> {
                        span.getStyle()
                                .set("background", "rgba(20, 184, 166, 0.15)")
                                .set("color", "#14b8a6")
                                .set("border", "1px solid rgba(20,184,166,0.3)");
                    }

                    case QUALITY_CHECKER -> {
                        span.getStyle()
                                .set("background", "rgba(132, 204, 22, 0.15)")
                                .set("color", "#84cc16")
                                .set("border", "1px solid rgba(132,204,22,0.3)");
                    }

                    case WAREHOUSE_WORKER -> {
                        span.getStyle()
                                .set("background", "rgba(100, 116, 139, 0.15)")
                                .set("color", "#64748b")
                                .set("border", "1px solid rgba(100,116,139,0.3)");
                    }

                    case LOGISTICS_WORKER -> {
                        span.getStyle()
                                .set("background", "rgba(249, 115, 22, 0.15)")
                                .set("color", "#f97316")
                                .set("border", "1px solid rgba(249,115,22,0.3)");
                    }

                    case DELIVERY_DRIVER -> {
                        span.getStyle()
                                .set("background", "rgba(59, 130, 246, 0.15)")
                                .set("color", "#3b82f6")
                                .set("border", "1px solid rgba(59,130,246,0.3)");
                    }



                    case SUPERVISOR -> {
                        span.getStyle()
                                .set("background", "rgba(37, 99, 235, 0.15)")
                                .set("color", "#2563eb")
                                .set("border", "1px solid rgba(37,99,235,0.3)");
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

            String date = common.dateFormatter(e.getCreated(),"MMMM d yyyy");

            Span span = commonComponents.spanCrafter(date,"stat-example");


            return  span;
        }).setHeader("Joined").setAutoWidth(true);


        // ======================== Material actions =====================================
        grid.addComponentColumn(e->{


            HorizontalLayout h = new HorizontalLayout();
            Button edit = commonComponents.buttonThemeAndIconNoNavigate("", ButtonVariant.LUMO_ICON, VaadinIcon.PENCIL,"Blue");


            edit.addClickListener(editValue->{

                common.customNavigate("EmployeesEdit/" + e.getId());
            });


            Button delete = commonComponents.buttonThemeAndIconNoNavigate("", ButtonVariant.LUMO_ICON, VaadinIcon.TRASH,"Red");

            delete.addClickListener(deleteValue->{
                common.deleteConfirmation(e.getFullName());
               common.setBooleanConsumer(canDelete->{
                   if(canDelete){
                       employeeService.deleteEmployee(e.getId());
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
