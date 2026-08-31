package com.example.demo.Enums;

public enum ActionDesciptionEnum {

    Order_Created("Order Created"),
    Order_Updated("Order Updated"),
    Order_Deleted("Order Deleted"),
    Order_Status_Change("Order Status Changed"),

    Product_Created("Product Created"),
    Product_Updated("Product Updated"),
    Product_Deleted("Product Deleted"),
    Product_Status_Change("Product Status Changed"),

    Material_Created("Material Created"),
    Material_Updated("Material Updated"),
    Material_Deleted("Material Deleted"),
    Material_Status_Change("Material Status Changed"),
    Material_Stock_Change("Material Stock Changed"),

    Employee_Created("Employee Created"),
    Employee_Updated("Employee Updated"),
    Employee_Deleted("Employee Deleted"),
    Employee_Status_Change("Employee Status Changed"),
    Employee_Work_Started("Employee Work Started"),
    Employee_Work_Ended("Employee Work Ended"),

    System_Check("System Check"),

    ALL("All");

    private final String displayName;

    ActionDesciptionEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}