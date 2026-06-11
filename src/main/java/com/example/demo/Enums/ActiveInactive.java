package com.example.demo.Enums;

public enum ActiveInactive {

    ALL("All"),
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private String getDisplayNames;

    ActiveInactive(String getDisplayNames) {
        this.getDisplayNames = getDisplayNames;
    }

    public String getGetDisplayNames() {
        return getDisplayNames;
    }
}
