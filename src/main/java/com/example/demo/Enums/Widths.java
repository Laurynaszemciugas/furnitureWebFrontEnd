package com.example.demo.Enums;

public enum Widths {

    FULL_WIDTH("Full Width", "100%"),
    HALF_WIDTH("Half Width", "calc(50% - 8px)"),
    THREE_QUARTS("Three Quarters", "calc(75% - 8px)"),
    QUARTER_WIDTH("Quarter Width", "calc(25% - 8px)"),
    AUTO_WIDTH("Auto Width", "auto");

    private final String name;
    private final String width;

    Widths(String name, String width) {
        this.name = name;
        this.width = width;
    }

    public String getName() {
        return name;
    }

    public String getWidth() {
        return width;
    }

    @Override
    public String toString() {
        return name;
    }
}