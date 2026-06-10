package com.example.demo.Enums;

public enum Enabled {

    ALL("All"),
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private String getDisplayNames;

    Enabled(String getDisplayNames) {
        this.getDisplayNames = getDisplayNames;
    }

    public String getGetDisplayNames() {
        return getDisplayNames;
    }
}
