package com.example.demo.Enums;

public enum MaterialType {

    WOOD("Wood"),
    METAL("Metal"),
    PLASTIC("Plastic"),
    GLASS("Glass"),
    STONE("Stone"),
    MARBLE("Marble"),
    GRANITE("Granite"),
    CONCRETE("Concrete"),

    LEATHER("Leather"),
    FABRIC("Fabric"),
    VELVET("Velvet"),
    LINEN("Linen"),
    COTTON("Cotton"),

    RATTAN("Rattan"),
    CORK("Cork"),

    CERAMIC("Ceramic"),
    PORCELAIN("Porcelain"),

    RUBBER("Rubber"),
    FOAM("Foam"),

    PLYWOOD("Plywood"),
    PARTICLE_BOARD("Particle Board"),
    LAMINATE("Laminate"),

    CARBON_FIBER("Carbon Fiber"),
    ACRYLIC("Acrylic"),
    ALL("All");

    private final String displayName;

    MaterialType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}