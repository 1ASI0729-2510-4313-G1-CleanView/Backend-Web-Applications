package pe.upc.cleanview.backend.collectionpoints.domain.services;

import pe.upc.cleanview.backend.collectionpoints.domain.model.aggregates.CollectionPoint;
import pe.upc.cleanview.backend.collectionpoints.domain.model.commands.CreateCollectionPointCommand;
import pe.upc.cleanview.backend.collectionpoints.domain.model.commands.UpdateCollectionPointCommand;
import pe.upc.cleanview.backend.collectionpoints.domain.model.commands.DeleteCollectionPointCommand;

import java.util.Optional;

public interface CollectionPointCommandService {
    Long handle(CreateCollectionPointCommand command);
    Optional<CollectionPoint> handle(UpdateCollectionPointCommand command);
    void handle(DeleteCollectionPointCommand command);
}