package com.acme.center.platform.monitoring.domain.services;

import com.acme.center.platform.monitoring.domain.model.aggregates.Sensor;
import com.acme.center.platform.monitoring.domain.model.queries.GetAllSensorByStoreIdQuery;
import com.acme.center.platform.monitoring.domain.model.queries.GetAllSensorsQuery;
import com.acme.center.platform.monitoring.domain.model.queries.GetSensorByIdQuery;

import java.util.List;
import java.util.Optional;

public interface SensorQueryService {

    List<Sensor> handle(GetAllSensorsQuery query);

    Optional<Sensor> handle(GetSensorByIdQuery query);

    List<Sensor> handle(GetAllSensorByStoreIdQuery query);
}
