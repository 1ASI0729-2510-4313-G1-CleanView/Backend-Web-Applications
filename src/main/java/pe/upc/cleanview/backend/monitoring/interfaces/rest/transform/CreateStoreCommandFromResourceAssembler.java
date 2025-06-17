package pe.upc.cleanview.backend.monitoring.interfaces.rest.transform;

import pe.upc.cleanview.backend.monitoring.domain.model.commands.CreateStoreCommand;
import pe.upc.cleanview.backend.monitoring.interfaces.rest.resources.CreateStoreResource;

public class CreateStoreCommandFromResourceAssembler {

    public static CreateStoreCommand toCommandFromResource(CreateStoreResource resource) {
        return new CreateStoreCommand(
                resource.sensorsId(),
                resource.name(),
                resource.storeNumber(),
                resource.amountSensor(),
                resource.fillPercent(),
                resource.color(),
                resource.ubication()
        );
    }

}
