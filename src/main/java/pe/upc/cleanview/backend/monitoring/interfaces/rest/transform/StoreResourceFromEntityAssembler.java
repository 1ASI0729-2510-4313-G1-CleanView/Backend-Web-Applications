package pe.upc.cleanview.backend.monitoring.interfaces.rest.transform;

import pe.upc.cleanview.backend.monitoring.domain.model.aggregates.Sensor;
import pe.upc.cleanview.backend.monitoring.domain.model.aggregates.Store;
import pe.upc.cleanview.backend.monitoring.domain.model.entities.Waste;
import pe.upc.cleanview.backend.monitoring.interfaces.rest.resources.StoreResource;

import java.util.List;
import java.util.Optional;

public class StoreResourceFromEntityAssembler {

    public static StoreResource toResourceFromEntity(Store entity){

        System.out.println(entity.getSensorsId().stream().map(Sensor::getId).toList());

        return new StoreResource(
                entity.getId(),
                /* *
                 * if list is empty going to return empty json
                 * example: []
                 * */
                entity.getSensorsId()
                        .stream()
                        .map(Sensor::getId)
                        .toList(),
                entity.getName(),
                entity.getNumberStore(),
                entity.getAmountSensor(),
                entity.getFillPercent(),
                entity.getColor(),
                entity.getUbication()
        );
    }

}
