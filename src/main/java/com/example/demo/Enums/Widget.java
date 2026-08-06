package com.example.demo.Enums;

import com.vaadin.flow.component.icon.VaadinIcon;

public enum Widget {

    ORDER_MINI_STATS("Order Overview", VaadinIcon.CLIPBOARD_CHECK, "#2563EB","No_picture.png"),
    ORDER_BY_STATUS("Orders by Status", VaadinIcon.BAR_CHART, "#0EA5E9","No_picture.png"),
    ORDER_VALUE_OVER_TIME("Order Value Trend", VaadinIcon.LINE_CHART, "#10B981","No_picture.png"),
    ORDER_TOP_CUSTOMERS("Top Customers", VaadinIcon.USERS, "#8B5CF6","No_picture.png"),
    ORDER_RECENT_ORDERS("Recent Orders", VaadinIcon.CLOCK, "#F59E0B","No_picture.png"),

    PRODUCT_MINI_STATS("Product Overview", VaadinIcon.PACKAGE, "#2563EB","No_picture.png"),
    PRODUCT_TOP_SELLING_PRODUCTS("Top Selling Products", VaadinIcon.TROPHY, "#F59E0B","No_picture.png"),
    PRODUCT_BY_CATEGORY("Products by Category", VaadinIcon.GRID_BIG, "#06B6D4","No_picture.png"),
    PRODUCT_LOW_STOCK("Low Stock Products", VaadinIcon.WARNING, "#EF4444","No_picture.png"),
    PRODUCT_PERFORMANCE("Product Performance", VaadinIcon.TRENDING_UP, "#22C55E","No_picture.png"),

    MATERIAL_MINI_STATS("Material Overview", VaadinIcon.CUBE, "#2563EB","No_picture.png"),
    MATERIAL_BY_STATUS("Materials by Status", VaadinIcon.CHECK_CIRCLE, "#14B8A6","No_picture.png"),
    MATERIAL_USAGE_OVERTIME("Material Usage Trend", VaadinIcon.LINE_CHART, "#84CC16","No_picture.png"),
    MATERIAL_LOW_STOCK("Low Stock Materials", VaadinIcon.WARNING, "#DC2626","No_picture.png"),
    MATERIAL_RECENT_MOVEMENT("Recent Material Movement", VaadinIcon.EXCHANGE, "#6366F1","No_picture.png");

    private final String title;
    private final VaadinIcon icon;
    private final String color;
    private final String imageUrl;

    Widget(String title, VaadinIcon icon, String color, String imageUrl) {
        this.title = title;
        this.icon = icon;
        this.color = color;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public VaadinIcon getIcon() {
        return icon;
    }

    public String getColor() {
        return color;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}