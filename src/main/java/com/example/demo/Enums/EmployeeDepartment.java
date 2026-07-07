package com.example.demo.Enums;

public enum EmployeeDepartment {

    PRODUCTION("Production"),
    ASSEMBLY("Assembly"),
    LOGISTICS("Logistics"),
    FINISHING("Finishing"),
    HELPING("Helping"),
    ALL("All");

    private final String displayName;

    EmployeeDepartment(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}