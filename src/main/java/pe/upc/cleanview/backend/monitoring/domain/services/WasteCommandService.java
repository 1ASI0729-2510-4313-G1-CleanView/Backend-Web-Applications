package pe.upc.cleanview.backend.monitoring.domain.services;


import pe.upc.cleanview.backend.monitoring.domain.model.commands.CreateWasteCommand;
import pe.upc.cleanview.backend.monitoring.domain.model.entities.Waste;

import java.util.Optional;

public interface WasteCommandService {

    Optional<Waste> handle(CreateWasteCommand command);

}
