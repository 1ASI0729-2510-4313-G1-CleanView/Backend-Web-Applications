package pe.upc.cleanview.backend.monitoring.domain.services;

import pe.upc.cleanview.backend.monitoring.domain.model.aggregates.Sensor;
import pe.upc.cleanview.backend.monitoring.domain.model.queries.GetAllSensorByStoreIdQuery;
import pe.upc.cleanview.backend.monitoring.domain.model.queries.GetAllSensorsQuery;
import pe.upc.cleanview.backend.monitoring.domain.model.queries.GetSensorByIdQuery;

import java.util.List;
import java.util.Optional;

public interface SensorQueryService {

    List<Sensor> handle(GetAllSensorsQuery query);

    Optional<Sensor> handle(GetSensorByIdQuery query);

    List<Sensor> handle(GetAllSensorByStoreIdQuery query);
}
