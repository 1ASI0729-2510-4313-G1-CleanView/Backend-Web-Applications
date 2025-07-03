package com.acme.center.platform.monitoring.domain.model.commands;

public record CreateWasteCommand(
        String name,
        String type,
        int amount
) {
}
