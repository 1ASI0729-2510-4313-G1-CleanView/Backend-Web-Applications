package com.acme.center.platform.monitoring.interfaces.rest.resources;

import java.util.List;

public record StoreResource(
        Long id,
        List<Long> sensorsId,
        String name,
        int storeNumber,
        int amountSensor,
        String fillPercent,
        String color,
        String ubication
) {
}
