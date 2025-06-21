package pe.upc.cleanview.backend.tips.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.upc.cleanview.backend.tips.domain.model.aggregates.SustainableAction;
import pe.upc.cleanview.backend.tips.domain.model.commands.CreateSustainableActionCommand;
import pe.upc.cleanview.backend.tips.domain.model.commands.DeleteSustainableActionCommand;
import pe.upc.cleanview.backend.tips.domain.model.valueobjects.SustainableActionType;
import pe.upc.cleanview.backend.tips.domain.services.SustainableActionCommandService;
import pe.upc.cleanview.backend.tips.infraestructura.persistence.jpa.repositories.SustainableActionRepository;

import java.util.Optional;

@Service
public class SustainableSustainableActionCommandServiceImpl implements SustainableActionCommandService {

    private final SustainableActionRepository actionRepository;

    SustainableSustainableActionCommandServiceImpl(SustainableActionRepository sustainableActionRepository) {
        this.actionRepository = sustainableActionRepository;
    }

    @Override
    public Optional<SustainableAction> handle(CreateSustainableActionCommand command) {

        if (command.title() == null || command.title().isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (command.description() == null || command.description().isBlank()) {
            throw new IllegalArgumentException("Description is required");
        }
        if (command.type() == null || command.type().isBlank()) {
            throw new IllegalArgumentException("Type is required");
        }

        SustainableActionType actionType;
        try {
            actionType = SustainableActionType.fromString(command.type());
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException(
                    "Invalid sustainable action type: " + command.type() + ". Valid types: STORAGE, OPERATIONAL, REGULATION"
            );
        }

        var action = new SustainableAction(
                new CreateSustainableActionCommand(
                        command.title(),
                        command.description(),
                        actionType.name(),
                        command.favorite()
                )
        );

        actionRepository.save(action);
        return Optional.of(action);
    }

    @Override
    public void handle(DeleteSustainableActionCommand command) {
        actionRepository.deleteById(command.id());
    }
}
