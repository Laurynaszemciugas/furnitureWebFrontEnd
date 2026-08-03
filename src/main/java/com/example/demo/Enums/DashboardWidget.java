package com.example.demo.Enums;

import com.vaadin.flow.component.icon.VaadinIcon;

public enum DashboardWidget {

    ORDER_STATS("Order Statistics", VaadinIcon.CLIPBOARD_CHECK),
    PRODUCT_STATS("Product Statistics", VaadinIcon.PACKAGE),
    MATERIAL_STATS("Material Statistics", VaadinIcon.CUBE),
    EMPLOYEE_STATS("Employee Statistics", VaadinIcon.USERS),
    SALES_REPORT("Sales Report", VaadinIcon.CHART),
    FINANCIAL_REPORT("Financial Report", VaadinIcon.MONEY),
    INVENTORY_REPORT("Inventory Report", VaadinIcon.ARCHIVES),
    LOW_STOCK("Low Stock", VaadinIcon.WARNING),
    RECENT_ORDERS("Recent Orders", VaadinIcon.CLOCK),
    TOP_PRODUCTS("Top Products", VaadinIcon.TROPHY),
    CUSTOMER_REPORT("Customer Report", VaadinIcon.USER_CARD),
    ORDER_STATUS("Order Status", VaadinIcon.TASKS),
    REVENUE_CHART("Revenue Chart", VaadinIcon.LINE_CHART),
    MATERIAL_USAGE("Material Usage", VaadinIcon.TOOLS),
    PRODUCTION_PROGRESS("Production Progress", VaadinIcon.FACTORY),
    CUSTOM_WIDGET("Custom Widget", VaadinIcon.COG);

    private final String name;
    private final VaadinIcon icon;

    DashboardWidget(String name, VaadinIcon icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public VaadinIcon getIcon() {
        return icon;
    }
}