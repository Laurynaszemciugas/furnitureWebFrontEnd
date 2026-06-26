package com.example.demo.Pages.Orders.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.CurrentFilterDisplay;
import com.example.demo.ControllerModels.CommonDtos.Employee;
import com.example.demo.ControllerModels.Filter.Order.OrderFilterHolder;
import com.example.demo.ControllerModels.Orders.OrderAddProducts;
import com.example.demo.ControllerModels.Products.ProductFeedModel;
import com.example.demo.DTOS.ComboBoxEmployees;
import com.example.demo.DTOS.ComboBoxMaterial;
import com.example.demo.Enums.MaterialType;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.Enums.Stock;
import com.example.demo.Pages.Orders.OrderAdd.OrderAdd;
import com.example.demo.ServerDBCall.CommonCalls.CommonCalls;
import com.example.demo.ServerDBCall.EmployeeCalls.EmployeeCalls;
import com.example.demo.Services.Products.ProductService;
import com.sun.jdi.LongValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Setter;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Setter
public class OrderFilters {


    CommonComponents commonComponents;
    Common common;
    ProductService productService;

    Consumer<OrderStatus> orderStatusConsumer;
    Consumer<Double> fromCostConsumer;
    Consumer<Double> toCostConsumer;
    Consumer<LocalDate> fromDateConsumer;
    Consumer<LocalDate> toDateConsumer;
    Consumer<Long> amountOfProductsConsumer;
    Consumer<String> clearFilters;
    Consumer<String> prompt;
    Consumer<Long> employeeId;
    Consumer<Long> productId;

    CurrentFilterDisplay currentFilterDisplay;
    OrderFilterHolder filterData = new OrderFilterHolder();



    EmployeeCalls employeeCalls;

    List<ComboBoxEmployees> comboBoxEmployees = new ArrayList<>();
    List<OrderAddProducts> productFeedModelList = new ArrayList<>();

    @SneakyThrows
    public OrderFilters(CommonComponents commonComponents, Common common, EmployeeCalls employeeCalls,ProductService productService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.employeeCalls = employeeCalls;
        this.productService = productService;

        comboBoxEmployees.addAll(employeeCalls.getMiniEmployeeData());
        productFeedModelList.addAll(productService.getProductsForAddOrder());

    }

    public void setCurrentFilterDisplay(CurrentFilterDisplay currentFilterDisplay) {
        this.currentFilterDisplay = currentFilterDisplay;
    }

    public void showFilters(){

        Dialog filters = new Dialog("Filters");

        VerticalLayout filterHolder = new VerticalLayout();
        filterHolder.setSpacing(false);
        filterHolder.setPadding(false);



        // =========== FROM DATE =====================
        DatePicker from = new DatePicker("Date from");
        currentFilterDisplay.setComponentValue("dateFromChoice",filterData,from);
        from.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(), LocalDate.of(1000,12,12),null,filterData,"dateFromChoice","Date from",fromDateConsumer);
        });


        // =========== TO DATE =====================
        DatePicker to = new DatePicker("Date to");
        currentFilterDisplay.setComponentValue("dateToChoice",filterData,to);
        to.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(), LocalDate.of(1000,12,12),null,filterData,"dateToChoice","Date to",toDateConsumer);
        });


        FormLayout dateHolder = new FormLayout();

        dateHolder.add(
                from,
                to
        );

        dateHolder.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 2)
        );

        // =========== PRODUCT COUNT =====================
        IntegerField amountOfProducts = new IntegerField("Products count in the order");
        amountOfProducts.setWidthFull();
        amountOfProducts.setMax(100);
        amountOfProducts.setMin(0);
        amountOfProducts.setStep(1);
        amountOfProducts.setStepButtonsVisible(true);
        currentFilterDisplay.setComponentValue("amountOfProductsChoice",filterData,amountOfProducts);
        amountOfProducts.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue() == null ? null : Long.valueOf(e.getValue()), 0L,null,filterData,"amountOfProductsChoice","Amount of products",amountOfProductsConsumer);
        });



        // =========== FROM AMOUNT =====================
        NumberField fromAmount = new NumberField("Order price from");
        fromAmount.setMax(100000);
        fromAmount.setMin(0);
        fromAmount.setStep(200);
        fromAmount.setStepButtonsVisible(true);
        currentFilterDisplay.setComponentValue("priceFromChoice",filterData,fromAmount);
        fromAmount.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(), 0.0,null,filterData,"priceFromChoice","Order price from",fromCostConsumer);
        });


        // =========== TO AMOUNT =====================
        NumberField toAmount = new NumberField("Order price to");
        toAmount.setMax(100000);
        toAmount.setMin(0);
        toAmount.setStep(200);
        toAmount.setMin(0);
        toAmount.setStepButtonsVisible(true);
        currentFilterDisplay.setComponentValue("priceToChoice",filterData,toAmount);
        toAmount.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue(), 0.0,null,filterData,"priceToChoice","Order price to",toCostConsumer);
        });



        FormLayout amountHolder = new FormLayout();

        amountHolder.add(
                fromAmount,
                toAmount
        );
        amountHolder.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 2)
        );


        ComboBox<ComboBoxEmployees> employeesComboBox = new ComboBox<>("Employee in order");
        employeesComboBox.setWidthFull();
        employeesComboBox.setItems(comboBoxEmployees);
        employeesComboBox.setItemLabelGenerator(ComboBoxEmployees::getFullName);

        employeesComboBox.setValue(comboBoxEmployees.stream().filter(e->e.getId().equals(filterData.getEmployee()))
                .findFirst().orElse(null));

        employeesComboBox.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue() == null ? null : Long.valueOf(e.getValue().getId()), 0L,e.getValue().getFullName(),filterData,"employee","Employee in order",employeeId);
        });

        ComboBox<OrderAddProducts> materialComboBox = new ComboBox<>("Products in order");

        materialComboBox.setWidthFull();
        materialComboBox.setItems(productFeedModelList);
        materialComboBox.setItemLabelGenerator(OrderAddProducts::getProductName);

        materialComboBox.setValue(productFeedModelList.stream().filter(e->e.getId().equals(filterData.getProducts()))
                .findFirst().orElse(null));
        materialComboBox.addValueChangeListener(e->{
            currentFilterDisplay.filterSetter(e.getValue() == null ? null : Long.valueOf(e.getValue().getId()), 0L,e.getValue().getProductName(),filterData,"products","Products in order",productId);
        });

        filterHolder.add(
                amountHolder,
                dateHolder,
                amountOfProducts,
                employeesComboBox,
                materialComboBox
        );

        Button button = new Button("Back", e-> filters.close());



        filters.getFooter().add(button);


        filters.add(
                filterHolder
        );

        filters.open();


    }


    public HorizontalLayout Buttons(){



        Button allButton = commonComponents.normalButtonNoNavigate("All", "transparent-button");
        allButton.addClassName("active");
        allButton.addClickListener(e->{
            currentFilterDisplay.filterSetter(
                    OrderStatus.ALL,
                    OrderStatus.ALL,
                    null,
                    filterData,
                    "orderStatusChoice",
                    "Order status",
                    orderStatusConsumer
            );
        });

        Button pendingButton = commonComponents.normalButtonNoNavigate("Pending", "transparent-button");
        pendingButton.addClickListener(e->{
            currentFilterDisplay.filterSetter(
                    OrderStatus.Pending,
                    OrderStatus.ALL,
                    null,
                    filterData,
                    "orderStatusChoice",
                    "Order status",
                    orderStatusConsumer
            );
        });

        Button inProgressButton = commonComponents.normalButtonNoNavigate("In Progress", "transparent-button");
        inProgressButton.addClickListener(e->{
            currentFilterDisplay.filterSetter(
                    OrderStatus.In_Progress,
                    OrderStatus.ALL,
                    null,
                    filterData,
                    "orderStatusChoice",
                    "Order status",
                    orderStatusConsumer
            );
        });

        Button finishedButton = commonComponents.normalButtonNoNavigate("Finished", "transparent-button");
        finishedButton.addClickListener(e->{
            currentFilterDisplay.filterSetter(
                    OrderStatus.Finished,
                    OrderStatus.ALL,
                    null,
                    filterData,
                    "orderStatusChoice",
                    "Order status",
                    orderStatusConsumer
            );
        });

        List<Button> buttons = List.of(allButton,pendingButton,inProgressButton,finishedButton);

        for(var s : buttons){

            currentFilterDisplay.setReloadButtons(ee->{

                if(filterData.getOrderStatusChoice().equals(OrderStatus.ALL)) {
                    orderStatusConsumer.accept(OrderStatus.ALL);
                    buttons.forEach(button ->
                            button.removeClassName("active"));
                    allButton.addClassName("active");
                }
            });

            s.addClickListener(e->{
                buttons.forEach(b-> b.removeClassName("active"));
                s.addClassName("active");
            });

        }




        HorizontalLayout buttonsHolder = new HorizontalLayout();
        buttonsHolder.addClassName("layout-flex");
        buttonsHolder.add(
                allButton,
                pendingButton,
                inProgressButton,
                finishedButton

        );

        Button clear = new Button("Clear filters",VaadinIcon.ERASER.create(), e-> clearFilters.accept("yep"));


        HorizontalLayout allHolder = new HorizontalLayout();
        allHolder.setWidthFull();
        allHolder.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        allHolder.add(
                buttonsHolder,
                clear
        );
        allHolder.addClassName("layout-flex");

        return allHolder;
    }


    public HorizontalLayout moreFilters(){
        HorizontalLayout nameOfGrids = new HorizontalLayout();
        nameOfGrids.addClassName("layout-flex");
        nameOfGrids.setWidthFull();

        Button showMoreFilters = new Button(commonComponents.iconCrafter(VaadinIcon.FILTER,"30px","grey"), e-> showFilters());
        showMoreFilters.addClassName("transparent-button");

        TextField search = commonComponents.textFieldCrafter("Search products...","",VaadinIcon.SEARCH);

        search.addValueChangeListener(e->{
           String value = e.getValue().isBlank() ? "ALL" : e.getValue();
           prompt.accept(value);
        });


        nameOfGrids.add(
                currentFilterDisplay.getFilters(),
                commonComponents.spanCrafterWordNoHide("Orders List","activityFeed-name"),
                commonComponents.spaceFiller(),
                search,
                showMoreFilters
        );
        return nameOfGrids;
    }





}
