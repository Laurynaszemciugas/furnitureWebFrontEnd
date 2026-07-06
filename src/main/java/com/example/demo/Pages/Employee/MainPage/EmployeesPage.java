package com.example.demo.Pages.Employee.MainPage;


import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.CurrentFilterDisplay;
import com.example.demo.Common.Paganation;
import com.example.demo.ControllerModels.Filter.Employee.EmployeeFilterHolder;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Employee.Components.EmployeePageComponents.EmployeeBriefExplanations;
import com.example.demo.Pages.Employee.Components.EmployeePageComponents.EmployeeFilters;
import com.example.demo.Pages.Employee.Components.EmployeePageComponents.EmployeeGrid;
import com.example.demo.Pages.Employee.Components.EmployeePageComponents.EmployeeMiniStats;
import com.example.demo.Services.EmployeeService.EmployeeService;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

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


        employeeFilters.setCurrentFilterDisplay(currentFilterDisplay);

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);



        filterMemory.removeAll();
        filterMemory.setWidthFull();
        filterMemory.setPadding(false);



        filterMemory.add(
                employeeBriefExplanations.briefExplanation(),
                employeeMiniStats.miniStatHolder(employeeService.getMiniStats(
                                common.dateCrafter(0,0,0,0,true),
                                common.dateCrafter(0,1,1,0,true))),
                employeeFilters.filters()
        );


    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

            removeAll();

            add(mainLayout());

    }

    public VerticalLayout mainLayout() {

        verticalLayout.setMaxWidth("1650px");
        verticalLayout.getStyle().set("margin-top", "5px");
        verticalLayout.addClassName("main-island");

        reloadData();


        employeeFilters.setEmployeeAcInConsumer(e->{
            setNewPage();
            filterData.setEmployeeAcIn(e);
            filterFeed();
        });
        employeeFilters.setGetPrompConsumer(e->{
            setNewPage();
            filterData.setPromt(e);
            filterFeed();
        });

        employeeFilters.setEmployeeCategoryConsumer(e->{
            setNewPage();
            filterData.setEmployeeCategory(e);
            filterFeed();
        });
        employeeFilters.setEmployeeDepartmentConsumer(e->{
            setNewPage();
            filterData.setEmployeeDepartment(e);
            filterFeed();
        });
        employeeFilters.setHourlyRateConsumer(e->{
            setNewPage();
            filterData.setHourlyRate(e);
            filterFeed();
        });
        employeeFilters.setFromJoinedConsumer(e->{
            setNewPage();
            filterData.setFromJoined(e);
            filterFeed();
        });
        employeeFilters.setToJoinedConsumer(e->{
            setNewPage();
            filterData.setToJoined(e);
            filterFeed();
        });
        paganation.setOnPageChange(e->{
            e = e-1;
            filterData.setPage(e);
            filterFeed();
        });
        //needed
        currentFilterDisplay.setReloadController(e->{
            setNewPage();
            filterData = (EmployeeFilterHolder) e;
            filterFeed();
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



        verticalLayout.add(
                filterMemory,
                gridFilterHolder()

        );
    }


    public void filterFeed(){


        verticalLayout.removeAll();

        verticalLayout.add(
                filterMemory,
                gridFilterHolder()

        );
    }


    public VerticalLayout gridFilterHolder(){
        VerticalLayout v = new VerticalLayout();
        v.setPadding(false);
        v.setWidthFull();

        v.add(
                employeeGrid.gridHolder(employeeService.getEmployeeFeed(filterData)),
                paganation.buttonHolder(Math.toIntExact(employeeService.getPageCount(filterData)))

        );

        return v;
    }



    public void setNewPage(){
        filterData.setPage(0);
        paganation.updateUIFromExternal(1);
    }



}
