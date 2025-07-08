package pe.upc.cleanview.backend.monitoring.interfaces.rest.resources;

public record WasteResource(
        Long id,
        String name,
        String type,
        int amount
) {
}
