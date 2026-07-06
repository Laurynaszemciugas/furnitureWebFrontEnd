package com.example.demo.Pages.Employee.Components.EmployeePageComponents;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class EmployeeBriefExplanations {


    CommonComponents commonComponents;
    Common common;

    public EmployeeBriefExplanations(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
    }

    public HorizontalLayout briefExplanation(){

        HorizontalLayout v = new HorizontalLayout();
        v.setWidthFull();
        v.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);



        v.add(
                commonComponents.biefPageExplanation("Employees"),
                commonComponents.buttonThemeAndIcon("Add employee","EmployeesAdd", ButtonVariant.PRIMARY, VaadinIcon.PLUS,"White"));
        return v;
    }

}
