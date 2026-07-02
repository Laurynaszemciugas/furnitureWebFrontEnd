package com.example.demo.Pages.Employee.EmployeeAdd;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.CommonDtos.MaterialImageData;
import com.example.demo.Enums.ActiveInactive;
import com.example.demo.MainLayout.MainLayout;
import com.example.demo.Services.Products.ProductService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "EmployeesAdd", layout = MainLayout.class)
public class EmployeeAddPage extends VerticalLayout implements BeforeEnterObserver {




    CommonComponents commonComponents;
    Common common;
    ProductService productService;



    TextField name = new TextField("Employee name");
    TextField lastName = new TextField("Employee lastname");
    TextField emailAddress = new TextField("Email address");
    TextField phoneNumber = new TextField("Phone number");
    DatePicker dateOfBirth = new DatePicker("Date of birth");
    TextArea address = new TextArea("Address");








    public EmployeeAddPage(CommonComponents commonComponents, Common common, ProductService productService) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.productService = productService;

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);



        configureFields();

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
                briefExplanation("Add new employee"),
                personalInfo()
        );

        return verticalLayout;
    }



    public VerticalLayout personalInfo(){
        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");

        HorizontalLayout hMain = new HorizontalLayout();
        hMain.setWidthFull();

        VerticalLayout v1 = new VerticalLayout();
        v1.setWidthFull();
        v1.add(
                name,
                new Upload()
        );

        HorizontalLayout h1 = new HorizontalLayout();
        h1.setWidthFull();

        h1.add(
                name,
                lastName
        );

       // h1.getStyle().set("background-color","green");


        VerticalLayout v2 = new VerticalLayout();
        v2.add(
                phoneNumber,
                new Upload()
        );

// user one horizontal and two vertical to di dinamically i guess

        HorizontalLayout h2 = new HorizontalLayout();
        //h2.getStyle().set("background-color","red");
        h2.add(
                emailAddress
        );


        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();

        formLayout.getStyle()
                .set("--vaadin-form-layout-column-spacing", "15px");

        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("900px", 3)
        );

        formLayout.add(
                h1,h2
        );

        formLayout.setColspan(h1, 2);

        formLayout.setColspan(h2, 1);

        hMain.add(
                formLayout
        );





        v.add(
                hMain

        );

        return v;
    }

    public void configureFields(){
        name.setWidthFull();

        lastName.setWidthFull();

        emailAddress.setWidthFull();
    }





    public HorizontalLayout briefExplanation(String name){


        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        HorizontalLayout buttonHolder = new HorizontalLayout();

        Button cancel = commonComponents.normalThemeButton("Cancel","Employees", ButtonVariant.LUMO_ICON);
        Button createOrder = commonComponents.normalThemeButtonNoNavigate("Save", ButtonVariant.LUMO_PRIMARY);


        createOrder.addClickListener(e->{





        });


        buttonHolder.add(
                cancel,
                createOrder
        );


        h.add(
                commonComponents.biefPageExplanation(name),
                buttonHolder

        );


        return h;
    }






}
