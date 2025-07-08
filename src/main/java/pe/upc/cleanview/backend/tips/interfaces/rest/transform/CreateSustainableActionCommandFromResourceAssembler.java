package pe.upc.cleanview.backend.tips.interfaces.rest.transform;

import pe.upc.cleanview.backend.tips.domain.model.commands.CreateSustainableActionCommand;
import pe.upc.cleanview.backend.tips.interfaces.rest.resources.CreateSustainableActionResource;

public class CreateSustainableActionCommandFromResourceAssembler {
    public static CreateSustainableActionCommand toCommandFromResource(CreateSustainableActionResource resource, Long creatorUserId) {
        return new CreateSustainableActionCommand(
                resource.title(),
                resource.description(),
                resource.type(),
                creatorUserId
        );
    }
}