package pe.upc.cleanview.backend.monitoring.interfaces.rest.transform;

import pe.upc.cleanview.backend.monitoring.domain.model.commands.UpdateStoreCommand;
import pe.upc.cleanview.backend.monitoring.interfaces.rest.resources.UpdateStoreResource;

public class UpdateStoreCommandFromResourceAssembler {
    public static UpdateStoreCommand toCommandFromResource(Long id, UpdateStoreResource resource) {
        return new UpdateStoreCommand(
                id,
                resource.sensorsId(),
                resource.name(),
                resource.numberStore(),
                resource.amountSensor(),
                resource.fillPercent(),
                resource.color(),
                resource.ubication()
        );
    }
}
