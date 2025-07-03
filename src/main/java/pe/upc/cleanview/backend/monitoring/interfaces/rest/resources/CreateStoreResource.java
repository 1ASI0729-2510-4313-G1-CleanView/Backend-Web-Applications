package com.acme.center.platform.monitoring.interfaces.rest.resources;

import java.util.List;

public record CreateStoreResource(
        String name,
        int storeNumber,
        int amountSensor,
        String fillPercent,
        String color,
        String ubication
) {

    public CreateStoreResource {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name cannot be blank");
        if (storeNumber < 0) throw new IllegalArgumentException("store_number must be non-negative");
        if (amountSensor < 0) throw new IllegalArgumentException("amountSensor must be non-negative");
        if (fillPercent == null || fillPercent.isBlank()) throw new IllegalArgumentException("fillPercent cannot be blank");
        if (color == null || color.isBlank()) throw new IllegalArgumentException("color cannot be blank");
        if (ubication == null || ubication.isBlank()) throw new IllegalArgumentException("ubication cannot be blank");
    }
}
