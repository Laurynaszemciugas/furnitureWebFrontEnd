package com.example.demo.Enums;

public enum Priority {

    LOW_PRIORITY("Low Priority"),
    MEDIUM_PRIORITY("Medium Priority"),
    HIGH_PRIORITY("High Priority"),
    OVERDUE("Overdue"),
    ALL("All");

    private final String displayName;

    Priority(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}