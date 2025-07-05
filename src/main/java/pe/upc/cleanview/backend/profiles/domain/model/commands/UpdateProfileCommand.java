package pe.upc.cleanview.backend.profiles.domain.model.commands;

public record UpdateProfileCommand(Long profileId,
                                   String email,
                                   Integer age) {
}
