package com.acme.center.platform.monitoring.interfaces.rest.transform;

import com.acme.center.platform.monitoring.domain.model.commands.CreateStoreCommand;
import com.acme.center.platform.monitoring.interfaces.rest.resources.CreateStoreResource;

public class CreateStoreCommandFromResourceAssembler {

    public static CreateStoreCommand toCommandFromResource(CreateStoreResource resource) {
        return new CreateStoreCommand(
                resource.name(),
                resource.storeNumber(),
                resource.amountSensor(),
                resource.fillPercent(),
                resource.color(),
                resource.ubication()
        );
    }

}
