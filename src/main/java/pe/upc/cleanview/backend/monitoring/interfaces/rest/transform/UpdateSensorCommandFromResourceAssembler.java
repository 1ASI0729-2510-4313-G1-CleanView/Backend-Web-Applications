package pe.upc.cleanview.backend.monitoring.interfaces.rest.transform;

import pe.upc.cleanview.backend.monitoring.domain.model.commands.UpdateSensorCommand;
import pe.upc.cleanview.backend.monitoring.interfaces.rest.resources.UpdateSensorResource;

public class UpdateSensorCommandFromResourceAssembler {

    public static UpdateSensorCommand toCommandFromResource(Long id, UpdateSensorResource resource) {
        return new UpdateSensorCommand(
                id,
                resource.wastesId(),
                resource.serialNumber(),
                resource.location(),
                resource.battery(),
                resource.lastDetection(),
                resource.units(),
                resource.capacity(),
                resource.currentCapacity(),
                resource.percentage(),
                resource.collection()
        );
    }

}
