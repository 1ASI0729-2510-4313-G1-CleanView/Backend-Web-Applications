package com.acme.center.platform.monitoring.interfaces.rest.resources;

public record UpdateWasteResource(
        Long id,
        String name,
        String type,
        int amount
) {
}
