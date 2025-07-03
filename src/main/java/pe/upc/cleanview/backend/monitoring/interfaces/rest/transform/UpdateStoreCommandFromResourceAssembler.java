package com.acme.center.platform.monitoring.interfaces.rest.transform;

import com.acme.center.platform.monitoring.domain.model.commands.UpdateStoreCommand;
import com.acme.center.platform.monitoring.interfaces.rest.resources.UpdateStoreResource;

public class UpdateStoreCommandFromResourceAssembler {
    public static UpdateStoreCommand toCommandFromResource(Long id, UpdateStoreResource resource) {
        return new UpdateStoreCommand(
                id,
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
