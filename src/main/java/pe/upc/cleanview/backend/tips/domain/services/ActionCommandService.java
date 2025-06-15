package pe.upc.cleanview.backend.tips.domain.services;

import pe.upc.cleanview.backend.tips.domain.model.aggregates.Action;
import pe.upc.cleanview.backend.tips.domain.model.commands.CreateActionCommand;
import pe.upc.cleanview.backend.tips.domain.model.commands.DeleteActionCommand;

import java.util.Optional;

public interface ActionCommandService {
    Optional<Action> handle(CreateActionCommand command);

}
