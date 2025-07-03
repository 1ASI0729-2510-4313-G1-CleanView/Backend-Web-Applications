package com.acme.center.platform.monitoring.interfaces.rest.transform;

import com.acme.center.platform.monitoring.domain.model.aggregates.Sensor;
import com.acme.center.platform.monitoring.domain.model.entities.Waste;
import com.acme.center.platform.monitoring.interfaces.rest.resources.SensorResource;

import java.util.stream.Collectors;

public class SensorResourceFromEntityAssembler {

    public static SensorResource toResourceFromEntity(Sensor entity) {

        System.out.println(entity.getWastesId().stream().map(Waste::getId).toList());

        return new SensorResource(
                entity.getId(),
                entity.getWastesId().stream().map(Waste::getId).toList(),
                entity.getSerialNumber(),
                entity.getLocation(),
                entity.getBattery(),
                entity.getLastDetection(),
                entity.getUnits(),
                entity.getCapacity(),
                entity.getCurrentCapacity(),
                entity.getPercentage(),
                entity.getCollection(),
                entity.getStatus()
        );
    }

}
