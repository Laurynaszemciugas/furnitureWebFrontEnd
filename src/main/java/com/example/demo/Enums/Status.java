package com.example.demo.Enums;

public enum Status {

    Enabled("Enabled"),
    Disabled("Disabled");

    private String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
