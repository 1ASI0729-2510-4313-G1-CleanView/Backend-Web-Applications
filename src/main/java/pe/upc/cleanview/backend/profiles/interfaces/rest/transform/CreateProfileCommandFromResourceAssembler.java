package pe.upc.cleanview.backend.profiles.interfaces.rest.transform;

import pe.upc.cleanview.backend.profiles.domain.model.commands.CreateProfileCommand;
import pe.upc.cleanview.backend.profiles.domain.model.valueobjects.UserId;
import pe.upc.cleanview.backend.profiles.interfaces.rest.resources.CreateProfileResource;

public class CreateProfileCommandFromResourceAssembler {
    public static CreateProfileCommand toCommandFromResource(CreateProfileResource resource) {
        return new CreateProfileCommand(
                resource.username(),
                resource.password(),
                new UserId(resource.userId())
        );
    }
}
