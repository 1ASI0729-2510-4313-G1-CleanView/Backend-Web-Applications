package com.acme.center.platform.monitoring.application.internal.queryservices;

import com.acme.center.platform.monitoring.domain.model.aggregates.Sensor;
import com.acme.center.platform.monitoring.domain.model.queries.GetAllSensorByStoreIdQuery;
import com.acme.center.platform.monitoring.domain.model.queries.GetAllSensorsQuery;
import com.acme.center.platform.monitoring.domain.model.queries.GetSensorByIdQuery;
import com.acme.center.platform.monitoring.domain.services.SensorQueryService;
import com.acme.center.platform.monitoring.infraestructure.persistence.jpa.repositories.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SensorQueryServiceImpl implements SensorQueryService {

    private SensorRepository sensorRepository;

    SensorQueryServiceImpl(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }


    @Override
    public List<Sensor> handle(GetAllSensorsQuery query) {
        return sensorRepository.findAll();
    }

    @Override
    public Optional<Sensor> handle(GetSensorByIdQuery query) {
        return sensorRepository.findById(query.id());
    }

    @Override
    public List<Sensor> handle(GetAllSensorByStoreIdQuery query) {
        return List.of();
    }

}
