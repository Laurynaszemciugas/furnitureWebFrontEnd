package com.example.demo.Enums;


public enum ActionTrackerEnum {

    SYSTEM("System"),
    USER("User"),
    EMPLOYEE("Employee"),
    CLIENT("Client"),
    MANAGER("Manager"),
    ALL("All");

    private final String displayName;

    ActionTrackerEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}