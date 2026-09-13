package com.example.demo.Enums;

import lombok.Getter;

@Getter
public enum ProductFinishStepStatus {

    FINISHED("Finished"),
    IN_PROGRESS("In Progress"),
    NOT_STARTED("Not Started");

    private final String displayName;

    ProductFinishStepStatus(String displayName) {
        this.displayName = displayName;
    }
}
