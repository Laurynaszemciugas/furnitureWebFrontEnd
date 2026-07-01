package com.example.demo.Pages.Employee.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.CurrentFilterDisplay;
import com.example.demo.ControllerModels.Filter.Employee.EmployeeFilterHolder;
import com.example.demo.ControllerModels.Filter.Material.MaterialFilterHolder;
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
import com.vaadin.flow.component.textfield.IntegerField;
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
    Consumer<EmployeeCategory> employeeCategoryConsumer;
    Consumer<EmployeeDepartment> employeeDepartmentConsumer;
    Consumer<Double> hourlyRateConsumer;
    Consumer<LocalDate> fromJoinedConsumer;
    Consumer<LocalDate> toJoinedConsumer;
    Consumer<EmployeeFilterHolder> clearFilters;


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


        // get current filter display
        v.add(currentFilterDisplay.getFilters());


        HorizontalLayout buttonHolder = new HorizontalLayout();
        buttonHolder.addClassName("layout-flex");

        HorizontalLayout buttonHolderhOLDER = new HorizontalLayout();
        buttonHolderhOLDER.addClassName("layout-flex");
        buttonHolderhOLDER.setWidthFull();
        buttonHolderhOLDER.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        Button all = commonComponents.normalButtonNoNavigate(EmployeeAcIn.ALL.getDisplayName(), "transparent-button");
        all.addClickListener(e->{
            currentFilterDisplay.filterSetter(
                    EmployeeAcIn.ALL,
                    EmployeeAcIn.ALL,
                    null,
                    filterData,
                    "employeeAcIn",
                    "Activity",
                    employeeAcInConsumer
            );

        });
        all.addClassName("active");
        Button active = commonComponents.normalButtonNoNavigate(EmployeeAcIn.ACTIVE.getDisplayName(), "transparent-button");
        active.addClickListener(e->{
            currentFilterDisplay.filterSetter(
                    EmployeeAcIn.ACTIVE,
                    EmployeeAcIn.ALL,
                    null,
                    filterData,
                    "employeeAcIn",
                    "Activity",
                    employeeAcInConsumer
            );
        });
        Button inactive = commonComponents.normalButtonNoNavigate(EmployeeAcIn.INACTIVE.getDisplayName(), "transparent-button");
        inactive.addClickListener(e->{
            currentFilterDisplay.filterSetter(
                    EmployeeAcIn.INACTIVE,
                    EmployeeAcIn.ALL,
                    null,
                    filterData,
                    "employeeAcIn",
                    "Activity",
                    employeeAcInConsumer
            );
        });
        Button onLeave = commonComponents.normalButtonNoNavigate(EmployeeAcIn.ON_LEAVE.getDisplayName(), "transparent-button");
        onLeave.addClickListener(e->{
            currentFilterDisplay.filterSetter(
                    EmployeeAcIn.ON_LEAVE,
                    EmployeeAcIn.ALL,
                    null,
                    filterData,
                    "employeeAcIn",
                    "Activity",
                    employeeAcInConsumer
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
                active,
                inactive,
                onLeave
        );

        buttonHolderhOLDER.add(
                buttonHolder,
                clear
        );


        List<Button> buttonList = List.of(all,active,inactive,onLeave);

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

        TextField search = commonComponents.textFieldCrafter("Search products...","",VaadinIcon.SEARCH);
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



        Span name = commonComponents.spanCrafter("Materials list","stat-value");

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
        ComboBox<EmployeeCategory> employeeCategoryComboBox = new ComboBox<>("Employee category");
        employeeCategoryComboBox.setWidthFull();
        employeeCategoryComboBox.setItems(EmployeeCategory.values());
        currentFilterDisplay.setComponentValue("employeeCategory",filterData,employeeCategoryComboBox);
        employeeCategoryComboBox.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(),EmployeeCategory.All,null,filterData,"employeeCategory","Employee category",employeeCategoryConsumer);
        });


        // ========================== employee category ============================
        ComboBox<EmployeeDepartment> employeeDepartmentComboBox = new ComboBox<>("Employee department");
        employeeDepartmentComboBox.setWidthFull();
        employeeDepartmentComboBox.setItems(EmployeeDepartment.values());
        currentFilterDisplay.setComponentValue("employeeDepartment",filterData,employeeDepartmentComboBox);
        employeeDepartmentComboBox.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(),EmployeeDepartment.ALL,null,filterData,"employeeDepartment","Employee department",employeeDepartmentConsumer);
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
            currentFilterDisplay.filterSetter(e.getValue(),0.0,null,filterData,"hourlyRate","Hourly salary",hourlyRateConsumer);
        });


        DatePicker dateFrom = new DatePicker("Joined from");
            currentFilterDisplay.setComponentValue("fromJoined",filterData,dateFrom);
        dateFrom.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(),LocalDate.of(1000,12,12),null,filterData,"fromJoined","From date",fromJoinedConsumer);
        });


        DatePicker dateTo = new DatePicker("Joined to");
        currentFilterDisplay.setComponentValue("toJoined",filterData,dateTo);
        dateTo.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(),LocalDate.of(1000,12,12),null,filterData,"toJoined","To date",toJoinedConsumer);
        });

        HorizontalLayout dateFromTo = commonComponents.doubleValueRow(
                dateFrom,
                dateTo
        );
        dialogHolder.add(
                employeeCategoryComboBox,
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
