package pe.upc.cleanview.backend.monitoring.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.upc.cleanview.backend.monitoring.domain.model.commands.CreateSensorCommand;
import pe.upc.cleanview.backend.monitoring.domain.model.commands.DeleteSensorCommand;
import pe.upc.cleanview.backend.monitoring.domain.model.commands.UpdateSensorCommand;
import pe.upc.cleanview.backend.monitoring.domain.model.entities.Sensor;
import pe.upc.cleanview.backend.monitoring.domain.services.SensorCommandService;
import pe.upc.cleanview.backend.monitoring.infraestructure.persistence.jpa.repositories.SensorRepository;

import java.util.Optional;

@Service
public class SensorCommandServiceImpl implements SensorCommandService {

    private SensorRepository sensorRepository;

    SensorCommandServiceImpl(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Override
    public Optional<Sensor> handle(CreateSensorCommand command) {
        var sensor = new Sensor(command);
        sensorRepository.save(sensor);

        return Optional.of(sensor);
    }

    @Override
    public Optional<Sensor> handle(UpdateSensorCommand command) {
        var sensorOptional = sensorRepository.findById(command.id());
        if (sensorOptional.isEmpty())
            throw new IllegalArgumentException("Sensor with id " + command.id() + " not found");
        var sensor = sensorOptional.get();
        sensor.Update(command);
        try {
            var updated = sensorRepository.save(sensor);
            return Optional.of(updated);
        }catch (Exception e) {
            throw new IllegalArgumentException("Error while updating sensor with id " + command.id(), e);
        }
    }

    @Override
    public void handle(DeleteSensorCommand command) {
        var id = command.id();
        var sensorOptional = sensorRepository.findById(id);
        if (sensorOptional.isEmpty())
            throw new IllegalArgumentException("Sensor with id " + id + " not found");

        try {
            sensorRepository.delete(sensorOptional.get());
        }catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting sensor with id " + id, e);
        }
    }
}
