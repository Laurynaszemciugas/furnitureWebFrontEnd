package com.example.demo.Enums;

public enum OrderStatus {

    In_Progress("In Progress"),
    Finished("Finished"),
    Pending("Pending"),
    CANCELLED("Cancelled"),
    ALL("All");

    private String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
