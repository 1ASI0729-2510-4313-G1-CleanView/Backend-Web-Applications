package com.acme.center.platform.monitoring.interfaces.rest.transform;

import com.acme.center.platform.monitoring.domain.model.aggregates.Sensor;
import com.acme.center.platform.monitoring.domain.model.aggregates.Store;
import com.acme.center.platform.monitoring.domain.model.entities.Waste;
import com.acme.center.platform.monitoring.interfaces.rest.resources.StoreResource;

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
                entity.getStoreNumber(),
                entity.getAmountSensor(),
                entity.getFillPercent(),
                entity.getColor(),
                entity.getUbication()
        );
    }

}
