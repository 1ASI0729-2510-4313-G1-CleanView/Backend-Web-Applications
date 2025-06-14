package pe.upc.cleanview.backend.collectionpoints.interfaces.rest.transform;



import pe.upc.cleanview.backend.collectionpoints.domain.model.commands.CreateCollectionPointCommand;
import pe.upc.cleanview.backend.collectionpoints.interfaces.rest.resources.CreateCollectionPointResource;

public class CreateCollectionPointCommandFromResourceAssembler {
    public static CreateCollectionPointCommand toCommandFromResource(CreateCollectionPointResource resource) {
        return new CreateCollectionPointCommand(
                resource.name(),
                resource.schedule(),
                resource.phone(),
                resource.materials(),
                resource.lat(),
                resource.lng()
        );
    }
}
