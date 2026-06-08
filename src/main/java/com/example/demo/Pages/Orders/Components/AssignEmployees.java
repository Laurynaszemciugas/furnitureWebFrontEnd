package com.example.demo.Pages.Orders.Components;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.CommonDtos.Employee;
import com.example.demo.ControllerModels.CommonDtos.EmployeeJoin.OrderEmployees;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.DTOS.ComboBoxEmployees;
import com.example.demo.Enums.EmployeeCategory;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.ServerDBCall.EmployeeCalls.EmployeeCalls;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import lombok.SneakyThrows;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

public class AssignEmployees {

    CommonComponents commonComponents;
    Common common;
    EmployeeCalls employeeCalls;


    List<ComboBoxEmployees> listEmployees = new ArrayList<>();
    Long employeeId;
    String employeeName;
    EmployeeCategory employeeCategoryNew;
    String employeeProfilePic;

    @SneakyThrows
    public AssignEmployees(CommonComponents commonComponents, Common common, EmployeeCalls employeeCalls) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.employeeCalls = employeeCalls;

        listEmployees.addAll(employeeCalls.getMiniEmployeeData());

    }

    public VerticalLayout employeeAssignment(Orders selectedOrder, VerticalLayout employeeHolder){

        VerticalLayout v = new VerticalLayout();
        v.addClassName("island");
        v.setPadding(false);
        v.setWidthFull();

        v.add(
                assignEmployeesComponent(selectedOrder,employeeHolder),
                employeeHolder
        );

        return  v;

    }


    public HorizontalLayout assignEmployeesComponent(Orders selectedOrder, VerticalLayout employeeHolder) {


        HorizontalLayout employeeLayer = new HorizontalLayout();

        employeeLayer.setPadding(false);
        employeeLayer.setWidthFull();
        employeeLayer.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        Button addEmployee = commonComponents.buttonThemeAndIconNoNavigate("Add employee", ButtonVariant.LUMO_PRIMARY, VaadinIcon.PLUS, "White");

        if (OrderStatus.Finished.equals(selectedOrder.getOrderStatus())) {
            addEmployee.setEnabled(false);
        }

        addEmployee.addClickListener(e -> {
            showEmployeeDialog(selectedOrder, employeeHolder);
        });

        employeeLayer.add(
                commonComponents.spanCrafterWordNoHide("Assign Employees", "activityFeed-name"),
                addEmployee

        );

        return employeeLayer;
    }


    public Dialog showEmployeeDialog(Orders selectedOrder, VerticalLayout employeeHolder) {



        Dialog dialog = new Dialog("Select employee ");
        dialog.setHeight("550px");
        dialog.setWidth("700px");

        Grid<ComboBoxEmployees> employeeGrid = new Grid<>(ComboBoxEmployees.class,false);
        employeeGrid.setItems(listEmployees);

        employeeGrid.addComponentColumn(e->{
            Image image = commonComponents.imageCrafter(e.getProfileImage() != null ? e.getProfileImage() : "No_picture.png", "80px", "80px", "50px");
            Span employeeFullName = commonComponents.spanCrafter(e.getFullName(), "stat-example");
            Span employeeRole = commonComponents.spanCrafter(e.getEmployeeCategory().toString(), "stat-title");

            VerticalLayout v = new VerticalLayout();
            v.add(employeeFullName,employeeRole);

            HorizontalLayout h = new HorizontalLayout();
            h.add(image,v);
            h.setWidthFull();

            return h;
        }).setHeader("Employee").setAutoWidth(true);



        employeeGrid.addComponentColumn(e->{
            Button button = new Button("Select");



            if(selectedOrder.getEmployees() != null){
            for(var s : selectedOrder.getEmployees()) {
                if (s.getEmployee().getId().equals(e.getId())) {
                    button.setText("Selected");
                    button.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
                    break;
                }
            }

            }


            button.addClickListener(ee -> {

                employeeId = e.getId();
                employeeName = e.getFullName();
                employeeCategoryNew = e.getEmployeeCategory();
                employeeProfilePic = e.getProfileImage();

                if (selectedOrder.getEmployees() == null || !selectedOrder.getEmployees().stream().anyMatch(emp -> emp.getEmployee().getId().equals(employeeId)) ) {

                    employeeHolder.add(
                            loadEmployees(
                                    employeeId,
                                    employeeName,
                                    employeeCategoryNew,
                                    employeeProfilePic,
                                    selectedOrder
                            ));

                    OrderEmployees employees = new OrderEmployees();

                    Employee employee = new Employee();
                    employee.setId(employeeId);

                    employees.setEmployee(employee);

                    if(selectedOrder.getEmployees() == null){
                        selectedOrder.setEmployees(new ArrayList<>());
                    }
                    selectedOrder.getEmployees().add(employees);


                    employeeGrid.setItems(listEmployees);


                } else {
                    commonComponents.showNotification(String.format("%s %s", employeeName, "is already used"), 3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.LUMO_ERROR);
                }
            });

            return button;
        }).setHeader("Actions").setAutoWidth(true);

        employeeGrid.setPartNameGenerator(employee -> {
            // Check if the current row's employee is in the selectedOrder list
            if (selectedOrder != null && selectedOrder.getEmployees() != null) {
                boolean isAlreadySelected = selectedOrder.getEmployees().stream()
                        .anyMatch(emp -> emp.getEmployee().getId().equals(employee.getId()));


                if (isAlreadySelected) {
                    System.out.println("here");
                    return "newColor";
                }
            }
            return null;
        });

        VerticalLayout v = new VerticalLayout();
        v.setWidthFull();

        HorizontalLayout empDisplay = new HorizontalLayout();

        ComboBox<ComboBoxEmployees> comboBox = new ComboBox<>("Select employee");
        comboBox.setWidth("250px");
        comboBox.setItems(listEmployees);
        comboBox.setItemLabelGenerator(ComboBoxEmployees::getFullName);

        comboBox.setRenderer(new ComponentRenderer<>(employee -> {

            HorizontalLayout comboBoxEmployee = new HorizontalLayout();
            comboBoxEmployee.setAlignItems(FlexComponent.Alignment.CENTER);
            comboBoxEmployee.setWidthFull();

            Image image = commonComponents.imageCrafter(employee.getProfileImage() != null ? employee.getProfileImage() : "No_picture.png", "30px", "30px", "20px");
            Span employeeFullName = commonComponents.spanCrafter(employee.getFullName(), "stat-example");
            Span employeeRole = commonComponents.spanCrafter(employee.getEmployeeCategory().toString(), "stat-title");

            VerticalLayout nameRole = new VerticalLayout();
            nameRole.setWidthFull();

            nameRole.add(
                    employeeFullName,
                    employeeRole
            );

            comboBoxEmployee.add(
                    image,
                    nameRole
            );

            return comboBoxEmployee;

        }));


        comboBox.addValueChangeListener(e -> {
            employeeId = e.getValue().getId();
            employeeName = e.getValue().getFullName();
            employeeCategoryNew = e.getValue().getEmployeeCategory();
            employeeProfilePic = e.getValue().getProfileImage();

            empDisplay.removeAll();
            empDisplay.addClassName("island");
            empDisplay.setAlignItems(FlexComponent.Alignment.CENTER);

            Span empID = new Span(employeeId.toString());
            Image empImage = commonComponents.imageCrafter(employeeProfilePic != null ? employeeProfilePic : "No_picture.png", "40px", "40px", "20px");
            Span empName = new Span(employeeName);
            Span empRole = new Span(employeeCategoryNew.toString());

            empDisplay.add(
                    empID,
                    empImage,
                    empName,
                    empRole
            );

            v.removeAll();
            v.add(
                    comboBox,
                    empDisplay
            );

        });




        v.add(
                employeeGrid
        );

        dialog.add(
                v
        );


        dialog.open();

        return dialog;

    }


    public HorizontalLayout loadEmployees(Long id, String name, EmployeeCategory employeeCategory, String profilePic, Orders selectedOrder) {

        HorizontalLayout h = new HorizontalLayout();
        h.setAlignItems(FlexComponent.Alignment.CENTER);
        h.addClassName("island");
        h.setWidthFull();

        Button button = commonComponents.buttonThemeAndIconNoNavigate("", ButtonVariant.LUMO_ICON, VaadinIcon.TRASH, "Blue");
        button.setVisible(false);

        if (OrderStatus.Finished.equals(selectedOrder.getOrderStatus())) {
            button.setEnabled(false);
        }

        h.getElement().addEventListener("mouseenter", e -> {
            if (!OrderStatus.Finished.equals(selectedOrder.getOrderStatus())) {
                button.setVisible(true);
            }
        });


        h.getElement().addEventListener("mouseleave", e -> {
            if (!OrderStatus.Finished.equals(selectedOrder.getOrderStatus())) {
                button.setVisible(false);
            }

        });

        button.addClickListener(e -> {
            h.getParent().ifPresent(parent -> {
                ((HasComponents) parent).remove(h);
            });

            selectedOrder.getEmployees().removeIf(emp -> emp.getEmployee().getId().equals(id));


        });

        String url = "";
        if (profilePic != null) {
            url = profilePic;
        } else {
            url = "No_picture.png";
        }
        Image image = commonComponents.imageCrafter(url, "50px", "50px", "20px");
        Span fullName = commonComponents.spanCrafterWordNoHide(name, "stat-example");
        Span role = commonComponents.spanCrafterWordNoHide(employeeCategory.toString(), "stat-title");

        h.add(
                image,
                fullName,
                commonComponents.spaceFiller(),
                role,
                button
        );


        return h;
    }
}
