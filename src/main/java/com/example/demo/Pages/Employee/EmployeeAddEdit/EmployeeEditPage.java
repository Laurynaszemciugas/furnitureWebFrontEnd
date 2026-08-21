package com.example.demo.Pages.Employee.EmployeeAddEdit;


import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.BreadCrums.BreadCrumsDto;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Employee.EmployeeAddEdit.Components.EmployeeAddEditComponents;
import com.example.demo.Services.AI.AIService;
import com.example.demo.Services.EmployeeService.EmployeeService;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "EmployeesEdit/:item", layout = MainLayout.class)
public class EmployeeEditPage extends VerticalLayout implements BeforeEnterObserver {




    CommonComponents commonComponents;
    Common common;
    EmployeeService employeeService;

    AIService aiService;

    EmployeeAddEditComponents addEditComponents;




    int itemChoice = 0;




    public EmployeeEditPage(CommonComponents commonComponents, Common common, EmployeeService employeeService,AIService aiService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.employeeService = employeeService;

        this.aiService = aiService;

        this.addEditComponents = new EmployeeAddEditComponents(commonComponents,common,aiService);

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);



        addEditComponents.configureFields();

        addClassName("animation-page");

    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        removeAll();

        int page = Math.toIntExact( Integer.parseInt(beforeEnterEvent.getRouteParameters().get("item").orElse(null)));

        this.itemChoice  = page;

        add(mainLayout());

    }

    public VerticalLayout mainLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");



        addEditComponents.setEmp(e->{
            employeeService.editExistingEmployee(e);
        });


        verticalLayout.add(
                commonComponents.breadCrums(new BreadCrumsDto("Employees", "Employees"),new BreadCrumsDto("Edit employee", null)),
                addEditComponents.briefExplanation("Add new employee", "Save"),
                addEditComponents.employeeInformation(employeeService.getEmployee((long) itemChoice))
        );

        return verticalLayout;
    }




















}
