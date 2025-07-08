package pe.upc.cleanview.backend.monitoring.application.internal.commandservices;

import pe.upc.cleanview.backend.monitoring.domain.model.aggregates.Sensor;
import pe.upc.cleanview.backend.monitoring.domain.model.commands.CreateStoreCommand;
import pe.upc.cleanview.backend.monitoring.domain.model.commands.DeleteStoreCommand;
import pe.upc.cleanview.backend.monitoring.domain.model.commands.UpdateStoreCommand;
import pe.upc.cleanview.backend.monitoring.domain.model.aggregates.Store;
import pe.upc.cleanview.backend.monitoring.domain.services.StoreCommandService;
import pe.upc.cleanview.backend.monitoring.infraestructure.persistence.jpa.repositories.SensorRepository;
import pe.upc.cleanview.backend.monitoring.infraestructure.persistence.jpa.repositories.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoreCommandServiceImpl implements StoreCommandService {

    private final StoreRepository storeRepository;
    private final SensorRepository sensorRepository;

    StoreCommandServiceImpl(StoreRepository storeRepository, SensorRepository sensorRepository) {
        this.storeRepository = storeRepository;
        this.sensorRepository = sensorRepository;
    }

    @Override
    public Optional<Store> handle(CreateStoreCommand command) {
        if(storeRepository.findByName(command.name()).isPresent()) {
            throw new IllegalArgumentException("Store with name " + command.name() + " already exists");
        }
        Store store = new Store(command);
        storeRepository.save(store);
        return Optional.of(store);
    }

    @Override
    public Optional<Store> handle(UpdateStoreCommand command) {
        if (storeRepository.findById(command.id()).isEmpty())
            throw new IllegalArgumentException("Store with id " + command.id() + " does not exist");
        var store = storeRepository.findById(command.id()).get();

        System.out.println("Buscando sensores con IDs: " + command.sensorsId());

        List<Sensor> sensors = command.sensorsId().stream()
                        .map(sensorsId -> sensorRepository.findById(sensorsId)
                                .orElseThrow(() -> new RuntimeException("Sensor not found: " + sensorsId)))
                        .toList();

        System.out.println("Sensores encontrados: " + sensors.size());
        System.out.println("Sensores encontrados: " + sensors
                .stream().map(Sensor::getId).toList());

        store.Update(command);
        try {
            store.addSensorToStore(sensors);
            storeRepository.save(store);
            return Optional.of(store);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Error updating store", e);
        }
    }

    @Override
    public void handle(DeleteStoreCommand command) {
        var id = command.id();
        var store = storeRepository.findById(id);
        if (store.isEmpty())
            throw new IllegalArgumentException("Store with id " + id + " does not exist");

        try {
            storeRepository.deleteById(id);
        }catch (Exception e) {
            throw new IllegalArgumentException("Error deleting store", e);
        }
    }
}
