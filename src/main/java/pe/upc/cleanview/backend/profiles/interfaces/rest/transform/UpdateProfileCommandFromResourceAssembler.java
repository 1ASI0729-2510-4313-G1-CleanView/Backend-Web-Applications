package pe.upc.cleanview.backend.profiles.interfaces.rest.transform;


import pe.upc.cleanview.backend.profiles.domain.model.commands.UpdateProfileCommand;
import pe.upc.cleanview.backend.profiles.interfaces.rest.resources.UpdateProfileResource;

public class UpdateProfileCommandFromResourceAssembler {
    public static UpdateProfileCommand toCommandFromResource(Long profileId, UpdateProfileResource resource) {
        return new UpdateProfileCommand(
                profileId,
                resource.email(),
                resource.age()
        );
    }
}
