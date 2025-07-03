package com.acme.center.platform.monitoring.interfaces.rest.transform;

import com.acme.center.platform.monitoring.domain.model.commands.CreateSensorCommand;
import com.acme.center.platform.monitoring.interfaces.rest.resources.CreateSensorResource;


public class CreateSensorCommandFromResourceAssembler {

    public static CreateSensorCommand toCommandFromResource(CreateSensorResource resource) {
        return new CreateSensorCommand(
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
