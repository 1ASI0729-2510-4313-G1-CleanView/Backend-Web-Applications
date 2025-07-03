package com.acme.center.platform.monitoring.domain.services;

import com.acme.center.platform.monitoring.domain.model.commands.CreateStoreCommand;
import com.acme.center.platform.monitoring.domain.model.commands.DeleteStoreCommand;
import com.acme.center.platform.monitoring.domain.model.commands.UpdateStoreCommand;
import com.acme.center.platform.monitoring.domain.model.aggregates.Store;

import java.util.Optional;

public interface StoreCommandService {

    Optional<Store> handle(CreateStoreCommand command);

    Optional<Store> handle(UpdateStoreCommand command);

    void handle(DeleteStoreCommand command);

}
