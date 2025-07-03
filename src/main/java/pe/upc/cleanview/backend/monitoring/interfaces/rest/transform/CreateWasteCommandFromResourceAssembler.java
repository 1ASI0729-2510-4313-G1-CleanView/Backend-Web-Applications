package com.acme.center.platform.monitoring.interfaces.rest.transform;

import com.acme.center.platform.monitoring.domain.model.commands.CreateWasteCommand;
import com.acme.center.platform.monitoring.interfaces.rest.resources.CreateWasteResource;

public class CreateWasteCommandFromResourceAssembler {

    public static CreateWasteCommand toCommandFromResource(CreateWasteResource resource) {
        return new CreateWasteCommand(
                resource.name(),
                resource.type(),
                resource.amount()
        );
    }

}
