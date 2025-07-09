package pe.upc.cleanview.backend.monitoring.interfaces.rest.transform;

import pe.upc.cleanview.backend.monitoring.domain.model.commands.CreateSensorCommand;
import pe.upc.cleanview.backend.monitoring.interfaces.rest.resources.CreateSensorResource;


public class CreateSensorCommandFromResourceAssembler {

    public static CreateSensorCommand toCommandFromResource(CreateSensorResource resource) {
        return new CreateSensorCommand(
                resource.wastesId(),
                resource.serialNumber(),
                resource.location(),
                resource.battery(),
                resource.units(),
                resource.capacity(),
                resource.currentCapacity(),
                resource.percentage(),
                resource.collection()
        );
    }

}
