package pe.upc.cleanview.backend.profiles.domain.model.commands;

import pe.upc.cleanview.backend.profiles.domain.model.valueobjects.UserId;

public record CreateProfileCommand(String username,
                                   String password,
                                   UserId userId) {
}