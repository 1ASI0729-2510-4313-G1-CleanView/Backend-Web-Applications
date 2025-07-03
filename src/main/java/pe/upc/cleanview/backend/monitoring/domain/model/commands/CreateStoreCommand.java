package com.acme.center.platform.monitoring.domain.model.commands;



public record CreateStoreCommand(
        String name,
        int storeNumber,
        int amountSensor,
        String fillPercent,
        String color,
        String ubication
) {}