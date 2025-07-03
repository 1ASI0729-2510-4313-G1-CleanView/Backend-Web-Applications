package com.acme.center.platform.monitoring.interfaces.rest.resources;

public record CreateWasteResource(
        String name,
        String type,
        int amount
) {
}
