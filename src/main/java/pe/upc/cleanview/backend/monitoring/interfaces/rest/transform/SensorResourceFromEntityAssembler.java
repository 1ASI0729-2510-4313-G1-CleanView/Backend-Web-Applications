package pe.upc.cleanview.backend.monitoring.interfaces.rest.transform;

import pe.upc.cleanview.backend.monitoring.domain.model.aggregates.Sensor;
import pe.upc.cleanview.backend.monitoring.domain.model.entities.Waste;
import pe.upc.cleanview.backend.monitoring.interfaces.rest.resources.SensorResource;

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
