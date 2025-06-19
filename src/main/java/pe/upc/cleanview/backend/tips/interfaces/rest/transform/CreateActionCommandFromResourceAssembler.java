package pe.upc.cleanview.backend.tips.interfaces.rest.transform;

import pe.upc.cleanview.backend.tips.domain.model.commands.CreateActionCommand;
import pe.upc.cleanview.backend.tips.interfaces.rest.resources.CreateActionResource;

public class CreateActionCommandFromResourceAssembler {

    public static CreateActionCommand toCommandFromResource(CreateActionResource resource) {
        return new CreateActionCommand(
                resource.title(),
                resource.description(),
                resource.type(),
                resource.favorite()
        );
    }
}
