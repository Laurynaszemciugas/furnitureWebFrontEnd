package com.example.demo.Pages.Employee.EmployeeAddEdit.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.SinglePhotoLogic;
import com.example.demo.ControllerModels.CommonDtos.Employee;
import com.example.demo.ControllerModels.CommonDtos.User;
import com.example.demo.Enums.EmployeeAcIn;
import com.example.demo.Enums.EmployeeRole;
import com.example.demo.Enums.EmployeeDepartment;
import com.example.demo.Enums.EmploymentType;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.data.binder.Binder;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.function.Consumer;

@Setter
public class EmployeeAddEditComponents {

    CommonComponents commonComponents;
    Common common;
    SinglePhotoLogic singlePhotoLogic;



    // personal info
    TextField name = new TextField("Employee name");
    TextField lastName = new TextField("Employee lastname");
    TextField emailAddress = new TextField("Email address");
    TextField phoneNumber = new TextField("Phone number");
    DatePicker dateOfBirth = new DatePicker("Date of birth");
    TextArea address = new TextArea("Address");


    // job information

    ComboBox<EmployeeDepartment> department  = new ComboBox<>("Department");
    ComboBox<EmployeeRole> role = new ComboBox<>("Role");
    NumberField hourlySalary = new NumberField("Hourly salary");
    ComboBox<EmployeeAcIn> acInComboBox = new ComboBox("Activity");
    DateTimePicker dateOfJoining = new DateTimePicker("Date of joining");
    ComboBox<EmploymentType> employmentType = new ComboBox<>("Employment type");

    // account information

    TextField accountGmail = new TextField("Account gmail");
    PasswordField password = new PasswordField("Password");
    PasswordField confirmPassword = new PasswordField("Confirm password");

    Employee employee = new Employee();

    Consumer<Employee> emp;

    boolean dataExists = false;

    private final Binder<Void> binder = new Binder<>();


    public EmployeeAddEditComponents(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;

        this.singlePhotoLogic = new SinglePhotoLogic(commonComponents,common);




    }


    public HorizontalLayout briefExplanation(String pageName){


        HorizontalLayout h = new HorizontalLayout();
        h.setWidthFull();
        h.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        HorizontalLayout buttonHolder = new HorizontalLayout();

        Button cancel = commonComponents.normalThemeButton("Cancel","Employees", ButtonVariant.LUMO_ICON);
        Button createOrder = commonComponents.normalThemeButtonNoNavigate("Save", ButtonVariant.LUMO_PRIMARY);

        singlePhotoLogic.setNewImage(e->{
                employee.setProfileImage(e);
        });


        createOrder.addClickListener(e->{


            if(binder.validate().isOk()){

                employee.setName(name.getValue());
                employee.setLastName(lastName.getValue());
                employee.setGmail(emailAddress.getValue());
                employee.setPhoneNumber(phoneNumber.getValue());
                employee.setDateOfBirth(dateOfBirth.getValue());
                employee.setAddress(address.getValue());

                employee.setProfileImage(singlePhotoLogic.getImageData());

                employee.setEmployeeDepartment(department.getValue());
                employee.setEmployeeCategory(role.getValue());
                employee.setEmploymentType(employmentType.getValue());
                employee.setHourlyRate(hourlySalary.getValue());

                employee.setCreated(dateOfJoining.getValue());

                employee.setGmail(accountGmail.getValue());

                employee.setEmployeeAcIn(acInComboBox.getValue());

                User user = new User();
                user.setGmail(emailAddress.getValue());
                user.setPassword(password.getValue());
                user.setName(name.getValue());
                user.setLastName(lastName.getValue());

                employee.setUser(user);

                emp.accept(employee);

            }
            else{
                commonComponents.showNotification("Form is not filled properly",3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.ERROR);
            }



        });


        buttonHolder.add(
                cancel,
                createOrder
        );


        h.add(
                commonComponents.biefPageExplanation(pageName),
                buttonHolder

        );


        return h;
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







        VerticalLayout h2 = new VerticalLayout();
        h2.setPadding(false);
        h2.setSpacing(false);
        h2.getStyle().set("gap", "15px");

        h2.add(
                emailAddress,
                singlePhotoLogic.imageGetterShower()
        );


        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();

        formLayout.getStyle().set("--vaadin-form-layout-column-spacing", "25px");

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
                department,
                role,
                employmentType,
                acInComboBox,
                hourlySalary,
                dateOfJoining
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
                accountGmail,
                password,
                confirmPassword
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

    public void loadData(Employee emp){




        if(emp != null){



            dataExists = true;

            employee.setId(emp.getId());


            name.setValue(emp.getName());
            lastName.setValue(emp.getLastName());
            emailAddress.setValue(emp.getGmail());
            phoneNumber.setValue(emp.getPhoneNumber());
            dateOfBirth.setValue(emp.getDateOfBirth());
            address.setValue(emp.getAddress());
            singlePhotoLogic.setImageData(emp.getProfileImage());

            department.setValue(emp.getEmployeeDepartment());
            role.setValue(emp.getEmployeeCategory());
            employmentType.setValue(emp.getEmploymentType());
            hourlySalary.setValue(emp.getHourlyRate());
            acInComboBox.setValue(emp.getEmployeeAcIn());
            dateOfJoining.setValue(emp.getCreated());

            accountGmail.setValue(emp.getGmail());



        }

        binder();

    }


    public VerticalLayout employeeInformation(Employee employee){

        loadData(employee);

        VerticalLayout alLInfo = new VerticalLayout();
        alLInfo.setPadding(false);

        alLInfo.add(
                personalInfo(),
                jobInformation(),
                accountInformation()
        );



        return  alLInfo;

    }

    public void configureFields(){
        name.setWidthFull();

        lastName.setWidthFull();

        emailAddress.setWidthFull();

        emailAddress.addValueChangeListener(e->{
           accountGmail.setValue(e.getValue());
        });

        dateOfBirth.setWidthFull();

        phoneNumber.setWidthFull();



        acInComboBox.setWidthFull();
        acInComboBox.setItems(Arrays.stream(EmployeeAcIn.values()).filter(e -> !e.equals(EmployeeAcIn.ALL)).toList());
        acInComboBox.setItemLabelGenerator(EmployeeAcIn::getDisplayName);
        acInComboBox.setValue(EmployeeAcIn.ACTIVE);

        department.setWidthFull();
        department.setItems(Arrays.stream(EmployeeDepartment.values()).filter(e -> !e.equals(EmployeeDepartment.ALL)).toList());
        department.setItemLabelGenerator(EmployeeDepartment::getDisplayName);


        role.setWidthFull();
        role.setItems(Arrays.stream(EmployeeRole.values()).filter(e -> !e.equals(EmployeeRole.ALL)).toList());
        role.setItemLabelGenerator(EmployeeRole::getDisplayName);

        hourlySalary.setWidthFull();
        hourlySalary.setStep(0.01);
        hourlySalary.setMin(0.01);
        hourlySalary.setStepButtonsVisible(true);

        dateOfJoining.setWidthFull();
        dateOfJoining.setReadOnly(true);
        dateOfJoining.setValue(LocalDateTime.now());

        employmentType.setWidthFull();
        employmentType.setItems(Arrays.stream(EmploymentType.values()).filter(e -> !e.equals(EmploymentType.ALL)).toList());
        employmentType.setItemLabelGenerator(EmploymentType::getDisplayName);

        accountGmail.setWidthFull();

        password.setWidthFull();

        confirmPassword.setWidthFull();

        address.setWidthFull();
        address.setHeight("140px");
    }



    public void binder() {

        // PERSONAL INFO
        binder.forField(name)
                .asRequired("Employee name is required")
                .withValidator(v -> v.length() >= 2, "Employee name must be at least 2 characters")
                .bind(v -> null, (v, value) -> {
                });

        binder.forField(lastName)
                .asRequired("Employee lastname is required")
                .withValidator(v -> v.length() >= 2, "Employee lastname must be at least 2 characters")
                .bind(v -> null, (v, value) -> {
                });

        binder.forField(emailAddress)
                .asRequired("Email address is required")
                .withValidator(this::isValidEmail, "Invalid email address")
                .bind(v -> null, (v, value) -> {
                });

        binder.forField(hourlySalary)
                .asRequired("Hourly salary is required")
                .withValidator(
                        salary -> salary > 0.0,
                        "Hourly salary must be greater than 0"
                )
                .bind(v -> null, (v, value) -> {
                });

        binder.forField(phoneNumber)
                .asRequired("Phone number is required")
                .withValidator(v -> v.matches("^[0-9+\\- ]{6,20}$"), "Invalid phone number")
                .bind(v -> null, (v, value) -> {
                });

        binder.forField(dateOfBirth)
                .asRequired("Date of birth is required")
                .withValidator(
                        date -> date.isBefore(LocalDate.now()),
                        "Date of birth must be in the past"
                )
                .bind(v -> null, (v, value) -> {
                });

        binder.forField(address)
                .asRequired("Address is required")
                .withValidator(v -> v.length() >= 5, "Address must be at least 5 characters")
                .bind(v -> null, (v, value) -> {
                });


        // JOB INFORMATION
        binder.forField(department)
                .asRequired("Department is required")
                .bind(v -> null, (v, value) -> {
                });

        binder.forField(role)
                .asRequired("Role is required")
                .bind(v -> null, (v, value) -> {
                });


        binder.forField(acInComboBox)
                .asRequired("Employee status is required")
                .bind(v -> null, (v, value) -> {
                });


        binder.forField(employmentType)
                .asRequired("Employment type is required")
                .bind(v -> null, (v, value) -> {
                });


        // ACCOUNT INFORMATION
        binder.forField(accountGmail)
                .asRequired("Account gmail is required")
                .withValidator(this::isValidEmail, "Invalid account gmail")
                .bind(v -> null, (v, value) -> {
                });

        if (!dataExists) {
            binder.forField(password)
                    .asRequired("Password is required")
                    .withValidator(v -> v.length() >= 8, "Password must be at least 8 characters")
                    .bind(v -> null, (v, value) -> {
                    });

            binder.forField(confirmPassword)
                    .asRequired("Confirm password is required")
                    .withValidator(
                            confirm -> confirm.equals(password.getValue()),
                            "Passwords do not match"
                    )
                    .bind(v -> null, (v, value) -> {
                    });
        }
    }

    private boolean isValidEmail(String email) {
        return email != null &&
                email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }







}
