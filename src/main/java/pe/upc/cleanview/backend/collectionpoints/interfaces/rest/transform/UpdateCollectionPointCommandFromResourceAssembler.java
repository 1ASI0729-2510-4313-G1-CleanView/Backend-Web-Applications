package pe.upc.cleanview.backend.collectionpoints.interfaces.rest.transform;


import pe.upc.cleanview.backend.collectionpoints.domain.model.commands.UpdateCollectionPointCommand;
import pe.upc.cleanview.backend.collectionpoints.interfaces.rest.resources.UpdateCollectionPointResource;

public class UpdateCollectionPointCommandFromResourceAssembler {
    public static UpdateCollectionPointCommand toCommandFromResource(Long id, UpdateCollectionPointResource resource) {
        return new UpdateCollectionPointCommand(
                id,
                resource.name(),
                resource.schedule(),
                resource.phone(),
                resource.materials(),
                resource.lat(),
                resource.lng()
        );
    }
}
