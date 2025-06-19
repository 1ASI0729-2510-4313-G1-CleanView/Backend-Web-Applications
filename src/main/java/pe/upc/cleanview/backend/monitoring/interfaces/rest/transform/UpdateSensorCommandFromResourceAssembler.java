package pe.upc.cleanview.backend.monitoring.interfaces.rest.transform;


import pe.upc.cleanview.backend.monitoring.domain.model.commands.UpdateSensorCommand;
import pe.upc.cleanview.backend.monitoring.interfaces.rest.resources.UpdateSensorResource;

public class UpdateSensorCommandFromResourceAssembler {

    public static UpdateSensorCommand toCommandFromResource(Long id, UpdateSensorResource resource) {
        return new UpdateSensorCommand(
                id,
                resource.serialNumber(),
                resource.wastesId(),
                resource.location(),
                resource.status(),
                resource.battery(),
                resource.lastModified(),
                resource.units(),
                resource.capacity(),
                resource.currentCapacity(),
                resource.percentage(),
                resource.collection()
        );
    }

}
