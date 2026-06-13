package com.example.demo.Enums;

public enum ActiveInactive {


    ACTIVE("Active"),
    INACTIVE("Inactive"),
    ALL("All");

    private String getDisplayNames;

    ActiveInactive(String getDisplayNames) {
        this.getDisplayNames = getDisplayNames;
    }

    public String getGetDisplayNames() {
        return getDisplayNames;
    }
}
