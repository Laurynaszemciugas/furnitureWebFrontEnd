package com.example.demo.Pages.DashBoard.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.DashBoard.TopEmployeesModel;
import com.example.demo.Services.EmployeeService.EmployeeService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class TopEmployees {

    CommonComponents commonComponents;
    Common common;


    String top = "";

    EmployeeService employeeService;

    public TopEmployees(CommonComponents commonComponents, Common common, EmployeeService employeeService) {
        this.commonComponents = commonComponents;
        this.common = common;

        this.employeeService = employeeService;
    }

    public void findBestEmployee(List<TopEmployeesModel> list){
        top =  list.stream()
                .max(Comparator.comparingLong(TopEmployeesModel::getUnitsProduced))
                .map(TopEmployeesModel::getName)
                .orElse(null);
    }

    public VerticalLayout topEmployees(){


        List<TopEmployeesModel> topEmployeesModelList = employeeService.getTopEmployeesModel();

        boolean empty = (topEmployeesModelList == null || topEmployeesModelList.isEmpty());

        if(!empty){
            findBestEmployee(topEmployeesModelList);
        }

        VerticalLayout mainLayout2 = new VerticalLayout(commonComponents.spanCrafterWordNoHide("Top employees","stat-value"));
        mainLayout2.setWidth("600px");
        mainLayout2.addClassName("island");


        // add data here from db
        VerticalLayout material2 = new VerticalLayout();

        if(empty){
            material2.add(commonComponents.noDataFound());
        }
        else{
            for(var s : topEmployeesModelList){
                material2.add(topEmployeeCrafter(s.getProfilePicUrl(),s.getName(),s.getUnitsProduced(),s.getHourlySalary()));
            }
        }

        // scroller that takes material as data from materialLowNoStockFeed
        Scroller scroller2 = new Scroller(material2);
        scroller2.setSizeFull();
        scroller2.setHeight("400px");

        Button button = commonComponents.normalThemeButton("View All Employee", "s", ButtonVariant.LUMO_PRIMARY);
        button.addClassName("accentButtons");

        // simple button in the middle
        HorizontalLayout buttonAtTheBottom2 = new HorizontalLayout(button);
        buttonAtTheBottom2.setWidthFull();
        buttonAtTheBottom2.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        // add remainin stuff to the main layout of the low no material layout
        mainLayout2.add(scroller2,buttonAtTheBottom2);

        return mainLayout2;
    }




    public HorizontalLayout topEmployeeCrafter(String profilePic, String employeeName,long unitsProduced, double hourlySalary){




        Span span = commonComponents.spanCrafter("Top employee","new-badge");
        HorizontalLayout stockAlert = new HorizontalLayout(span);
        stockAlert.setHeight("15px");
        stockAlert.getStyle().set("position","absolute").set("top","10px").set("right","10px");

        HorizontalLayout iconHolder = new HorizontalLayout(commonComponents.iconCrafter(VaadinIcon.CIRCLE,"20px","blue"));
        iconHolder.getStyle().set("margin-top","5px");

        Image image =new Image(profilePic,profilePic + " picture");
        image.setWidth("60px");
        image.setHeight("60px");
        image.getStyle().set("border-radius","30px");

        VerticalLayout e = new VerticalLayout(
                commonComponents.tripleValueRow(commonComponents.spanCrafterWordNoHide(employeeName,"activityFeed-name"),
                        commonComponents.spanCrafterWordNoHide(" - ","activityFeed-name"),
                        commonComponents.spanCrafterWordNoHide(String.format("%d %s",unitsProduced,"- units produced"),"stat-example")),
                commonComponents.spanCrafterWordNoHide(String.format("%s %.2f","Hourly salary", hourlySalary),"stat-description")
        );
        e.setWidthFull();
        e.setPadding(false);
        e.setSpacing(false);


        HorizontalLayout h12 = new HorizontalLayout(image,e);
        h12.getStyle().set("position","relative");
        h12.addClassName("island");
        h12.setWidthFull();

        if(employeeName.equalsIgnoreCase(top)){
            h12.add(stockAlert);
        }



        return  h12;
    }

    public double estimatedPayThisMonth(double hourlySalary, long hoursWorkedThisMonth){
        return  hourlySalary * hoursWorkedThisMonth;
    }


}
