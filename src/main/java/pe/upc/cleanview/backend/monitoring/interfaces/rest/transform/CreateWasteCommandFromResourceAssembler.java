package pe.upc.cleanview.backend.monitoring.interfaces.rest.transform;

import pe.upc.cleanview.backend.monitoring.domain.model.commands.CreateWasteCommand;
import pe.upc.cleanview.backend.monitoring.interfaces.rest.resources.CreateWasteResource;

public class CreateWasteCommandFromResourceAssembler {

    public static CreateWasteCommand toCommandFromResource(CreateWasteResource resource) {
        return new CreateWasteCommand(
                resource.name(),
                resource.type(),
                resource.amount()
        );
    }

}
