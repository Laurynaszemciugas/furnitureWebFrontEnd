package com.example.demo.Pages.Employee.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.Employee.EmployeeBriefDto;
import com.example.demo.ControllerModels.Material.MaterialBriefDto;
import com.example.demo.Services.EmployeeService.EmployeeService;
import com.example.demo.Services.Material.MaterialService;
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

            Span span = commonComponents.spanCrafter(e.getEmployeeCategory() == null ? "Unknown" : String.valueOf(e.getEmployeeCategory()),"stat-example");
            span.getStyle().set("width", "fit-content");
            span.addClassName("stock-badge");




            return  span;
        }).setHeader("Role").setAutoWidth(true);

        // ======================== Material description=====================================
        grid.addComponentColumn(e->{


            Span span = commonComponents.spanCrafterWordNoHide(e.getEmployeeDepartment() == null ? "Unknown" : e.getEmployeeDepartment().toString(),"stat-title");



            return  span;
        }).setHeader("Department").setResizable(true);

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



            Span span = commonComponents.spanCrafter(e.getHourlyRate() == null ? "Unknown" : String.valueOf(e.getHourlyRate() + " Eur"),"stat-title");


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
            Button edit = commonComponents.buttonThemeAndIconNoNavigate("", ButtonVariant.LUMO_ICON, VaadinIcon.PENCIL,"Black");


            edit.addClickListener(editValue->{

//                common.customNavigate("MaterialEdit/" +e.getId());
            });


            Button delete = commonComponents.buttonThemeAndIconNoNavigate("", ButtonVariant.LUMO_ICON, VaadinIcon.TRASH,"Red");

            delete.addClickListener(deleteValue->{
                common.deleteConfirmation(e.getFullName());
               common.setBooleanConsumer(canDelete->{
//                   if(canDelete){
//                       materialService.removeProduct(e.getId());
//                   }
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
