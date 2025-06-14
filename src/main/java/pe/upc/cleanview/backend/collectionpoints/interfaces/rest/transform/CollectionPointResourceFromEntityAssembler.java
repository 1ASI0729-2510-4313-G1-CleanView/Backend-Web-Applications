package pe.upc.cleanview.backend.collectionpoints.interfaces.rest.transform;



import pe.upc.cleanview.backend.collectionpoints.domain.model.aggregates.CollectionPoint;
import pe.upc.cleanview.backend.collectionpoints.interfaces.rest.resources.CollectionPointResource;

public class CollectionPointResourceFromEntityAssembler {
    public static CollectionPointResource toResourceFromEntity(CollectionPoint entity) {
        return new CollectionPointResource(
                entity.getId(),
                entity.getName(),
                entity.getSchedule(),
                entity.getPhone(),
                entity.getMaterials(),
                entity.getLat(),
                entity.getLng()
        );
    }
}
