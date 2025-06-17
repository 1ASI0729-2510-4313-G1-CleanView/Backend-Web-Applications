package pe.upc.cleanview.backend.monitoring.application.internal.queryservices;


import org.springframework.stereotype.Service;
import pe.upc.cleanview.backend.monitoring.domain.model.entities.Sensor;
import pe.upc.cleanview.backend.monitoring.domain.model.queries.GetAllSensorsQuery;
import pe.upc.cleanview.backend.monitoring.domain.model.queries.GetSensorByIdQuery;
import pe.upc.cleanview.backend.monitoring.domain.services.SensorQueryService;
import pe.upc.cleanview.backend.monitoring.infraestructure.persistence.jpa.repositories.SensorRepository;

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

}
