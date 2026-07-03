package com.example.demo.Pages.Employee.EmployeeAdd;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.CommonDtos.MaterialImageData;
import com.example.demo.Enums.ActiveInactive;
import com.example.demo.Enums.EmployeeCategory;
import com.example.demo.Enums.EmployeeDepartment;
import com.example.demo.Enums.EmploymentType;
import com.example.demo.MainLayout.MainLayout;
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



    // personal info
    TextField name = new TextField("Employee name");
    TextField lastName = new TextField("Employee lastname");
    TextField emailAddress = new TextField("Email address");
    TextField phoneNumber = new TextField("Phone number");
    DatePicker dateOfBirth = new DatePicker("Date of birth");
    TextArea address = new TextArea("Address");


    // job information

    ComboBox<EmployeeDepartment> department  = new ComboBox<>("Department");
    ComboBox<EmployeeCategory> role = new ComboBox<>("Role");
    TextField jobTittle = new TextField("Job tittle");
    IntegerField employeeId = new IntegerField("Employee id");
    DatePicker dateOfJoining = new DatePicker("Date of joining");
    ComboBox<EmploymentType> employmentType = new ComboBox<>("Employment type");

    // account information

    TextField accountGmail = new TextField("Account gmail");
    PasswordField password = new PasswordField("Password");
    PasswordField confirmPassword = new PasswordField("Confirm password");



    String imageData = "";


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
                personalInfo(),
                jobInformation(),
                accountInformation()
        );

        return verticalLayout;
    }



    public VerticalLayout personalInfo(){
        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();
        v.addClassName("island");



        VerticalLayout h1 = new VerticalLayout();
        h1.setPadding(false);
        h1.setSpacing(false);
        h1.getStyle().set("gap", "15px");

        h1.add(
                commonComponents.doubleValueRow(name,lastName),
                commonComponents.doubleValueRow(phoneNumber,dateOfBirth),
                address
        );

        //h1.getStyle().set("background-color","green");

        VerticalLayout imageHolder = new VerticalLayout();
        imageHolder.setPadding(false);
        imageHolder.setSpacing(false);
        imageHolder.setSizeFull();
        imageHolder.getStyle().set("margin-top", "10px");






        InMemoryUploadHandler inMemoryHandler = UploadHandler
                .inMemory((metadata, data) -> {
                    String fileName = metadata.fileName();
                    String mimeType = metadata.contentType();

                    imageData = common.imageMaker(data,mimeType);

                });
        Upload upload = new Upload(inMemoryHandler);

        Image profilePic = commonComponents.imageCrafter("Screenshot 2026-04-27 001745.png","50%", "130px", "5px");
        Image tableExample = commonComponents.imageCrafter("Screenshot 2026-04-27 001745.png","90px", "90px", "50%");
        Image addEmployeeExample = commonComponents.imageCrafter("Screenshot 2026-04-27 001745.png","80px", "80px", "50px");

        upload.addAllFinishedListener(e->{
            profilePic.setSrc(imageData);
            tableExample.setSrc(imageData);
            addEmployeeExample.setSrc(imageData);
        });

        upload.setMaxFiles(1);
        upload.setAcceptedFileTypes(".PNG");
        upload.getStyle().set("margin-top", "5px");
        upload.setWidthFull();


        HorizontalLayout profilePhotoHolder = new HorizontalLayout();
        profilePhotoHolder.setWidthFull();
        profilePhotoHolder.setAlignItems(Alignment.CENTER);
        profilePhotoHolder.setJustifyContentMode(JustifyContentMode.BETWEEN);
        profilePhotoHolder.add(
                profilePic,
                tableExample,
                addEmployeeExample
        );

        Span profilePicSpan = commonComponents.spanCrafterWordNoHide("Profile photo","stat-title");

        imageHolder.add(
                profilePicSpan,
                profilePhotoHolder,
                upload
        );

        VerticalLayout h2 = new VerticalLayout();
        h2.setPadding(false);
        h2.setSpacing(false);
        h2.getStyle().set("gap", "15px");

       // h2.getStyle().set("background-color","red");
        h2.add(
                emailAddress,
                imageHolder
        );


        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();

        formLayout.getStyle()
                .set("--vaadin-form-layout-column-spacing", "25px");

        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("900px", 3)
        );

        formLayout.add(
                h1,h2
        );

        formLayout.setColspan(h1, 2);

        formLayout.setColspan(h2, 1);






        v.add(
                commonComponents.spanCrafterWordNoHide("Personal information","activityFeed-name"),
                formLayout

        );

        return v;
    }

    public VerticalLayout jobInformation(){

        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();
        v.addClassName("island");

        FormLayout formLayout = new FormLayout();

        formLayout.getStyle()
                .set("--vaadin-form-layout-column-spacing", "25px")
                .set("--vaadin-form-layout-row-spacing", "25px");

        formLayout.add(
                department,jobTittle,role,employeeId,dateOfJoining,employmentType
        );

        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("900px", 3)
        );

        v.add(
                commonComponents.spanCrafterWordNoHide("Job information","activityFeed-name"),
                formLayout
        );


        return  v;

    }

    public VerticalLayout accountInformation(){

        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();
        v.addClassName("island");

        FormLayout formLayout = new FormLayout();

        formLayout.getStyle()
                .set("--vaadin-form-layout-column-spacing", "25px")
                .set("--vaadin-form-layout-row-spacing", "25px");

        formLayout.add(
               accountGmail,password,confirmPassword
        );

        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("900px", 3)
        );

        v.add(
                commonComponents.spanCrafterWordNoHide("Account information","activityFeed-name"),
                formLayout
        );


        return  v;

    }

    public void configureFields(){
        name.setWidthFull();

        lastName.setWidthFull();

        emailAddress.setWidthFull();

        dateOfBirth.setWidthFull();

        phoneNumber.setWidthFull();


        employeeId.setWidthFull();

        department.setWidthFull();
        department.setItems(EmployeeDepartment.values());

        role.setWidthFull();
        role.setItems(EmployeeCategory.values());

        jobTittle.setWidthFull();

        dateOfJoining.setWidthFull();

        employmentType.setWidthFull();
        employmentType.setItems(EmploymentType.values());

        accountGmail.setWidthFull();

        password.setWidthFull();

        confirmPassword.setWidthFull();

        address.setWidthFull();
        address.setHeight("140px");
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
