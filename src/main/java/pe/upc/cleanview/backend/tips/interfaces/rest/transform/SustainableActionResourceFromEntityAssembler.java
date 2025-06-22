package pe.upc.cleanview.backend.tips.interfaces.rest.transform;

import pe.upc.cleanview.backend.tips.domain.model.aggregates.SustainableAction;
import pe.upc.cleanview.backend.tips.interfaces.rest.resources.SustainableActionResource;

public class SustainableActionResourceFromEntityAssembler {
    public static SustainableActionResource toResourceFromEntity(SustainableAction entity){
        return new SustainableActionResource(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getSustainableActionType().name(),
                entity.isFavorite()
        );
    }
}
