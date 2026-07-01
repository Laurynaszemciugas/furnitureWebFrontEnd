package com.example.demo.Enums;

public enum EmployeeAcIn {

    ACTIVE("Active"),
    INACTIVE("Inactive"),
    ON_LEAVE("On leave"),
    ALL("All");

    private String displayName;

    EmployeeAcIn(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
