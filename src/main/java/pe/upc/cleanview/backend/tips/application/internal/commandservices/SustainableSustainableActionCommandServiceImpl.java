package pe.upc.cleanview.backend.tips.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.upc.cleanview.backend.iam.interfaces.acl.IamContextFacade; // Asegúrate de importar esto si lo necesitas para otras partes del servicio, como la eliminación.
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
    private final IamContextFacade iamContextFacade; // Asegúrate de que esté inyectado si lo usas en el método de eliminar.

    // Constructor actualizado para inyectar IamContextFacade
    SustainableSustainableActionCommandServiceImpl(SustainableActionRepository sustainableActionRepository, IamContextFacade iamContextFacade) {
        this.actionRepository = sustainableActionRepository;
        this.iamContextFacade = iamContextFacade;
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


        if (command.creatorUserId() == null) {
            throw new IllegalArgumentException("Creator User ID is required to create a sustainable action.");
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
                command.title(),
                command.description(),
                actionType.name(),
                command.creatorUserId() // ¡Aquí se pasa el ID del creador!
        );

        actionRepository.save(action);
        return Optional.of(action);
    }

    @Override
    public void handle(DeleteSustainableActionCommand command) {
        Optional<SustainableAction> actionOptional = actionRepository.findById(command.id());
        if (actionOptional.isEmpty()) {
            throw new IllegalArgumentException("Sustainable action with ID " + command.id() + " not found.");
        }

        SustainableAction actionToDelete = actionOptional.get();

        Long authenticatedUserId = iamContextFacade.getCurrentUserId(); // Obtiene el ID del usuario logueado

        if (authenticatedUserId == null) {
            throw new IllegalStateException("User is not authenticated. Cannot delete action.");
        }

        // Verifica si el usuario autenticado es el creador de la acción
        if (!actionToDelete.getCreatorUserId().equals(authenticatedUserId)) {
            throw new IllegalStateException("User is not authorized to delete this sustainable action. Only the creator can delete it.");
        }

        actionRepository.deleteById(command.id());
    }
}
