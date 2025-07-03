package com.acme.center.platform.monitoring.interfaces.rest.transform;

import com.acme.center.platform.monitoring.domain.model.entities.Waste;
import com.acme.center.platform.monitoring.interfaces.rest.resources.WasteResource;

public class WasteResourceFromEntityAssembler {

    public static WasteResource toResourceFromEntity(Waste entity){
        return new WasteResource(
                entity.getId(),
                entity.getName(),
                entity.getType().getWasteType(),
                entity.getAmount()
        );
    }

}
