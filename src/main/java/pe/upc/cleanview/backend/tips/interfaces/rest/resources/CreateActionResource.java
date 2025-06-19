package pe.upc.cleanview.backend.tips.interfaces.rest.resources;

public record CreateActionResource(
        String title,
        String description,
        String type,
        boolean favorite) {
}
