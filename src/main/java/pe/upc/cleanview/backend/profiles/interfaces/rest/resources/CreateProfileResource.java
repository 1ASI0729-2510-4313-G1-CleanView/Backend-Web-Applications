package pe.upc.cleanview.backend.profiles.interfaces.rest.resources;

public record CreateProfileResource(
        String username,
        String password,
        Long userId) {
}
