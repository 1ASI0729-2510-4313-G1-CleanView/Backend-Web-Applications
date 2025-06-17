package pe.upc.cleanview.backend.monitoring.application.internal.commandservices;


import org.springframework.stereotype.Service;
import pe.upc.cleanview.backend.monitoring.domain.model.commands.CreateStoreCommand;
import pe.upc.cleanview.backend.monitoring.domain.model.commands.UpdateStoreCommand;
import pe.upc.cleanview.backend.monitoring.domain.model.entities.Store;
import pe.upc.cleanview.backend.monitoring.domain.services.StoreCommandService;
import pe.upc.cleanview.backend.monitoring.infraestructure.persistence.jpa.repositories.StoreRepository;

import java.util.Optional;

@Service
public class StoreCommandServiceImpl implements StoreCommandService {

    private final StoreRepository storeRepository;

    StoreCommandServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
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
        store.Update(command);
        try {
            var updated = storeRepository.save(store);
            return Optional.of(updated);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Error updating store", e);
        }
    }
}
