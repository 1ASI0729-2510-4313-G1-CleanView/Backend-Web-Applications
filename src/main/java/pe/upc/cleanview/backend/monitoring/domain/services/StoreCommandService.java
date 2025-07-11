package pe.upc.cleanview.backend.monitoring.domain.services;

import pe.upc.cleanview.backend.monitoring.domain.model.commands.CreateStoreCommand;
import pe.upc.cleanview.backend.monitoring.domain.model.commands.DeleteStoreCommand;
import pe.upc.cleanview.backend.monitoring.domain.model.commands.UpdateStoreCommand;
import pe.upc.cleanview.backend.monitoring.domain.model.aggregates.Store;

import java.util.Optional;

public interface StoreCommandService {

    Optional<Store> handle(CreateStoreCommand command);

    Optional<Store> handle(UpdateStoreCommand command);

    void handle(DeleteStoreCommand command);

}
