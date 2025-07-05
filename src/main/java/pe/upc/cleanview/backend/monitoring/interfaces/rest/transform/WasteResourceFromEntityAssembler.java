package pe.upc.cleanview.backend.monitoring.interfaces.rest.transform;

import pe.upc.cleanview.backend.monitoring.domain.model.entities.Waste;
import pe.upc.cleanview.backend.monitoring.interfaces.rest.resources.WasteResource;

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
