package com.acme.center.platform.monitoring.interfaces.rest.resources;

public record WasteResource(
        Long id,
        String name,
        String type,
        int amount
) {
}
