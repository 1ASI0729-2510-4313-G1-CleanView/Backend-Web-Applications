package com.acme.center.platform.monitoring.domain.services;

import com.acme.center.platform.monitoring.domain.model.commands.CreateSensorCommand;
import com.acme.center.platform.monitoring.domain.model.commands.DeleteSensorCommand;
import com.acme.center.platform.monitoring.domain.model.commands.UpdateSensorCommand;
import com.acme.center.platform.monitoring.domain.model.aggregates.Sensor;

import java.util.Optional;

public interface SensorCommandService {

    Optional<Sensor> handle(CreateSensorCommand command);

    Optional<Sensor> handle(UpdateSensorCommand command);

    void handle(DeleteSensorCommand command);

}
