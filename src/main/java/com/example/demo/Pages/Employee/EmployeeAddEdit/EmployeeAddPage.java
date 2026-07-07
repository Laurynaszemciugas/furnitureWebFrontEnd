package com.example.demo.Pages.Employee.EmployeeAddEdit;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Employee.EmployeeAddEdit.Components.EmployeeAddEditComponents;
import com.example.demo.Services.EmployeeService.EmployeeService;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "EmployeesAdd", layout = MainLayout.class)
public class EmployeeAddPage extends VerticalLayout implements BeforeEnterObserver {




    CommonComponents commonComponents;
    Common common;
    EmployeeService employeeService;


    EmployeeAddEditComponents addEditComponents;









    public EmployeeAddPage(CommonComponents commonComponents, Common common, EmployeeService employeeService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.employeeService = employeeService;

        this.addEditComponents = new EmployeeAddEditComponents(commonComponents,common);

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);



        addEditComponents.configureFields();

    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        removeAll();


        add(mainLayout());

    }

    public VerticalLayout mainLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");
        verticalLayout.addClassName("main-island");



        addEditComponents.setEmp(e->{
            employeeService.saveNewEmployee(e);
        });


        verticalLayout.add(
                addEditComponents.briefExplanation("Add new employee"),
                addEditComponents.employeeInformation(null)
        );

        return verticalLayout;
    }




















}
