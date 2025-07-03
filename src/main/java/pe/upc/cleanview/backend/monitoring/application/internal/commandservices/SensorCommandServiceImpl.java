package com.acme.center.platform.monitoring.application.internal.commandservices;

import com.acme.center.platform.monitoring.domain.model.commands.CreateSensorCommand;
import com.acme.center.platform.monitoring.domain.model.commands.DeleteSensorCommand;
import com.acme.center.platform.monitoring.domain.model.commands.UpdateSensorCommand;
import com.acme.center.platform.monitoring.domain.model.aggregates.Sensor;
import com.acme.center.platform.monitoring.domain.model.entities.Waste;
import com.acme.center.platform.monitoring.domain.services.SensorCommandService;
import com.acme.center.platform.monitoring.infraestructure.persistence.jpa.repositories.SensorRepository;
import com.acme.center.platform.monitoring.infraestructure.persistence.jpa.repositories.WasteRespository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SensorCommandServiceImpl implements SensorCommandService {

    private final SensorRepository sensorRepository;
    private final WasteRespository wasteRespository;

    SensorCommandServiceImpl(SensorRepository sensorRepository,
                             WasteRespository wasteRespository) {
        this.sensorRepository = sensorRepository;
        this.wasteRespository = wasteRespository;
    }

    @Override
    public Optional<Sensor> handle(CreateSensorCommand command) {
        var sensor = new Sensor(command);
        List<Waste> wastes = command.wastesId().stream()
                .map(wastesId -> wasteRespository.findById(wastesId)
                        .orElseThrow(() -> new RuntimeException("Waste not found: " + wastesId)))
                .collect(Collectors.toList());
        System.out.println(" -- [[  Waste found: " + wastes.stream().map(Waste::getId).toList());
        sensor.addWastesToSensor(wastes);
        sensorRepository.save(sensor);
        return Optional.of(sensor);
    }

    @Override
    public Optional<Sensor> handle(UpdateSensorCommand command) {
        var sensorOptional = sensorRepository.findById(command.id());
        if (sensorOptional.isEmpty())
            throw new IllegalArgumentException("Sensor with id " + command.id() + " not found");
        var sensor = sensorOptional.get();

        List<Waste> wastes = command.wastesId().stream()
                .map(wastesId -> wasteRespository.findById(wastesId)
                        .orElseThrow(() -> new RuntimeException("Waste not found: " + wastesId)))
                .toList();

        sensor.Update(command);
        try {
            sensorRepository.save(sensor);
            sensor.addWastesToSensor(wastes);
            return Optional.of(sensor);
        }
        catch (Exception e) {
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
