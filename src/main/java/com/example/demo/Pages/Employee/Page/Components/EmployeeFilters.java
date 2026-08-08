package com.example.demo.Pages.Employee.Page.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.CurrentFilterDisplay;
import com.example.demo.ControllerModels.Filter.Employee.EmployeeFilterHolder;
import com.example.demo.Enums.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

@Setter
public class EmployeeFilters {

    CommonComponents commonComponents;
    Common common;
    CurrentFilterDisplay currentFilterDisplay;


    EmployeeFilterHolder filterData = new EmployeeFilterHolder();

    // filter consumers
    Consumer<EmployeeAcIn> employeeAcInConsumer;
    Consumer<String> getPrompConsumer;
    Consumer<EmployeeRole> employeeCategoryConsumer;
    Consumer<EmployeeDepartment> employeeDepartmentConsumer;
    Consumer<Double> hourlyRateConsumer;
    Consumer<LocalDate> fromJoinedConsumer;
    Consumer<LocalDate> toJoinedConsumer;
    Consumer<EmployeeFilterHolder> clearFilters;






    boolean firstLoad = true;

    public EmployeeFilters(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;




    }

    public void setCurrentFilterDisplay(CurrentFilterDisplay currentFilterDisplay) {
        this.currentFilterDisplay = currentFilterDisplay;
    }

    public VerticalLayout filters(){
        VerticalLayout v = new VerticalLayout();
        v.setPadding(false);

        if(firstLoad){
            v.addClassName("smooth-panel");
            firstLoad = false;
        }
        else{
            v.removeClassName("smooth-panel");
        }

        // get current filter display
        v.add(currentFilterDisplay.getFilters());


        HorizontalLayout buttonHolder = new HorizontalLayout();
        buttonHolder.addClassName("layout-flex");

        HorizontalLayout buttonHolderhOLDER = new HorizontalLayout();
        buttonHolderhOLDER.addClassName("layout-flex");
        buttonHolderhOLDER.setWidthFull();
        buttonHolderhOLDER.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        Button all = commonComponents.normalButtonNoNavigate(EmployeeRole.ALL.getDisplayName(), "transparent-button");
        all.addClickListener(e->{
            currentFilterDisplay.filterSetter(
                    EmployeeRole.ALL,
                    EmployeeRole.ALL,
                    null,
                    filterData,
                    "employeeCategory",
                    employeeCategoryConsumer
            );

        });
        all.addClassName("active");
        Button worker = commonComponents.normalButtonNoNavigate(EmployeeRole.WORKER.getDisplayName(), "transparent-button");
        worker.addClickListener(e->{
            currentFilterDisplay.filterSetter(
                    EmployeeRole.WORKER,
                    EmployeeRole.ALL,
                    null,
                    filterData,
                    "employeeCategory",
                    employeeCategoryConsumer
            );
        });
        Button assembler = commonComponents.normalButtonNoNavigate(EmployeeRole.ASSEMBLER.getDisplayName(), "transparent-button");
        assembler.addClickListener(e->{
            currentFilterDisplay.filterSetter(
                    EmployeeRole.ASSEMBLER,
                    EmployeeRole.ALL,
                    null,
                    filterData,
                    "employeeCategory",
                    employeeCategoryConsumer
            );
        });
        Button carpenter = commonComponents.normalButtonNoNavigate(EmployeeRole.CARPENTER.getDisplayName(), "transparent-button");
        carpenter.addClickListener(e->{
            currentFilterDisplay.filterSetter(
                    EmployeeRole.CARPENTER,
                    EmployeeRole.ALL,
                    null,
                    filterData,
                    "employeeCategory",
                    employeeCategoryConsumer
            );
        });
        Button finisher = commonComponents.normalButtonNoNavigate(EmployeeRole.FINISHER.getDisplayName(), "transparent-button");
        finisher.addClickListener(e->{
            currentFilterDisplay.filterSetter(
                    EmployeeRole.FINISHER,
                    EmployeeRole.ALL,
                    null,
                    filterData,
                    "employeeCategory",
                    employeeCategoryConsumer
            );
        });
        Button werehouse = commonComponents.normalButtonNoNavigate(EmployeeRole.WAREHOUSE.getDisplayName(), "transparent-button");
        werehouse.addClickListener(e->{
            currentFilterDisplay.filterSetter(
                    EmployeeRole.WAREHOUSE,
                    EmployeeRole.ALL,
                    null,
                    filterData,
                    "employeeCategory",
                    employeeCategoryConsumer
            );
        });
        Button manager = commonComponents.normalButtonNoNavigate(EmployeeRole.MANAGER.getDisplayName(), "transparent-button");
        manager.addClickListener(e->{
            currentFilterDisplay.filterSetter(
                    EmployeeRole.MANAGER,
                    EmployeeRole.ALL,
                    null,
                    filterData,
                    "employeeCategory",
                    employeeCategoryConsumer
            );
        });

        Button clear = new Button("Clear filters", VaadinIcon.ERASER.create());
        clear.addClickListener(e->{
            filterData = new EmployeeFilterHolder();
            clearFilters.accept(filterData);
            currentFilterDisplay.clearAllData();

        });

        buttonHolder.add(
                all,
                assembler,
                carpenter,
                finisher,
                werehouse,
                manager
        );

        buttonHolderhOLDER.add(
                buttonHolder,
                clear
        );


        List<Button> buttonList = List.of(
                all,
                assembler,
                carpenter,
                finisher,
                werehouse,
                manager);

        for(var s : buttonList){


            currentFilterDisplay.setReloadButtons(ee->{

                if(filterData.getEmployeeAcIn().equals(EmployeeAcIn.ALL)) {
                    employeeAcInConsumer.accept(EmployeeAcIn.ALL);
                    buttonList.forEach(button ->
                            button.removeClassName("active"));
                    all.addClassName("active");
                }
            });

            s.addClickListener(e->{

                buttonList.forEach(button->
                        button.removeClassName("active"));
                s.addClassName("active");

            });




        }




        TextField search = commonComponents.textFieldCrafter("Search employees...","",VaadinIcon.SEARCH);
        search.addValueChangeListener(e->{
            String value = e.getValue().isBlank() ? "ALL" : e.getValue();
            getPrompConsumer.accept(value);
        });
        Button showMoreFilters = new Button(commonComponents.iconCrafter(VaadinIcon.FILTER,"30px","grey"), e-> showMoreFilters());
        showMoreFilters.addClassName("transparent-button");
        HorizontalLayout h3 = new HorizontalLayout();
        h3.setPadding(false);

        h3.add(
                search,
                showMoreFilters
        );



        Span name = commonComponents.spanCrafter("Employees list","stat-value");

        HorizontalLayout h2 = new HorizontalLayout();
        h2.addClassName("layout-flex");
        h2.setWidthFull();
        h2.setPadding(false);
        h2.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        h2.add(name,h3);

        v.add(
                h2,
                buttonHolderhOLDER
        );

        return  v;
    }

    public Dialog showMoreFilters(){

        Dialog dialog = new Dialog("Filters");
        dialog.getHeader().add(VaadinIcon.FILTER.create());
        dialog.setHeaderTitle("Filters");
        dialog.setHeight("auto");
        dialog.setDraggable(true);

        Button back = new Button("Back", e-> dialog.close());

        VerticalLayout dialogHolder = new VerticalLayout();
        dialogHolder.setSpacing(false);
        dialogHolder.setPadding(false);





        // ========================== employee category ============================
        ComboBox<EmployeeDepartment> employeeDepartmentComboBox = new ComboBox<>("Employee department");
        employeeDepartmentComboBox.setWidthFull();
        employeeDepartmentComboBox.setItems(EmployeeDepartment.values());
        currentFilterDisplay.setComponentValue("employeeDepartment",filterData,employeeDepartmentComboBox);
        employeeDepartmentComboBox.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(),EmployeeDepartment.ALL,null,filterData,"employeeDepartment",employeeDepartmentConsumer);
        });

        // ========================== employee activity ============================
        ComboBox<EmployeeAcIn> employeeAcInComboBox = new ComboBox<>("Employee activity");
        employeeAcInComboBox.setWidthFull();
        employeeAcInComboBox.setItems(EmployeeAcIn.values());
        employeeAcInComboBox.setItemLabelGenerator(EmployeeAcIn::getDisplayName);
        currentFilterDisplay.setComponentValue("employeeAcIn",filterData,employeeAcInComboBox);
        employeeAcInComboBox.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(), EmployeeAcIn.ALL,null,filterData,"employeeAcIn",employeeAcInConsumer);
        });






        // ================= Hourly rate AMOUNT =================================
        NumberField hourlyRate  = new NumberField("Hourly salary");
        hourlyRate.setInvalid(false);
        hourlyRate.setStep(0.5);
        hourlyRate.setMax(100000);
        hourlyRate.setMin(0);
        hourlyRate.setStepButtonsVisible(true);
        hourlyRate.setWidthFull();
        currentFilterDisplay.setComponentValue("hourlyRate",filterData,hourlyRate);
        hourlyRate.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(),0.0,null,filterData,"hourlyRate",hourlyRateConsumer);
        });


        DatePicker dateFrom = new DatePicker("Joined from");
            currentFilterDisplay.setComponentValue("fromJoined",filterData,dateFrom);
        dateFrom.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(),LocalDate.of(1000,12,12),null,filterData,"fromJoined",fromJoinedConsumer);
        });


        DatePicker dateTo = new DatePicker("Joined to");
        currentFilterDisplay.setComponentValue("toJoined",filterData,dateTo);
        dateTo.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(),LocalDate.of(1000,12,12),null,filterData,"toJoined",toJoinedConsumer);
        });

        HorizontalLayout dateFromTo = commonComponents.doubleValueRow(
                dateFrom,
                dateTo
        );
        dialogHolder.add(
                employeeAcInComboBox,
                employeeDepartmentComboBox,
                hourlyRate,
                dateFromTo
        );

        dialog.add(dialogHolder);
        dialog.getFooter().add(back);

        dialog.open();

    return dialog;
    }










}
