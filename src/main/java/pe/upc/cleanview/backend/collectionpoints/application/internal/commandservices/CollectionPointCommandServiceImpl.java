package pe.upc.cleanview.backend.collectionpoints.application.internal.commandservices;


import org.springframework.stereotype.Service;
import pe.upc.cleanview.backend.collectionpoints.domain.model.aggregates.CollectionPoint;
import pe.upc.cleanview.backend.collectionpoints.domain.model.commands.CreateCollectionPointCommand;
import pe.upc.cleanview.backend.collectionpoints.domain.model.commands.UpdateCollectionPointCommand;
import pe.upc.cleanview.backend.collectionpoints.domain.model.commands.DeleteCollectionPointCommand;
import pe.upc.cleanview.backend.collectionpoints.domain.services.CollectionPointCommandService;
import pe.upc.cleanview.backend.collectionpoints.infrastructure.persistence.jpa.repositories.CollectionPointRepository;

import java.util.Optional;

@Service
public class CollectionPointCommandServiceImpl implements CollectionPointCommandService {

    private final CollectionPointRepository collectionPointRepository;

    public CollectionPointCommandServiceImpl(CollectionPointRepository collectionPointRepository) {
        this.collectionPointRepository = collectionPointRepository;
    }

    @Override
    public Long handle(CreateCollectionPointCommand command) {
        var name = command.name();
        if (collectionPointRepository.existsByName(name)) {
            throw new IllegalArgumentException("Collection Point with name '" + name + "' already exists");
        }

        var collectionPoint = new CollectionPoint(command);
        try {
            collectionPointRepository.save(collectionPoint);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving Collection Point: " + e.getMessage());
        }

        return collectionPoint.getId();
    }

    @Override
    public Optional<CollectionPoint> handle(UpdateCollectionPointCommand command) {
        var id = command.id();
        var name = command.name();

        if (collectionPointRepository.existsByNameAndIdIsNot(name, id)) {
            throw new IllegalArgumentException("Collection Point with name '" + name + "' already exists");
        }

        var collectionPointOptional = collectionPointRepository.findById(id);
        if (collectionPointOptional.isEmpty()) {
            throw new IllegalArgumentException("Collection Point with id " + id + " does not exist");
        }

        var collectionPoint = collectionPointOptional.get();
        collectionPoint.updateInformation(
                command.name(),
                command.schedule(),
                command.phone(),
                command.materials(),
                command.lat(),
                command.lng()
        );

        try {
            var updated = collectionPointRepository.save(collectionPoint);
            return Optional.of(updated);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating Collection Point: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteCollectionPointCommand command) {
        var id = command.collectionPointId();
        if (!collectionPointRepository.existsById(id)) {
            throw new IllegalArgumentException("Collection Point with id " + id + " does not exist");
        }

        try {
            collectionPointRepository.deleteById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting Collection Point: " + e.getMessage());
        }
    }
}
