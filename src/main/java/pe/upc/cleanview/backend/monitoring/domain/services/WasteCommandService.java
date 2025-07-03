package com.acme.center.platform.monitoring.domain.services;

import com.acme.center.platform.monitoring.domain.model.commands.CreateWasteCommand;
import com.acme.center.platform.monitoring.domain.model.commands.DeleteWasteCommand;
import com.acme.center.platform.monitoring.domain.model.entities.Waste;

import java.util.Optional;

public interface WasteCommandService {

    Optional<Waste> handle(CreateWasteCommand command);

    void handle(DeleteWasteCommand command);

}
