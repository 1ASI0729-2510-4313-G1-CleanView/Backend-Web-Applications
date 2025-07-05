package pe.upc.cleanview.backend.monitoring.interfaces.rest.resources;

public record CreateWasteResource(
        String name,
        String type,
        int amount
) {
}
