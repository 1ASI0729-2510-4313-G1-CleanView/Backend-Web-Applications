package com.acme.center.platform.monitoring.domain.model.commands;


import java.util.List;

public record UpdateStoreCommand(
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
