package pe.upc.cleanview.backend.monitoring.interfaces.rest.transform;

import pe.upc.cleanview.backend.monitoring.domain.model.entities.Sensor;
import pe.upc.cleanview.backend.monitoring.interfaces.rest.resources.SensorResource;

public class SensorResourceFromEntityAssembler {

    public static SensorResource toResourceFromEntity(Sensor entity) {
        return new SensorResource(
                entity.getId(),
                entity.getSerialNumber(),
                entity.getWastesId(),
                entity.getLocation(),
                entity.getStatus(),
                entity.getBattery(),
                entity.getLastModified(),
                entity.getUnits(),
                entity.getCapacity(),
                entity.getCurrentCapacity(),
                entity.getPercentage(),
                entity.getCollection()
        );
    }

}
