package pe.upc.cleanview.backend.tips.interfaces.rest.resources;

public record CreateSustainableActionResource(
        String title,
        String description,
        String type,
        Long creatorUserId) {
}
