package pe.upc.cleanview.backend.tips.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.upc.cleanview.backend.tips.domain.model.aggregates.Action;
import pe.upc.cleanview.backend.tips.domain.model.commands.CreateActionCommand;
import pe.upc.cleanview.backend.tips.domain.services.ActionCommandService;
import pe.upc.cleanview.backend.tips.infraestructura.persistence.jpa.repositories.ActionRepository;

import java.util.Optional;

@Service
public class ActionCommandServiceImpl implements ActionCommandService {

    private final ActionRepository actionRepository;

    ActionCommandServiceImpl(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    @Override
    public Optional<Action> handle(CreateActionCommand command){
        var action = new Action(command);
        actionRepository.save(action);
        return Optional.of(action);
    }
}
