package com.example.demo.Pages.Employee.EmployeeAdd;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.CommonDtos.MaterialImageData;
import com.example.demo.Enums.ActiveInactive;
import com.example.demo.Enums.EmployeeCategory;
import com.example.demo.Enums.EmployeeDepartment;
import com.example.demo.Enums.EmploymentType;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Pages.Employee.Components.EmployeeAddPage.EmployeeAddEditComponents;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.streams.InMemoryUploadHandler;
import com.vaadin.flow.server.streams.UploadHandler;

@Route(value = "EmployeesAdd", layout = MainLayout.class)
public class EmployeeAddPage extends VerticalLayout implements BeforeEnterObserver {




    CommonComponents commonComponents;
    Common common;
    ProductService productService;


    EmployeeAddEditComponents addEditComponents;









    public EmployeeAddPage(CommonComponents commonComponents, Common common, ProductService productService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.productService = productService;

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


        verticalLayout.add(
                addEditComponents.briefExplanation("Add new employee"),
                addEditComponents.employeeInformation(null)
        );

        return verticalLayout;
    }




















}
