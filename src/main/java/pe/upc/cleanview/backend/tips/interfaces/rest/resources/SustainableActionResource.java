package pe.upc.cleanview.backend.tips.interfaces.rest.resources;

public record SustainableActionResource(
        Long id,
        String title,
        String description,
        String type,
        Long creatorUserId
) {
}