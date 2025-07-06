package pe.upc.cleanview.backend.monitoring.domain.services;

import pe.upc.cleanview.backend.monitoring.domain.model.commands.CreateSensorCommand;
import pe.upc.cleanview.backend.monitoring.domain.model.commands.DeleteSensorCommand;
import pe.upc.cleanview.backend.monitoring.domain.model.commands.UpdateSensorCommand;
import pe.upc.cleanview.backend.monitoring.domain.model.aggregates.Sensor;

import java.util.Optional;

public interface SensorCommandService {

    Optional<Sensor> handle(CreateSensorCommand command);

    Optional<Sensor> handle(UpdateSensorCommand command);

    void handle(DeleteSensorCommand command);

}
