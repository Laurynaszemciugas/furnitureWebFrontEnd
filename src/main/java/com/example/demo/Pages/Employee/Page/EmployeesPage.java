package com.example.demo.Pages.Employee.Page;


import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.CurrentFilterDisplay;
import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.Common.Paganation;
import com.example.demo.ControllerModels.Employee.EmployeeBriefDto;
import com.example.demo.ControllerModels.Filter.Employee.EmployeeFilterHolder;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Employee.Page.Components.EmployeeBriefExplanations;
import com.example.demo.Pages.Employee.Page.Components.EmployeeFilters;
import com.example.demo.Pages.Employee.Page.Components.EmployeeGrid;
import com.example.demo.Pages.Employee.Page.Components.EmployeeMiniStats;
import com.example.demo.Services.EmployeeService.EmployeeService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Route(value = "Employees", layout = MainLayout.class)
public class EmployeesPage extends VerticalLayout implements BeforeEnterObserver {

    CommonComponents commonComponents;
    Common common;
    EmployeeService employeeService;

    VerticalLayout verticalLayout = new VerticalLayout();

    VerticalLayout filterMemory = new VerticalLayout();


    Paganation paganation;
    CurrentFilterDisplay currentFilterDisplay;

    EmployeeBriefExplanations employeeBriefExplanations;
    EmployeeMiniStats employeeMiniStats;
    EmployeeFilters employeeFilters;
    EmployeeGrid employeeGrid;

    EmployeeFilterHolder filterData = new EmployeeFilterHolder();

    SessionCrafter sessionCrafter;


    Div gridHolder = new Div();


    public EmployeesPage(CommonComponents commonComponents, Common common, EmployeeService employeeService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.employeeService = employeeService;
        this.employeeBriefExplanations = new EmployeeBriefExplanations(commonComponents,common);
        this.employeeMiniStats = new EmployeeMiniStats(commonComponents,common);
        this.employeeFilters = new EmployeeFilters(commonComponents,common);
        this.employeeGrid = new EmployeeGrid(commonComponents,common,employeeService);

        this.paganation = new Paganation();
        this.currentFilterDisplay = new CurrentFilterDisplay(commonComponents,common);
        this.sessionCrafter = new SessionCrafter();


        employeeFilters.setCurrentFilterDisplay(currentFilterDisplay);

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);



        filterMemory.removeAll();
        filterMemory.setWidthFull();
        filterMemory.setPadding(false);



        addClassName("animation-page");


        gridHolder.setWidthFull();


    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

            removeAll();

            add(mainLayout());

    }

    public VerticalLayout mainLayout() {

        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");

        reloadData();


        employeeFilters.setEmployeeAcInConsumer(e->{
            setNewPage();
            filterData.setEmployeeAcIn(e);
            loadGridValues();
        });
        employeeFilters.setGetPrompConsumer(e->{
            setNewPage();
            filterData.setPromt(e);
            loadGridValues();
        });

        employeeFilters.setEmployeeCategoryConsumer(e->{
            setNewPage();
            filterData.setEmployeeCategory(e);
            loadGridValues();
        });
        employeeFilters.setEmployeeDepartmentConsumer(e->{
            setNewPage();
            filterData.setEmployeeDepartment(e);
            loadGridValues();
        });
        employeeFilters.setHourlyRateConsumer(e->{
            setNewPage();
            filterData.setHourlyRate(e);
            loadGridValues();
        });
        employeeFilters.setFromJoinedConsumer(e->{
            setNewPage();
            filterData.setFromJoined(e);
            loadGridValues();
        });
        employeeFilters.setToJoinedConsumer(e->{
            setNewPage();
            filterData.setToJoined(e);
            loadGridValues();
        });
        paganation.setOnPageChange(e->{
            e = e-1;
            filterData.setPage(e);
            loadGridValues();
        });
        //needed
        currentFilterDisplay.setReloadController(e->{
            setNewPage();
            filterData = (EmployeeFilterHolder) e;
            reloadData();
        });

        employeeService.setSuccess(e->{
            reloadData();
        });
        employeeFilters.setClearFilters(e->{
            filterData =  e;
            reloadData();
        });




        return verticalLayout;
    }

    public void reloadData(){

        verticalLayout.removeAll();

        filterData = new EmployeeFilterHolder();

        filterMemory.removeAll();
        filterMemory.add(
                employeeBriefExplanations.briefExplanation(),
                employeeMiniStats.miniStatHolder(
                        employeeService.getMiniStats(common.dateCrafter(0,0,0,0,true),
                                common.dateCrafter(0,1,1,0,true))),
                employeeFilters.filters()

        );



        loadGridValues();


        verticalLayout.add(
                filterMemory,
                gridHolder

        );
    }









    public VerticalLayout gridFilterHolder(List<EmployeeBriefDto> items){
        VerticalLayout v = new VerticalLayout();
        v.setPadding(false);
        v.setWidthFull();

        v.add(
                employeeGrid.gridHolder(items),
                paganation.buttonHolder(Math.toIntExact(employeeService.getPageCount(filterData)))

        );

        return v;
    }


    public void loadGridValues(){
        UI ui = UI.getCurrent();
        String jwt = sessionCrafter.extractSession("JWT", String.class);

        gridHolder.removeAll();
        gridHolder.add(
                commonComponents.shimmer(5)
        );

        CompletableFuture
                .supplyAsync(()->{

                    List<EmployeeBriefDto> items = employeeService.getEmployeeFeed(filterData,jwt);
                    common.timer(250);
                    return items;
                })
                .thenAccept(e->{
                    ui.access(() -> {
                        gridHolder.removeAll();
                        gridHolder.add(gridFilterHolder(e));
                    });
                });
    }



    public void setNewPage(){
        filterData.setPage(0);
        paganation.updateUIFromExternal(1);
    }



}
