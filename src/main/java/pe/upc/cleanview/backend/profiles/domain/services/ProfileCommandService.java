package pe.upc.cleanview.backend.profiles.domain.services;



import pe.upc.cleanview.backend.profiles.domain.model.aggregates.Profile;
import pe.upc.cleanview.backend.profiles.domain.model.commands.CreateProfileCommand;
import pe.upc.cleanview.backend.profiles.domain.model.commands.DeleteProfileCommand;
import pe.upc.cleanview.backend.profiles.domain.model.commands.UpdateProfileCommand;

import java.util.Optional;

public interface ProfileCommandService {
    Optional<Profile> handle(CreateProfileCommand command);
    Optional<Profile> handle(UpdateProfileCommand command);
    void handle(DeleteProfileCommand command);
}
