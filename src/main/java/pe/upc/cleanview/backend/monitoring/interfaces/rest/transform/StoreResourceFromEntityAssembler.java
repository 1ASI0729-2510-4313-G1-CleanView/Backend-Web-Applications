package pe.upc.cleanview.backend.monitoring.interfaces.rest.transform;

import pe.upc.cleanview.backend.monitoring.domain.model.entities.Store;
import pe.upc.cleanview.backend.monitoring.interfaces.rest.resources.StoreResource;

public class StoreResourceFromEntityAssembler {

    public static StoreResource toResourceFromEntity(Store entity){
        return new StoreResource(
                entity.getId(),
                entity.getSensorsId(),
                entity.getName(),
                entity.getStoreNumber(),
                entity.getAmountSensor(),
                entity.getFillPercent(),
                entity.getColor(),
                entity.getUbication()
        );
    }

}
