package pe.upc.cleanview.backend.monitoring.interfaces.rest.resources;

public record UpdateWasteResource(
        Long id,
        String name,
        String type,
        int amount
) {
}
