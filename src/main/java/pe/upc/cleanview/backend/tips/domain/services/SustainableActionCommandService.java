package pe.upc.cleanview.backend.tips.domain.services;

import pe.upc.cleanview.backend.tips.domain.model.aggregates.SustainableAction;
import pe.upc.cleanview.backend.tips.domain.model.commands.CreateSustainableActionCommand;
import pe.upc.cleanview.backend.tips.domain.model.commands.DeleteSustainableActionCommand;

import java.util.Optional;

public interface SustainableActionCommandService {
    Optional<SustainableAction> handle(CreateSustainableActionCommand command);

    void handle(DeleteSustainableActionCommand command);
}
