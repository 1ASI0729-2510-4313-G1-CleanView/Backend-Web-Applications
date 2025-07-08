package pe.upc.cleanview.backend.profiles.interfaces.rest.resources;

public record UpdateProfileResource(
        String email,
        Integer age
) {
}